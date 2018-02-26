package com.playground.android.vuki.tasks;

import android.app.Service;
import android.os.AsyncTask;

import com.playground.android.vuki.helpers.NotesHelper;

/**
 * Created by mvukosav
 */
public class MyTask extends AsyncTask<Integer, Integer, String> {

    private Service service;
    private final static int TASK_DURATION = 3000;
    private static String TAG = MyTask.class.getCanonicalName();

    public MyTask( Service service ) {
        this.service = service;
    }

    @Override
    protected String doInBackground( Integer... params ) {
        int taskCount = params[0];
        for ( int i = 0; i < taskCount; i++ ) {
            performLongTask();
            publishProgress( getPercentage( i, taskCount ) );
        }
        return taskCount + " zadataka izvrseno";
    }

    @Override
    protected void onPostExecute( String resultMsg ) {
        NotesHelper.log( TAG, resultMsg );
        service.stopSelf();
    }

    @Override
    protected void onProgressUpdate( Integer... values ) {
        String updateMsg = values[0] + "% izvrseno";
        NotesHelper.log( TAG, updateMsg );
    }

    private int getPercentage( int current, float max ) {
        return (int) ( ( ( current + 1 ) / max ) * 100 );
    }

    private void performLongTask() {
        try {
            Thread.sleep( TASK_DURATION );
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
    }
}
