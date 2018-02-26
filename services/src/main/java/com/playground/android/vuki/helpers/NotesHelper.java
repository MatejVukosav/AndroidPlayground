package com.playground.android.vuki.helpers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by mvukosav
 */
public class NotesHelper {
    public static void toast( Context context, String msg ) {
        Toast.makeText( context, msg, Toast.LENGTH_SHORT ).show();
    }

    public static void log( String tag, String msg ) {
        Log.d( tag, msg );
    }
}
