package com.vwwsapp.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vwwsapp.helpers.DateType;
import com.vwwsapp.helpers.DeEncrypter;
import com.vwwsapp.model.MeterReader;
import com.vwwsapp.model.MeterReaderAssignments;
import com.vwwsapp.model.MyUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBHelper2 extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "vwwsapp.db";

    // User table name
    private static final String TABLE_METER_READERS = "meter_readers";
    private static final String TABLE_METER_READER_ASSIGNMENTS = "meter_reader_assignments";
    private static final String TABLE_READINGS = "readings";

    public DBHelper2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String COLUMN_1 = "id";
    private static final String COLUMN_2 = "meter_reader_no";
    private static final String COLUMN_3 = "fname";
    private static final String COLUMN_4 = "mi";
    private static final String COLUMN_5 = "lname";
    private static final String COLUMN_6 = "city";
    private static final String COLUMN_7 = "city_id";
    private static final String COLUMN_8 = "barangay";
    private static final String COLUMN_9 = "barangay_id";
    private static final String COLUMN_10 = "purok";
    private static final String COLUMN_11 = "purok_id";
    private static final String COLUMN_12 = "address";
    private static final String COLUMN_13 = "user_name";
    private static final String COLUMN_14 = "password";

    // create table sql query
    private String CREATE_METER_READERS_TABLE = "CREATE TABLE " + TABLE_METER_READERS + "("
            + COLUMN_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_2 + " varchar(255),"
            + COLUMN_3 + " varchar(255),"
            + COLUMN_4 + " varchar(255),"
            + COLUMN_5 + " varchar(255),"
            + COLUMN_6 + " varchar(255),"
            + COLUMN_7 + " varchar(255),"
            + COLUMN_8 + " varchar(255),"
            + COLUMN_9 + " varchar(255),"
            + COLUMN_10 + " varchar(255),"
            + COLUMN_11 + " varchar(255),"
            + COLUMN_12 + " varchar(255),"
            + COLUMN_13 + " varchar(255),"
            + COLUMN_14 + " varchar(255)" + ")";


    private String CREATE_METER_READER_ASSIGNMENTS_TABLE = "CREATE TABLE " + TABLE_METER_READER_ASSIGNMENTS + "("
            + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "meter_reader_id" + " varchar(255),"
            + "meter_reader_no" + " varchar(255),"
            + "meter_reader_name" + " varchar(255),"
            + "customer_id" + " varchar(255),"
            + "customer_no" + " varchar(255),"
            + "customer_name" + " varchar(255),"
            + "barangay" + " varchar(255),"
            + "barangay_id" + " varchar(255),"
            + "purok" + " varchar(255),"
            + "purok_id" + " varchar(255),"
            + "date_added" + " datetime,"
            + "date_updated" + " datetime,"
            + "added_by_id" + " varchar(255),"
            + "update_by_id" + " varchar(255),"
            + "status" + " int,"
            + "occupancy_id" + " varchar(255),"
            + "occupancy" + " varchar(255),"
            + "occupancy_type_id" + " varchar(255),"
            + "occupancy_type" + " varchar(255),"
            + "occupancy_type_code" + " varchar(255),"
            + "city" + " varchar(255),"
            + "city_id" + " varchar(255),"
            + "sitio" + " varchar(255),"
            + "sitio_id" + " varchar(255),"
            + "meter_no" + " varchar(255),"
            + "pipe_size" + " varchar(255)" + ")";


    private String CREATE_READINGS_TABLE = "CREATE TABLE " + TABLE_READINGS + "("
            + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "reading_no" + " varchar(255),"
            + "meter_reader_id" + " varchar(255),"
            + "meter_reader_name" + " varchar(255),"
            + "customer_id" + " varchar(255),"
            + "customer_no" + " varchar(255),"
            + "customer_name" + " varchar(255),"
            + "previous_reading_date" + " date,"
            + "previous_reading" + " double,"
            + "current_reading" + " double,"
            + "city" + " varchar(255),"
            + "city_id" + " varchar(255),"
            + "barangay" + " varchar(255),"
            + "barangay_id" + " varchar(255),"
            + "purok" + " varchar(255),"
            + "purok_id" + " varchar(255),"
            + "sitio" + " varchar(255),"
            + "sitio_id" + " varchar(255),"
            + "created_at" + " datetime,"
            + "updated_at" + " datetime,"
            + "created_by" + " varchar(255),"
            + "updated_by" + " varchar(255),"
            + "status" + " int,"
            + "occupancy_id" + " varchar(255),"
            + "occupancy" + " varchar(255),"
            + "occupancy_type_id" + " varchar(255),"
            + "occupancy_type" + " varchar(255),"
            + "occupancy_type_code" + " varchar(255),"
            + "actual_consumption" + " double,"
            + "amount_due" + " double,"
            + "mf" + " double,"
            + "mr" + " double,"
            + "interest" + " double,"
            + "discount" + " double,"
            + "net_due" + " double,"
            + "is_paid" + " int,"
            + "or_id" + " varchar(255),"
            + "or_no" + " varchar(255),"
            + "date_uploaded" + " date,"
            + "is_uploaded" + "int,"
            + "pipe_size" + " varchar(255)"
            + ")";


    // create table settings
    private static final String TABLE_SETTING = "settings";

    // User Table Columns names
    private static final String COLUMN_TABLE_ID = "id";
    private static final String COLUMN_TABLE_BUSINESS_NAME = "business_name";
    private static final String COLUMN_TABLE_BUSINESS_ADDRESS = "business_address";
    private static final String COLUMN_TABLE_SERVER_IP = "ip_address";

    private String CREATE_SETTING_TABLE = "CREATE TABLE " + TABLE_SETTING + "("
            + COLUMN_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TABLE_BUSINESS_NAME + " TEXT,"
            + COLUMN_TABLE_BUSINESS_ADDRESS + " TEXT," + COLUMN_TABLE_SERVER_IP + " TEXT" + ")";

    // drop table sql query
    private String DROP_METER_READERS_TABLE = "DROP TABLE IF EXISTS " + TABLE_METER_READERS;

    private String DROP_SETTINGS_TABLE = "DROP TABLE IF EXISTS " + TABLE_SETTING;
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_METER_READERS_TABLE);
        db.execSQL(CREATE_SETTING_TABLE);
        db.execSQL(CREATE_METER_READER_ASSIGNMENTS_TABLE);
        db.execSQL(CREATE_READINGS_TABLE);
