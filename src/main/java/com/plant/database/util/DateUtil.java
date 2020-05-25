package com.plant.database.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateUtil
 *
 * @author 18044703
 * @date 2020/5/16
 */
public class DateUtil {

    private DateUtil(){}

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

    private static final SimpleDateFormat SECOND_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public static Date defaultParse(String dateString) {

        try {
            return FORMATTER.parse(dateString);
        } catch (ParseException e) {
            try {
                return SECOND_FORMATTER.parse(dateString);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
