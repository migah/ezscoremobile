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

    private static String getLive(Match match, String returnString, Date date) {
        Date dateNow = new Date();

        if (match.getisFinished()) {
            return "Finished";
        }
        if (date.before(dateNow)) {
            return "Live!";
        }

        return returnString;
    }
}
