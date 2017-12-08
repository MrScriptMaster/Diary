package com.novaly.android.proto;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.List;

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
}

public interface Item {
	TYPE type();
	String getTitle();
	String getBody();
	Date getDate(MOMENT time_moment);
	boolean isPeriodic();
	List<String> getAttachments(TYPE type);
}
