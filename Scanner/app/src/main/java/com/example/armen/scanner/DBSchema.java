package com.example.armen.scanner;

public class DBSchema {

    public static final String TABLE_NAME = "QRCodes";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ID = "_id";
    public static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + "(" +COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT);";
}
