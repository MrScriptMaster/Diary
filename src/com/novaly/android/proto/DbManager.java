package com.novaly.android.proto;

import java.util.List;

import com.novaly.android.proto.DbSchema;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.util.Log;

public class DbManager extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "AppDataBase.db";
	
	enum GET_BY {
		TITLE,	// поиск по заголовкам
		BODY,	// поиск по содержимому
		DATE;	// поиск по дате
	}
	
	/**
	 * Вызывается, когда база создается или пересоздается.
	 * 
	 * @param db
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		
	}
	
	/**
	 * Вызывается, когда меняется структура базы данных.
	 * 
	 * @param db
	 * @param oldVersion
	 * @param newVersion
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 1) {
            onCreate(db);
        } else {
        	
        }
    }
	
	public List<Item> getNotes(GET_BY criteria) {
		// TODO
		return null;
	}
	
	public List<Item> getEvents(GET_BY criteria) {
		// TODO
		return null;
	}
}
