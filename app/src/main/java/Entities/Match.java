package Entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rasmusmadsen on 06/04/2017.
 */

public class Match implements Serializable{
    private String $key;
    private String creatorId;
    private boolean isFinished;
    private Sport sport;
    private List<Round> rounds;
    private String team1;
    private String team2;
    private String startTime;
    private Long team1Score;
    private Long team2Score;

    public Match() {
    }

    public String get$key() {
        return $key;
    }

    public void set$key(String $key) {
        this.$key = $key;
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


}
