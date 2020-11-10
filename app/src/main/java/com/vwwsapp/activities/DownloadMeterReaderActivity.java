package com.vwwsapp.activities;

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
import com.vwwsapp.model.MeterReader;
import com.vwwsapp.model.OccupancyTypes;
import com.vwwsapp.sql.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DownloadMeterReaderActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = AppCompatActivity.class.getSimpleName();
    private AppCompatActivity activity = DownloadMeterReaderActivity.this;
    private ListView lv;

    ArrayList<HashMap<String, String>> readerList;
    List<MeterReader> meter_readers=new ArrayList();
    List<OccupancyTypes> occupancy_types=new ArrayList();
    private AppCompatButton appCompatDownloadReaders;
    private AppCompatButton appCompatSaveToPhone;
    private EditText textInputIpAddress;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_meter_readers);
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
        appCompatSaveToPhone = (AppCompatButton) findViewById(R.id.btnSaveReadersToPhone);
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
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatDownloadReader:
                new getMeterReaders().execute();
                new getOccupancy_types().execute();
                break;
            case R.id.btnSaveReadersToPhone:
                confirm_save_to_phone();
                break;
        }
    }


    private class getMeterReaders extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            Toast.makeText(activity, "Json Data is downloading", Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            String ip_address=textInputIpAddress.getText().toString().trim();
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "http://"+ip_address+"/vwws/read.php";
            String jsonStr = sh.makeServiceCall(url);

//            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                meter_readers.clear();
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray readers = jsonObj.getJSONArray("readers");

                    // looping through All Contacts
                    for (int i = 0; i < readers.length(); i++) {
                        JSONObject c = readers.getJSONObject(i);
                        String id = c.getString("id");
                        String meter_reader_no = c.getString("meter_reader_no");
                        String name= c.getString("fname")+ " "+c.getString("mi")+ " "+c.getString("lname");
                        // Phone node is JSON Object
                        // tmp hash map for single contact
                        HashMap<String, String> reader = new HashMap<>();
                        // adding each child node to HashMap key => value
                        reader.put("meter_reader_no", meter_reader_no);
                        reader.put("name", name);
                        // adding contact to contact list
                        readerList.add(reader);

                        //Add to Arraylist
                         int id2=Integer.parseInt(c.getString("id"));
                         String meter_reader_no2=c.getString("meter_reader_no");
                         String fname=c.getString("fname");
                         String mi=c.getString("mi");
                         String lname=c.getString("lname");
                         String city=c.getString("city");
                         String city_id=c.getString("city_id");
                         String barangay=c.getString("barangay");
                         String barangay_id=c.getString("barangay_id");
                         String purok=c.getString("purok");
                         String purok_id=c.getString("purok_id");
                         String address=c.getString("address");
                         String user_name=c.getString("user_name");
                         String password=c.getString("password");
                         MeterReader re=new MeterReader(id2,meter_reader_no2,fname,mi,lname,city,city_id,barangay,barangay_id,purok,purok_id,address,user_name,password);
                        meter_readers.add(re);
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
            ListAdapter adapter = new SimpleAdapter(activity, readerList,
                    R.layout.item_meter_readers, new String[]{"id", "meter_reader_no"},
                    new int[]{R.id.meter_reader_no, R.id.name});
            lv.setAdapter(adapter);
        }
    }

    private class getOccupancy_types extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            String ip_address=textInputIpAddress.getText().toString().trim();
            HttpHandler sh = new HttpHandler();
            String url = "http://"+ip_address+"/vwws/occupancy.php";
            String jsonStr = sh.makeServiceCall(url);
            if (jsonStr != null) {
                occupancy_types.clear();
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray readers = jsonObj.getJSONArray("readers");

                    // looping through All Contacts
                    for (int i = 0; i < readers.length(); i++) {
                        JSONObject c = readers.getJSONObject(i);
                        int id = FitIn.toInt(c.getString("id"));
                        String occupancy = c.getString("occupancy");
                        String occupancy_type_id = c.getString("occupancy_type_id");
                        String occupancy_type_name = c.getString("occupancy_type_name");
                        String occupancy_type_code = c.getString("occupancy_type_code");
                        String pipe_size = c.getString("pipe_size");
                        String cubic = c.getString("cubic");
                        double mf = FitIn.toDouble(c.getString("mf"));
                        double mr = FitIn.toDouble(c.getString("mr"));
                        double charge = FitIn.toDouble(c.getString("charge"));
                        String date_added = c.getString("date_added");
                        String occupancy1 = c.getString("occupancy");
                        String date_updated = c.getString("date_updated");
                        String update_by_id = c.getString("update_by_id");
                        int status = FitIn.toInt(c.getString("status"));
                        String remarks = c.getString("remarks");

                        OccupancyTypes occ=new OccupancyTypes(id,occupancy,occupancy_type_id,occupancy_type_name,
                                occupancy_type_code,pipe_size,cubic,mf,mr,charge,date_added,occupancy1,date_updated,update_by_id,status,remarks);

                        occupancy_types.add(occ);
                    }

                    Log.w("count","count: "+occupancy_types.size());

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

        }
    }

    private void confirm_save_to_phone(){
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
    private void save_data(){
        List<MeterReader> readers=meter_readers;
        Log.i("Meter Readers", ""+readers.size());
        Log.i("occupancy_types", ""+occupancy_types.size());
        dbHelper.addMeterReader(readers);
        dbHelper.addOccupancy(occupancy_types);
        Toast.makeText(DownloadMeterReaderActivity.this,"Record Successfully Added!",Toast.LENGTH_SHORT).show();
        Intent activity2Intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(activity2Intent);
    }
}
