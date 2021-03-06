package pineapple.ezscore;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Entities.Match;
import Entities.MatchLocation;
import Entities.Sport;
import Repositories.MatchRepository;
import Repositories.SportRepository;
import Utilities.ListDrawer;
import Utilities.GPSManager;
import Utilities.ToolbarInitializer;

public class NewMatchActivity extends AppCompatActivity {

    private DrawerLayout layout;
    private Toolbar toolbar;
    private Spinner spinner;
    private Calendar myCalendar;
    private TextView txtDate;
    private Context context;
    private TextView txtTime;
    private Button btnSubmit;
    private Button btnLocation;
    private EditText input_team1;
    private EditText input_team2;
    private TextView txtLocationAdded;

    private ListView drawerList;

    private SportRepository sportRepository;
    private MatchRepository matchRepository;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    private int matchHour;
    private int matchMinute;

    private MatchLocation location;
    private GPSManager gpsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_match);
        initVariables();
        checkPermissions();
        initSpinner();
        initListeners();
    }
    /**
     * Initializes the variables
     */
    private void initVariables() {
        sportRepository = new SportRepository();
        matchRepository = new MatchRepository();
        databaseReference = FirebaseDatabase.getInstance().getReference("sport");
        firebaseAuth = FirebaseAuth.getInstance();

        layout = (DrawerLayout) findViewById(R.id.newMatchLayout);
        toolbar = (Toolbar) findViewById(R.id.new_match_toolbar);
        spinner = (Spinner) findViewById(R.id.sports_spinner);
        myCalendar = Calendar.getInstance();
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtTime = (TextView) findViewById(R.id.txtTime);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        input_team1 = (EditText) findViewById(R.id.input_team1);
        input_team2 = (EditText) findViewById(R.id.input_team2);
        drawerList = (ListView) findViewById(R.id.newMatchDrawer);
        btnLocation = (Button) findViewById(R.id.btnLocation);
        txtLocationAdded = (TextView) findViewById(R.id.txtLocationAdded);

        location = null;
        context = this;

        drawerList = ListDrawer.initList(this,this, drawerList);
        toolbar = ToolbarInitializer.initToolbar(this, toolbar, layout);

        gpsManager = new GPSManager(context);
    }

    /**
     * Initializes listeners
     */
    private void initListeners() {
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String longitude = String.valueOf(gpsManager.getDeviceLocation().getLongitude());
                String latitude = String.valueOf(gpsManager.getDeviceLocation().getLatitude());
                location = new MatchLocation(latitude, longitude);
                btnLocation.setVisibility(View.GONE);
                txtLocationAdded.setText("Location added");
                txtLocationAdded.setVisibility(View.VISIBLE);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String creatorId = firebaseAuth.getCurrentUser().getUid();
                Sport sport = (Sport) spinner.getSelectedItem();
                String team1 = input_team1.getText().toString();
                String team2 = input_team2.getText().toString();

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                Calendar calendar = myCalendar;
                calendar.set(Calendar.HOUR_OF_DAY, matchHour);
                calendar.set(Calendar.MINUTE, matchMinute);

                String startTime = dateFormat.format(calendar.getTime());

                Match match;

                if (location == null) {
                    match = new Match(creatorId,sport,team1, team2, startTime);
                } else {
                    match = new Match(creatorId,sport,team1, team2, startTime, location);
                }

                matchRepository.addMatch(match);

                startActivity(new Intent(NewMatchActivity.this, MyMatchesActivity.class));

            }
        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedMinute < 10) {
                            txtTime.setText(selectedHour + ":" + selectedMinute + "0");
                        } else {
                            txtTime.setText(selectedHour + ":" + selectedMinute);
                        }
                        matchHour = selectedHour;
                        matchMinute = selectedMinute;
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

    /**
     * Initializes the spinner
     */
    private void initSpinner() {
        final ArrayAdapter<Sport> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, sportRepository.getSports());
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                spinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Checks for and requests ACCESS_FINE_LOCATION permission
     */
    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    /**
     * Updates the date label
     */
    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        txtDate.setText(sdf.format(myCalendar.getTime()));
    }
}
