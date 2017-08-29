package com.example.android.servurance.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tanujanuj on 26/08/17.
 */

public class SignUpDbHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME="ideal.db";
    private static final int DATABASE_VERSION=2;
    public SignUpDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_SIGNUP_TABLE="CREATE TABLE " + SignUpContract.SignUpEntry.TABLE_NAME +"("+
                SignUpContract.SignUpEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                SignUpContract.SignUpEntry.COLUMN_NAME+" TEXT NOT NULL,"+
                SignUpContract.SignUpEntry.COLUMN_DATE+" TEXT NOT NULL,"+
                SignUpContract.SignUpEntry.COLUMN_FATHER_NAME+" TEXT NOT NULL,"+
                SignUpContract.SignUpEntry.COLUMN_PAN_NUMBER+" TEXT NOT NULL "+
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_SIGNUP_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS"+ SignUpContract.SignUpEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
