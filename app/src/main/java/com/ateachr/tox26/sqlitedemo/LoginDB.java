package com.ateachr.tox26.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LoginDB {

    public static final String CREATE_LOGIN_TABLE =
            "CREATE TABLE user (_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)";

    public static final String DROP_LOGIN_TABLE =
            "DROP TABLE IF EXISTS user";

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_LOGIN_TABLE);
            db.execSQL("INSERT INTO user VALUES (1, 'sanjeev', '1234')");
            db.execSQL("INSERT INTO user VALUES (2, 'kumar', '1234')");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_LOGIN_TABLE);
            onCreate(db);
        }
    }

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public LoginDB(Context context) {
        dbHelper = new DBHelper(context, "login.db", null, 1);
    }

    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }

    private void openWriteableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        if (db != null)
            db.close();
    }

    public long  insertLogin(String login, String password) {
        ContentValues cv = new ContentValues();
        cv.put("username", login);
        cv.put("password", password);

        this.openWriteableDB();
        long rowID = db.insert("user", null, cv);
        this.closeDB();

        return rowID;
    }

    public int deleteLogin(String username) {
        String where = "username = ?";
        String[] whereArgs = { username };

        this.openWriteableDB();
        int rowCount = db.delete("user", where, whereArgs);
        this.closeDB();

        return rowCount;
    }

    public String getPassword(String username) {
        String where = "username = ?";
        String[] whereArgs = { username };
        String password;

        try {
            this.openReadableDB();
            Cursor cursor = db.query("user", null, where, whereArgs, null, null, null);
            cursor.moveToFirst();
            password = cursor.getString(2);
            this.closeDB();
        } catch (Exception e) {
            return "User Not Found";
        }
        return password;
    }

    public int getRowID(String username) {
        String where = "username = ?";
        String[] whereArgs = { username };
        int rowID;

        try {
            this.openReadableDB();
            Cursor cursor = db.query("user", null, where, whereArgs, null, null, null);
            cursor.moveToFirst();
            rowID = cursor.getInt(0);
            this.closeDB();
        } catch (Exception e) {
            return -1;
        }
        return rowID;
    }

}
