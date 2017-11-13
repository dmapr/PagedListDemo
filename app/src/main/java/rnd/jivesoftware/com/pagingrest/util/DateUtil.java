package rnd.jivesoftware.com.pagingrest.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    private final static SimpleDateFormat ISO8601_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH);

    public static String toISO8601(Date date) {
        synchronized (ISO8601_DATE_FORMAT) {
            return ISO8601_DATE_FORMAT.format(date);
        }
    }
}
