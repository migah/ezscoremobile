package Gateways;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Entities.Sport;

/**
 * Created by michaelfranch on 08/05/2017.
 */

public class SportGateway {

    private DatabaseReference datebaseReference;
    private ArrayList<Sport> sports;

    /**
     * Constructor with database reference connection, adds all matches from "sport" table in Firebase
     * to "sports" ArrayList.
     */
    public SportGateway(){
        datebaseReference = FirebaseDatabase.getInstance().getReference("sport");
        sports = new ArrayList<>();
        datebaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sports.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Sport sport = ds.getValue(Sport.class);
                    sport.setId(ds.getKey());
                    sports.add(sport);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Returns an ArrayList of sports.
     * @return ArrayList of sport.
     */
    public ArrayList<Sport> getSports() {
        return sports;
    }

}
