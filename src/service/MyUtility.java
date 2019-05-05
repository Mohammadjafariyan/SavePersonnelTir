package service;

import ir.huri.jcal.JalaliCalendar;
import org.apache.commons.lang.time.DateUtils;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class MyUtility {
    public static String ConvertToShamsi(Date date) {

        Calendar gc = Calendar.getInstance();
        gc.setTime(date);

        int year = gc.get(Calendar.YEAR);
        int m = gc.get(Calendar.MONTH);
        int d = gc.get(Calendar.DAY_OF_MONTH);


        JalaliCalendar jalaliDate = new JalaliCalendar(new GregorianCalendar(year, m, d));


        return jalaliDate.toString();
    }

    public static Date ConvertToGaregoiran(int year, int month, int day) {
        JalaliCalendar jalaliDate = new JalaliCalendar(year,
                month, day);
        return jalaliDate.toGregorian().getTime();

    }


    //ماه ایرانی را میگیرد و به انگلیسی تبدیل می کند
    public static Date GetMonthBeginInGaregorian(int year, int month) {

        return ConvertToGaregoiran(year, month, 1);
    }

    public static Date GetMonthEndInGaregorian(int year, int month) {
        JalaliCalendar firstday = new JalaliCalendar(year,
                month, 1);
        Date monthFirstDay = firstday.toGregorian().getTime();

        Date lastDay = DateUtils.addDays(monthFirstDay, firstday.getMonthLength() - 1);

        return lastDay;

    }

    public static DefaultComboBoxModel GetYearList() {
        List<Object> years = new LinkedList<>();

        for (int i = 0; i < 100; i++) {
            years.add(1390 + i);
        }

        return new DefaultComboBoxModel(years.toArray());

    }

    public static String convertToEnglishDigits(String value)
    {
        if(value==null)
            return "";

        String answer = value;
        answer = answer.replace("1","١");
        answer = answer.replace("2","٢");
        answer = answer.replace("3","٣");
        answer = answer.replace("4","٤");
        answer = answer.replace("5","٥");
        answer = answer.replace("6","٦");
        answer = answer.replace("7","٧");
        answer = answer.replace("8","٨");
        answer = answer.replace("9","٩");
        answer = answer.replace("0","٠");
        return answer;
    }

    public static Object[] months = {
            "فروردین", "اردیبهشت",
            "خرداد",
            "تیر",
            "مرداد",
            "شهریور",
            "مهر",
            "آبان",
            "آذر",
            "دی",
            "بهمن",
            "اسفند",
    };

    public static DefaultComboBoxModel GetMonthList() {


        return new DefaultComboBoxModel(months);
    }

    public static int GetCurrentMonthNumber() {
        Date d = new Date();

        Calendar gc = Calendar.getInstance();
        gc.setTime(d);
        JalaliCalendar jalaliDate = new JalaliCalendar(
                new GregorianCalendar(gc.get(Calendar.YEAR)
                        , gc.get(Calendar.MONTH), gc.get(Calendar.DAY_OF_MONTH)));

        int month = jalaliDate.getMonth();
        return month;
    }

    public static Object GetCurrentMonth() {

        int l = GetCurrentMonthNumber();
        return months[(int) l - 1];
    }

    public static int GetCurrentYear() {

        Date d = new Date();

        Calendar gc = Calendar.getInstance();
        gc.setTime(d);
        JalaliCalendar jalaliDate = new JalaliCalendar(
                new GregorianCalendar(gc.get(Calendar.YEAR)
                        , gc.get(Calendar.MONTH), gc.get(Calendar.DAY_OF_MONTH)));

        return jalaliDate.getYear();
    }

    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static Object[] GetMonthDays(int year, int month) {
        JalaliCalendar jalaliDate = new JalaliCalendar(year, month, 1);

        Object[] ints = new Object[jalaliDate.getMonthLength()];
        for (int i = 1; i <= jalaliDate.getMonthLength(); i++) {
            ints[i - 1] = i;
        }
        return ints;
    }

    public static int GetCurrentDay() {
        Date d = new Date();

        Calendar gc = Calendar.getInstance();
        gc.setTime(d);
        JalaliCalendar jalaliDate = new JalaliCalendar(
                new GregorianCalendar(gc.get(Calendar.YEAR)
                        , gc.get(Calendar.MONTH), gc.get(Calendar.DAY_OF_MONTH)));

        return jalaliDate.getDay();
    }
}
