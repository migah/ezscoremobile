package pineapple.ezscore;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Entities.Match;
import Utilities.DateFormatter;
import Utilities.ListDrawer;
import Utilities.MatchAdapter;
import Repositories.MatchRepository;
import Utilities.MatchStates;
import Utilities.ToolbarInitializer;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MatchRepository matchRepository;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    private ListView mDrawerList;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    private String matchState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkSignOut();
        initVariables();
        initListeners();
    }

    /**
     * Checks if a logout boolean was sent with the intent and signs out if the boolean is true.
     */
    private void checkSignOut() {
        mAuth = FirebaseAuth.getInstance();
        if (getIntent().getSerializableExtra("logout") != null && (boolean) getIntent().getSerializableExtra("logout")) {
            mAuth.signOut();
        }
    }

    /**
     * Initializes the variables
     */
    private void initVariables() {
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList = ListDrawer.initList(this, this, mDrawerList);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar = ToolbarInitializer.initToolbar(this, toolbar, drawerLayout);
        toolbar.inflateMenu(R.menu.sort_toolbar);
        databaseReference = FirebaseDatabase.getInstance().getReference("matches");

        matchRepository = new MatchRepository();

        matchState = MatchStates.ALL;
    }

    /**
     * Initializes the databaseReference
     */
    private void initDatabaseReference() {
        ArrayList<Match> matches = new ArrayList<>();

        for (Match match : matchRepository.getMatches()) {
            if (matchState.equals(MatchStates.ALL)) {
                matches.add(match);
            } else if (matchState.equals(MatchStates.RESULTS) && DateFormatter.getState(match).equals(MatchStates.RESULTS)) {
                matches.add(match);
            } else if (matchState.equals(MatchStates.LIVE) && DateFormatter.getState(match).equals(MatchStates.LIVE)) {
                matches.add(match);
            } else if (matchState.equals(MatchStates.STARTING_LATER) && DateFormatter.getState(match).equals(MatchStates.STARTING_LATER)) {
                matches.add(match);
            }
        }

        final MatchAdapter adbMatch = new MatchAdapter (this, matches);
        recyclerView.setAdapter(adbMatch);
    }

    /**
     * Initializes listeners
     */
    private void initListeners() {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                matchState = item.getTitle().toString().toLowerCase();
                initDatabaseReference();
                return true;
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                initDatabaseReference();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
