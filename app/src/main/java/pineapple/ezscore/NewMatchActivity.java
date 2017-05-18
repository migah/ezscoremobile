package pineapple.ezscore;

import android.*;
import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import Entities.Match;
import Entities.Sport;
import Repositories.MatchRepository;
import Repositories.SportRepository;
import Utilities.DrawerListStuff;
import Utilities.GPSManager;
import Utilities.ToolbarInitializer;

public class NewMatchActivity extends AppCompatActivity {

    DrawerLayout layout;
    Toolbar toolbar;
    Spinner spinner;
    Calendar myCalendar;
    TextView txtDate;
    Context context;
    TextView txtTime;
    Button btnSubmit;
    Button btnLocation;
    EditText input_team1;
    EditText input_team2;

    ListView drawerList;

    SportRepository sportRepository;
    MatchRepository matchRepository;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    private int matchHour;
    private int matchMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_match);

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

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

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

        context = this;

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSManager gpsManager = new GPSManager(context);
                System.out.println(gpsManager.getDeviceLocation());
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

                Match match = new Match(creatorId,sport,team1, team2, startTime);

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
                        txtTime.setText( selectedHour + ":" + selectedMinute);
                        matchHour = selectedHour;
                        matchMinute = selectedMinute;
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        drawerList = DrawerListStuff.initList(this,this, drawerList);
        toolbar = ToolbarInitializer.initToolbar(this, toolbar, layout);
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        txtDate.setText(sdf.format(myCalendar.getTime()));
    }
}
