package com.vwwsapp.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.vwwsapp.helpers.FitIn;
import com.vwwsapp.helpers.HttpHandler;
import com.vwwsapp.model.MeterReaderAssignments;
import com.vwwsapp.model.MyUser;
import com.vwwsapp.model.OccupancyTypes;
import com.vwwsapp.model.Reading;
import com.vwwsapp.sql.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DownloadMeterReaderAssignmentsActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = AppCompatActivity.class.getSimpleName();
    private AppCompatActivity activity = DownloadMeterReaderAssignmentsActivity.this;
    private ListView lv;

    ArrayList<HashMap<String, String>> readerList;

    List<MeterReaderAssignments> assignments = new ArrayList();
    ArrayList<Reading> readings = new ArrayList<>();
    private AppCompatButton appCompatDownloadReaders;
    private AppCompatButton appCompatSaveToPhone;
    private EditText textInputIpAddress;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_meter_reader_assignments);
        getSupportActionBar().hide();
        initViews();
        initObjects();
        initListeners();

    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        appCompatDownloadReaders = (AppCompatButton) findViewById(R.id.appCompatDownloadReader);
        appCompatSaveToPhone = (AppCompatButton) findViewById(R.id.btnSaveReadersToPhone2);
        textInputIpAddress = (EditText) findViewById(R.id.tf_ip_address);
    }

    private void initListeners() {
        appCompatDownloadReaders.setOnClickListener(this);
        appCompatSaveToPhone.setOnClickListener(this);
    }

    private void initObjects() {

        readerList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);
        dbHelper = new DBHelper(activity);
