package com.example.piotrek.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayMessageActivity extends AppCompatActivity {
    private FeedReaderDbHelper feedReaderDbHelper;
    private Intent intent;
    private ListView messageListView;
    ArrayAdapter<String> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        feedReaderDbHelper = new FeedReaderDbHelper(this);
        intent = getIntent();
        messageListView = (ListView) findViewById(R.id.message_list);
        if(intent.getBooleanExtra("Show", true)) {
            displayData();
        } else {
            saveData();
        }
    }
    public void saveData() {
        String message = intent.getStringExtra("Message");
// Gets the data repository in write mode
        SQLiteDatabase db = feedReaderDbHelper.getWritableDatabase();
// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, message);
// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insertWithOnConflict(
                FeedReaderContract.FeedEntry.TABLE_NAME, null, values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        successMessage(message);
    }
    public void displayData() {
        feedReaderDbHelper = new FeedReaderDbHelper(this);
        SQLiteDatabase db = feedReaderDbHelper.getReadableDatabase();
// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
        };
        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,
// The table to query
                projection,
// The columns to return
                null,
// The columns for the WHERE clause
                null,
// The values for the WHERE clause
                null,
// don’t group the rows
                null,
// don’t filter by row groups
                null
// The sort order
        );
        ArrayList<String> messageList = new ArrayList<>();
        while(cursor.moveToNext()) {
            int idx =
                    cursor.getColumnIndex
                            (FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE);
            messageList.add(cursor.getString(idx));
        }
        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<String>(this,
                    R.layout.single_message, // what view to use for the items
                    R.id.text_message, // where to put the String of data
                    messageList); // where to get all the data
            messageListView.setAdapter(mAdapter); // set it as
//the adapter of the ListView instance
        } else {
            mAdapter.clear();
            mAdapter.addAll(messageList);
            mAdapter.notifyDataSetChanged();
        }
        cursor.close();
        db.close();
    }
    public void successMessage(String savedMessaged) {
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText("Message added to database: " + savedMessaged);
        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        layout.addView(textView);
    }
    public void delete(View view) {
        View parent = (View) view.getParent();
        TextView textView = (TextView) parent.findViewById(R.id.text_message);
        String message = String.valueOf(textView.getText());
        SQLiteDatabase db = feedReaderDbHelper.getWritableDatabase();
        db.delete(FeedReaderContract.FeedEntry.TABLE_NAME,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + " = ?",
                new String[]{message});
        db.close();
        displayData();
    }
}