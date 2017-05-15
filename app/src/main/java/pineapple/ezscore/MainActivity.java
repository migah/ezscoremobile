package pineapple.ezscore;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Utilities.DrawerListStuff;
import Utilities.MatchAdapter;
import Repositories.MatchRepository;
import Utilities.ToolbarInitializer;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MatchRepository matchRepository;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    private ListView mDrawerList;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        if (getIntent().getSerializableExtra("logout") != null && (boolean) getIntent().getSerializableExtra("logout")) {
            mAuth.signOut();
        }

        initVariables();
        initDatabaseReference();
    }

    private void initVariables() {
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList = DrawerListStuff.initList(this, mDrawerList);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar = ToolbarInitializer.initToolbar(this, toolbar, drawerLayout);

        databaseReference = FirebaseDatabase.getInstance().getReference("matches");

        matchRepository = new MatchRepository();
    }

    private void initDatabaseReference() {
        final MatchAdapter adbMatch = new MatchAdapter (this, matchRepository.getMatches());
        recyclerView.setAdapter(adbMatch);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recyclerView.setAdapter(adbMatch);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
