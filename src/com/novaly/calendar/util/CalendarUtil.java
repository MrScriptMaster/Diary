package com.novaly.calendar.util;

import java.util.Calendar;

public class CalendarUtil {
	//										  0     1     2     3     4     5     6
	private static String defWeekNames[] = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};  
	//                                         0     1     2     3     4     5      6     7     8     9    10     11
	private static String defMonthNames[] = {"Jan","Feb","Mar","Apr","May","Jun,","Jul","Aug","Sen","Oct","Nov","Dec"};
	/**
	 * To return days number of the month in selected year.
	 * 
	 * @param year - year on the Gregorian calendar
	 * @param month - number from 1 to 12
	 * @return Normally method return days number else return -1. 
	 */
	public static int getMonthsDays(int year, int month) {
		switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				return 31;
			case 4:
			case 6:
			case 9:
			case 11:
				return 30;
			case 2:
				if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
					return 29;
				} else {
					return 28;
				}
			default:
				break;
		}
				
		return -1;
	}
	
	/**
	 * To return first day of first week of selected month.
	 * 
	 * @param year
	 * @param month (0 - January ... 11 - December)
	 * @return 1 - Sunday ... 7 - Saturday
	 */
	public static int getFirstWeekDay(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, 1);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * To return line number of the week in which the specified number of the month is located.
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	/*
	 * Mon Tue Wed Thu Fri Sat Sun
	 *  1   2   3   4   5   6   7   <-- line 0 
	 *  8   9  10  11  12  13  14   <-- line 1
	 *  ..........................
	 *  
	 *  int i = getWeekRow(someYear, someMonth, 13); // i = 1
	 *  i = getWeekRow(someYear, someMonth, 2);		 // i = 0
	 */
	public static int getWeekRow(int year, int month, int day) {
        int week = getFirstWeekDay(year, month);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        int lastWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (lastWeek == 7)
            day--;
        return (day + week - 1) / 7;
    }
	
	/**
	 *  To return the number of weeks to the target date from the week in which the specified date (last*) is located.
	 * 
	 * @param lastYear
	 * @param lastMonth
	 * @param lastDay
	 * @param year
	 * @param month
	 * @param day
	 * @return Return 0 if year/month/day locates on the same week as lastYear/lastMonth/lastDay.
	 * If number is negative a date (year/month/day) is located on week before current week.
	 * If number is positive a date (year/month/day) is located on week after current week.
	 */
	public static int getWeeksAgo(int lastYear, int lastMonth, int lastDay, int year, int month, int day) {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.set(lastYear, lastMonth, lastDay);
        end.set(year, month, day);
        int week = start.get(Calendar.DAY_OF_WEEK);
        start.add(Calendar.DATE, -week);
        week = end.get(Calendar.DAY_OF_WEEK);
        end.add(Calendar.DATE, 7 - week);
        float v = (end.getTimeInMillis() - start.getTimeInMillis()) / (3600 * 1000 * 24 * 7 * 1.0f);
        return (int) (v - 1);
    }
	
	/**
	 * To return the number of months to the target date (year/month) from the current month.
	 * 
	 * @param lastYear
	 * @param lastMonth
	 * @param year
	 * @param month
	 * @return Return 0 if year/month locates on the same month as lastYear/lastMonth.
	 * If number is negative a date (year/month) is located in month before current month.
	 * If number is positive a date (year/month) is locates in month after current month.
	 */
	public static int getMonthsAgo(int lastYear, int lastMonth, int year, int month) {
	       return (year - lastYear) * 12 + (month - lastMonth);
	}
	
	public static void printCalendar(int year, int month) {
		if (month > 11) month = 11;
		if (month < 0) month = 0;
		if (year < 0) year = -year;
		
		// Template
		//-------|-------|-------|-------|
		// Mon Tue Wed Thu Fri Sat Sun
		//   1   2   3   4   5   6   7
		//   8   9  10  11  12  13  14
		//-------------------------------|
		// Print head
		System.out.println("          " + defMonthNames[month] + ", " + year);
		System.out.print(" ");
		for (int i = 1; i < defWeekNames.length; i++) {
			System.out.print(defWeekNames[i] + " ");
		}
		System.out.println(defWeekNames[0]);
		System.out.print(" ");
		
		// Определить каким днем недели приходится начало выбранного месяца
		int preMonthBegin,
		    preMonthEnd = getMonthsDays(year, month-1),
		    monthBegin = 1,
		    monthEnd = getMonthsDays(year, month);
		
		int dayCounter = 0, weekCounter = 0;
		
		if (preMonthEnd == -1) {
			preMonthEnd = getMonthsDays(year-1, 11);
		}
		
		int firstDay = getFirstWeekDay(year, month);
		if (firstDay == 0) { // Sunday
			preMonthBegin = preMonthEnd - 5;
		} else { // Others
			preMonthBegin = preMonthEnd - (7 - firstDay);
		}
		
		
	}
	
	// for testing
	public static void main(String[] args) {
		/*for (int i = 1; i < 13; i++) {
			System.out.print(CalendarUtil.getMonthsDays(2017, i));
			System.out.print(" ");
		}
		System.out.println("");
		System.out.println(CalendarUtil.getFirstWeekDay(2017, 11));
		System.out.println("getWeekRow");
		System.out.println(CalendarUtil.getWeekRow(2017, 11, 31));
		System.out.println("");
		System.out.println(getWeeksAgo(2017, 11, 6, 2017, 11, 13));
		System.out.println(getMonthsAgo(2017, 11, 2017, 10));
		System.out.println(Calendar.DAY_OF_MONTH);*/
		
		//printCalendar(2017, 11);

		System.out.println(4 % 7);
		
	}

}
