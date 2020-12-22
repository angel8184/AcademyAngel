package com.academy.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTimeUtil {

    public static String convertTWDate (String dateStr, String beforeFormat, String afterFormat){//轉年月格式

        if (dateStr == null)
            return "";

        SimpleDateFormat df4 = new SimpleDateFormat(beforeFormat);
        SimpleDateFormat df2 = new SimpleDateFormat(afterFormat);
        Calendar cal = Calendar.getInstance();

        try {
            cal.setTime(df4.parse(dateStr));
            if (cal.get(Calendar.YEAR) > 1492)
                cal.add(Calendar.YEAR, -1911);
            else cal.add(Calendar.YEAR, +1911);

            return df2.format(cal.getTime()).replaceFirst("^0+(?!$)", "");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
