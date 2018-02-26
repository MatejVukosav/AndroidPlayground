package com.playground.android.vuki.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by mvukosav
 */
public class BoundService extends Service {

    public class BoundServiceBinder extends Binder {
        public BoundService getService() {
            return BoundService.this;
        }
    }

    private final BoundServiceBinder boundServiceBinder = new BoundServiceBinder();

    @Override
    public IBinder onBind( Intent intent ) {
        return boundServiceBinder;
    }

    private int counter = 0;
    private Thread thread = null;

    @Override
    public void onCreate() {
        super.onCreate();
        startCounter();
    }

    private void startCounter() {
        thread = new Thread( () -> {
            while ( true ) {
                counter++;
                try {
                    Thread.sleep( 500 );
                } catch ( InterruptedException e ) {
                    e.printStackTrace();
                }
            }
        } );
        thread.start();
    }

    public String getCounterValue() {
        return counter + "";
    }

    @Override
    public void onDestroy() {

        if ( thread != null ) {
            thread.interrupt();
        }
        super.onDestroy();
    }
}