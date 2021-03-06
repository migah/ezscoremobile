package pineapple.ezscore;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Entities.Match;
import Entities.Round;
import Repositories.MatchRepository;
import Utilities.DateFormatter;
import Utilities.ListDrawer;
import Utilities.ToolbarInitializer;

public class MatchEditActivity extends AppCompatActivity {

    private DrawerLayout layout;
    private Toolbar toolbar;
    private TextView txtTime;
    private TextView txtTeam1;
    private TextView txtTeam2;
    private EditText inputTeam1Score;
    private EditText inputTeam2Score;
    private Button btnUpdateMatch;
    private Button btnAddRound;
    private ListView listRounds;
    private ListView drawerList;

    private RelativeLayout roundLayout;
    private Button btnUpdateRound;
    private Button btnRoundDone;
    private Button btnMatchDone;
    private TextView txtRoundTeam1;
    private TextView txtRoundTeam2;
    private EditText inputRoundTeam1Score;
    private EditText inputRoundTeam2Score;

    private String matchKey;
    private Match match;
    private Round round;
    private MatchRepository matchRepository;
    private DatabaseReference databaseReference;

    private long roundNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_edit);

        initVariables();
        fillInputs();
        initButtons();
        drawerList = ListDrawer.initList(this, this, drawerList);
        toolbar = ToolbarInitializer.initToolbar(this, toolbar, layout);
    }

    /**
     * Initializes the variables
     */
    private void initVariables() {
        layout = (DrawerLayout) findViewById(R.id.matchEditLayout);
        toolbar = (Toolbar) findViewById(R.id.matchToolbar);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtTeam1 = (TextView) findViewById(R.id.inputTeam1);
        txtTeam2 = (TextView) findViewById(R.id.inputTeam2);
        inputTeam1Score = (EditText) findViewById(R.id.inputTeam1Score);
        inputTeam2Score = (EditText) findViewById(R.id.inputTeam2Score);
        btnUpdateMatch = (Button) findViewById(R.id.btnUpdateMatch);
        btnMatchDone = (Button) findViewById(R.id.btnMatchEnd);
        btnAddRound = (Button) findViewById(R.id.btnAddRound);
        listRounds = (ListView) findViewById(R.id.listRounds);
        roundLayout = (RelativeLayout) findViewById(R.id.roundLayout);
        btnUpdateRound = (Button) findViewById(R.id.btnUpdateRound);
        btnRoundDone = (Button) findViewById(R.id.btnRoundDone);
        txtRoundTeam1 = (TextView) findViewById(R.id.inputRoundTeam1);
        txtRoundTeam2 = (TextView) findViewById(R.id.inputRoundTeam2);
        inputRoundTeam1Score = (EditText) findViewById(R.id.inputRoundTeam1Score);
        inputRoundTeam2Score = (EditText) findViewById(R.id.inputRoundTeam2Score);
        drawerList = (ListView) findViewById(R.id.matchEditDrawer);
        roundNo = 0;

        matchRepository = new MatchRepository();
        matchKey = (String) getIntent().getSerializableExtra("MatchKey");
        databaseReference = FirebaseDatabase.getInstance().getReference("matches");
    }

    /**
     * Fills inputs and labels with match data
     */
    private void fillInputs() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                match = matchRepository.getMatch(matchKey);
                txtTime.setText(DateFormatter.getDate(match));
                txtTeam1.setText(match.getTeam1());
                txtTeam2.setText(match.getTeam2());
                inputTeam1Score.setText(match.getTeam1Score().toString());
                inputTeam2Score.setText(match.getTeam2Score().toString());
                txtRoundTeam1.setText(match.getTeam1());
                txtRoundTeam2.setText(match.getTeam2());

                listRounds.setAdapter(new ArrayAdapter<Round>(MatchEditActivity.this, android.R.layout.simple_list_item_1, match.getRounds()));
                if (match.getisFinished()) {
                    btnMatchDone.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Initializes listeners for the buttons
     */
    private void initButtons() {
        btnUpdateMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMatch();
            }
        });
        btnAddRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAddRound.setVisibility(View.GONE);
                roundLayout.setVisibility(View.VISIBLE);
                long i = 0;
                for (Round round : match.getRounds()) {
                    if (round.getRoundNo() > i) {
                        i = round.getRoundNo();
                    }
                }
                i++;
                round = new Round(i);
                roundNo = round.getRoundNo();
                match.getRounds().add(round);
                inputRoundTeam1Score.setText(0 + "");
                inputRoundTeam2Score.setText(0 + "");
                updateRound((int)roundNo);
            }
        });
        btnUpdateRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateRound((int) roundNo);
            }
        });
        btnRoundDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAddRound.setVisibility(View.VISIBLE);
                roundLayout.setVisibility(View.GONE);
                updateRound((int) roundNo);
            }
        });
        btnMatchDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MatchEditActivity.this)
                        .setTitle("Are you sure you want to end the match?")
                        .setMessage("This cannot be undone")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                match.setisFinished(true);
                                updateMatch();
                                startActivity(new Intent(MatchEditActivity.this, MyMatchesActivity.class));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .show();
            }
        });
        listRounds.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Round round = match.getRounds().get(i);
                btnAddRound.setVisibility(View.GONE);
                roundLayout.setVisibility(View.VISIBLE);
                inputRoundTeam1Score.setText(round.getTeam1score() + "");
                inputRoundTeam2Score.setText(round.getTeam2score() + "");
                roundNo = round.getRoundNo();
            }
        });
    }

    /**
     * Updates the match
     */
    private void updateMatch() {
        match.setTeam1Score(Long.parseLong(inputTeam1Score.getText().toString()));
        match.setTeam2Score(Long.parseLong(inputTeam2Score.getText().toString()));
        matchRepository.updateMatch(match);
        Snackbar.make(layout, "Match updated", 2000).show();
    }

    /**
     * Updates the round
     * @param roundNo the number of the round
     */
    private void updateRound(int roundNo) {
        match.getRounds().get(roundNo - 1).setTeam1score(Long.parseLong(inputRoundTeam1Score.getText().toString()));
        match.getRounds().get(roundNo - 1).setTeam2score(Long.parseLong(inputRoundTeam2Score.getText().toString()));
        updateMatch();
    }

}
