package pineapple.ezscore;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Adapters.MatchAdapter;
import Repositories.MatchRepository;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private MatchRepository mr;
    private DatabaseReference dr;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ListView mDrawerList;
    private DrawerLayout drawerLayout;
    private ArrayAdapter<String> mAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        initAuthListener();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        mr = new MatchRepository();

        final MatchAdapter adbMatch;

        adbMatch = new MatchAdapter (this, mr.getMatches());
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

        toolbar.inflateMenu(R.menu.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_menu_white_48dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void initAuthListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    System.out.println("IN");
                    String[] mMenuItems = {"All Matches", "My Matches", "Logout"};
                    addDrawerItems(mMenuItems);
                } else {
                    // No user signed in
                    System.out.println("OUT");
                    String[] mMenuItems = {"All Matches", "Login"};
                    addDrawerItems(mMenuItems);
                }
            }
        };
    }

    private void addDrawerItems(String[] mMenuItems) {
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mMenuItems);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (adapterView.getItemAtPosition(i).toString().toLowerCase()) {
                    case "all matches":
                        drawerLayout.closeDrawers();
                        break;
                    case "my matches":
                        System.out.println("Works!");
                        break;
                    case "logout":
                        mAuth.signOut();
                        break;
                    case "login":
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
