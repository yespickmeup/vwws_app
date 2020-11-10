package com.vwwsapp.sql;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vwwsapp.model.Setting;
import com.vwwsapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class DbSettings extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "vwwsapp.db";

    // User table name
    private static final String TABLE_SETTING = "settings";

    // User Table Columns names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_BUSINESS_NAME = "business_name";
    private static final String COLUMN_BUSINESS_ADDRESS = "business_address";
    private static final String COLUMN_SERVER_IP = "ip_address";

    // create table sql query
    private String CREATE_SETTING_TABLE = "CREATE TABLE " + TABLE_SETTING + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_BUSINESS_NAME + " TEXT,"
            + COLUMN_BUSINESS_ADDRESS + " TEXT," + COLUMN_SERVER_IP + " TEXT" + ")";

    // drop table sql query
    private String DROP_SETTING_TABLE = "DROP TABLE IF EXISTS " + TABLE_SETTING;

    /**
     * Constructor
     *
     * @param context
     */
    public DbSettings(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SETTING_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_SETTING_TABLE);

        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param user
     */

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<Setting> geSettings() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_ID,
                COLUMN_BUSINESS_NAME,
                COLUMN_BUSINESS_ADDRESS,
                COLUMN_SERVER_IP
        };
        // sorting orders
        String sortOrder =
                COLUMN_BUSINESS_NAME + " ASC";
        List<Setting> settingList = new ArrayList<Setting>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_SETTING, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Setting setting = new Setting();
                setting.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
                setting.setBusiness_name(cursor.getString(cursor.getColumnIndex(COLUMN_BUSINESS_NAME)));
                setting.setBusiness_address(cursor.getString(cursor.getColumnIndex(COLUMN_BUSINESS_ADDRESS)));
                setting.setIp_address(cursor.getString(cursor.getColumnIndex(COLUMN_SERVER_IP)));
                // Adding user record to list
                settingList.add(setting);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return settingList;
    }

    public void addSetting() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_BUSINESS_NAME, "Business Name");
        values.put(COLUMN_BUSINESS_ADDRESS, "Business Address");
        values.put(COLUMN_SERVER_IP, "192.168.10.118");

        // Inserting Row
        db.insert(TABLE_SETTING, null, values);
        db.close();

    }

}
