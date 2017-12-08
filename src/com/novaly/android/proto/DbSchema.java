package com.novaly.android.proto;

import android.provider.BaseColumns;

public class DbSchema {
	
	public DbSchema () {}
	
	public static abstract class FeedEvent implements BaseColumns {
		public static final String TABLE_NAME = "events";
		public static final String COLUMN_NAME_ENTRY_ID = "event_id";
		public static final String COLUMN_NAME_TITLE = "event_title";
		public static final String COLUMN_NAME_DESCRIPTION = "event_desc";
		public static final String COLUMN_NAME_EVENT_START = "event_start";
		public static final String COLUMN_NAME_EVENT_END = "event_end";
		public static final String COLUMN_NAME_PLACE = "event_place"; 
		public static final String COLUMN_NAME_FLAG_PERIODIC = "event_is_periodic";
		public static final String COLUMN_NAME_PICTURE = "event_picture";
		public static final String COLUMN_NAME_RING = "event_ring"; 
	}
	
	public static abstract class FeedNote implements BaseColumns {
		public static final String TABLE_NAME = "notes";
		public static final String COLUMN_NAME_ENTRY_ID = "note_id";
		public static final String COLUMN_NAME_CREATE_DATE = "note_create_date";
		public static final String COLUMN_NAME_ALTERED_DATE = "note_altered_date";
		public static final String COLUMN_NAME_TITLE = "note_title";
		public static final String COLUMN_NAME_BODY = "note_body";
		public static final String COLUMN_ATTACHMENTS = "note_attachments";
	}
}