//        new getAssignments().execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatDownloadReader:
                new getAssignments(activity).execute();
                new getReadingsOverride(activity).execute();

                break;
            case R.id.btnSaveReadersToPhone2:
                confirm_save_to_phone();
                break;
        }
    }

    private class getAssignments extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog;
        public getAssignments(AppCompatActivity activity) {
            dialog = new ProgressDialog(activity);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Downloading Assignments from Server");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            String ip_address = textInputIpAddress.getText().toString().trim();
            HttpHandler sh = new HttpHandler();
            String url = "http://" + ip_address + "/vwws/consumers.php?id=" + MyUser.getMeter_reader_no();
            String jsonStr = sh.makeServiceCall(url);
            if (jsonStr != null) {
                assignments.clear();
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray readers = jsonObj.getJSONArray("readers");

                    // looping through All Contacts
                    for (int i = 0; i < readers.length(); i++) {
                        JSONObject c = readers.getJSONObject(i);
                        int id = FitIn.toInt(c.getString("id"));
                        String meter_reader_id = c.getString("meter_reader_id");
                        String meter_reader_no = c.getString("meter_reader_no");
                        String meter_reader_name = c.getString("meter_reader_name");
                        String customer_id = c.getString("customer_id");
                        String customer_no = c.getString("customer_no");
                        String customer_name = c.getString("customer_name");
                        String barangay = c.getString("barangay");
                        String barangay_id = c.getString("barangay_id");
                        String purok = c.getString("purok");
                        String purok_id = c.getString("purok_id");
                        String date_added = c.getString("date_added");
                        String date_updated = c.getString("date_updated");
                        String added_by_id = c.getString("added_by_id");
                        String update_by_id = c.getString("update_by_id");
                        int status = Integer.parseInt(c.getString("status"));
                        String occupancy_id = c.getString("occupancy_id");
                        String occupancy = c.getString("occupancy");
                        String occupancy_type_id = c.getString("occupancy_type_id");
                        String occupancy_type = c.getString("occupancy_type");
                        String occupancy_type_code = c.getString("occupancy_type_code");
                        String city = c.getString("city");
                        String city_id = c.getString("city_id");
                        String sitio = c.getString("sitio");
                        String sitio_id = c.getString("sitio_id");
                        String meter_no = c.getString("meter_no");
                        String pipe_size = c.getString("pipe_size");

                        MeterReaderAssignments mra = new MeterReaderAssignments(id, meter_reader_id, meter_reader_no, meter_reader_name, customer_id, customer_no, customer_name, barangay, barangay_id, purok, purok_id, date_added, date_updated, added_by_id, update_by_id, status, occupancy_id, occupancy, occupancy_type_id, occupancy_type, occupancy_type_code, city, city_id, sitio, sitio_id, meter_no, pipe_size);
                        assignments.add(mra);
                        HashMap<String, String> reader = new HashMap<>();
                        // adding each child node to HashMap key => value
                        reader.put("id", "" + id);
                        reader.put("meter_reader_id", meter_reader_id);
                        reader.put("meter_reader_no", meter_reader_no);
                        reader.put("meter_reader_name", meter_reader_name);
                        reader.put("customer_id", customer_id);
                        reader.put("customer_no", customer_no);
                        reader.put("customer_name", customer_name);
                        reader.put("barangay", barangay);
                        reader.put("barangay_id", barangay_id);
                        reader.put("purok", purok);
                        reader.put("purok_id", purok_id);
                        reader.put("date_added", date_added);
                        reader.put("date_updated", date_updated);
                        reader.put("added_by_id", added_by_id);
                        reader.put("update_by_id", update_by_id);
                        reader.put("status", "" + status);
                        reader.put("occupancy_id", occupancy_id);
                        reader.put("occupancy", occupancy);
                        reader.put("occupancy_type_id", occupancy_type_id);
                        reader.put("occupancy_type", occupancy_type);
                        reader.put("occupancy_type_code", occupancy_type_code);
                        reader.put("city", city);
                        reader.put("city_id", city_id);
                        reader.put("sitio", sitio);
                        reader.put("sitio_id", sitio_id);
                        reader.put("meter_no", meter_no);
                        reader.put("pipe_size", pipe_size);

                        readerList.add(reader);
                    }

                    Log.w("count", "count: " + readerList.size());

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(activity, readerList,
                    R.layout.item_meter_reader_assignments, new String[]{"customer_no", "customer_name"},
                    new int[]{R.id.customer_no, R.id.customer_name});
            lv.setAdapter(adapter);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

        }
    }

    private void confirm_save_to_phone() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Save data to phone?")
                .setTitle("")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        save_data();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // CANCEL
                    }
                });
        builder.show();
    }

    private class getReadingsOverride extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog;
        public getReadingsOverride(AppCompatActivity activity) {
            dialog = new ProgressDialog(activity);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Downloading Override Readings from Server");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            String ip_address = textInputIpAddress.getText().toString().trim();
            HttpHandler sh = new HttpHandler();
            String url = "http://" + ip_address + "/vwws/readings_override.php?";
            String jsonStr = sh.makeServiceCall(url);
            if (jsonStr != null) {
                assignments.clear();
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray readers = jsonObj.getJSONArray("readers");

                    // looping through All Contacts
                    for (int i = 0; i < readers.length(); i++) {
                        JSONObject c = readers.getJSONObject(i);
                        int id = FitIn.toInt(c.getString("id"));
                        String reading_no = c.getString("reading_no");
                        String meter_reader_id = c.getString("meter_reader_id");
                        String meter_reader_name = c.getString("meter_reader_name");
                        String customer_id = c.getString("customer_id");
                        String customer_no = c.getString("customer_no");
                        String customer_name = c.getString("customer_name");
                        String customer_meter_no = c.getString("customer_meter_no");
                        String previous_reading_date = c.getString("previous_reading_date");
                        double previous_reading = c.getDouble("previous_reading");
                        double current_reading = c.getDouble("current_reading");
                        String city = c.getString("city");
                        String city_id = c.getString("city_id");

                        String barangay = c.getString("barangay");
                        String barangay_id = c.getString("barangay_id");
                        String purok = c.getString("purok");
                        String purok_id = c.getString("purok_id");
                        String sitio = c.getString("sitio");
                        String sitio_id = c.getString("sitio_id");
                        String created_at = c.getString("created_at");
                        String updated_at = c.getString("updated_at");
                        String created_by = c.getString("created_by");
                        String updated_by = c.getString("updated_by");
                        String status = c.getString("status");
                        String occupancy_id = c.getString("occupancy_id");
                        String occupancy = c.getString("occupancy");

                        String occupancy_type_id = c.getString("occupancy_type_id");
                        String occupancy_type = c.getString("occupancy_type");
                        String occupancy_type_code = c.getString("occupancy_type_code");
                        double actual_consumption = c.getDouble("actual_consumption");
                        double amount_due = c.getDouble("amount_due");
                        double mf = c.getDouble("mf");
                        double mr = c.getDouble("mr");
                        double interest = c.getDouble("interest");
                        double discount = c.getDouble("discount");
                        double net_due = c.getDouble("net_due");
                        int is_paid = c.getInt("is_paid");
                        String or_id = c.getString("or_id");
                        String or_no = c.getString("or_no");
                        String date_uploaded = c.getString("date_uploaded");
                        String is_uploaded = c.getString("is_uploaded");
                        String pipe_size = c.getString("pipe_size");
                        Reading read = new Reading(id, reading_no, meter_reader_id, meter_reader_name, customer_id, customer_no, customer_name, customer_meter_no, previous_reading_date, previous_reading, current_reading, city, city_id, barangay, barangay_id, purok, purok_id, sitio, sitio_id, created_at, updated_at, created_by, updated_by, status, occupancy_id, occupancy, occupancy_type_id, occupancy_type, occupancy_type_code, actual_consumption, amount_due, mf, mr, interest, discount, net_due, is_paid, or_id, or_no, date_uploaded, is_uploaded, pipe_size);
                        readings.add(read);

                    }


                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

        }
    }


    private void save_data() {
        List<MeterReaderAssignments> assigns = assignments;
        Log.i("Assignments", "" + assigns.size());
        dbHelper.addMeterReaderAssignments(assignments);
        dbHelper.addReadingsOverride(readings);
        Toast.makeText(DownloadMeterReaderAssignmentsActivity.this, "Record Successfully Added!", Toast.LENGTH_SHORT).show();
        Intent activity2Intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(activity2Intent);
    }
}
