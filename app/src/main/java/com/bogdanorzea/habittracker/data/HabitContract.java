package com.bogdanorzea.habittracker.data;

import android.provider.BaseColumns;

public class HabitContract {
    /* Prevent the contract class from being instantiated */
    private HabitContract() {
    }

    /* Inner class that defines the table contents */
    public final static class HabitEntry implements BaseColumns {

        // Table name
        public final static String TABLE_NAME = "habits";

        // Column names
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_HABIT_NAME = "name";
        public final static String COLUMN_HABIT_DATE = "dt";
        public final static String COLUMN_HABIT_DURATION_MIN = "duration";
        public final static String COLUMN_HABIT_LOCATION = "location";

        // Data types
        public static final String TEXT_TYPE = " TEXT";
        public static final String INTEGER_TYPE = " INTEGER";
        public static final String COMMA_SEPARATOR = ",";
        public static final String NOT_NULL = " NOT NULL";
        public static final String DATETIME_TYPE = " DATETIME";

        // CREATE TABLE statement
        public final static String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEPARATOR +
                        COLUMN_HABIT_NAME + TEXT_TYPE + NOT_NULL + COMMA_SEPARATOR +
                        COLUMN_HABIT_DATE + DATETIME_TYPE + " DEFAULT current_timestamp" + COMMA_SEPARATOR +
                        COLUMN_HABIT_DURATION_MIN + INTEGER_TYPE + NOT_NULL + COMMA_SEPARATOR +
                        COLUMN_HABIT_LOCATION + TEXT_TYPE + ")";

        // DELETE TABLE statement
        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
