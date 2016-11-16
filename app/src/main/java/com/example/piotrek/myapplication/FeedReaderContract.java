package com.example.piotrek.myapplication;

import android.provider.BaseColumns;

public final class FeedReaderContract {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE +
                    " TEXT NOT NULL )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;
    public static String getSqlCreateEntries() {
        return SQL_CREATE_ENTRIES;
    }
    public static String getSqlDeleteEntries() {
        return SQL_DELETE_ENTRIES;
    }
    // To prevent someone from accidentally instantiating the contract class,
// make the constructor private.
    private FeedReaderContract() {}
    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_TITLE = "title";
    }
}