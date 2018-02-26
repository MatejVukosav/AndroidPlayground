package com.playground.android.vuki.content_providers.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * Created by mvukosav
 */
public class AppProvider extends ContentProvider {

    private AppDatabase appDatabase;
    private static final UriMatcher uriMatcher = buildUriMatcher();

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "." + TaskContract.CONTENT_AUTHORITY
            + "." + TaskContract.TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "." + TaskContract.CONTENT_AUTHORITY
            + "." + TaskContract.TABLE_NAME;

    public static final int TASKS = 100;
    public static final int TASK_ID = 101;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher( UriMatcher.NO_MATCH );
        // content:/com.playground.android.vuki.content_providers.provider/Task/
        matcher.addURI( TaskContract.CONTENT_AUTHORITY, TaskContract.TABLE_NAME, TASKS );
        // content://com.playground.android.vuki.content_providers.provider/Task/7
        matcher.addURI( TaskContract.CONTENT_AUTHORITY, TaskContract.TABLE_NAME + "/#", TASK_ID );
        return matcher;
    }

    public AppProvider() {
    }

    @Override
    public boolean onCreate() {
        appDatabase = AppDatabase.getDatabase( getContext() );
        return false;
    }

    @Override
    public String getType( @NonNull Uri uri ) {
        final int match = uriMatcher.match( uri );
        switch ( match ) {
            case TASKS:
                return CONTENT_TYPE;
            case TASK_ID:
                return CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException( "Unknown Uri: " + uri );
        }
    }

    @Override
    public Cursor query( @NonNull Uri uri, String[] projection, String selection,
                         String[] selectionArgs, String sortOrder ) {

        final int match = uriMatcher.match( uri );
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        switch ( match ) {
            case TASKS:
                sqLiteQueryBuilder.setTables( TaskContract.TABLE_NAME ); // SELECT * FROM Task;
                break;
            case TASK_ID:
                sqLiteQueryBuilder.setTables( TaskContract.TABLE_NAME );
                long id = TaskContract.getTaskId( uri );
                sqLiteQueryBuilder.appendWhere( TaskContract.Columns._ID + " = " + id );
                break;

            default:
                throw new IllegalArgumentException( "Unknown Uri: " + uri );

        }

        SQLiteDatabase sqLiteDatabase = appDatabase.getReadableDatabase();
        return sqLiteQueryBuilder.query( sqLiteDatabase, projection, selection, selectionArgs, null, null, sortOrder );
    }

    @Override
    public int delete( @NonNull Uri uri, String selection, String[] selectionArgs ) {

        final int match = uriMatcher.match( uri );
        final SQLiteDatabase db;
        int count;
        String additionalSelectionCriteria;

        switch ( match ) {
            case TASKS:
                db = appDatabase.getWritableDatabase();
                count = db.delete( TaskContract.TABLE_NAME, selection, selectionArgs );
                break;
            case TASK_ID:
                db = appDatabase.getWritableDatabase();
                long taskId = TaskContract.getTaskId( uri );
                // WHERE id = taskid iz Urija
                additionalSelectionCriteria = TaskContract.Columns._ID + " = " + taskId;
                if ( ( ( selection != null ) && selection.length() > 0 ) ) {
                    additionalSelectionCriteria += " AND (" + selection + ") ";
                }
                count = db.delete( TaskContract.TABLE_NAME, additionalSelectionCriteria, selectionArgs );
                break;
            default:
                throw new IllegalArgumentException( "Unknown Uri: " + uri );
        }
        return count;
    }

    @Override
    public Uri insert( @NonNull Uri uri, ContentValues values ) {
        final int match = uriMatcher.match( uri );
        final SQLiteDatabase db;

        Uri returningUri = null;
        long recordId;

        switch ( match ) {

            case TASKS:
                db = appDatabase.getWritableDatabase();
                recordId = db.insert( TaskContract.TABLE_NAME, null, values );
                if ( recordId >= 0 ) {
                    returningUri = TaskContract.buildTaskUri( recordId );
                } else {
                    throw new SQLException( "Insert failed: " + uri );
                }
        }
        return returningUri;

    }

    @Override
    public int update( @NonNull Uri uri, ContentValues values, String selection,
                       String[] selectionArgs ) {

        final int match = uriMatcher.match( uri );
        final SQLiteDatabase db;
        int count;
        String additionalSelectionCriteria;

        switch ( match ) {
            case TASKS:
                db = appDatabase.getWritableDatabase();
                count = db.update( TaskContract.TABLE_NAME, values, selection, selectionArgs );
                break;

            case TASK_ID:
                db = appDatabase.getWritableDatabase();
                long taskId = TaskContract.getTaskId( uri );
                additionalSelectionCriteria = TaskContract.Columns.TASK_NAME + " = " + taskId;
                if ( ( selection != null ) && ( selection.length() > 0 ) ) {
                    additionalSelectionCriteria += " AND (" + selection + ");";
                }
                count = db.update( TaskContract.TABLE_NAME, values, additionalSelectionCriteria, selectionArgs );
                break;

            default:
                throw new IllegalArgumentException( "Unknown Uri: " + uri );
        }
        return count;
    }

}
