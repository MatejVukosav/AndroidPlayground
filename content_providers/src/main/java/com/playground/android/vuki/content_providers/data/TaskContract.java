package com.playground.android.vuki.content_providers.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by mvukosav
 */
public class TaskContract {

    public static final String TABLE_NAME = "Task";

    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String TASK_NAME = "Name";
        public static final String TASK_DESCRIPTION = "Description";
        public static final String TASKS_SORT_ORDER = "SortOrder";

        private Columns() {
        }
    }

    public static final String CONTENT_AUTHORITY = "com.playground.android.vuki.content_providers.provider";
    public static final Uri CONTENT_AUTHORITY_URI = Uri.parse( "content://" + CONTENT_AUTHORITY );
    public static final Uri CONTENT_URI = Uri.withAppendedPath( CONTENT_AUTHORITY_URI, TABLE_NAME );

    // id:7
    // content://com.playground.android.vuki.content_providers.provider/Task/7
    public static Uri buildTaskUri( long id ) {
        return ContentUris.withAppendedId( CONTENT_URI, id );
    }

    // uri: content://com.playground.android.vuki.content_providers.provider/Task/256
    // id:256
    public static long getTaskId( Uri uri ) {
        return ContentUris.parseId( uri );
    }
}
