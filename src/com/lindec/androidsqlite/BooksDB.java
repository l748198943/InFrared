package com.lindec.androidsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BooksDB extends SQLiteOpenHelper {
	private final static String DATABASE_NAME = "Infrared.db";
	private final static int DATABASE_VERSION = 1;

	// irbasicinfo表
	private final static String IRBASICINFO_TABLE_NAME = "irbasicinfo_table";
	public final static String IRBASICINFO_ID = "_id";
	public final static String IRBASICINFO_TYPEID = "typeid";
	public final static String IRBASICINFO_DEVTYPE = "devtype";
	public final static String IRBASICINFO_BCCHNO = "bcchno";
	public final static String IRBASICINFO_FIXEDCODE = "fixedcode";
	// ircode表
	private final static String IRCODE_TABLE_NAME = "ircode_table";
	public final static String IRCODE_ID = "_id";
	public final static String IRCODE_TYPEID = "typeid";
	public final static String IRCODE_INST = "inst";
	public final static String IRCODE_INSTCODE = "instcode";
	public final static String IRCODE_CODE = "code";

	public BooksDB(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// 创建table
	@Override
	public void onCreate(SQLiteDatabase db) {
		String irbasicinfo_sql = "CREATE TABLE " + IRBASICINFO_TABLE_NAME + " (" + IRBASICINFO_ID
				+ " INTEGER primary key autoincrement, " + IRBASICINFO_TYPEID + " text, " + IRBASICINFO_DEVTYPE
				+ " text, " + IRBASICINFO_BCCHNO + " text, " + IRBASICINFO_FIXEDCODE + " text);";

		String ircode_sql = "CREATE TABLE " + IRCODE_TABLE_NAME + " (" + IRCODE_ID
				+ " INTEGER primary key autoincrement, " + IRCODE_TYPEID + " text, " + IRCODE_INST + " text, "
				+ IRCODE_INSTCODE + " text, " + IRCODE_CODE + " text);";
		db.execSQL(irbasicinfo_sql);
		db.execSQL(ircode_sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String irbasicinfo_sql = "DROP TABLE IF EXISTS " + IRBASICINFO_TABLE_NAME;
		String ircode_sql = "DROP TABLE IF EXISTS " + IRCODE_TABLE_NAME;
		db.execSQL(irbasicinfo_sql);
		db.execSQL(ircode_sql);
		onCreate(db);
	}

	public Cursor irbasicinfo_select() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(IRBASICINFO_TABLE_NAME, null, null, null, null, null, null);
		return cursor;
	}

	public Cursor ircode_select() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(IRBASICINFO_TABLE_NAME, null, null, null, null, null, null);
		return cursor;
	}

	// irbasicinfo增加操作
	public long irbasicinfo_insert(String typeid, String devtype, String bcchno, String fixedcode) {
		SQLiteDatabase db = this.getWritableDatabase();
		/* ContentValues */
		ContentValues cv = new ContentValues();
		cv.put(IRBASICINFO_TYPEID, typeid);
		cv.put(IRBASICINFO_DEVTYPE, devtype);
		cv.put(IRBASICINFO_BCCHNO, bcchno);
		cv.put(IRBASICINFO_FIXEDCODE, fixedcode);
		long row = db.insert(IRBASICINFO_TABLE_NAME, null, cv);
		return row;
	}

	// irbasicinfo删除操作
	public void irbasicinfo_delete(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = IRBASICINFO_ID + " = ?";
		String[] whereValue = { Integer.toString(id) };
		db.delete(IRBASICINFO_TABLE_NAME, where, whereValue);
	}

	// irbasicinfo修改操作
	public void irbasicinfo_update(int id, String typeid, String devtype, String bcchno, String fixedcode) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = IRBASICINFO_ID + " = ?";
		String[] whereValue = { Integer.toString(id) };

		ContentValues cv = new ContentValues();
		cv.put(IRBASICINFO_TYPEID, typeid);
		cv.put(IRBASICINFO_DEVTYPE, devtype);
		cv.put(IRBASICINFO_BCCHNO, bcchno);
		cv.put(IRBASICINFO_FIXEDCODE, fixedcode);
		db.update(IRBASICINFO_TABLE_NAME, cv, where, whereValue);
	}

	// ircode_增加操作
	public long ircode_insert(String typeid, String inst, String instcode, String code) {
		SQLiteDatabase db = this.getWritableDatabase();
		/* ContentValues */
		ContentValues cv = new ContentValues();
		cv.put(IRCODE_TYPEID, typeid);
		cv.put(IRCODE_INST, inst);
		cv.put(IRCODE_INSTCODE, instcode);
		cv.put(IRCODE_CODE, code);
		long row = db.insert(IRCODE_TABLE_NAME, null, cv);
		return row;
	}

	// ircode_删除操作
	public void ircode_delete(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = IRCODE_ID + " = ?";
		String[] whereValue = { Integer.toString(id) };
		db.delete(IRCODE_TABLE_NAME, where, whereValue);
	}

	// ircode_修改操作
	public void ircode_update(int id, String typeid, String devtype, String bcchno, String fixedcode) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = IRCODE_ID + " = ?";
		String[] whereValue = { Integer.toString(id) };

		ContentValues cv = new ContentValues();
		cv.put(IRCODE_TYPEID, typeid);
		cv.put(IRCODE_TYPEID, devtype);
		cv.put(IRCODE_TYPEID, bcchno);
		cv.put(IRCODE_TYPEID, fixedcode);
		db.update(IRCODE_TABLE_NAME, cv, where, whereValue);
	}

}