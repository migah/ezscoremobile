package Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Entities.Match;

/**
 * Created by rasmusmadsen on 15/05/2017.
 */

public class DateFormatter {

    private static SimpleDateFormat formattedDateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
    private static SimpleDateFormat prettyDateFormatter = new SimpleDateFormat("EEEE dd/MM HH:mm");

    private static final String LIVE = "Live!";
    private static final String FINISHED = "Finished";

    /**
     * Formats the date.
     * @param match the match the date belongs to
     * @return a string with the correct date
     */
    public static String getDate(Match match) {
        String returnString = "";
        Date date = null;
        try {
            date = formattedDateFormatter.parse(match.getStartTime());
            returnString = prettyDateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Error parsing date";
        }
        return getLive(match, returnString, date);
    }

    /**
     * Check if the match is live or not
     * @param match the match the date belongs to
     * @param returnString the string to return, if match is upcoming
     * @param date the date of the match
     * @return a string with match state on live or finished.
     */
    private static String getLive(Match match, String returnString, Date date) {
        Date dateNow = new Date();

        if (match.getisFinished()) {
            return LIVE;
        }
        if (date.before(dateNow)) {
            return FINISHED;
        }

        return returnString;
    }

    /**
     * Check is the match is live, done or upcoming
     * @param match the match, which states gets returned
     * @return a string with live, done or upcoming
     */
    public static String getState(Match match) {
        switch (getDate(match)) {
            case LIVE:
                return MatchStates.LIVE;
            case FINISHED:
                return MatchStates.RESULTS;
            default:
                return MatchStates.STARTING_LATER;
        }
    }
}
