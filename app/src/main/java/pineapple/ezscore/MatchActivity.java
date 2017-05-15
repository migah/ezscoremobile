package pineapple.ezscore;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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
import Utilities.DateFormatter;

public class MatchActivity extends AppCompatActivity {

    private DrawerLayout matchLayout;

    private Toolbar toolbar;

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

        matchLayout = (DrawerLayout) findViewById(R.id.matchLayout);
        toolbar = (Toolbar) findViewById(R.id.matchToolbar);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtTeam1 = (TextView) findViewById(R.id.txtTeam1);
        txtTeam2 = (TextView) findViewById(R.id.txtTeam2);
        txtTeam1Score = (TextView) findViewById(R.id.txtTeam1Score);
        txtTeam2Score = (TextView) findViewById(R.id.txtTeam2Score);
        listRounds = (ListView) findViewById(R.id.listRounds);

        context = this;

        setDatabaseReference();

        toolbar.inflateMenu(R.menu.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_menu_white_48dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matchLayout.openDrawer(GravityCompat.START);
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getTitle().toString().toLowerCase()) {
                    case "add match":
                        startActivity(new Intent(MatchActivity.this, NewMatchActivity.class));
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
    }

    private void setDatabaseReference() {
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("matches");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                match = matchRepository.getMatch(matchKey);
                txtTime.setText(DateFormatter.getDate(match));
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
}
