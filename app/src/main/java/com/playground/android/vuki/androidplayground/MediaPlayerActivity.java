package com.playground.android.vuki.androidplayground;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

public class MediaPlayerActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int ID_PERMISSIONS = 1;
    private Button bStart, bStop;
    private MediaRecorder mediaRecorder;
    private File audioFile;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_media_player );

        initWidgets();
        setupListeners();
        askPermissions();
    }

    private void askPermissions() {
        if ( ActivityCompat.checkSelfPermission( this, Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission( this, Manifest.permission.RECORD_AUDIO ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO }, ID_PERMISSIONS );
        }
    }

    private void initWidgets() {
        bStart = findViewById( R.id.bStart );
        bStop = findViewById( R.id.bStop );
    }

    private void setupListeners() {
        bStart.setOnClickListener( this );
        bStop.setOnClickListener( this );
    }

    @Override
    public void onClick( View v ) {
        if ( v.getId() == R.id.bStart ) {
            startRecording();
        } else if ( v.getId() == R.id.bStop ) {
            stopRecording();
        }
    }

    private void startRecording() {
        bStart.setEnabled( false );
        bStop.setEnabled( true );

        File sampleDir = Environment.getExternalStorageDirectory();

        try {
            audioFile = File.createTempFile( "zvuk", ".3gp", sampleDir );

            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource( MediaRecorder.AudioSource.MIC );
            mediaRecorder.setOutputFile( audioFile.getAbsolutePath() );
            mediaRecorder.setOutputFormat( MediaRecorder.OutputFormat.THREE_GPP );
            mediaRecorder.setAudioEncoder( MediaRecorder.AudioEncoder.AMR_NB );
            mediaRecorder.prepare();
            mediaRecorder.start();

        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        bStart.setEnabled( true );
        bStop.setEnabled( false );

        mediaRecorder.stop();
        mediaRecorder.reset();
        mediaRecorder.release();
    }
}

