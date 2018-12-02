package com.land.utils;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Dashrath on 1/3/2018.
 */

public class DateConvertUtil {

    public static final SimpleDateFormat GENERAL_DATE_TIME_FORMAT = new SimpleDateFormat("dd MMM yyy hh:mm a");

    public static final SimpleDateFormat POST_DATE_TIME_FORMAT = new SimpleDateFormat("dd MMM yyy hh:mm a");
    public static final SimpleDateFormat SERVER_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat GROUP_DATE_FORMAT = new SimpleDateFormat("MMM-dd-yyyy");
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm a");
    public static final SimpleDateFormat TIMER_FORMAT = new SimpleDateFormat("hh:mm ss");
    public static final SimpleDateFormat DATE_DAY_FORMAT = new SimpleDateFormat("EEE");
    public static final SimpleDateFormat DATE_YYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATE_DD_MM_YYYY = new SimpleDateFormat("dd-MMM-yyyy");

    public static String convertToServerDatePostDate(String date) {
        try {
            Date fromDate = SERVER_DATE_TIME_FORMAT.parse(date);
            return POST_DATE_TIME_FORMAT.format(fromDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertCalenderTo(Calendar calendar, SimpleDateFormat outFormat) {
        return outFormat.format(calendar.getTime());
    }

    public static String convertDateTo(Date date, SimpleDateFormat outFormat) {

        return outFormat.format(date);
    }

    public static String convertStringTO(String date, SimpleDateFormat inputFormat, SimpleDateFormat outFormat) {
        try {
            Date fromDate = inputFormat.parse(date);
            return outFormat.format(fromDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertToServerDate(String date, SimpleDateFormat outFormat) {
        try {
            Date fromDate = SERVER_DATE_TIME_FORMAT.parse(date);
            return outFormat.format(fromDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertPostDateToServerDate(String date) {
        try {
            Date fromDate = POST_DATE_TIME_FORMAT.parse(date);
            return SERVER_DATE_TIME_FORMAT.format(fromDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    public static String convertsubscritiondate(String date, SimpleDateFormat simpleDateForma, SimpleDateFormat returndateFormate) {

        try {
            Date fromDate = simpleDateForma.parse(date);
            return returndateFormate.format(fromDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static Calendar convertDateToCalender(String date) {
        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("Africa/Lusaka"));
        Calendar newCalender = Calendar.getInstance();

        try {
            Date fromDate = SERVER_DATE_TIME_FORMAT.parse(date);
            calendar.setTime(fromDate);

            newCalender.setTimeInMillis(calendar.getTimeInMillis());

            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newCalender;
    }

    public static Calendar convertDateToCalender(String date, SimpleDateFormat outFormat) {
        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("Africa/Lusaka"));
        Calendar newCalender = Calendar.getInstance();

        try {
            Date fromDate = outFormat.parse(date);
            calendar.setTime(fromDate);

            newCalender.setTimeInMillis(calendar.getTimeInMillis());

            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newCalender;

    }


    public static String convertDateToCalender(Calendar calendar, SimpleDateFormat outFormat) {
        try {
            return outFormat.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String parseDate(String dateString) {

        Date date = null;
        String str = null;

        try {
            date = SERVER_DATE_TIME_FORMAT.parse(dateString);
            str = GROUP_DATE_FORMAT.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    public static String getFormattedChatDate(long timestampInMilliseconds) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(timestampInMilliseconds);

        Calendar now = Calendar.getInstance();

        final String timeFormatString = "h:mm aa";
        final String dateTimeFormatString = "EEE, MMM d, h:mm aa";
        final long HOURS = 60 * 60 * 60;
        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE)) {
            return "Today " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1) {
            return "Yesterday " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return DateFormat.format(dateTimeFormatString, smsTime).toString();
        } else {
            return DateFormat.format("MMM dd yyyy, h:mm aa", smsTime).toString();
        }
    }

    public static Calendar convertCalendar(Calendar calendar, TimeZone timeZone) {
        Calendar ret = new GregorianCalendar(timeZone);
        ret.setTimeInMillis(calendar.getTimeInMillis() +
                timeZone.getOffset(calendar.getTimeInMillis()) -
                TimeZone.getDefault().getOffset(calendar.getTimeInMillis()));
        ret.getTime();
        return ret;
    }


    public static Calendar getServerCalender() {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = DateConvertUtil.convertCalendar(calendar1, TimeZone.getTimeZone("Africa/Lusaka"));
        // Calendar calendar2=calendar1;
        return calendar2;
    }


    public static String convertDateFormat(String date, SimpleDateFormat inputFormat, SimpleDateFormat outputFormat) {

        try {
            Date fromDate = inputFormat.parse(date);
            return outputFormat.format(fromDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static int getCountOfDays(String createdDateString, String expireDateString) {
        SimpleDateFormat dateFormat = DATE_DD_MM_YYYY;

        Date createdConvertedDate = null, expireCovertedDate = null, todayWithZeroTime = null;
        try {
            createdConvertedDate = dateFormat.parse(createdDateString);
            expireCovertedDate = dateFormat.parse(expireDateString);
            long diff = createdConvertedDate.getTime() - expireCovertedDate.getTime();

            float dayCount = (float) diff / (24 * 60 * 60 * 1000);

            return (int) dayCount;

        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

    }

}

