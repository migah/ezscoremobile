package Gateways;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Entities.Match;

/**
 * Created by rasmusmadsen on 06/04/2017.
 */


public class MatchGateway {

    private DatabaseReference dr;
    private ArrayList<Match> matches;

    public MatchGateway() {
        dr = FirebaseDatabase.getInstance().getReference("matches");
        matches = new ArrayList<>();
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                matches.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Match match = ds.getValue(Match.class);
                    match.set$key(ds.getKey());

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

}
