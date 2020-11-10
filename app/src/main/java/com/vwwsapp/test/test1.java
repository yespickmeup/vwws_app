package com.vwwsapp.test;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.vwwsapp.activities.R;
import com.vwwsapp.model.MeterReaderAssignments;

import java.util.HashMap;
import java.util.List;

public class test1 {

//    private class getAssignments extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... arg0) {
//            List<MeterReaderAssignments> mra= dbHelper.getMeterReaderAssignments("");
//            Log.i("mra: ",""+mra.size());
//            for(MeterReaderAssignments as:mra){
//                HashMap<String, String> reader = new HashMap<>();
//                // adding each child node to HashMap key => value
//                reader.put("id", ""+as.getId());
//                reader.put("meter_reader_id", as.getMeter_reader_no());
//                reader.put("meter_reader_no", as.getMeter_reader_no());
//                reader.put("meter_reader_name", as.getMeter_reader_name());
//                reader.put("customer_id", as.getCustomer_id());
//                reader.put("customer_no", as.getCustomer_no());
//                reader.put("customer_name", as.getCustomer_name());
//                reader.put("barangay", as.getBarangay());
//                reader.put("barangay_id", as.getBarangay_id());
//                reader.put("purok", as.getPurok());
//                reader.put("purok_id", as.getPurok_id());
//                reader.put("date_added", as.getDate_added());
//                reader.put("date_updated", as.getDate_updated());
//                reader.put("added_by_id", as.getAdded_by_id());
//                reader.put("update_by_id", as.getUpdate_by_id());
//                reader.put("status", ""+as.getStatus());
//                reader.put("occupancy_id", as.getOccupancy_id());
//                reader.put("occupancy", as.getOccupancy());
//                reader.put("occupancy_type_id", as.getOccupancy_type_id());
//                reader.put("occupancy_type", as.getOccupancy_type());
//                reader.put("occupancy_type_code", as.getOccupancy_type_code());
//                reader.put("city", as.getCity());
//                reader.put("city_id", as.getCity_id());
//                reader.put("sitio", as.getSitio());
//                reader.put("sitio_id", as.getSitio_id());
//                reader.put("meter_no", as.getMeter_no());
//                reader.put("pipe_size", as.getPipe_size());
//                // adding pipe_size to contact list
//                readerList2.add(reader);
//            }
//            return null;
//        }
//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//            ListAdapter adapter = new SimpleAdapter(activity, readerList2,
//                    R.layout.item_meter_reader_assignments, new String[]{"customer_no", "customer_name"},
//                    new int[]{R.id.customer_no, R.id.customer_name});
//            recyclerViewUsers.setAdapter(adapter);
//        }
//    }
}
