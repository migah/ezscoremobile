package Repositories;

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
            if (match.getId().equals($key))
                return match;
        }
        return null;
    }

    public void addMatch(Match match) {
        mg.addMatch(match);
    }

    public void updateMatch(Match match) {
        String matchId = match.getId();
        match.setId(null);
        mg.updateMatch(match, matchId);
    }

    public void removeMatch(String matchId) {
        mg.removeMatch(matchId);
    }
}
