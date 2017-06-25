package com.bogdanorzea.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bogdanorzea.habittracker.data.HabitContract;
import com.bogdanorzea.habittracker.data.HabitDbHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private HabitDbHelper mDbHelper;

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new HabitDbHelper(this);

        // Update the display information
        displayDatabaseInfo();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertDummyHabit();
                displayDatabaseInfo();
            }
        });

    }

    private void insertDummyHabit() {
        // Retrieve stored information
        String name = "Yoga";
        String dt = getCurrentDateTime();
        int duration = 20;
        String location = "Home";

        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Maps values to columns
        ContentValues values = new ContentValues();
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_NAME, name);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_DATE, dt);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_DURATION_MIN, duration);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_LOCATION, location);

        // Insert the new row, returning the value of the new row
        long newRowId = db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);

        if (newRowId > -1) {
            Log.i("INSERT", "Habit saved with id: " + newRowId);
        } else {
            Log.i("INSERT", "Error with saving habit." + newRowId);
        }
    }

    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void displayDatabaseInfo() {
        // Get text view to display database information
        TextView displayView = (TextView) findViewById(R.id.text);
        displayView.setText("The habits table contains:\n\n");

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                HabitContract.HabitEntry._ID,
                HabitContract.HabitEntry.COLUMN_HABIT_NAME,
                HabitContract.HabitEntry.COLUMN_HABIT_DATE,
                HabitContract.HabitEntry.COLUMN_HABIT_DURATION_MIN,
                HabitContract.HabitEntry.COLUMN_HABIT_LOCATION
        };

        // Performs "SELECT * FROM habits"
        // to get a Cursor that contains all rows from the habits table.
        Cursor cursor = db.query(HabitContract.HabitEntry.TABLE_NAME,
                projection, null, null, null, null, null);

        try {
            // Get the column index
            int nameColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_NAME);
            int dateColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_DATE);
            int durationColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_DURATION_MIN);
            int locationColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_LOCATION);

            while (cursor.moveToNext()) {
                String currentName = cursor.getString(nameColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);
                int currentDuration = cursor.getInt(durationColumnIndex);
                String currentLocation = cursor.getString(locationColumnIndex);

                displayView.append("\n" + currentName + " - " +
                        currentDate + " - " +
                        currentDuration + " - " +
                        currentLocation);
            }
        } finally {
            // Always close the cursor when you're done reading from it.
            // This releases all its resources and makes it invalid.
            cursor.close();
        }

    }
}
