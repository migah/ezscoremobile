package pineapple.ezscore;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Adapters.MatchAdapter;
import Entities.Match;
import Repositories.MatchRepository;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private MatchRepository mr;
    private DatabaseReference dr;

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        addDrawerItems();

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        mr = new MatchRepository();

        final MatchAdapter adbMatch;

        adbMatch = new MatchAdapter (mr.getMatches());
        rv.setAdapter(adbMatch);

        dr = FirebaseDatabase.getInstance().getReference("matches");
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rv.setAdapter(adbMatch);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addDrawerItems() {
        String[] mMenuItems = {"All Matches", "My Matches", "Logut"};
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mMenuItems);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        //All Matches
                        break;
                    case 1:
                        //My Matches
                        break;
                    case 2:
                        //Logout
                        break;
                }
            }
        });
    }

}
