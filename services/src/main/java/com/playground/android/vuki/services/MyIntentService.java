package com.playground.android.vuki.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.playground.android.vuki.activites.MainActivity;

/**
 * Created by mvukosav
 */
public class MyIntentService extends IntentService {

    Handler handler = new Handler();

    public MyIntentService() {
        super( "MyIntentService" );
    }

    @Override
    protected void onHandleIntent( @Nullable Intent intent ) {
        if ( intent != null ) {
            int taskCount = intent.getIntExtra( MainActivity.TASK_COUNT_KEY, 0 );
            for ( int i = 0; i < taskCount; i++ ) {
                performLongTask();
                int progress = ( (int) ( ( i + 1 ) / (double) taskCount * 100 ) );
                showProgress( progress );
            }
        }
    }

    private void showProgress( final int progress ) {
        handler.post( () -> {
            String msg = progress + "% completed";
            Toast.makeText( getApplicationContext(), msg, Toast.LENGTH_SHORT ).show();
        } );
    }

    private void performLongTask() {
        try {
            Thread.sleep( 4000 );
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
    }
}
