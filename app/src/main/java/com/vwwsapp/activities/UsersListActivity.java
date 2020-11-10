package com.vwwsapp.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.vwwsapp.adapters.ConsumersAdapter;
import com.vwwsapp.adapters.ReadingAdapter;
import com.vwwsapp.helpers.BluetoothService;
import com.vwwsapp.helpers.Command;
import com.vwwsapp.helpers.DateType;
import com.vwwsapp.helpers.DateUtils1;
import com.vwwsapp.helpers.DeviceListActivity;
import com.vwwsapp.helpers.FitIn;
import com.vwwsapp.helpers.PrinterCommand;
import com.vwwsapp.model.MeterReaderAssignments;
import com.vwwsapp.model.MyUser;
import com.vwwsapp.model.OccupancyTypes;
import com.vwwsapp.model.Reading;
import com.vwwsapp.model.User;
import com.vwwsapp.sql.DBHelper;
import com.vwwsapp.sql.DBReadings;
import com.vwwsapp.sql.DatabaseHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lalit on 10/10/2016.
 */

public class UsersListActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private AppCompatActivity activity = UsersListActivity.this;
    private AppCompatTextView textViewName;
    private AppCompatTextView textViewRows;
    private AppCompatTextView lbl_reading_pending;
    private AppCompatTextView lbl_reading_checked;
    private AppCompatTextView lbl_rows_uploaded;
    private ListView recyclerViewUsers;
    private ListView recyclerViewConsumers;
    private List<User> listUsers;
    private List<MeterReaderAssignments> listAssignments;

    private ConsumersAdapter consumersAdapter;
    private DatabaseHelper databaseHelper;
    ArrayList<HashMap<String, String>> readerList2;
    private DBHelper dbHelper;
    private ListView lv;
    final Context context = this;
    private DBReadings dbHelperReadings;
    ArrayList<HashMap<String, String>> userList = null;
    private TextView lbl_connected_to;
    private Button btn_test_print = null;
    private UploadReadingsActivity ura;
    //Init Bluetooth
    ImageView imageViewPicture;
    private static final String TAG = "Main_Activity";
    private static final boolean DEBUG = true;
    /******************************************************************************************************/
    // Message types sent from the BluetoothService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final int MESSAGE_CONNECTION_LOST = 6;
    public static final int MESSAGE_UNABLE_CONNECT = 7;
    /*******************************************************************************************************/
    // Key names received from the BluetoothService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int REQUEST_CHOSE_BMP = 3;
    private static final int REQUEST_CAMER = 4;

    private static final String CHINESE = "GBK";
    private static final String THAI = "CP874";
    private static final String KOREAN = "EUC-KR";
    private static final String BIG5 = "BIG5";

    private String mConnectedDeviceName = null;
    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothService mService = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        getSupportActionBar().hide();
        initViews();
        initObjects();

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the session
        } else {
            if (mService == null)
                KeyListenerInit();
        }

    }

    @Override
    public synchronized void onResume() {
        super.onResume();

        if (mService != null) {

            if (mService.getState() == BluetoothService.STATE_NONE) {
                // Start the Bluetooth services
                mService.start();
            }
        }
    }

    @Override
    public synchronized void onPause() {
        super.onPause();
        if (DEBUG)
            Log.e(TAG, "- ON PAUSE -");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (DEBUG)
            Log.e(TAG, "-- ON STOP --");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth services
        if (mService != null)
            mService.stop();
        if (DEBUG)
            Log.e(TAG, "--- ON DESTROY ---");
    }

    private void KeyListenerInit() {

        Bitmap bm = getImageFromAssetsFile("demo.bmp");
        if (null != bm) {
            imageViewPicture.setImageBitmap(bm);
        }

        mService = new BluetoothService(this, mHandler);
        Intent serverIntent = new Intent(UsersListActivity.this, DeviceListActivity.class);
        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
    }

    private Bitmap getImageFromAssetsFile(String fileName) {
        Bitmap image = null;
        AssetManager am = getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;

    }

    /****************************************************************************************************/
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    if (DEBUG)
                        Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:

                            lbl_connected_to.setText(R.string.title_connected_to);
                            lbl_connected_to.append(mConnectedDeviceName);


                            break;
                        case BluetoothService.STATE_CONNECTING:
                            lbl_connected_to.setText(R.string.title_connecting);

                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
                            lbl_connected_to.setText(R.string.title_not_connected);

                            break;
                    }
                    break;
                case MESSAGE_WRITE:

                    break;
                case MESSAGE_READ:

                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(),
                            "Connected to " + mConnectedDeviceName,
                            Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(),
                            msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case MESSAGE_CONNECTION_LOST:    //???????
                    Toast.makeText(getApplicationContext(), "Device connection was lost",
                            Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_UNABLE_CONNECT:     //??????
                    Toast.makeText(getApplicationContext(), "Unable to connect device",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    private void Print_Test() {
        String lang = getString(R.string.strLang);
        String msg = "Congratulations!\n\n";
        String data = "You have sucessfully created communications between your device and our bluetooth printer.\n"
                + "  the company is a high-tech enterprise which specializes" +
                " in R&D,manufacturing,marketing of thermal printers and barcode scanners.\n\n";
//        SendDataByte(PrinterCommand.POS_Print_Text(msg, CHINESE, 0, 1, 1, 0));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (DEBUG)
            Log.d(TAG, "onActivityResult1 " + resultCode);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE: {
                // When DeviceListActivity returns with a device to connect
                if (resultCode == UsersListActivity.RESULT_OK) {
                    // Get the device MAC address
                    String address = data.getExtras().getString(
                            DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // Get the BLuetoothDevice object
                    if (BluetoothAdapter.checkBluetoothAddress(address)) {
                        BluetoothDevice device = mBluetoothAdapter
                                .getRemoteDevice(address);
                        // Attempt to connect to the device
                        mService.connect(device);
                    }
                }
                break;
            }
            case REQUEST_ENABLE_BT: {
                // When the request to enable Bluetooth returns
                if (resultCode == UsersListActivity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a session
                    KeyListenerInit();
                } else {
                    // User did not enable Bluetooth or an error occured
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(this, R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            }
            case REQUEST_CHOSE_BMP: {
                if (resultCode == UsersListActivity.RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.MediaColumns.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    opts.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(picturePath, opts);
                    opts.inJustDecodeBounds = false;
                    if (opts.outWidth > 1200) {
                        opts.inSampleSize = opts.outWidth / 1200;
                    }
                    Bitmap bitmap = BitmapFactory.decodeFile(picturePath, opts);
                    if (null != bitmap) {
                        imageViewPicture.setImageBitmap(bitmap);
                    }
                } else {
                    Toast.makeText(this, getString(R.string.msg_statev1), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case REQUEST_CAMER: {
                if (resultCode == UsersListActivity.RESULT_OK) {
//                    handleSmallCameraPhoto(data);
                } else {
                    Toast.makeText(this, getText(R.string.camer), Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

/****************************************************************************************************/

    /**
     * This method is to initialize views
     */
    private void initViews() {
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        textViewRows = (AppCompatTextView) findViewById(R.id.lbl_rows1);
        recyclerViewUsers = (ListView) findViewById(R.id.recyclerViewUsers);
        lbl_connected_to = (TextView) findViewById(R.id.lbl_connected_to);
        lbl_reading_pending = (AppCompatTextView) findViewById(R.id.lbl_reading_pending);
        lbl_reading_checked = (AppCompatTextView) findViewById(R.id.lbl_reading_checked);
        lbl_rows_uploaded = (AppCompatTextView) findViewById(R.id.lbl_rows_uploaded);


    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listUsers = new ArrayList<>();
        listAssignments = new ArrayList<>();
        dbHelper = new DBHelper(activity);

        dbHelperReadings = new DBReadings(activity);
        lv = (ListView) findViewById(R.id.recyclerViewUsers);

        databaseHelper = new DatabaseHelper(activity);

        textViewName.setText("Welcome, " + MyUser.getScreen_name());


        ImageView btn = (ImageView) findViewById(R.id.imageView2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(UsersListActivity.this, v);
                popup.setOnMenuItemClickListener(UsersListActivity.this);
                popup.inflate(R.menu.popup_menu);
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.upload_item:
                                Intent readingsIntent = new Intent(activity, UploadReadingsActivity.class);
                                startActivity(readingsIntent);
                                return true;
                            case R.id.download_item:
                                Intent readingsAssignmentsIntent = new Intent(activity, DownloadMeterReaderAssignmentsActivity.class);
                                startActivity(readingsAssignmentsIntent);
                                return true;
                            case R.id.connect_to_printer:
                                if (!mBluetoothAdapter.isEnabled()) {
                                    Intent enableIntent = new Intent(
                                            BluetoothAdapter.ACTION_REQUEST_ENABLE);
                                    startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
                                    // Otherwise, setup the session
                                } else {
                                    if (mService == null)
                                        KeyListenerInit();
                                }
                            case R.id.logout_item:
                                Intent logIntent = new Intent(activity, LoginActivity.class);
                                startActivity(logIntent);
                            default:
                                return false;
                        }
                    }
                });
            }
        });


        ret_readings();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                final MeterReaderAssignments map = (MeterReaderAssignments) parent.getItemAtPosition(position);

                String meter_no = map.getMeter_no();
                String consumer_name = map.getCustomer_name();

                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.item_reading);

//                dialog.setTitle("Input Reading");

                TextView tf_reading_date = (TextView) dialog.findViewById(R.id.tf_reading_date);
                TextView tf_reading_customer_no = (TextView) dialog.findViewById(R.id.tf_reading_customer_no);
                TextView tf_reading_customer_name = (TextView) dialog.findViewById(R.id.tf_reading_customer_name);
                TextView tf_reading_meter_no = (TextView) dialog.findViewById(R.id.tf_reading_meter_no);
                TextView tf_reading_barangay = (TextView) dialog.findViewById(R.id.tf_reading_barangay);
                TextView tf_reading_occupancy = (TextView) dialog.findViewById(R.id.tf_reading_occupancy);
                Button btn_reading_cancel = (Button) dialog.findViewById(R.id.btn_reading_cancel);
                Button btn_reading_save = (Button) dialog.findViewById(R.id.btn_reading_save);
                final EditText tf_previous_reading = (EditText) dialog.findViewById(R.id.tf_previous_reading);
                final EditText tf_current_reading = (EditText) dialog.findViewById(R.id.tf_current_reading);
                TextView previous_reading_date = (TextView) dialog.findViewById(R.id.lbl_previous_reading_date);
                TextView current_reading_date = (TextView) dialog.findViewById(R.id.lbl_current_reading_date);
                Button btn_reading_print = (Button) dialog.findViewById(R.id.btn_reading_print);

                String date1 = DateType.convert_datetime_to_month2(map.getPurok());
                String date2 = DateType.convert_datetime_to_month2(map.getPurok_id());
                previous_reading_date.setText(date1);
                current_reading_date.setText(date2);

                if (map.getId() != 0) {
                    tf_previous_reading.setText(map.getSitio());
                    tf_current_reading.setText(map.getSitio_id());
                } else {
                    btn_reading_print.setVisibility(View.GONE);
                }
                btn_reading_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btn_reading_print.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Print
                        Reading reading = dbHelperReadings.getReading("" + map.getId());
                        Date d1 = new Date();
                        Date d0 = new Date();
                        try {
                            d1 = DateType.datetime.parse(reading.getCreated_at());
                        } catch (Exception e) {
                        }

                        try {
                            d0 = DateType.datetime.parse(reading.getPrevious_reading_date());
                        } catch (Exception e) {
                        }

                        String for_the_month_of = DateType.m.format(d1) + " " + DateType.y.format(d1);
                        String bill_number = reading.getReading_no();
                        String date_of_reading = DateType.day_and_time.format(d1);
                        String period_covered = DateType.slash1.format(d0) + "-" + DateType.slash.format(d1);
                        String meter_no = reading.getCustomer_meter_no();
                        String name = reading.getCustomer_name();
                        String address = reading.getBarangay() + ", " + reading.getCity();
                        String occu = reading.getOccupancy_type_code() + "" + reading.getOccupancy_type();
                        String pres_reading = FitIn.fmt_woc(reading.getCurrent_reading());
                        String prev_reading = FitIn.fmt_woc(reading.getPrevious_reading());
                        String usage = "" + reading.getActual_consumption();
                        String due = FitIn.fmt_wc_0(reading.getAmount_due());
                        String due_mf = FitIn.fmt_wc_0(reading.getMf());
                        String due_mr = FitIn.fmt_wc_0(reading.getMr());
                        String inte = FitIn.fmt_wc_0(reading.getInterest());
                        String total_due = FitIn.fmt_wc_0(reading.getNet_due());
                        Date d3 = DateUtils1.add_day(d1, 10);
                        String due_after = DateType.slash.format(d3);
                        String meter_reader = reading.getMeter_reader_name();
                        String pay_on = due_after;
                        String numbers = "2251234; 2255623; 4228321; 42299771";

                        print_reading(for_the_month_of, bill_number, date_of_reading, period_covered, meter_no, name, address, occu
                                , pres_reading, prev_reading, usage, due, due_mf, due_mr, inte, total_due, due_after, meter_reader, pay_on, numbers);

                        dialog.dismiss();
                    }
                });
                btn_reading_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (map.getId() != 0) {
                            int id = map.getId();
                            String reading_no = "";
                            String meter_reader_id = map.getMeter_reader_no();
                            String meter_reader_name = map.getMeter_reader_name();
                            String customer_id = map.getCustomer_id();
                            String customer_no = map.getCustomer_no();
                            String customer_name = map.getCustomer_name();
                            String customer_meter_no = map.getMeter_no();
                            String previous_reading_date = map.getDate_added();

                            double previous_reading = FitIn.toDouble(tf_previous_reading.getText().toString());
                            double current_reading = FitIn.toDouble(tf_current_reading.getText().toString());

                            String city = map.getCity();
                            String city_id = map.getCity_id();
                            String barangay = map.getBarangay();
                            String barangay_id = map.getBarangay_id();
                            String purok = map.getPurok();
                            String purok_id = map.getPurok_id();
                            String sitio = map.getSitio();
                            String sitio_id = map.getSitio_id();
                            String created_at = DateType.now();
                            String updated_at = DateType.now();

                            String created_by = MyUser.getId();
                            String updated_by = MyUser.getId();
                            String status = "0";
                            String occupancy_id = map.getOccupancy_id();
                            String occupancy = map.getOccupancy();
                            String occupancy_type_id = map.getOccupancy_type_id();
                            String occupancy_type = map.getOccupancy_type();
                            String occupancy_type_code = map.getOccupancy_type_code();
                            double actual_consumption = current_reading - previous_reading;
                            double amount_due = 0;
                            double mf = 0;
                            double mr = 0;
                            double interest = 0;
                            double discount = 0;
                            double net_due = 0;
                            int is_paid = 0;
                            String or_id = "";
                            String or_no = "";
                            String date_uploaded = null;
                            String is_uploaded = "0";
                            String pipe_size = map.getPipe_size();
                            List<OccupancyTypes> listOccupancy = dbHelperReadings.ret_type(occupancy_type_code, occupancy_type, pipe_size);
                            for (OccupancyTypes occ : listOccupancy) {
                                String[] cubics = occ.getCubic().split(",");
                                double low = FitIn.toDouble(cubics[0]);
                                double high = FitIn.toDouble(cubics[1]);
                                if (cubics[1].equalsIgnoreCase("above")) {
                                    high = 100000;
                                }
                                if (actual_consumption >= low && actual_consumption <= high) {
                                    mf = occ.getMf();
                                    mr = occ.getMr();
                                    amount_due = actual_consumption * occ.getCharge() + (occ.getMf() + occ.getMr());
                                }
                            }
                            net_due = amount_due;
                            Reading reading = new Reading(id, reading_no, meter_reader_id, meter_reader_name, customer_id, customer_no, customer_name, customer_meter_no, previous_reading_date, previous_reading, current_reading, city, city_id, barangay, barangay_id, purok, purok_id, sitio, sitio_id, created_at, updated_at, created_by, updated_by, status, occupancy_id, occupancy, occupancy_type_id, occupancy_type, occupancy_type_code, actual_consumption, amount_due, mf, mr, interest, discount, net_due, is_paid, or_id, or_no, date_uploaded, is_uploaded, pipe_size);
                            dbHelperReadings.updateReading(reading);
                            Toast.makeText(getApplicationContext(), "Successfully Updated",
                                    Toast.LENGTH_LONG).show();
                            ret_readings();

                            //End Print
                            dialog.dismiss();
                        } else {

                            int id = 0;
                            String reading_no = dbHelperReadings.increment_no();
                            Log.i("reading_no", "reading_no: " + reading_no);
                            String meter_reader_id = map.getMeter_reader_no();
                            String meter_reader_name = map.getMeter_reader_name();
                            String customer_id = map.getCustomer_id();
                            String customer_no = map.getCustomer_no();
                            String customer_name = map.getCustomer_name();
                            String customer_meter_no = map.getMeter_no();

                            String previous_reading_date = map.getDate_added();

                            double previous_reading = FitIn.toDouble(tf_previous_reading.getText().toString());
                            double current_reading = FitIn.toDouble(tf_current_reading.getText().toString());

                            String city = map.getCity();
                            String city_id = map.getCity_id();
                            String barangay = map.getBarangay();
                            String barangay_id = map.getBarangay_id();
                            String purok = map.getPurok();
                            String purok_id = map.getPurok_id();
                            String sitio = map.getSitio();
                            String sitio_id = map.getSitio_id();
                            String created_at = DateType.now();
                            String updated_at = DateType.now();

                            String created_by = MyUser.getId();
                            String updated_by = MyUser.getId();
                            String status = "0";
                            String occupancy_id = map.getOccupancy_id();
                            String occupancy = map.getOccupancy();
                            String occupancy_type_id = map.getOccupancy_type_id();
                            String occupancy_type = map.getOccupancy_type();
                            String occupancy_type_code = map.getOccupancy_type_code();
                            double actual_consumption = current_reading - previous_reading;
                            double amount_due = 0;
                            double mf = 0;
                            double mr = 0;
                            double interest = 0;
                            double discount = 0;
                            double net_due = 0;
                            int is_paid = 0;
                            String or_id = "";
                            String or_no = "";
                            String date_uploaded = null;
                            String is_uploaded = "0";
                            String pipe_size = map.getPipe_size();
                            List<OccupancyTypes> listOccupancy = dbHelperReadings.ret_type(occupancy_type_code, occupancy_type, pipe_size);
                            for (OccupancyTypes occ : listOccupancy) {
                                String[] cubics = occ.getCubic().split(",");
                                double low = FitIn.toDouble(cubics[0]);
                                double high = FitIn.toDouble(cubics[1]);
                                if (cubics[1].equalsIgnoreCase("above")) {
                                    high = 100000;
                                }
                                if (actual_consumption >= low && actual_consumption <= high) {
                                    mf = occ.getMf();
                                    mr = occ.getMr();
                                    amount_due = actual_consumption * occ.getCharge() + (occ.getMf() + occ.getMr());
                                }
                            }
                            net_due = amount_due;
                            Reading reading = new Reading(id, reading_no, meter_reader_id, meter_reader_name, customer_id, customer_no, customer_name, customer_meter_no, previous_reading_date, previous_reading, current_reading, city, city_id, barangay, barangay_id, purok, purok_id, sitio, sitio_id, created_at, updated_at, created_by, updated_by, status, occupancy_id, occupancy, occupancy_type_id, occupancy_type, occupancy_type_code, actual_consumption, amount_due, mf, mr, interest, discount, net_due, is_paid, or_id, or_no, date_uploaded, is_uploaded, pipe_size);
                            dbHelperReadings.addReading(reading);
                            Toast.makeText(getApplicationContext(), "Successfully Added",
                                    Toast.LENGTH_LONG).show();
                            ret_readings();

                            //Print
                            Date d1 = new Date();
                            Date d0 = new Date();
                            try {
                                d1 = DateType.datetime.parse(reading.getCreated_at());
                            } catch (Exception e) {
                            }

                            try {
                                d0 = DateType.datetime.parse(reading.getPrevious_reading_date());
                            } catch (Exception e) {
                            }

                            String for_the_month_of = DateType.m.format(d1) + " " + DateType.y.format(d1);
                            String bill_number = reading.getReading_no();
                            String date_of_reading = DateType.day_and_time.format(d1);
                            String period_covered = DateType.slash1.format(d0) + "-" + DateType.slash.format(d1);
                            String meter_no = reading.getCustomer_meter_no();
                            String name = reading.getCustomer_name();
                            String address = reading.getBarangay() + ", " + reading.getCity();
                            String occu = reading.getOccupancy_type_code() + "" + reading.getOccupancy_type();
                            String pres_reading = FitIn.fmt_woc(reading.getCurrent_reading());
                            String prev_reading = FitIn.fmt_woc(reading.getPrevious_reading());
                            String usage = "" + FitIn.fmt_woc(reading.getActual_consumption());
                            String due = FitIn.fmt_wc_0(reading.getAmount_due());
                            String due_mf = FitIn.fmt_wc_0(reading.getMf());
                            String due_mr = FitIn.fmt_wc_0(reading.getMr());
                            String inte = FitIn.fmt_wc_0(reading.getInterest());
                            String total_due = FitIn.fmt_wc_0(reading.getNet_due());
                            Date d3 = DateUtils1.add_day(d1, 10);
                            String due_after = DateType.slash.format(d3);
                            String meter_reader = reading.getMeter_reader_name();
                            String pay_on = due_after;
                            String numbers = "2251234; 2255623; 4228321; 42299771";

                            print_reading(for_the_month_of, bill_number, date_of_reading, period_covered, meter_no, name, address, occu
                                    , pres_reading, prev_reading, usage, due, due_mf, due_mr, inte, total_due, due_after, meter_reader, pay_on, numbers);

                            dialog.dismiss();
                        }

                    }
                });

                tf_reading_date.setText(DateType.month_date.format(new Date()));
                tf_reading_customer_no.setText(map.getCustomer_no());
                tf_reading_customer_name.setText(map.getCustomer_name());
                tf_reading_meter_no.setText(map.getMeter_no());
                tf_reading_barangay.setText(map.getBarangay());
                String occ = map.getOccupancy_type_code() + "-" + map.getOccupancy_type();
                tf_reading_occupancy.setText(occ);
                tf_current_reading.requestFocus();
                dialog.show();


            }
        });


    }

    private void print_reading(String for_the_month_of
            , String bill_number, String date_of_reading, String period_covered, String meter_no
            , String name, String address, String occu, String pres_reading, String prev_reading, String usage
            , String due, String due_mf, String due_mr, String inte, String total_due, String due_after, String meter_reader
            , String pay_on, String numbers
    ) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        String date = str + "\n\n\n\n\n\n";
        try {
            //Print Logo
//                                byte[] qrcode = PrinterCommand.getBarCommand("Zijiang Electronic Thermal Receipt Printer!", 0, 3, 6);//
            Command.ESC_Align[2] = 0x01;
            SendDataByte(Command.ESC_Align);
//                                SendDataByte(qrcode);

            SendDataByte(Command.ESC_Align);
            Command.GS_ExclamationMark[2] = 0x11;
            SendDataByte(Command.GS_ExclamationMark);
            SendDataByte("Valencia\nWaterworks\nSystem\n\n".getBytes("BIG5"));
            Command.ESC_Align[2] = 0x00;
            SendDataByte(Command.ESC_Align);
            Command.GS_ExclamationMark[2] = 0x00;
            SendDataByte(Command.GS_ExclamationMark);
            SendDataByte("      STATEMENT OF ACCOUNT\n\n".getBytes("BIG5"));

            Command.ESC_Align[2] = 0x00;
            SendDataByte(Command.ESC_Align);
            Command.GS_ExclamationMark[2] = 0x00;
            SendDataByte(Command.GS_ExclamationMark);
            for_the_month_of = "For the month of " + for_the_month_of + "\n";
            bill_number = "Bill Number: " + bill_number + "\n";
            date_of_reading = "Date: " + date_of_reading + "\n";
            period_covered = "Period Covered:" + period_covered + "\n";
            String line1 = "--------------------------------" + "\n";
            String line2 = "================================" + "\n";
            meter_no = "Meter No.: " + meter_no + "\n";
            SendDataByte(for_the_month_of.getBytes("GBK"));
            SendDataByte(bill_number.getBytes("GBK"));
            SendDataByte(date_of_reading.getBytes("GBK"));
            SendDataByte(period_covered.getBytes("GBK"));
            SendDataByte(line1.getBytes("GBK"));
            Command.ESC_Align[2] = 0x00;
            SendDataByte(Command.ESC_Align);
            Command.GS_ExclamationMark[2] = 0x00;
            SendDataByte(Command.GS_ExclamationMark);

            SendDataByte(meter_no.getBytes("GBK"));
            SendDataByte(line1.getBytes("GBK"));

            Command.ESC_Align[2] = 0x00;
            SendDataByte(Command.ESC_Align);
            Command.GS_ExclamationMark[2] = 0x00;


            name = "Name: " + name + "\n";
            address = address + "\n";
            occu = "Occupancy: " + occu + "\n";
            SendDataByte(name.getBytes("GBK"));
            SendDataByte(address.getBytes("GBK"));
            SendDataByte(occu.getBytes("GBK"));
            SendDataByte(line1.getBytes("GBK"));

            pres_reading = "Present Reading:           " + pres_reading + "\n";
            prev_reading = "Previous Reading:          " + prev_reading + "\n";
            usage = "Usage:                     " + usage + "\n";
            SendDataByte(pres_reading.getBytes("GBK"));
            SendDataByte(prev_reading.getBytes("GBK"));
            SendDataByte(usage.getBytes("GBK"));
            SendDataByte(line1.getBytes("GBK"));

            due = "Amount Due:              " + due + "\n";
            due_mf = "Mf:                      " + due_mf + "\n";
            due_mr = "Mr:                      " + due_mr + "\n";
            inte = "Interest:                " + inte + "\n";
            due_after = "Due After " + due_after + "     " + total_due + "\n";
            total_due = "Total Due:               " + total_due + "\n";
            SendDataByte(due.getBytes("GBK"));
            SendDataByte(due_mf.getBytes("GBK"));
            SendDataByte(due_mr.getBytes("GBK"));
            SendDataByte(inte.getBytes("GBK"));
            SendDataByte(total_due.getBytes("GBK"));
            SendDataByte(due_after.getBytes("GBK"));
            SendDataByte(line2.getBytes("GBK"));

            meter_reader = "Meter Reader:   " + meter_reader + "\n";
            SendDataByte(meter_reader.getBytes("GBK"));

            String msg1 = "       I M P O R T A N T " + "\n";
//            pay_on = "10/31/2019";
//            numbers = "2251234; 2255623; 4228314; 4229987";
            String msg2 = "TO AVOID DISCONNECTION PAY THIS BILL ON OR BEFORE " + pay_on
                    + " DIAL TRUNKLINE NUMBERS  " + numbers + "\n"
                    + "          THANK YOU" + "\n\n\n\n\n";
            SendDataByte(msg1.getBytes("GBK"));
            SendDataByte(msg2.getBytes("GBK"));
//                                SendDataByte("Number:  888888\nReceipt  S00003333\nCashier：1001\nDate：xxxx-xx-xx\nPrint Time：xxxx-xx-xx  xx:xx:xx\n".getBytes("GBK"));
//                                SendDataByte("Name    Quantity    price  Money\nShoes   10.00       899     8990\nBall    10.00       1599    15990\n".getBytes("GBK"));
//                                SendDataByte("Quantity：             20.00\ntotal：                16889.00\npayment：              17000.00\nKeep the change：      111.00\n".getBytes("GBK"));
//                                SendDataByte("company name：NIKE\nSite：www.xxx.xxx\naddress：ShenzhenxxAreaxxnumber\nphone number：0755-11111111\nHelpline：400-xxx-xxxx\n================================\n".getBytes("GBK"));
//                                Command.ESC_Align[2] = 0x01;
//                                SendDataByte(Command.ESC_Align);
//                                Command.GS_ExclamationMark[2] = 0x11;
//                                SendDataByte(Command.GS_ExclamationMark);
//                                SendDataByte("Welcome again!\n".getBytes("GBK"));
//                                Command.ESC_Align[2] = 0x00;
//                                SendDataByte(Command.ESC_Align);
//                                Command.GS_ExclamationMark[2] = 0x00;
//                                SendDataByte(Command.GS_ExclamationMark);

//                                SendDataByte("(The above information is for testing template, if agree, is purely coincidental!)\n".getBytes("GBK"));
//                                Command.ESC_Align[2] = 0x02;
            SendDataByte(Command.ESC_Align);
//                                SendDataString(date);
            SendDataByte(PrinterCommand.POS_Set_PrtAndFeedPaper(48));
            SendDataByte(Command.GS_V_m_n);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    ArrayList<MeterReaderAssignments> arrayOfUsers = new ArrayList<MeterReaderAssignments>();

    private void ret_readings() {
        arrayOfUsers = dbHelperReadings.getMeterReaderAssignments("");
        ReadingAdapter adapter1 = new ReadingAdapter(this, arrayOfUsers);
        ListView listView = (ListView) findViewById(R.id.recyclerViewUsers);
        listView.setAdapter(adapter1);
        int pending = 0;
        int checked = 0;
        int uploaded=0;
        for (MeterReaderAssignments mra : arrayOfUsers) {
            if (mra.getStatus() == 0) {
                pending++;
            } else {
                checked++;
            }
            if(FitIn.toInt(mra.getAdded_by_id())==1){
                uploaded++;
            }
        }
        textViewRows.setText("" + arrayOfUsers.size());
        lbl_reading_pending.setText("" + pending);
        lbl_reading_checked.setText("" + checked);
        lbl_rows_uploaded.setText("" + uploaded);
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.upload_item:
                // do your code
                return true;


            case R.id.download_item:
                Intent assignmentssIntent = new Intent(activity, DownloadMeterReaderAssignmentsActivity.class);
                startActivity(assignmentssIntent);
                return true;


            case R.id.logout_item:
                // do your code
                return true;


            default:
                return false;

        }
    }


    private void SendDataString(String data) {
        Log.i("mService.getState()", "mService.getState(): " + mService.getState());
        Print_Test();
        if (mService.getState() != BluetoothService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        if (data.length() > 0) {
            try {
                mService.write(data.getBytes("GBK"));
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void SendDataByte(byte[] data) {

        if (mService.getState() != BluetoothService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        mService.write(data);
    }


}
