package com.example.armen.scanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private  static final String DB_Name = "my.db";
    private static final int DB_Version = 1;
    private static DBHelper instance;

    public DBHelper(Context context) {
        super(context.getApplicationContext(), DB_Name, null, DB_Version);
    }

    public static DBHelper newInstance(Context c){
        if(instance == null) {
            instance = new DBHelper(c.getApplicationContext());
        }
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DBSchema.CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addQRCode(String name) {
        ContentValues cv = new ContentValues();
        cv.put(DBSchema.COLUMN_NAME, name);

        SQLiteDatabase db = getWritableDatabase();
        long rowId = db.insert(DBSchema.TABLE_NAME, null, cv);
        db.close();
        return rowId;
    }

    public List<String> getQRCode(){
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(DBSchema.TABLE_NAME, null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                String qrCode  = cursor.getString(cursor.getColumnIndex(DBSchema.COLUMN_NAME));
                list.add(qrCode);
                cursor.moveToNext();
            }
        }
        db.close();
        cursor.close();
        return list;
    }
}
