package com.example.asus.sqldesign_test.SqlDataTable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ASUS on 2017/2/21.
 */

public class DataBaseSQLHelp extends SQLiteOpenHelper {

    public static final String CREATE_Register = "create table register("
            +"id integer primary key autoincrement,"
            +"register_name text,"
            +"register_password text)";

    public static final String CREATE_Week_Plan_Add = "create table week_plan_add("
            +"id integer primary key autoincrement,"
            +"register_name text,"
            +"week_num text,"
            +"week_day text,"
            +"finish text,"
            +"plan_context text)";

    public static final String CREATE_Plan_Punch_Card = "create table plan_punch_card("
            +"id integer primary key autoincrement,"
            +"register_name text,"
            +"week_num text,"
            +"week_day text,"
            +"plan_context text)";

    private Context mContext;
    public DataBaseSQLHelp(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_Register);
        db.execSQL(CREATE_Week_Plan_Add);
        db.execSQL(CREATE_Plan_Punch_Card);
        Log.d("MainActivity","Creat Success");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
