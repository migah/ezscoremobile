package Entities;

/**
 * Created by rasmusmadsen on 18/05/2017.
 */

public class MatchLocation {
    private String latitude;
    private String longitude;

    public MatchLocation() {
    }

    public MatchLocation(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