//        addSetting();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
//        db.execSQL(DROP_METER_READERS_TABLE);
//        db.execSQL(DROP_SETTINGS_TABLE);

        db.execSQL(CREATE_READINGS_TABLE);
        // Create tables again
//        onCreate(db);

    }

    public void addMeterReader(List<MeterReader> readers) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {

            db.execSQL("delete from "+ TABLE_METER_READERS);
            for(MeterReader reader:readers){
                ContentValues values = new ContentValues();
//                values.put(COLUMN_1, reader.getId());
                values.put(COLUMN_2, reader.getMeter_reader_no());
                values.put(COLUMN_3, reader.getFname());
                values.put(COLUMN_4, reader.getMi());
                values.put(COLUMN_5, reader.getLname());
                values.put(COLUMN_6, reader.getCity());
                values.put(COLUMN_7, reader.getCity_id());
                values.put(COLUMN_8, reader.getBarangay());
                values.put(COLUMN_9, reader.getBarangay_id());
                values.put(COLUMN_10, reader.getPurok());
                values.put(COLUMN_11, reader.getPurok_id());
                values.put(COLUMN_12, reader.getAddress());
                values.put(COLUMN_13, reader.getUser_name());
                values.put(COLUMN_14, reader.getPassword());
                // Inserting Row
                db.insert(TABLE_METER_READERS, null, values);
            }

        }catch(Exception e){
            // here you can catch all the exceptions
            Log.e("addMeterReader",e.getMessage());
        } finally {
            db.close();
        }
    }

    public void addMeterReaderAssignments(List<MeterReaderAssignments> readers) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {

            String COlUMN_NAME="meter_reader_no";
            String value=MyUser.getMeter_reader_no();
            db.execSQL("DELETE FROM " + TABLE_METER_READER_ASSIGNMENTS+ " WHERE "+COlUMN_NAME+"='"+value+"'");
            for(MeterReaderAssignments reader:readers){
                ContentValues values = new ContentValues();

                values.put("meter_reader_id", reader.getMeter_reader_id());
                values.put("meter_reader_no", reader.getMeter_reader_no());
                values.put("meter_reader_name", reader.getMeter_reader_name());
                values.put("customer_id", reader.getCustomer_id());
                values.put("customer_no", reader.getCustomer_no());
                values.put("customer_name", reader.getCustomer_name());
                values.put("barangay", reader.getBarangay());
                values.put("barangay_id", reader.getBarangay_id());
                values.put("purok", reader.getPurok());
                values.put("purok_id", reader.getPurok_id());
                values.put("date_added", DateType.now());
                values.put("date_updated", DateType.now());
                values.put("added_by_id", MyUser.getId());
                values.put("update_by_id",MyUser.getId());
                values.put("status", 1);
                values.put("occupancy_id", reader.getOccupancy_id());
                values.put("occupancy", reader.getOccupancy());
                values.put("occupancy_type_id", reader.getOccupancy_type_id());
                values.put("occupancy_type", reader.getOccupancy_type());
                values.put("occupancy_type_code", reader.getOccupancy_type_code());
                values.put("city", reader.getCity());
                values.put("city_id", reader.getCity_id());
                values.put("sitio", reader.getSitio());
                values.put("sitio_id", reader.getSitio_id());
                values.put("meter_no", reader.getMeter_no());
                values.put("pipe_size", reader.getPipe_size());

                // Inserting Row
                db.insert(TABLE_METER_READER_ASSIGNMENTS, null, values);
            }

        }catch(Exception e){
            // here you can catch all the exceptions
            Log.e("addMeterReader",e.getMessage());
        } finally {
            db.close();
        }


    }


    public void addSetting() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("business_name", "business_name");
        values.put("business_address", "business_address");
        values.put("ip_address", "ip_address");

        // Inserting Row
        long newRowId = db.insert(TABLE_SETTING, null, values);

        db.close();

    }

    public List<MeterReader> getAllMeterReaders() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_1,
                COLUMN_2,
                COLUMN_3,
                COLUMN_4,
                COLUMN_5,
                COLUMN_6,
                COLUMN_7,
                COLUMN_8,
                COLUMN_9,
                COLUMN_10,
                COLUMN_11,
                COLUMN_12,
                COLUMN_13,
                COLUMN_14
        };
        // sorting orders
