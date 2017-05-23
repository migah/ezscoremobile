package Entities;

import java.io.Serializable;

/**
 * Created by rasmusmadsen on 03/05/2017.
 */

public class Round implements Serializable {
    private String id;
    private long roundNo;
    private long team1score;
    private long team2score;

    /**
     * Empty constructor for round.
     */
    public Round() {

    }

    /**
     * Constructor for round.
     * @param roundNo
     */
    public Round(long roundNo) {
        this.roundNo = roundNo;
        team1score = 0;
        team2score = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getRoundNo() {
        return roundNo;
    }

    public void setRoundNo(long roundNo) {
        this.roundNo = roundNo;
    }

    public long getTeam1score() {
        return team1score;
    }

    public void setTeam1score(long team1score) {
        this.team1score = team1score;
    }

    public long getTeam2score() {
        return team2score;
    }

    public void setTeam2score(long team2score) {
        this.team2score = team2score;
    }

    @Override
    public String toString() {
        return "(" + getTeam1score() + " - " + getTeam2score() + ")";
    }
}
