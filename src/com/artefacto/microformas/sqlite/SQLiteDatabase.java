package com.artefacto.microformas.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

public class SQLiteDatabase {
	android.database.sqlite.SQLiteDatabase db;
	
	public static String dbLock = "DBLOCK";
	
	SQLiteDatabase(android.database.sqlite.SQLiteDatabase db){
		this.db = db;
	}
	
	public void execSQL(String sql){
		synchronized(dbLock){
			db.execSQL(sql);
		}
	}
	
	public long insert(String table,String nullColumnHack, ContentValues values){
		synchronized(dbLock){
			return db.insert(table, nullColumnHack, values);
		}
	}
	
	public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
		synchronized(dbLock){
			return db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		}
	}
	
	public Cursor rawQuery(String sql, String[] selectionArgs){
		synchronized(dbLock){
			return db.rawQuery(sql, selectionArgs);
		}
	}
	
	public int update(String table, ContentValues values, String whereClause, String[] whereArgs){
		synchronized(dbLock){
			return db.update(table, values, whereClause, whereArgs);
		}
	}
	
	public void close(){
		synchronized(dbLock){
			db.close();
		}
	}
}
