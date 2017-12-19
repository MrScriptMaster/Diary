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
	
	public enum _BY {
		ID,		// 
		TITLE,	// по заголовкам
		BODY,	// по содержимому
		DATE;	// по дате
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
		//		`event_start`	TEXT,
		//		`event_end`	    TEXT,
		//		`event_place`	TEXT,
		//		`event_is_periodic`	NUMERIC,
		//		`event_picture`	TEXT,
		//		`event_ring`	TEXT
		//	)
		String CREATE_EVENTS_TABLE = String.format("CREATE TABLE %s ("
				+ "%s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,"
				+ "%s TEXT,"
				+ "%s TEXT,"
				+ "%s TEXT,"
				+ "%s TEXT,"
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
		//		`note_create_date`	TEXT,
		//		`note_altered_date`	TEXT,
		//		`note_title`		TEXT,
		//		`note_body`	        TEXT,
		//		`note_attachments`	TEXT
		//	)
		//
		String CREATE_NOTES_TABLE = String.format("CREATE TABLE %s ("
				+ "%s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,"
				+ "%s TEXT,"
				+ "%s TEXT,"
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
		// TODO
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
	
	/**
	 * Создает новую заметку и заносит ее в базу данных.
	 * 
	 * @param n_title
	 * @param n_body
	 * @param n_attachs
	 * @return
	 */
	public Note createNewNote(String n_title, String n_body, String n_attachs) {
		// TODO
		Note newNote = null;
		
		
		return newNote;
	}
	
	/**
	 * Удалить запись(и) событий по критерию.
	 * 
	 * @param input
	 * @param criteria - удалить можно по id по тексту в заголовке и по дате.
	 * @return
	 */
	public int dropEventsBy(String input, _BY criteria) {
		// TODO
		int result = -1;		
		
		if (_BY.ID == criteria) {
			
		}
		else if (_BY.TITLE == criteria) {
			
		}
		else if (_BY.DATE == criteria) {
			
		}
		
		return result;
	}
	
	/**
	 * Удалить запись(и) заметок по критерию.
	 * 
	 * @param input
	 * @param criteria - удалить можно по id по тексту в заголовке и по дате.
	 * @return
	 */
	public int dropNotesBy(String input, _BY criteria) {
		// TODO
		int result = -1;		
		
		if (_BY.ID == criteria) {
			
		}
		
		return result;
	}
	
	public int dropAllDataFrom(String table) {
		// TODO
		int result = -1;
		
		return result;
	}
	
	/**
	 * Получить все записи c заметками по указанному критерию.
	 * 
	 * @param input
	 * @param criteria - запросить можно по: дате, по заголовку, по содержимому.
	 * @return Если запрос успешно выполнился, то возвращает совместимый с 
	 * List список, иначе - null.
	 */
	public List<Item> getNotes(String input, _BY criteria) {
		// TODO
		return null;
	}
	
	/**
	 * Получить все записи с событиями по указанному критерию.
	 * 
	 * @param input
	 * @param criteria - запросить можно по: дате начла события
	 * @return Если запрос успешно выполнился, то возвращает совместимый с 
	 * List список, иначе - null.
	 */
	public List<Item> getEvents(String input, _BY criteria) {
		// TODO
		return null;
	}
}
