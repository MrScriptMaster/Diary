package com.novaly.android.proto;

import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

enum TYPE {
	TYPE_EVENT,
	TYPE_NOTE,
	TYPE_PICTURE,
	TYPE_SOUND;
}

enum MOMENT {
	BEGIN,
	END;
}

class Utils {
	
	/**
	 * Делит строку на подстроки. Разделитель - знак запятой (',').
	 * 
	 * @param input
	 * @return
	 */
	static ArrayDeque<String> prepareList(String input) {
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
	
	/**
	 * Проверяет формат даты и времени на соответствие
	 * шаблону
	 * YYYY-MM-DDThh:mm:ss
	 * 
	 * @param input
	 * @return Возвращает true, если соответствует, и
	 * false - если нет.
	 */
	static boolean isDate(String input) {
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
				date = null;
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
				time = null;
			}
		}
		return result;
	}
	
	/**
	 * Конвертирует строку в объект класса Date.
	 * 
	 * @param input - строка в формате YYYY-MM-DDThh:mm:ss  
	 * @return Возвращает null, если не удалось сконвертировать,
	 * и ссылку на объект, если удалось.
	 */
	static Date str2date(String input) {
		Date result = null;		
		
		if (isDate(input)) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			sf.setTimeZone(TimeZone.getTimeZone("UTC"));
			try {
				result = sf.parse(input);
			} catch (Exception e) {
				sf = null;
				result = null;
			}
		}
		return result;
	}
	
	/**
	 * Конвертирует объект класса Date в строку.
	 * 
	 * @param input
	 * @return Возвращает строку в формате yyyy-MM-DDTHH:mm:ss.
	 * При любых ошибках конвертирования строка будет пустой. 
	 */
	static String date2str(Date input) {
		String result = "";
		
		if (null != input) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			result = sf.format(input);
			sf = null;
		}
		return result;
	}
}

public interface Item {
	TYPE type();
	String getTitle();
	String getBody();
	Date getDate(MOMENT time_moment);
	boolean isPeriodic();
	List<String> getAttachments(TYPE type);
}
