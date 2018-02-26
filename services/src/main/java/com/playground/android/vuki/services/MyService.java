package com.playground.android.vuki.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.playground.android.vuki.helpers.NotesHelper;
import com.playground.android.vuki.tasks.MyTask;

/**
 * Created by mvukosav
 */
public class MyService extends Service {

    private boolean serviceOn = false;
    private static String TAG = MyService.class.getCanonicalName();

    @Override
    public int onStartCommand( Intent intent, int flags, int startId ) {
        if ( serviceOn ) {
            NotesHelper.log( TAG, "Service already running!" );

        } else {
            MyTask task = new MyTask( this );
            task.execute( 5 );
            serviceOn = true;
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        NotesHelper.log( TAG, "Service stopped.." );
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind( Intent intent ) {
        return null;
    }
}
