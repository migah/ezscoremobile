package pineapple.ezscore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Entities.Match;
import Repositories.MatchRepository;

public class MatchActivity extends AppCompatActivity {

    TextView txtView;
    private String matchKey;
    private Match match;
    private MatchRepository matchRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        matchKey = (String) getIntent().getSerializableExtra("MatchKey");
        matchRepository = new MatchRepository();


        txtView = (TextView) findViewById(R.id.txtView);
        txtView.setText(matchKey);

        setDatabaseReference();
    }

    private void setDatabaseReference() {
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("matches");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                match = matchRepository.getMatch(matchKey);
                txtView.setText(matchRepository.getMatch(matchKey).getTeam1());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
