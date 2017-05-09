package Gateways;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Entities.Match;

/**
 * Created by rasmusmadsen on 06/04/2017.
 */


public class MatchGateway {

    private DatabaseReference databaseReference;
    private ArrayList<Match> matches;

    public MatchGateway() {
        databaseReference = FirebaseDatabase.getInstance().getReference("matches");
        matches = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                matches.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Match match = ds.getValue(Match.class);
                    match.setId(ds.getKey());

                    matches.add(match);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public ArrayList<Match> getMatches() {
        return matches;
    }

    public void addMatch(Match match) {
        databaseReference.child(databaseReference.push().getKey()).setValue(match);
    }

    public void updateMatch(Match match, String matchId) {
        databaseReference.child(matchId).setValue(match);
    }

    public void removeMatch(String matchId) {
        databaseReference.child(matchId).removeValue();
    }
}
