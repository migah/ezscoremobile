package pineapple.ezscore;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapters.MatchAdapter;
import Entities.Match;
import Repositories.MatchRepository;

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
        initDrawer();
        initListeners();
        initToolbar();
    }

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

    private void initToolbar() {
        toolbar.setNavigationIcon(R.mipmap.ic_menu_white_48dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        for (Match match : matchRepository.getMatches()) {
            if (match.getCreatorId().equals(firebaseAuth.getCurrentUser().getUid())) {
                myMatches.add(match);
            }
        }
        recyclerView.setAdapter(new MatchAdapter(this, myMatches));
    }

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
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (adapterView.getItemAtPosition(i).toString().toLowerCase()) {
                    case "all matches":
                        startActivity(new Intent(MyMatchesActivity.this, MainActivity.class));
                        break;
                    case "my matches":
                        layout.closeDrawers();
                        break;
                    case "logout":
                        Intent intent = new Intent(MyMatchesActivity.this, MainActivity.class);
                        intent.putExtra("logout", true);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void initDrawer() {
        String[] menuItems = {"All Matches", "My Matches", "Logout"};
        drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuItems));
    }
}
