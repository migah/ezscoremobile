package pineapple.ezscore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Entities.Match;
import Gateways.MatchGateway;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference dr;
    private static final String TAG = "MainActivity";
    private TextView txt1;
    private TextView txt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dr = FirebaseDatabase.getInstance().getReference("matches");

        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);

        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //txt1.setText(dataSnapshot.child("-KgnHNI2vLgUhm7kcFFH").getValue());
                //txt2.setText(dataSnapshot.toString());
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds: dataSnapshot.getChildren()) {
            Match match = new Match();
            match.setTeam1(ds.child("-KgnHNI2vLgUhm7kcFFH").getValue(Match.class).getTeam1());
            match.setTeam2(ds.child("-KgnHNI2vLgUhm7kcFFH").getValue(Match.class).getTeam2());

            Log.d(TAG, "showData: team1: " + match.getTeam1());
            Log.d(TAG, "showData: team2: " + match.getTeam2());

            txt1.setText(match.getTeam1());
            txt2.setText(match.getTeam2());
        }


    }
}
