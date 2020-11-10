package com.vwwsapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vwwsapp.helpers.DateType;
import com.vwwsapp.helpers.HttpHandler;
import com.vwwsapp.model.Reading;
import com.vwwsapp.sql.DBReadings;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class UploadReadingsActivity extends AppCompatActivity implements View.OnClickListener {
    private AppCompatActivity activity = UploadReadingsActivity.this;
    private DBReadings readings;
    private TextView lbl_readings_count;
    private TextView lbl_readings_ip;
    private ArrayList<Reading> reading_list;
    private Button btn_upload_readings;
    private Button btn_cancel_upload_readings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_readings);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();


    }


    private void initViews() {
        lbl_readings_count = (TextView) findViewById(R.id.lbl_no_to_upload);
        lbl_readings_ip = (TextView) findViewById(R.id.tf_ip_address);

        btn_upload_readings = (Button) findViewById(R.id.btn_upload_readings);
        btn_cancel_upload_readings = (Button) findViewById(R.id.btn_cancel_upload_readings);

    }

    private void initListeners() {

    }

    private void initObjects() {
        reading_list = new ArrayList<>();
        readings = new DBReadings(activity);

        ret_readings();
        btn_upload_readings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new uploadReadings(activity).execute();

            }
        });
        btn_cancel_upload_readings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
            }
        });
    }



    private void ret_readings(){
        reading_list = readings.getReadings(" where is_uploaded=0");
        lbl_readings_count.setText("" + reading_list.size());
    }
    @Override
    public void onClick(View v) {

    }


    private class uploadReadings extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog;
        public uploadReadings(AppCompatActivity activity) {
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            dialog.setMessage("Uploading data to Server");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            String ip_address = lbl_readings_ip.getText().toString().trim();
            HttpClient client = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(client.getParams(),
                    10000); // Timeout Limit
            HttpResponse response;
            JSONObject json = new JSONObject();
            // Making a request to url and getting response
            String url = "http://" + ip_address + "/vwws/insert.php";

            for (Reading read : reading_list) {
                try {
                    String data = URLEncoder.encode("reading_no", "UTF-8") + "=" + URLEncoder.encode(read.getReading_no(), "UTF-8");
                    data += "&" + URLEncoder.encode("meter_reader_id", "UTF-8") + "=" + URLEncoder.encode(read.getMeter_reader_id(), "UTF-8");
                    data += "&" + URLEncoder.encode("meter_reader_name", "UTF-8") + "=" + URLEncoder.encode(read.getMeter_reader_name(), "UTF-8");
                    data += "&" + URLEncoder.encode("customer_id", "UTF-8") + "=" + URLEncoder.encode(read.getCustomer_id(), "UTF-8");
                    data += "&" + URLEncoder.encode("customer_no", "UTF-8") + "=" + URLEncoder.encode(read.getCustomer_no(), "UTF-8");
                    data += "&" + URLEncoder.encode("customer_name", "UTF-8") + "=" + URLEncoder.encode(read.getCustomer_name(), "UTF-8");
                    data += "&" + URLEncoder.encode("customer_meter_no", "UTF-8") + "=" + URLEncoder.encode(read.getCustomer_meter_no(), "UTF-8");
                    data += "&" + URLEncoder.encode("previous_reading_date", "UTF-8") + "=" + URLEncoder.encode(read.getPrevious_reading_date(), "UTF-8");
                    data += "&" + URLEncoder.encode("previous_reading", "UTF-8") + "=" + URLEncoder.encode(""+read.getPrevious_reading(), "UTF-8");
                    data += "&" + URLEncoder.encode("current_reading", "UTF-8") + "=" + URLEncoder.encode(""+read.getCurrent_reading(), "UTF-8");


                    data += "&" + URLEncoder.encode("city", "UTF-8") + "=" + URLEncoder.encode(read.getCity(), "UTF-8");
                    data += "&" + URLEncoder.encode("city_id", "UTF-8") + "=" + URLEncoder.encode(read.getCity_id(), "UTF-8");
                    data += "&" + URLEncoder.encode("barangay", "UTF-8") + "=" + URLEncoder.encode(read.getBarangay(), "UTF-8");
                    data += "&" + URLEncoder.encode("barangay_id", "UTF-8") + "=" + URLEncoder.encode(read.getBarangay_id(), "UTF-8");
                    data += "&" + URLEncoder.encode("purok", "UTF-8") + "=" + URLEncoder.encode(read.getPurok(), "UTF-8");
                    data += "&" + URLEncoder.encode("purok_id", "UTF-8") + "=" + URLEncoder.encode(read.getPurok_id(), "UTF-8");
                    data += "&" + URLEncoder.encode("sitio", "UTF-8") + "=" + URLEncoder.encode(read.getSitio(), "UTF-8");
                    data += "&" + URLEncoder.encode("sitio_id", "UTF-8") + "=" + URLEncoder.encode(read.getSitio_id(), "UTF-8");
                    data += "&" + URLEncoder.encode("created_at", "UTF-8") + "=" + URLEncoder.encode(read.getCreated_at(), "UTF-8");
                    data += "&" + URLEncoder.encode("updated_at", "UTF-8") + "=" + URLEncoder.encode(read.getUpdated_at(), "UTF-8");
                    data += "&" + URLEncoder.encode("created_by", "UTF-8") + "=" + URLEncoder.encode(read.getCreated_by(), "UTF-8");
                    data += "&" + URLEncoder.encode("updated_by", "UTF-8") + "=" + URLEncoder.encode(read.getUpdated_by(), "UTF-8");
                    data += "&" + URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode(read.getStatus(), "UTF-8");
                    data += "&" + URLEncoder.encode("occupancy_id", "UTF-8") + "=" + URLEncoder.encode(read.getOccupancy_id(), "UTF-8");
                    data += "&" + URLEncoder.encode("occupancy", "UTF-8") + "=" + URLEncoder.encode(read.getOccupancy(), "UTF-8");

                    data += "&" + URLEncoder.encode("occupancy_type_id", "UTF-8") + "=" + URLEncoder.encode(read.getOccupancy_type_id(), "UTF-8");
                    data += "&" + URLEncoder.encode("occupancy_type", "UTF-8") + "=" + URLEncoder.encode(read.getOccupancy_type(), "UTF-8");
                    data += "&" + URLEncoder.encode("occupancy_type_code", "UTF-8") + "=" + URLEncoder.encode(read.getOccupancy_type_code(), "UTF-8");
                    data += "&" + URLEncoder.encode("actual_consumption", "UTF-8") + "=" + URLEncoder.encode(""+read.getActual_consumption(), "UTF-8");
                    data += "&" + URLEncoder.encode("amount_due", "UTF-8") + "=" + URLEncoder.encode(""+read.getAmount_due(), "UTF-8");
                    data += "&" + URLEncoder.encode("mf", "UTF-8") + "=" + URLEncoder.encode(""+read.getMf(), "UTF-8");
                    data += "&" + URLEncoder.encode("mr", "UTF-8") + "=" + URLEncoder.encode(""+read.getMr(), "UTF-8");
                    data += "&" + URLEncoder.encode("interest", "UTF-8") + "=" + URLEncoder.encode(""+read.getInterest(), "UTF-8");
                    data += "&" + URLEncoder.encode("discount", "UTF-8") + "=" + URLEncoder.encode(""+read.getDiscount(), "UTF-8");
                    data += "&" + URLEncoder.encode("net_due", "UTF-8") + "=" + URLEncoder.encode(""+read.getNet_due(), "UTF-8");

                    data += "&" + URLEncoder.encode("is_paid", "UTF-8") + "=" + URLEncoder.encode(""+read.getIs_paid(), "UTF-8");
                    data += "&" + URLEncoder.encode("or_id", "UTF-8") + "=" + URLEncoder.encode(read.getOr_id(), "UTF-8");
                    data += "&" + URLEncoder.encode("or_no", "UTF-8") + "=" + URLEncoder.encode(read.getOr_no(), "UTF-8");
                    data += "&" + URLEncoder.encode("date_uploaded", "UTF-8") + "=" + URLEncoder.encode(DateType.now(), "UTF-8");
                    data += "&" + URLEncoder.encode("is_uploaded", "UTF-8") + "=" + URLEncoder.encode(read.getIs_uploaded(), "UTF-8");
                    data += "&" + URLEncoder.encode("pipe_size", "UTF-8") + "=" + URLEncoder.encode(read.getPipe_size(), "UTF-8");


                    String text = "";
                    BufferedReader reader = null;

                    // Send data
                    try {

                        // Defined URL  where to send data
                        URL urls = new URL(url);
                        // Send POST data request
                        URLConnection conn = urls.openConnection();
                        conn.setDoOutput(true);
                        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                        wr.write(data);
                        wr.flush();

                        // Get the server response

                        reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line = null;

                        // Read Server Response
                        while ((line = reader.readLine()) != null) {
                            // Append server response in string
                            sb.append(line + "\n");
                        }

                        text = sb.toString();

                        String msg="{\"message\":\"Data Inserted Successfully\"}";

                        if(text.contains("Inserted")){
                            readings.updateReadingUploaded(""+read.getId());
                        }

                    } catch (Exception ex) {
                        Log.d("error", "error: " + ex);
                    } finally {
                        try {

                            reader.close();
                        } catch (Exception ex) {
                            Log.d("error", "error: " + ex);
                        }
                    }

                    // Show response on activity



                } catch (UnsupportedEncodingException ex) {
                    Log.d("error", "error: " + ex);

                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ret_readings();
            Toast.makeText(getApplicationContext(), "Successfully Uploaded",Toast.LENGTH_SHORT).show();
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

    }
}
