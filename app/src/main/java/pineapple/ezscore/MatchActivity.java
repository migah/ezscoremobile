package pineapple.ezscore;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Entities.Match;
import Entities.Round;
import Repositories.MatchRepository;
import Utilities.DateFormatter;
import Utilities.ListDrawer;
import Utilities.ToolbarInitializer;

public class MatchActivity extends AppCompatActivity {

    private DrawerLayout matchLayout;
    private Toolbar toolbar;
    private TextView txtTime;
    private TextView txtTeam1;
    private TextView txtTeam2;
    private TextView txtTeam1Score;
    private TextView txtTeam2Score;
    private ListView listRounds;
    private ListView drawerList;
    private Button btnLocation;

    private String matchKey;
    private Match match;
    private MatchRepository matchRepository;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        initVariables();
        setDatabaseReference();
        initListeners();
    }

    /**
     * Sets the matches database reference
     */
    private void setDatabaseReference() {
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("matches");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                match = matchRepository.getMatch(matchKey);
                txtTime.setText(DateFormatter.getDate(match));
                txtTeam1.setText(match.getTeam1());
                txtTeam2.setText(match.getTeam2());
                txtTeam1Score.setText(match.getTeam1Score().toString());
                txtTeam2Score.setText(match.getTeam2Score().toString());

                ArrayAdapter<Round> arrayAdapter = new ArrayAdapter<>(context, R.layout.custom_list_layout, match.getRounds());
                listRounds.setAdapter(arrayAdapter);

                if (match.getLocation() == null){
                    btnLocation.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Initializes listeners
     */
    private void initListeners() {
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float latitude = Float.parseFloat(match.getLocation().getLatitude());
                Float longitude = Float.parseFloat(match.getLocation().getLongitude());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<" + latitude + ">,<" + longitude + " >?q=<" + latitude + ">,<" + longitude + ">(Match+Location)"));
                context.startActivity(intent);
            }
        });
    }

    /**
     * Initializes the variables
     */
    private void initVariables() {
        context = this;

        matchKey = (String) getIntent().getSerializableExtra("MatchKey");
        matchRepository = new MatchRepository();
        matchLayout = (DrawerLayout) findViewById(R.id.matchLayout);

        toolbar = (Toolbar) findViewById(R.id.matchToolbar);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtTeam1 = (TextView) findViewById(R.id.txtTeam1);
        txtTeam2 = (TextView) findViewById(R.id.txtTeam2);
        txtTeam1Score = (TextView) findViewById(R.id.txtTeam1Score);
        txtTeam2Score = (TextView) findViewById(R.id.txtTeam2Score);
        listRounds = (ListView) findViewById(R.id.listRounds);
        drawerList = (ListView) findViewById(R.id.matchViewDrawer);
        btnLocation = (Button) findViewById(R.id.btnLocation);

        toolbar = ToolbarInitializer.initToolbar(this, toolbar, matchLayout);
        drawerList = ListDrawer.initList(this, this, drawerList);
    }


}
