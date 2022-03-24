package com.buinam.schedulemanger.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    public static Date startOfDay(Date date) {
        if (date == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date endOfDay(Date date) {
        if (date == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    public static Date startOfMonth(Date date) {
        if (date == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, 01);

        return cal.getTime();
    }

    public static Date endOfMonth(Date date) {
        if (date == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

        return cal.getTime();
    }

    public static Date addMonth(Date date, int month) {
        if (date == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, month);

        return cal.getTime();
    }

    public static Date addYearAndMonth(Date date, int year, int month) {
        if (date == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, year);
        cal.add(Calendar.MONTH, month);

        return cal.getTime();
    }

    public static String dateTimeToStringNotUTC(LocalDateTime date, String format) {
        try {
            if (date == null)
                date = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            String formatDateTime = date.format(formatter);
            return formatDateTime;
        } catch (Exception ex) {

        }
        return "";
    }

    public static String dateTimeToString(Date date, String format) {
        String formatDateTime = "";
        if (date != null) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat(format);
                formatDateTime = formatter.format(date);
            } catch (Exception ex) {
                logger.warn(ex.toString());
            }
        }
        return formatDateTime;
    }

    public static Long dateDiff(LocalDateTime fromDate, LocalDateTime toDate, String type) {

        if ("MINUTES".equals(type))
            return ChronoUnit.MINUTES.between(fromDate, toDate);
        return 0L;
    }

    public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
        try {
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        } catch (Exception ex) {
            logger.warn("Error happened", ex);
        }
        return null;
    }

    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        try {
            return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        } catch (Exception ex) {
            // logger.warn("Error happened", ex);
        }
        return null;
    }

    public static LocalDateTime convertStringToLocalDateTime(String date, String format) {
        try {
            LocalDateTime dateTime = null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            if (date.indexOf(":") < 0) {
                //avoided Exception Text '????-??-??' could not be parsed at index 10
                LocalDate localDateTime = LocalDate.parse(date, formatter);
                dateTime = localDateTime.atStartOfDay();
            } else {
                dateTime = LocalDateTime.parse(date, formatter);
            }
            return dateTime;
        } catch (Exception ex) {
//            ex.printStackTrace();
            // logger.warn("Error happened", ex);
        }
        return null;
    }

    public static LocalDateTime getLocalDateTimeWithZoneLocal() {
        try {
            return LocalDateTime.now(ZoneId.systemDefault());
        } catch (Exception ex) {
            // logger.warn("Error happened", ex);
        }
        return null;
    }

    public static boolean isToday(LocalDateTime now, Long dueDate) {
        try {
            Date dateNow = convertLocalDateTimeToDate(now);
            long startDay = startOfDay(dateNow).getTime();
            long endDay = endOfDay(dateNow).getTime();
            return startDay <= dueDate && dueDate <= endDay;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isOverDue(LocalDateTime now, Long dueDate) {
        try {
            Date dateNow = convertLocalDateTimeToDate(now);
            long startOfToDay = startOfDay(dateNow).getTime();
            return dueDate < startOfToDay;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isIncoming(LocalDateTime now, Long dueDate) {
        try {
            Date dateNow = convertLocalDateTimeToDate(now);
            long endOfDay = endOfDay(dateNow).getTime();
            return dueDate > endOfDay;
        } catch (Exception e) {
            return false;
        }
    }

    public static String formatMonth(Integer month) {
        if (month < 10) {
            return "0" + month;
        }
        return month.toString();
    }
}
