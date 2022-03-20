package com.edcast.fraud.reporting.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;

public class DateTransformUtils {


    public static String reformatDate(String strDate) throws ParseException {
        SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
        Date date = input.parse(strDate);
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        strDate = output.format(date);
        return strDate;
    }

    public static LocalDateTime transformStrDateToLocalDateTime(String strDate) {
        DateTimeFormatter fmt = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd")
                .optionalStart()
                .appendPattern(" HH:mm:ss")
                .optionalEnd()
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();
        return LocalDate.parse(strDate, fmt).atStartOfDay();
    }

}
