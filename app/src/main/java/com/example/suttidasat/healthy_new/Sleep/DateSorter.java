package com.example.suttidasat.healthy_new.Sleep;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class DateSorter implements Comparator<Sleep> {
    @Override
    public int compare(Sleep d1, Sleep d2) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);
        try {

            String dSt1 = d1.getDate_sleep();
            Date day1 = sdf.parse(dSt1);
            Date day2 = sdf.parse(d2.getDate_sleep());

            return day1.compareTo(day2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
