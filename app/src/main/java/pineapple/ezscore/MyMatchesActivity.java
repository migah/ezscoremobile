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

import java.util.ArrayList;

import Utilities.DrawerListStuff;
import Utilities.MatchAdapter;
import Entities.Match;
import Repositories.MatchRepository;
import Utilities.ToolbarInitializer;

public class MyMatchesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListView drawerList;
    private DrawerLayout layout;
    private Toolbar toolbar;
    private MatchRepository matchRepository;
    private FirebaseAuth firebaseAuth;
    private ArrayList<Match> myMatches;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_matches);

        initVariables();
        initRecyclerView();
        drawerList = DrawerListStuff.initList(this, this, drawerList);
        initListeners();
        toolbar = ToolbarInitializer.initToolbar(this, toolbar, layout);
        toolbar.inflateMenu(R.menu.add_match_toolbar);
    }

    /**
     * Initializes the variables
     */
    private void initVariables() {
        recyclerView = (RecyclerView) findViewById(R.id.myMatchList);
        drawerList = (ListView) findViewById(R.id.myMatchDrawer);
        layout = (DrawerLayout) findViewById(R.id.myMatchLayout);
        toolbar = (Toolbar) findViewById(R.id.myMatchToolbar);
        matchRepository = new MatchRepository();
        firebaseAuth = FirebaseAuth.getInstance();
        myMatches = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("matches");
    }

    /**
     * Initializes the recyclerView
     */
    private void initRecyclerView() {
        myMatches.clear();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        for (Match match : matchRepository.getMatches()) {
            if (match.getCreatorId().equals(firebaseAuth.getCurrentUser().getUid())) {
                myMatches.add(match);
            }
        }
        recyclerView.setAdapter(new MatchAdapter(this, myMatches));
    }

    /**
     * Initializes listeners
     */
    private void initListeners() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                initRecyclerView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
