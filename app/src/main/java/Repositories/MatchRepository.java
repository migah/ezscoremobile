package Repositories;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import Entities.Match;
import Gateways.MatchGateway;

/**
 * Created by rasmusmadsen on 06/04/2017.
 */

public class MatchRepository {
    private final MatchGateway mg;

    public MatchRepository() {
        mg = new MatchGateway();
    }

    public List<Match> getMatches() {
        return mg.getMatches();
    }

    public Match getMatch(String $key) {
        for (Match match: mg.getMatches()) {
            if (match.get$key().equals($key))
                return match;
        }
        return null;
    }

    public void addMatch(Match match) {
        mg.addMatch(match);
    }
}
