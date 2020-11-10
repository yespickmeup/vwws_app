package com.vwwsapp.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vwwsapp.helpers.DateType;
import com.vwwsapp.helpers.DeEncrypter;
import com.vwwsapp.helpers.FitIn;
import com.vwwsapp.helpers.ReceiptIncrementor;
import com.vwwsapp.model.MeterReaderAssignments;
import com.vwwsapp.model.MyUser;
import com.vwwsapp.model.OccupancyTypes;
import com.vwwsapp.model.Reading;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DBReadings extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "vwwsapp.db";


    public DBReadings(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String increment_no() {
        // array of columns to fetch
        String id = MyUser.getMeter_reader_no() + "-000000000000";
        String[] columns = {
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select max(id) from readings where meter_reader_id like '" + MyUser.getMeter_reader_no()+"' ";
        Log.i("query","query: "+query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getString(0);
                String query2 = "select reading_no from readings where id=" + id;
                Cursor cursor2 = db.rawQuery(query2, null);
                if (cursor2.moveToFirst()) {
                    do {
                        id = cursor2.getString(0);
                    } while (cursor2.moveToNext());
                }
            } while (cursor.moveToNext());
        }
        Log.i("id","id: "+id);
        if (id == null) {
            id = MyUser.getMeter_reader_no() + "-000000000000";
        }

        id = ReceiptIncrementor.increment(id);
        cursor.close();
        db.close();

        // return user list
        return id;
    }


    public List<OccupancyTypes> ret_type(String occupancy_type_code, String occupancy_type, String pipe_size) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = " select * from occupancy_types where occupancy_type_code like'" + occupancy_type_code + "' and occupancy like '" + occupancy_type + "' and pipe_size like '" + pipe_size + "'";
        List<OccupancyTypes> list = new ArrayList();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                OccupancyTypes occ = new OccupancyTypes();
                occ.setId(Integer.parseInt(cursor.getString(0)));
                occ.setOccupancy(cursor.getString(1));
                occ.setOccupancy_type_id(cursor.getString(2));
                occ.setOccupancy_type_name(cursor.getString(3));
                occ.setOccupancy_type_code(cursor.getString(4));

                occ.setPipe_size(cursor.getString(5));
                occ.setCubic(cursor.getString(6));
                occ.setMf(Double.parseDouble(cursor.getString(7)));
                occ.setMr(Double.parseDouble(cursor.getString(8)));
                occ.setCharge(Double.parseDouble(cursor.getString(9)));

                occ.setDate_added(cursor.getString(10));
                occ.setDate_updated(cursor.getString(11));
                occ.setAdded_by_id(cursor.getString(12));
                occ.setUpdate_by_id(cursor.getString(13));
                occ.setStatus(Integer.parseInt(cursor.getString(14)));
                occ.setRemarks(cursor.getString(15));
                list.add(occ);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return list;
    }

    public void addReading(Reading reading) {

        SQLiteDatabase db = this.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put("reading_no", reading.getReading_no());
            values.put("meter_reader_id", reading.getMeter_reader_id());
            values.put("meter_reader_name", reading.getMeter_reader_name());
            values.put("customer_id", reading.getCustomer_id());
            values.put("customer_no", reading.getCustomer_no());
            values.put("customer_name", reading.getCustomer_name());
            values.put("customer_meter_no", reading.getCustomer_meter_no());
            values.put("previous_reading_date", reading.getPrevious_reading_date());
            values.put("previous_reading", reading.getPrevious_reading());
            values.put("current_reading", reading.getCurrent_reading());
            values.put("city", reading.getCity());
            values.put("city_id", reading.getCity_id());
            values.put("barangay", reading.getBarangay());
            values.put("barangay_id", reading.getBarangay_id());
            values.put("purok", reading.getPurok());
            values.put("purok_id", reading.getPurok_id());
            values.put("sitio", reading.getSitio());
            values.put("sitio_id", reading.getSitio_id());
            values.put("created_at", reading.getCreated_at());
            values.put("updated_at", reading.getUpdated_at());
            values.put("created_by", reading.getCreated_by());
            values.put("updated_by", reading.getUpdated_by());
            values.put("status", reading.getStatus());
            values.put("occupancy_id", reading.getOccupancy_id());
            values.put("occupancy", reading.getOccupancy());
            values.put("occupancy_type_id", reading.getOccupancy_type_id());
            values.put("occupancy_type", reading.getOccupancy_type());
            values.put("occupancy_type_code", reading.getOccupancy_type_code());
            values.put("actual_consumption", reading.getActual_consumption());
            values.put("amount_due", reading.getAmount_due());
            values.put("mf", reading.getMf());
            values.put("mr", reading.getMr());
            values.put("interest", reading.getInterest());
            values.put("net_due", reading.getNet_due());
            values.put("is_paid", reading.getIs_paid());
            values.put("or_id", reading.getOr_id());
            values.put("or_no", reading.getOr_no());
            values.put("date_uploaded", reading.getDate_uploaded());
            values.put("is_uploaded", reading.getIs_uploaded());
            values.put("pipe_size", reading.getPipe_size());

            // Inserting Row
            db.insert("readings", null, values);

        } catch (Exception e) {
            // here you can catch all the exceptions
            Log.e("add Reading", e.getMessage());
        } finally {
            db.close();
        }
    }

    public void updateReading(Reading reading) {

        SQLiteDatabase db = this.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put("current_reading", reading.getCurrent_reading());
            values.put("updated_at", reading.getUpdated_by());
            values.put("updated_by", reading.getUpdated_by());
            values.put("actual_consumption", reading.getActual_consumption());
            values.put("amount_due", reading.getAmount_due());
            values.put("mf", reading.getMf());
            values.put("mr", reading.getMr());
            values.put("interest", reading.getInterest());
            values.put("net_due", reading.getNet_due());

            // Inserting Row
            db.update("readings", values, " id = ?", new String[]{"" + reading.getId()});

        } catch (Exception e) {
            // here you can catch all the exceptions
            Log.e("add Reading", e.getMessage());
        } finally {
            db.close();
        }
    }

    public ArrayList<MeterReaderAssignments> getMeterReaderAssignments(String meter_reader_no) {
        // array of columns to fetch

        // sorting orders
//        String sortOrder =  COLUMN_USER_NAME + " ASC";
        String sortOrder = "";
        ArrayList<MeterReaderAssignments> meterReaderAssignments = new ArrayList<MeterReaderAssignments>();
        ArrayList<HashMap<String, String>> consumerList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        int year = FitIn.toInt(DateType.y.format(new Date()));
        int month = FitIn.toInt(DateType.m1.format(new Date()));

        // query the user table
        String selection = "meter_reader_no = ?";
        String s0 = "select "
                + "mra.id"
                + ",mra.meter_reader_id"
                + ",mra.meter_reader_no"
                + ",mra.meter_reader_name"
                + ",mra.customer_id"
                + ",mra.customer_no"
                + ",mra.customer_name"
                + ",mra.barangay"
                + ",mra.barangay_id"
                + ",mra.purok"
                + ",mra.purok_id"
                + ",mra.date_added"
                + ",mra.date_updated"
                + ",mra.added_by_id"
                + ",mra.update_by_id"
                + ",mra.status"
                + ",mra.occupancy_id"
                + ",mra.occupancy"
                + ",mra.occupancy_type_id"
                + ",mra.occupancy_type"
                + ",mra.occupancy_type_code"
                + ",mra.city"
                + ",mra.city_id"
                + ",mra.sitio"
                + ",mra.sitio_id"
                + ",ifnull(mra.meter_no,'')"
                + ",mra.pipe_size"
                + " from meter_reader_assignments mra "
                + " where mra.meter_reader_no like '" + MyUser.getMeter_reader_no() + "' order by mra.customer_name asc";

        Cursor cursor = db.rawQuery(s0, null);


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

                reader.setMeter_no(cursor.getString(25));
                reader.setPipe_size(cursor.getString(26));

                String s2 = "select "
                        + " id"
                        + ",customer_id"
                        + ",created_at"
                        + ",previous_reading"
                        + ",current_reading"
                        + ",previous_reading_date"
                        + ",created_at"
                        + ",updated_by"
                        + " from readings"
                        + " where customer_no='" + reader.getCustomer_no() + "' order by created_at desc limit 1";

                Cursor cursor2 = db.rawQuery(s2, null);
                Date d = new Date();
                int status = 0;
                int id2 = 0;
                String update_by_id = reader.getUpdate_by_id();
                String sitio_id = "cross1.png";
                reader.setSitio("0");
                reader.setSitio_id("0");
                reader.setPurok("");
                reader.setPurok_id("");
                reader.setAdded_by_id("");
                if (cursor2.moveToFirst()) {
                    do {
                        try {
                            String date = cursor2.getString(2);
                            reader.setSitio(cursor2.getString(3));
                            reader.setSitio_id(cursor2.getString(4));
                            reader.setPurok(cursor2.getString(5));
                            reader.setPurok_id(cursor2.getString(6));
                            reader.setAdded_by_id(cursor2.getString(7));
                            d = DateType.datetime.parse(date);
                            int year2 = FitIn.toInt(DateType.y.format(d));
                            int month2 = FitIn.toInt(DateType.m1.format(d));
                            if (year == year2 && month == month2) {
                                status = 1;
                                update_by_id = "" + cursor2.getInt(0);
                                id2 = cursor2.getInt(0);
                                sitio_id = "checked.png";
                            }
                        } catch (ParseException ex) {
                            System.out.println(ex);
                        }
                    } while (cursor2.moveToNext());
                }
                reader.setUpdate_by_id(update_by_id);
                reader.setId(id2);
                reader.setStatus(status);
//                reader.setSitio_id(sitio_id);
//                Log.i("Consumer","Consumer: "+reader.getCustomer_name()+ " "+reader.getStatus());
                meterReaderAssignments.add(reader);

                // Adding user record to list
//                meterReaderAssignments.add(reader);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return meterReaderAssignments;
    }

    public Reading getReading(String reading_no1) {

        Reading reading = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String s0 = "select "
                + "id"
                + ",reading_no"
                + ",meter_reader_id"
                + ",meter_reader_name"
                + ",customer_id"
                + ",customer_no"
                + ",customer_name"
                + ",customer_meter_no"
                + ",previous_reading_date"
                + ",previous_reading"
                + ",current_reading"
                + ",city"
                + ",city_id"
                + ",barangay"
                + ",barangay_id"
                + ",purok"
                + ",purok_id"
                + ",sitio"
                + ",sitio_id"
                + ",created_at"
                + ",updated_at"
                + ",created_by"
                + ",updated_by"
                + ",status"
                + ",occupancy_id"
                + ",occupancy"
                + ",occupancy_type_id"
                + ",occupancy_type"
                + ",occupancy_type_code"
                + ",actual_consumption"
                + ",amount_due"
                + ",mf"
                + ",mr"
                + ",interest"
                + ",discount"
                + ",net_due"
                + ",is_paid"
                + ",or_id"
                + ",or_no"
                + ",date_uploaded"
                + ",is_uploaded"
                + ",pipe_size"
                + " from readings"
                + " where id='" + reading_no1 + "'";

        Cursor cursor2 = db.rawQuery(s0, null);
        if (cursor2.moveToFirst()) {
            do {
                int id = cursor2.getInt(0);
                String reading_no = cursor2.getString(1);
                String meter_reader_id = cursor2.getString(2);
                String meter_reader_name = cursor2.getString(3);
                String customer_id = cursor2.getString(4);
                String customer_no = cursor2.getString(5);
                String customer_name = cursor2.getString(6);
                String customer_meter_no = cursor2.getString(7);
                String previous_reading_date = cursor2.getString(8);
                double previous_reading = cursor2.getDouble(9);
                double current_reading = cursor2.getDouble(10);

                String city = cursor2.getString(11);
                String city_id = cursor2.getString(12);
                String barangay = cursor2.getString(13);
                String barangay_id = cursor2.getString(14);
                String puro = cursor2.getString(15);
                String purok_id = cursor2.getString(16);
                String sitio = cursor2.getString(17);
                String sitio_id = cursor2.getString(18);
                String created_at = cursor2.getString(19);
                String updated_at = cursor2.getString(20);

                String created_by = cursor2.getString(21);
                String updated_by = cursor2.getString(22);
                String status = cursor2.getString(23);
                String occupancy_id = cursor2.getString(24);
                String occupancy = cursor2.getString(25);
                String occupancy_type_id = cursor2.getString(26);
                String occupancy_type = cursor2.getString(27);
                String occupancy_type_code = cursor2.getString(28);

                double actual_consumption = cursor2.getDouble(29);
                double amount_due = cursor2.getDouble(30);
                double mf = cursor2.getDouble(31);
                double mr = cursor2.getDouble(32);
                double interest = cursor2.getDouble(33);
                double discount = cursor2.getDouble(34);
                double net_due = cursor2.getDouble(35);
                int is_paid = cursor2.getInt(36);
                String or_id = cursor2.getString(37);
                String or_no = cursor2.getString(38);
                String date_uploaded = cursor2.getString(39);
                String is_uploaded = cursor2.getString(40);
                String pipe_size = cursor2.getString(41);

                reading = new Reading(id, reading_no, meter_reader_id, meter_reader_name, customer_id, customer_no, customer_name, customer_meter_no, previous_reading_date, previous_reading, current_reading, city, city_id, barangay, barangay_id, puro, purok_id, sitio, sitio_id, created_at, updated_at, created_by, updated_by, status, occupancy_id, occupancy, occupancy_type_id, occupancy_type, occupancy_type_code, actual_consumption, amount_due, mf, mr, interest, discount, net_due, is_paid, or_id
                        , or_no, date_uploaded, is_uploaded, pipe_size);
            } while (cursor2.moveToNext());

        }

        cursor2.close();
        db.close();
        return reading;
    }

    public ArrayList<Reading> getReadings(String where) {

        ArrayList<Reading> readings = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String s0 = "select "
                + "id"
                + ",reading_no"
                + ",meter_reader_id"
                + ",meter_reader_name"
                + ",customer_id"
                + ",customer_no"
                + ",customer_name"
                + ",customer_meter_no"
                + ",previous_reading_date"
                + ",previous_reading"
                + ",current_reading"
                + ",city"
                + ",city_id"
                + ",barangay"
                + ",barangay_id"
                + ",purok"
                + ",purok_id"
                + ",sitio"
                + ",sitio_id"
                + ",created_at"
                + ",updated_at"
                + ",created_by"
                + ",updated_by"
                + ",status"
                + ",occupancy_id"
                + ",occupancy"
                + ",occupancy_type_id"
                + ",occupancy_type"
                + ",occupancy_type_code"
                + ",actual_consumption"
                + ",amount_due"
                + ",mf"
                + ",mr"
                + ",interest"
                + ",discount"
                + ",net_due"
                + ",is_paid"
                + ",or_id"
                + ",or_no"
                + ",date_uploaded"
                + ",is_uploaded"
                + ",pipe_size"
                + " from readings"
                + " "+where;

        Cursor cursor2 = db.rawQuery(s0, null);
        if (cursor2.moveToFirst()) {
            do {
                int id = cursor2.getInt(0);
                String reading_no = cursor2.getString(1);
                String meter_reader_id = cursor2.getString(2);
                String meter_reader_name = cursor2.getString(3);
                String customer_id = cursor2.getString(4);
                String customer_no = cursor2.getString(5);
                String customer_name = cursor2.getString(6);
                String customer_meter_no = cursor2.getString(7);
                String previous_reading_date = cursor2.getString(8);
                double previous_reading = cursor2.getDouble(9);
                double current_reading = cursor2.getDouble(10);

                String city = cursor2.getString(11);
                String city_id = cursor2.getString(12);
                String barangay = cursor2.getString(13);
                String barangay_id = cursor2.getString(14);
                String puro = cursor2.getString(15);
                String purok_id = cursor2.getString(16);
                String sitio = cursor2.getString(17);
                String sitio_id = cursor2.getString(18);
                String created_at = cursor2.getString(19);
                String updated_at = cursor2.getString(20);

                String created_by = cursor2.getString(21);
                String updated_by = cursor2.getString(22);
                String status = cursor2.getString(23);
                String occupancy_id = cursor2.getString(24);
                String occupancy = cursor2.getString(25);
                String occupancy_type_id = cursor2.getString(26);
                String occupancy_type = cursor2.getString(27);
                String occupancy_type_code = cursor2.getString(28);

                double actual_consumption = cursor2.getDouble(29);
                double amount_due = cursor2.getDouble(30);
                double mf = cursor2.getDouble(31);
                double mr = cursor2.getDouble(32);
                double interest = cursor2.getDouble(33);
                double discount = cursor2.getDouble(34);
                double net_due = cursor2.getDouble(35);
                int is_paid = cursor2.getInt(36);
                String or_id = cursor2.getString(37);
                String or_no = cursor2.getString(38);
                String date_uploaded = cursor2.getString(39);
                String is_uploaded = cursor2.getString(40);
                String pipe_size = cursor2.getString(41);

               Reading reading = new Reading(id, reading_no, meter_reader_id, meter_reader_name, customer_id, customer_no, customer_name, customer_meter_no, previous_reading_date, previous_reading, current_reading, city, city_id, barangay, barangay_id, puro, purok_id, sitio, sitio_id, created_at, updated_at, created_by, updated_by, status, occupancy_id, occupancy, occupancy_type_id, occupancy_type, occupancy_type_code, actual_consumption, amount_due, mf, mr, interest, discount, net_due, is_paid, or_id
                        , or_no, date_uploaded, is_uploaded, pipe_size);
                readings.add(reading);
            } while (cursor2.moveToNext());

        }

        cursor2.close();
        db.close();
        return readings;
    }

    public void updateReadingUploaded(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put("is_uploaded",1);
//            Log.i("id:","id:"+id);
            // Inserting Row
            db.update("readings", values, " id = ?", new String[]{"" + id});

        } catch (Exception e) {
            // here you can catch all the exceptions
            Log.e("add Reading", e.getMessage());
        } finally {
            db.close();
        }
    }

}
