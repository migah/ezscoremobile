package pineapple.ezscore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import Entities.Match;
import Gateways.MatchGateway;
import Repositories.MatchRepository;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private MatchRepository mr;
    private DatabaseReference dr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.list);

        mr = new MatchRepository();

        final ArrayAdapter<Match> adapter = new ArrayAdapter<Match>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mr.getMatches());

        dr = FirebaseDatabase.getInstance().getReference("matches");
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
