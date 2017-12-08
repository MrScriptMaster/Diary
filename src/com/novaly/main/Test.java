package com.novaly.main;

import java.util.ArrayDeque;

public class Test {

	public static void main(String[] args) {
		String input = "aaabbbccc";
		ArrayDeque<String> test = prepareList(input);
		
		for (String str : test) {
			System.out.println(str);
		}
		
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
	
}
