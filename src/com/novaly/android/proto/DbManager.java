package com.novaly.android.proto;

import java.util.Date;
import java.util.List;

import com.novaly.android.proto.DbSchema;
import com.novaly.android.proto.Note;
import com.novaly.android.proto.Event;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
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
		/*
		 * Дату и время вносить в соответствии с ISO-8601.
		 * 
		 * YYYY-MM-DDThh:mm:ss
		 */
		Event newEvent = null;
		
		String strEventStart = Utils.date2str(e_start);
		String strEventEnd = Utils.date2str(e_end);
		
		SQLiteDatabase db = null;
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			
			values.put(DbSchema.FeedEvent.COLUMN_NAME_TITLE, e_title);
			values.put(DbSchema.FeedEvent.COLUMN_NAME_DESCRIPTION, e_description);
			values.put(DbSchema.FeedEvent.COLUMN_NAME_EVENT_START, strEventStart);
			values.put(DbSchema.FeedEvent.COLUMN_NAME_EVENT_END, strEventEnd);
			values.put(DbSchema.FeedEvent.COLUMN_NAME_PLACE, e_place);
			values.put(DbSchema.FeedEvent.COLUMN_NAME_FLAG_PERIODIC, e_is_periodic ? 1 : 0);
			values.put(DbSchema.FeedEvent.COLUMN_NAME_PICTURE, e_picture_path);
			values.put(DbSchema.FeedEvent.COLUMN_NAME_RING, e_sound_path);
			
			long SQL_result = -1;
			
			try {
				if (isAvailable(DbSchema.FeedEvent.TABLE_NAME, e_title, strEventStart, strEventEnd, e_is_periodic))
				{
					SQL_result = db.insertOrThrow(DbSchema.FeedEvent.TABLE_NAME, null, values);
					long _id = -1;
					
					newEvent = new Event();
					newEvent.m_Title = e_title;
					newEvent.m_Description = e_description;
					newEvent.m_Start = e_start;
					newEvent.m_End = e_end;
					newEvent.m_Place = e_place;
					newEvent.m_Is_Periodic = e_is_periodic;
					newEvent.m_PicturePath = e_picture_path;
					newEvent.m_SoundPath = e_sound_path;
					Log.d("DEBUG", "Request an id");
					newEvent.m_Id = _id = getRowId((Item) newEvent);
					Log.d("DEBUG", "New event has id = " + _id);
				}
				else {
					throw new Exception("Creation of an event is not available");
				}
				
			} catch (android.database.SQLException e) {
				Log.d("DEBUG", "Create event failed. SQL code = " + SQL_result);
			} catch (Exception e) {
				Log.d("DEBUG", e.getMessage());
			}
		} finally {
			if (db) 
				db.close();
		}
		
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
		SQLiteDatabase db = null;
		try {
			
		} finally {
			if (db)
				db.close();
		}
		
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
			Log.d("DEBUG", "No realized");
		}
		else if (_BY.DATE == criteria) {
			Log.d("DEBUG", "No realized");
		}
		
		return result;
	}
	
	/**
	 * Удалить запись(и) заметок по критерию.
	 * 
	 * @param input
	 * @param criteria - удалить можно по id и тексту в заголовке.
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
	
	/**
	 * Обновить поля в указанных колонках. В качестве критерия поиска используется id записи.
	 * Колонки нужно передавать
	 * текстовым списком, в котором используется разделитель ','. Не рекомендуется
	 * использовать пробелы. Замена значений будет производиться только для
	 * колонок указанных и существующих в списке имен колонок. Для
	 * ссылок null значение будет затираться.
	 * 
	 * @param ev - структура с обновленным полем
	 * @param columns - перечень имен колонок, разделенных запятыми
	 * @return Возвращает код SQL-запроса.
	 */
	public int updateEvent(Event ev, String columns) {
		int SQL_result = -1;
		SQLiteDatabase db = null;
		try {
			List<String> cols = (List<String>) Utils.prepareList(columns);	
			if (cols.isEmpty()) {
				throw new Exception("List of columns is empty");
			}
			if (null != ev) {
				if (ev.m_Id <= 0) {
					throw new Exception("Bad id in request: " + ev.m_Id);
				}
			}
			else {
				throw new Exception("Event is null");
			}

			try {
				db = this.getWritableDatabase();
				ContentValues values = new ContentValues();
				
				for (String columnName : cols) {
					if (DbSchema.FeedEvent.COLUMN_NAME_TITLE == columnName) {
						if (ev.m_Title != null) {
							values.put(DbSchema.FeedEvent.COLUMN_NAME_TITLE, ev.m_Title);
						}
						else {
							values.put(DbSchema.FeedEvent.COLUMN_NAME_TITLE, "");
						}
					}
					else if (DbSchema.FeedEvent.COLUMN_NAME_DESCRIPTION == columnName) {
						if (ev.m_Description != null) {
							values.put(DbSchema.FeedEvent.COLUMN_NAME_DESCRIPTION, ev.m_Description);
						}
						else {
							values.put(DbSchema.FeedEvent.COLUMN_NAME_DESCRIPTION, "");
						}
					}
					else if (DbSchema.FeedEvent.COLUMN_NAME_EVENT_START == columnName) {
						if (ev.m_Start != null) {
							values.put(DbSchema.FeedEvent.COLUMN_NAME_EVENT_START, Utils.date2str(ev.m_Start));
						}
						else {
							values.put(DbSchema.FeedEvent.COLUMN_NAME_EVENT_START, "");
						}
					}
					else if (DbSchema.FeedEvent.COLUMN_NAME_EVENT_END == columnName) {
						if (ev.m_End != null) {
							values.put(DbSchema.FeedEvent.COLUMN_NAME_EVENT_END, Utils.date2str(ev.m_End));
						}
						else {
							values.put(DbSchema.FeedEvent.COLUMN_NAME_EVENT_END, "");
						}
					}
					else if (DbSchema.FeedEvent.COLUMN_NAME_PLACE == columnName) {
						if (ev.m_Place != null) {
							values.put(DbSchema.FeedEvent.COLUMN_NAME_PLACE, ev.m_Place); 
						}
						else {
							values.put(DbSchema.FeedEvent.COLUMN_NAME_PLACE, "");
						}
					}
					else if (DbSchema.FeedEvent.COLUMN_NAME_FLAG_PERIODIC == columnName) {
						values.put(DbSchema.FeedEvent.COLUMN_NAME_FLAG_PERIODIC, ev.m_Is_Periodic ? 1 : 0);
					}
					else if (DbSchema.FeedEvent.COLUMN_NAME_PICTURE == columnName) {
						if (ev.m_PicturePath != null) {
							values.put(DbSchema.FeedEvent.COLUMN_NAME_PICTURE, ev.m_PicturePath);
						}
						else {
							values.put(DbSchema.FeedEvent.COLUMN_NAME_PICTURE, "");
						}
					}
					else if (DbSchema.FeedEvent.COLUMN_NAME_RING == columnName) {
						if (ev.m_SoundPath != null) {
							values.put(DbSchema.FeedEvent.COLUMN_NAME_RING, ev.m_SoundPath);
						}
						else {
							values.put(DbSchema.FeedEvent.COLUMN_NAME_RING, "");
						}
					}
					else {
						Log.d("DEBUG", "An unknown name was found: " + columnName);
					}
				}
				
				SQL_result = db.update(DbSchema.FeedEvent.TABLE_NAME, values,
					DbSchema.FeedEvent.COLUMN_NAME_ENTRY_ID + " = ?",
					new String[] {String.valueOf(ev.m_Id)});
				
			} catch (android.database.SQLException e) {
				Log.d("DEBUG", "Update event failed. SQL code = " + SQL_result);
			} finally {
				if (db) 
					db.close();
			}
		} catch (Exception e) {
			Log.d("DEBUG", e.getMessage());
		}
		
		return SQL_result;
	}
	
	/**
	 * Обновить поле заметки.
	 * 
	 * @param nt - структура с измененным полем
	 * @param columnName - поддерживаются все колонки, кроме индекса и дат
	 * @return
	 */
	public int updateNote(Note nt, String columnName) {
		//TODO
		int SQL_result = -1;
		SQLiteDatabase db = null;
		
		try {
			
		} finally {
			if (db)
				db.close();
		}
		
		return SQL_result;
	}
	
	/**
	 * Проверка дубликатов записей.
	 * 
	 * @param e_title - заголовок
	 * @param e_start - в контесте события - начало; в контексте заметки - момент создания
	 * @param e_end - в контексте события - конец; в контексте заметки - не используется
	 * @param e_is_periodic - в контексте собвтия - флаг периодичности; в контексте заметки - не используется 
	 * @return Возвращает true, если записей с такими значениями не существует. Возвращает
	 * false, когда есть дубликат или создание по каким-либо причинам невозможно.
	 */
	final private boolean isAvailable(String table,
			  String e_title, 
			  String e_start, String e_end,
			  boolean e_is_periodic)
	{
		boolean result = false;
		String id_field = null;
		String title_field = null;
		String start_field = null;
		String stop_field = null;
		String periodic_field = null;
		SQLiteDatabase db = null;
		try {	
			db = this.getWritableDatabase();
			Cursor cursor = null;
			try {		
				// bad solution
				if (table == DbSchema.FeedEvent.TABLE_NAME) 
				{
					id_field = DbSchema.FeedEvent.COLUMN_NAME_ENTRY_ID;
					title_field = DbSchema.FeedEvent.COLUMN_NAME_TITLE;
					start_field = DbSchema.FeedEvent.COLUMN_NAME_EVENT_START;
					stop_field = DbSchema.FeedEvent.COLUMN_NAME_EVENT_END;
					periodic_field = DbSchema.FeedEvent.COLUMN_NAME_FLAG_PERIODIC;
				} 
				else if (table == DbSchema.FeedNote.TABLE_NAME) 
				{
					id_field = DbSchema.FeedNote.COLUMN_NAME_ENTRY_ID;
					title_field = DbSchema.FeedNote.COLUMN_NAME_TITLE;
					start_field = DbSchema.FeedNote.COLUMN_NAME_CREATE_DATE;
					stop_field = null;
					periodic_field = null;
				}
				else {
					throw new Exception(table + " is not supported");
				}
				//---------------
				Log.d("DEBUG", "Trying catch a cursor for " + table);
				int rowCounter = 0;
				if (table == DbSchema.FeedEvent.TABLE_NAME) {
					cursor = db.query(
							table,
			/*SELECT*/	    new String[] {
								id_field
							},
			/*WHERE*/	    String.format("%s= ? AND %s= ? AND %s= ? AND %s= ?", 
							title_field, start_field, stop_field, periodic_field),
							new String[] {e_title, e_start, e_end, Integer.toString(e_is_periodic ? 1 : 0)},
			/*GROUP BY*/	null,
			/*HAVING*/		null,
			/*ORDER BY*/	null,
			/*LIMIT*/		Integer.toString(2)
							);
					if (cursor) {
						rowCounter = cursor.getCount();
					}
				}
				else if (table == DbSchema.FeedNote.TABLE_NAME) {
					cursor = db.query(
							table,
			/*SELECT*/	    new String[] {
								id_field
							},
			/*WHERE*/	    String.format("%s= ? AND %s= ?", 
							title_field, start_field),
							new String[] {e_title, e_start},
			/*GROUP BY*/	null,
			/*HAVING*/		null,
			/*ORDER BY*/	null,
			/*LIMIT*/		Integer.toString(2)
							);
					if (cursor) {
						rowCounter = cursor.getCount();
					}
				}
				
				if (rowCounter != 0) {
					result = false;
					Log.d("DEBUG", "It was found a dup");
				}
				else {
					result = true;
					Log.d("DEBUG", "It wasn't found a dup");
				}
				
			} catch (Exception e) {
				Log.d("DEBUG", e.getMessage());
				result = false;
			} finally {
				if (cursor) {
					cursor.close();
					Log.d("DEBUG", "Cursor is closed");
				}
			}
		} finally {
			if (db)
				db.close();
		}
		
		return result;
	}
	
	/**
	 * Вернуть индекс записи в БД.
	 * 
	 * @param item - структура интерфейса Item. Поддерживаются
	 * только события и заметки.
	 * @return Возвращает индекс записи, который присвоила база данных после регистрации.
	 */
	final private long getRowId(Item item) {
		long result = -1;
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			Cursor cursor = null;
			try {
				if (null != item) {
					if (item.type() == TYPE.TYPE_EVENT) {
						Event ev = (Event) item;
						cursor = db.query(
								DbSchema.FeedEvent.TABLE_NAME,
				/*SELECT*/	    new String[] {
									DbSchema.FeedEvent.COLUMN_NAME_ENTRY_ID
								},
				/*WHERE*/	    String.format("%s= ? AND %s= ? AND %s= ? AND %s= ?", 
									DbSchema.FeedEvent.COLUMN_NAME_TITLE, 
									DbSchema.FeedEvent.COLUMN_NAME_EVENT_START, 
									DbSchema.FeedEvent.COLUMN_NAME_EVENT_END, 
									DbSchema.FeedEvent.COLUMN_NAME_FLAG_PERIODIC),
								new String[] {ev.m_Title, Utils.date2str(ev.m_Start), 
									Utils.date2str(ev.m_End), Integer.toString(ev.isPeriodic() ? 1 : 0)},
				/*GROUP BY*/	null,
				/*HAVING*/		null,
				/*ORDER BY*/	null,
				/*LIMIT*/		Integer.toString(1)
								);				
					}
					else if (item.type() == TYPE.TYPE_NOTE) {
						Note nt = (Note) item;
						cursor = db.query(
								DbSchema.FeedNote.TABLE_NAME,
				/*SELECT*/	    new String[] {
									DbSchema.FeedNote.COLUMN_NAME_ENTRY_ID
								},
				/*WHERE*/	    String.format("%s= ? AND %s= ?", 
									DbSchema.FeedNote.COLUMN_NAME_TITLE,
									DbSchema.FeedNote.COLUMN_NAME_CREATE_DATE),
								new String[] {nt.m_Title, Utils.date2str(nt.m_CreateDate)},
				/*GROUP BY*/	null,
				/*HAVING*/		null,
				/*ORDER BY*/	null,
				/*LIMIT*/		Integer.toString(1)
								);
					}
					if (null != cursor) {
						if (cursor.moveToFirst()) {
							result = cursor.getInt(0);
						}
					}
				}
			} catch (Exception e) {
				Log.d("DEBUG", e.getMessage());
			} finally {
				if (cursor) {
					cursor.close();
				}
			}
		} finally {
			if (db)
				db.close();
		}
		Log.d("DEBUG", "Request for ID returned " + result);
		
		return result;
	}
}