//        String sortOrder =  COLUMN_USER_NAME + " ASC";
        String sortOrder =  "";
        List<MeterReader> meterReaderList = new ArrayList<MeterReader>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        Cursor cursor = db.query(TABLE_METER_READERS, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MeterReader reader = new MeterReader();
                reader.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_1))));
                reader.setMeter_reader_no(cursor.getString(cursor.getColumnIndex(COLUMN_2)));
                reader.setMeter_reader_no(cursor.getString(cursor.getColumnIndex(COLUMN_3)));
                reader.setMeter_reader_no(cursor.getString(cursor.getColumnIndex(COLUMN_4)));
                reader.setMeter_reader_no(cursor.getString(cursor.getColumnIndex(COLUMN_5)));
                reader.setMeter_reader_no(cursor.getString(cursor.getColumnIndex(COLUMN_6)));
                reader.setMeter_reader_no(cursor.getString(cursor.getColumnIndex(COLUMN_7)));
                reader.setMeter_reader_no(cursor.getString(cursor.getColumnIndex(COLUMN_8)));
                reader.setMeter_reader_no(cursor.getString(cursor.getColumnIndex(COLUMN_9)));
                reader.setMeter_reader_no(cursor.getString(cursor.getColumnIndex(COLUMN_10)));
                reader.setMeter_reader_no(cursor.getString(cursor.getColumnIndex(COLUMN_11)));
                reader.setMeter_reader_no(cursor.getString(cursor.getColumnIndex(COLUMN_12)));
                reader.setMeter_reader_no(cursor.getString(cursor.getColumnIndex(COLUMN_13)));
                reader.setMeter_reader_no(cursor.getString(cursor.getColumnIndex(COLUMN_14)));
                // Adding user record to list
                meterReaderList.add(reader);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return meterReaderList;
    }

    public void updateMeterReader(MeterReader reader) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_1, reader.getId());
        values.put(COLUMN_2, reader.getMeter_reader_no());
        values.put(COLUMN_3, reader.getFname());
        values.put(COLUMN_4, reader.getMi());
        values.put(COLUMN_5, reader.getLname());
        values.put(COLUMN_6, reader.getCity());
        values.put(COLUMN_7, reader.getCity_id());
        values.put(COLUMN_8, reader.getBarangay());
        values.put(COLUMN_9, reader.getBarangay_id());
        values.put(COLUMN_10, reader.getPurok());
        values.put(COLUMN_11, reader.getPurok_id());
        values.put(COLUMN_12, reader.getAddress());
        values.put(COLUMN_13, reader.getUser_name());
        values.put(COLUMN_14, reader.getPassword());


        // updating row
        db.update(TABLE_METER_READERS, values, COLUMN_1 + " = ?",
                new String[]{String.valueOf(reader.getId())});
        db.close();
    }
    public void deleteMeterReader(MeterReader reader) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_METER_READERS, COLUMN_1 + " = ?",
                new String[]{String.valueOf(reader.getId())});
        db.close();
    }

    public boolean checkReader(String user_name) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_1
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_13 + " = ?";
        // selection argument
        String[] selectionArgs = {user_name};
        // query user table with condition

        Cursor cursor = db.query(TABLE_METER_READERS, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order


        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public boolean checkUser(String user_name, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_1,COLUMN_2,COLUMN_13,COLUMN_3,COLUMN_4,COLUMN_5
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_13 + " = ?" + " AND " + COLUMN_14 + " = ?";

        // selection arguments
//        Log.i("user_name","user_name: "+user_name);
//        Log.i("password","password1: "+password);
        password= DeEncrypter.encrypt(password);
//        Log.i("password","password2: "+password);
        String[] selectionArgs = {user_name, password};

        // query user table with conditions

        Cursor cursor = db.query(TABLE_METER_READERS, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();
        if (cursor.moveToFirst())
        {
            do
            {
                String id=cursor.getString(0);
                String reader_no=cursor.getString(1);
                String username=cursor.getString(2);
                String name=cursor.getString(3)+ " "+cursor.getString(4)+" "+cursor.getString(5);
                MyUser.setId(id);
                MyUser.setMeter_reader_no(reader_no);
                MyUser.setUsername(username);
                MyUser.setScreen_name(name);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        if (cursorCount > 0) {

            return true;
        }
        return false;
    }

    public ArrayList<HashMap<String, String>> getMeterReaderAssignments(String meter_reader_no) {
        // array of columns to fetch
        String[] columns = {
                "id",
                "meter_reader_id",
                "meter_reader_no",
                "meter_reader_name",
                "customer_id",
                "customer_no",
                "customer_name",
                "barangay",
                "barangay_id",
                "purok",
                "purok_id",
                "date_added",
                "date_updated",
                "added_by_id",
                "update_by_id",
                "status",
                "occupancy_id",
                "occupancy",
                "occupancy_type_id",
                "occupancy_type",
                "occupancy_type_code",
                "city",
                "city_id",
                "sitio",
                "sitio_id",
                "meter_no",
                "pipe_size"
        };
        // sorting orders
//        String sortOrder =  COLUMN_USER_NAME + " ASC";
        String sortOrder =  "";
        List<MeterReaderAssignments> meterReaderAssignments = new ArrayList<MeterReaderAssignments>();
        ArrayList<HashMap<String, String>> consumerList=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        String selection = "meter_reader_no = ?";

        Cursor cursor = db.query(TABLE_METER_READER_ASSIGNMENTS, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                new String[]{String.valueOf(MyUser.getMeter_reader_no())},        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        if (cursor.moveToFirst()) {
            do {
                MeterReaderAssignments reader = new MeterReaderAssignments();
                reader.setId(Integer.parseInt(cursor.getString(0)));
                reader.setMeter_reader_id(cursor.getString(1));
                reader.setMeter_reader_no(cursor.getString(2));
                reader.setMeter_reader_name(cursor.getString(3));
                reader.setCustomer_id(cursor.getString(4));
                reader.setCustomer_no(cursor.getString(5));
                reader.setCustomer_name(cursor.getString(6));
                reader.setBarangay(cursor.getString(7));
                reader.setBarangay_id(cursor.getString(8));
                reader.setPurok(cursor.getString(9));
                reader.setPurok_id(cursor.getString(10));
                reader.setDate_added(cursor.getString(11));
                reader.setDate_updated(cursor.getString(12));
                reader.setAdded_by_id(cursor.getString(13));
                reader.setUpdate_by_id(cursor.getString(14));
                reader.setStatus(Integer.parseInt(cursor.getString(15)));
                reader.setOccupancy_id(cursor.getString(16));
                reader.setOccupancy(cursor.getString(17));
                reader.setOccupancy_type_id(cursor.getString(18));
                reader.setOccupancy_type(cursor.getString(19));
                reader.setOccupancy_type_code(cursor.getString(20));
                reader.setCity(cursor.getString(21));
                reader.setCity_id(cursor.getString(22));
                reader.setSitio(cursor.getString(23));
                reader.setSitio_id(cursor.getString(24));
                reader.setMeter_no(cursor.getString(25));
                reader.setPipe_size(cursor.getString(26));

                HashMap<String, String> reader1 = new HashMap<>();
                // adding each child node to HashMap key => value
                reader1.put("id", ""+reader.getId());
                reader1.put("meter_reader_id", ""+reader.getMeter_reader_id());
                reader1.put("meter_reader_no", ""+reader.getMeter_reader_no());
                reader1.put("meter_reader_name", reader.getMeter_reader_name());
                reader1.put("customer_id", reader.getCustomer_id());
                reader1.put("customer_no", ""+reader.getCustomer_no());
                reader1.put("customer_name", ""+reader.getCustomer_name());
                reader1.put("barangay", ""+reader.getBarangay());
                reader1.put("barangay_id", ""+reader.getBarangay_id());
                reader1.put("purok", ""+reader.getPurok());
                reader1.put("purok_id", ""+reader.getPurok_id());
                reader1.put("date_added", ""+reader.getDate_added());
                reader1.put("date_updated", ""+reader.getDate_updated());
                reader1.put("added_by_id", ""+reader.getAdded_by_id());
                reader1.put("update_by_id", ""+reader.getUpdate_by_id());
                reader1.put("status", ""+reader.getStatus());
                reader1.put("occupancy_id", ""+reader.getOccupancy_id());
                reader1.put("occupancy", ""+reader.getOccupancy());
                reader1.put("occupancy_type_id", ""+reader.getOccupancy_type_id());
                reader1.put("occupancy_type", ""+reader.getOccupancy_type());
                reader1.put("occupancy_type_code", ""+reader.getOccupancy_type_code());
                reader1.put("city", ""+reader.getCity());
                reader1.put("city_id", ""+reader.getCity_id());
                reader1.put("sitio", ""+reader.getSitio());
                reader1.put("sitio_id", ""+reader.getSitio_id());
                reader1.put("meter_no", ""+reader.getMeter_no());
                reader1.put("pipe_size", ""+reader.getPipe_size());
                // adding contact to contact list
                consumerList.add(reader1);

                // Adding user record to list
//                meterReaderAssignments.add(reader);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return consumerList;
    }


}
