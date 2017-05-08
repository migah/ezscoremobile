package Entities;

import java.io.Serializable;

/**
 * Created by rasmusmadsen on 03/05/2017.
 */

public class Round implements Serializable {
    private String $key;
    private long roundNo;
    private long team1score;
    private long team2score;

    public String get$key() {
        return $key;
    }

    public void set$key(String $key) {
        this.$key = $key;
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
}
