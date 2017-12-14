package com.novaly.android.proto;

import java.util.Date;
import java.util.List;

import com.novaly.android.proto.DbSchema;
import com.novaly.android.proto.Note;
import com.novaly.android.proto.Event;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

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
		// Таблица с событиями
		// 
		//CREATE TABLE `events` (
		//		`event_id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
		//		`event_title`	TEXT,
		//		`event_desc`	TEXT,
		//		`event_start`	NUMERIC,
		//		`event_end`	NUMERIC,
		//		`event_place`	TEXT,
		//		`event_is_periodic`	NUMERIC,
		//		`event_picture`	TEXT,
		//		`event_ring`	TEXT
		//	)
		String CREATE_EVENTS_TABLE = String.format("CREATE TABLE %s ("
				+ "%s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,"
				+ "%s TEXT,"
				+ "%s TEXT,"
				+ "%s NUMERIC,"
				+ "%s NUMERIC,"
				+ "%s TEXT,"
				+ "%s NUMERIC,"
				+ "%s TEXT,"
				+ "%s TEXT )", 
				//-------------------------------------------
				DbSchema.FeedEvent.TABLE_NAME,
				DbSchema.FeedEvent.COLUMN_NAME_ENTRY_ID,
				DbSchema.FeedEvent.COLUMN_NAME_TITLE,
				DbSchema.FeedEvent.COLUMN_NAME_DESCRIPTION,
				DbSchema.FeedEvent.COLUMN_NAME_EVENT_START,
				DbSchema.FeedEvent.COLUMN_NAME_EVENT_END,
				DbSchema.FeedEvent.COLUMN_NAME_PLACE,
				DbSchema.FeedEvent.COLUMN_NAME_FLAG_PERIODIC,
				DbSchema.FeedEvent.COLUMN_NAME_PICTURE,
				DbSchema.FeedEvent.COLUMN_NAME_RING
				);
		//
		//CREATE TABLE `notes` (
		//		`note_id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
		//		`note_create_date`	NUMERIC,
		//		`note_altered_date`	NUMERIC,
		//		`note_title`	TEXT,
		//		`note_body`	TEXT,
		//		`note_attachments`	TEXT
		//	)
		//
		String CREATE_NOTES_TABLE = String.format("CREATE TABLE %s ("
				+ "%s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,"
				+ "%s NUMERIC,"
				+ "%s NUMERIC,"
				+ "%s TEXT,"
				+ "%s TEXT,"
				+ "%s TEXT )", 
				//--------------------------------------------
				DbSchema.FeedNote.TABLE_NAME,
				DbSchema.FeedNote.COLUMN_NAME_ENTRY_ID,
				DbSchema.FeedNote.COLUMN_NAME_CREATE_DATE,
				DbSchema.FeedNote.COLUMN_NAME_ALTERED_DATE,
				DbSchema.FeedNote.COLUMN_NAME_TITLE,
				DbSchema.FeedNote.COLUMN_NAME_BODY,
				DbSchema.FeedNote.COLUMN_ATTACHMENTS
				);
		Log.d("DEBUG", CREATE_EVENTS_TABLE);
		db.execSQL(CREATE_EVENTS_TABLE);
		Log.d("DEBUG", CREATE_NOTES_TABLE);
		db.execSQL(CREATE_NOTES_TABLE);
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
        	db.execSQL("DROP TABLE IF EXISTS " + DbSchema.FeedEvent.TABLE_NAME);
        	db.execSQL("DROP TABLE IF EXISTS " + DbSchema.FeedNote.TABLE_NAME);
        	onCreate(db);
        } else {
        	
        }
    }
	
	/**
	 * Создает новое событие и заносит его в базу данных.
	 * 
	 * @param e_title
	 * @param e_description
	 * @param e_place
	 * @param e_start
	 * @param e_end
	 * @param e_is_periodic
	 * @param e_picture_path
	 * @param e_sound_path
	 * @return Вернет структуру
	 */
	public Event createNewEvent(String e_title, String e_description, String e_place,
								  Date e_start, Date e_end,
								  boolean e_is_periodic,
								  String e_picture_path,
								  String e_sound_path)
	{
		/*
		 * Дату и время вносить в соответствии с ISO-8601.
		 * 
		 * YYYY-MM-DDThh:mm:ss
		 */
		Event newEvent = null;
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put();
		
		return newEvent;
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
