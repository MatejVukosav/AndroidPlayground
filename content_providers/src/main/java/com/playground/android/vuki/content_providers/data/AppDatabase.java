package com.playground.android.vuki.content_providers.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mvukosav
 */
public class AppDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TaskDatabase.db";
    public static final int DATABASE_VERSION = 1;

    private static AppDatabase database = null;

    private AppDatabase( Context context ) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    public  static AppDatabase getDatabase( Context context ) {
        if ( database == null ) {
            database = new AppDatabase( context );
        }
        return database;
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        String sSql;
        sSql = "CREATE TABLE " + TaskContract.TABLE_NAME + " ("
                + TaskContract.Columns._ID + " INTEGER PRIMARY KEY, "
                + TaskContract.Columns.TASK_NAME + " TEXT NOT NULL, "
                + TaskContract.Columns.TASK_DESCRIPTION + " TEXT, "
                + TaskContract.Columns.TASKS_SORT_ORDER + " INTEGER"
                + ");";

        db.execSQL( sSql );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {

        switch ( oldVersion ) {
            case 1:
                // logika za prvi upgrade
                /*
                sSql = "CREATE TABLE " + TaskContract.TABLE_NAME + " ("
                    + TaskContract.Columns._ID + " INTEGER PRIMARY KEY, "
                    + TaskContract.Columns.TASK_NAME + " TEXT NOT NULL, "
                    + TaskContract.Columns.TASK_DESCRIPTION + " TEXT, "
                    + TaskContract.Columns.TASKS_SORT_ORDER + " INTEGER, "
                    + TaskContract.Columns.TASKS_GROUP_BY + " INTEGER"
                    + ");";
                */
                break;
            default:
                throw new IllegalStateException( "Unknown Db version" );
        }
    }
}
