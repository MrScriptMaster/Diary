package com.novaly.main;

import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.TimeZone;

public class Test {

	public static void main(String[] args) {
		Date date = new Date();
		String input = "2017-12-19T14:04:00";
		System.out.println(date2str(date));
		System.out.println(isDate("2143--5-52T12:24:43"));
		System.out.println(str2date(input));
	}
	
	static public ArrayDeque<String> prepareList(String input) {
		String delimeter = ",";
		String [] subStr = input.split(delimeter);
		ArrayDeque<String> deque = null;
		
		if (subStr.length != 0) {
			deque = new ArrayDeque<>();
			for (int i = 0; i < subStr.length; i++) {
				deque.add(subStr[i]);
			}
		}
		
		return deque;
	}
	
	static String date2str(Date input) {
		String result = "";
		
		if (null != input) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			result = sf.format(input);
		}
		return result;
	}
	
	static boolean isDate(String input) {
		// TODO
		boolean result = false;
		
		if (null != input) {
			String [] parts_1 = input.split("T", 2);
			if (parts_1.length == 2) 
				result = true;
			
			// Проверка даты
			if (result) {
				String [] date = parts_1[0].split("-", 3);
				if (date.length == 3) {
					try {
						int counter = 0;
						for (String datePart : date) {
							switch (counter) {
								case 0: // год
									if (datePart.length() != 4
									    || Integer.parseInt(datePart) < 0) result = false;
									break;
								case 1: // месяц
								case 2: // число
									if (datePart.length() != 2
									    || Integer.parseInt(datePart) < 0) result = false;
									break;
							}
							counter++;
							if (false == result) break;
						}
					} catch (Exception e) {
						result = false;
					} 
				}
				else {
					result = false;
				}
			}
			
			// Проверка времени
			if (result) {
				String [] time = parts_1[1].split(":",3);
				if (time.length == 3) {
					try {
						for (String timePart : time) {
							if (timePart.length() != 2
								|| Integer.parseInt(timePart) < 0)
							{
								result = false;
								break;
							}
						}
					} catch (Exception e) {
						result = false;
					}
				}
				else {
					result = false;
				}
			}
		}
		return result;
	}
	
	static Date str2date(String input) {
		// TODO
		Date result = null;		
		
		if (isDate(input)) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			sf.setTimeZone(TimeZone.getTimeZone("UTC"));
			try {
				result = sf.parse(input);
			} catch (Exception e) {
				
			}
		}
		
		return result;
	}
}
