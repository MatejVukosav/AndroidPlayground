package com.playground.android.vuki.content_providers;

import android.content.ContentValues;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.playground.android.vuki.content_providers.data.TaskContract;
import com.playground.android.vuki.content_providers.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_main );
        setupListeners();
    }

    private void setupListeners() {

        binding.btnAdd.setOnClickListener( v -> {
            ContentValues contentValues = new ContentValues();
            contentValues.put( TaskContract.Columns.TASK_NAME, binding.etTaskName.getText().toString() );
            contentValues.put( TaskContract.Columns.TASK_DESCRIPTION, binding.etTaskDescription.getText().toString() );
            getContentResolver().insert( TaskContract.CONTENT_URI, contentValues );

            Toast.makeText( MainActivity.this, "Inserted", Toast.LENGTH_SHORT ).show();
        } );

        binding.btnUpdate.setOnClickListener( v -> {
            ContentValues contentValues = new ContentValues();
            contentValues.put( TaskContract.Columns.TASK_DESCRIPTION, binding.etTaskDescription.getText().toString() );
            String selection = TaskContract.Columns.TASK_NAME + " = ?";
            String newTaskName = binding.etTaskName.getText().toString();
            String args[] = { newTaskName };
            getContentResolver().update( TaskContract.CONTENT_URI, contentValues, selection, args );
        } );

        binding.btnRefresh.setOnClickListener( v -> {
            Cursor cursor = getContentResolver().query( TaskContract.CONTENT_URI, null, null, null, null );
            String[] fromColumns = { TaskContract.Columns.TASK_DESCRIPTION };
            int[] toView = { android.R.id.text1 };
            adapter = new SimpleCursorAdapter( MainActivity.this, android.R.layout.simple_expandable_list_item_1, cursor, fromColumns, toView, 0 );
            binding.lvResults.setAdapter( adapter );
        } );

        binding.btnDelete.setOnClickListener( v -> {
            String selection = TaskContract.Columns.TASK_NAME + " = ?";
            String desc = binding.etTaskName.getText().toString();
            String args[] = { desc };
            getContentResolver().delete( TaskContract.CONTENT_URI, selection, args );
        } );

        binding.lvResults.setOnItemClickListener( ( parent, view, position, id ) -> {
            String idToDelete = "/" + id;
            getContentResolver().delete( Uri.parse( TaskContract.CONTENT_URI + idToDelete ), null, null );
        } );
    }
}
