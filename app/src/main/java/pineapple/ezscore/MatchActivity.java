package pineapple.ezscore;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Entities.Match;
import Entities.Round;
import Repositories.MatchRepository;

public class MatchActivity extends AppCompatActivity {

    private TextView txtTime;
    private TextView txtTeam1;
    private TextView txtTeam2;
    private TextView txtTeam1Score;
    private TextView txtTeam2Score;
    private ListView listRounds;

    private String matchKey;
    private Match match;
    private MatchRepository matchRepository;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        matchKey = (String) getIntent().getSerializableExtra("MatchKey");
        matchRepository = new MatchRepository();

        txtTime = (TextView) findViewById(R.id.txtTime);
        txtTeam1 = (TextView) findViewById(R.id.txtTeam1);
        txtTeam2 = (TextView) findViewById(R.id.txtTeam2);
        txtTeam1Score = (TextView) findViewById(R.id.txtTeam1Score);
        txtTeam2Score = (TextView) findViewById(R.id.txtTeam2Score);
        listRounds = (ListView) findViewById(R.id.listRounds);

        context = this;

        setDatabaseReference();
    }

    private void setDatabaseReference() {
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("matches");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                match = matchRepository.getMatch(matchKey);
                txtTime.setText(getTime());
                txtTeam1.setText(match.getTeam1());
                txtTeam2.setText(match.getTeam2());
                txtTeam1Score.setText(match.getTeam1Score().toString());
                txtTeam2Score.setText(match.getTeam2Score().toString());

                ArrayAdapter<Round> arrayAdapter = new ArrayAdapter<>(context, R.layout.custom_list_layout, match.getRounds());
                listRounds.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String getTime() {
        //TODO
        //2017-04-26T20:45
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        Date date;
        Date dateNow = new Date();
        try {
            date = parser.parse(match.getStartTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return "Error converting time";
        }

        if (match.getisFinished()) {
            return "Finished";
        }

        if (date.after(dateNow)) {
            return "Starting later";
        } else if (date.before(dateNow)) {
            return "Live!";
        }

        return "Error calculating time";
    }
}
