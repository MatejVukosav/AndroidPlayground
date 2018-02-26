package com.playground.android.vuki.activites;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.playground.android.vuki.services.BoundService;
import com.playground.android.vuki.services.MyService;
import com.playground.android.vuki.services.R;
import com.playground.android.vuki.services.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static final String TASK_COUNT_KEY = "task.count.key";
    private ActivityMainBinding binding;
    private Intent serviceIntent;

    private BoundService boundService;
    private boolean serviceBound = false;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected( ComponentName name, IBinder service ) {
            BoundService.BoundServiceBinder boundServiceBinder = (BoundService.BoundServiceBinder) service;
            boundService = boundServiceBinder.getService();
            serviceBound = true;
        }

        @Override
        public void onServiceDisconnected( ComponentName name ) {
            serviceBound = false;
        }
    };

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_main );

        serviceIntent = new Intent( this, MyService.class );
        serviceIntent.setPackage( MyService.class.getPackage().getName() );

        binding.btnStart.setOnClickListener( v -> {
            startService( serviceIntent );
        } );

        binding.btnStop.setOnClickListener( v -> stopService( serviceIntent ) );

        setupButtons();
        setupListeners();
    }

    private void setupButtons() {
        if ( serviceBound ) {
            binding.btnStartBoundService.setVisibility( View.INVISIBLE );
            binding.btnStopBoundService.setVisibility( View.VISIBLE );
            binding.btnChkCounterBoundService.setVisibility( View.VISIBLE );
        } else {
            binding.btnStartBoundService.setVisibility( View.VISIBLE );
            binding.btnStopBoundService.setVisibility( View.INVISIBLE );
            binding.btnChkCounterBoundService.setVisibility( View.INVISIBLE );
        }
    }

    private void setupListeners() {
        binding.btnStartBoundService.setOnClickListener( v -> {
            if ( !serviceBound ) {
                Intent i = new Intent( getApplicationContext(), BoundService.class );
                startService( i );
                bindService( i, serviceConnection, BIND_AUTO_CREATE );
                serviceBound = true;
                setupButtons();
            }
        } );
        binding.btnStopBoundService.setOnClickListener( v -> {
            if ( serviceBound ) {
                unbindService( serviceConnection );
                serviceBound = false;
                Intent i = new Intent( getApplicationContext(), BoundService.class );
                stopService( i );
                setupButtons();
            }
        } );
        binding.btnChkCounterBoundService.setOnClickListener( v -> {
            if ( serviceBound ) {
                String counterValue = boundService.getCounterValue();
                Toast.makeText( getApplicationContext(), counterValue, Toast.LENGTH_SHORT ).show();
            }
        } );

    }
}
