package Repositories;

import java.util.List;

import Entities.Match;
import Gateways.MatchGateway;

/**
 * Created by rasmusmadsen on 06/04/2017.
 */

public class MatchRepository {
    private final MatchGateway mg;

    /**
     * Constructor with reference to MatchGateway
     */
    public MatchRepository() {
        mg = new MatchGateway();
    }

    /**
     * Returns a list of matches from the MatchGateway
     * @return list of matches
     */
    public List<Match> getMatches() {
        return mg.getMatches();
    }

    /**
     * Gets one match from MatchGateway
     * @param $key of the match to find
     * @return one match from database
     */
    public Match getMatch(String $key) {
        for (Match match: mg.getMatches()) {
            if (match.getId().equals($key))
                return match;
        }
        return null;
    }

    /**
     * Adds a match to the database through the MatchGateway
     * @param match to be added
     */
    public void addMatch(Match match) {
        match.getSport().setId(null);
        mg.addMatch(match);
    }

    /**
     * Updates a match in the datbase through MatchGateway
     * @param match to be updated
     */
    public void updateMatch(Match match) {
        String matchId = match.getId();
        match.setId(null);
        match.getSport().setId(null);
        mg.updateMatch(match, matchId);
    }

    /**
     * Remove one match from the databse through MatchGateway
     * @param matchId id of match to be removed
     */
    public void removeMatch(String matchId) {
        mg.removeMatch(matchId);
    }
}
