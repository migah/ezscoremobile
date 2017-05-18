package Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rasmusmadsen on 06/04/2017.
 */

public class Match implements Serializable{
    private String id;
    private String creatorId;
    private boolean isFinished;
    private Sport sport;
    private List<Round> rounds;
    private String team1;
    private String team2;
    private String startTime;
    private Long team1Score;
    private Long team2Score;
    private MatchLocation location;

    public Match() {

    }

    public Match(String creatorId, Sport sport, String team1, String team2, String startTime, MatchLocation location) {
        this.creatorId = creatorId;
        this.sport = sport;
        this.team1 = team1;
        this.team2 = team2;
        this.startTime = startTime;
        this.location = location;

        Round round = new Round();
        round.setRoundNo(1);
        round.setTeam1score(0);
        round.setTeam2score(0);

        isFinished = false;
        rounds = new ArrayList<>();
        rounds.add(round);
        team1Score = Long.parseLong("0");
        team2Score = Long.parseLong("0");
    }

    public Match(String creatorId, Sport sport, String team1, String team2, String startTime) {
        this.creatorId = creatorId;
        this.sport = sport;
        this.team1 = team1;
        this.team2 = team2;
        this.startTime = startTime;
        this.location = null;

        Round round = new Round();
        round.setRoundNo(1);
        round.setTeam1score(0);
        round.setTeam2score(0);

        isFinished = false;
        rounds = new ArrayList<>();
        rounds.add(round);
        team1Score = Long.parseLong("0");
        team2Score = Long.parseLong("0");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Long getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(Long team1Score) {
        this.team1Score = team1Score;
    }

    public Long getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(Long team2Score) {
        this.team2Score = team2Score;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public boolean getisFinished() {
        return isFinished;
    }

    public void setisFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public MatchLocation getLocation() {
        return location;
    }

    public void setLocation(MatchLocation location) {
        this.location = location;
    }
}
