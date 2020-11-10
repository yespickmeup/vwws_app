package com.vwwsapp.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.vwwsapp.activities.R;
import com.vwwsapp.helpers.InputValidation;
import com.vwwsapp.model.MeterReader;
import com.vwwsapp.model.Setting;
import com.vwwsapp.sql.DBHelper;
import com.vwwsapp.sql.DatabaseHelper;
import com.vwwsapp.sql.DbSettings;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private AppCompatTextView textViewLinkRegister;

    private AppCompatButton appCompatButtonLogin;
    private AppCompatButton popup_download_meter_readers_cancel;
    private AppCompatButton popup_download_meter_readers_save;


    private InputValidation inputValidation;


    private DBHelper dbHelper;
    private DbSettings dbSettings;


    Button showPopupBtn, closePopupBtn;
    PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();

//        List<MeterReader> meter_readers=dbHelper.getAllMeterReaders();
//
//        if(meter_readers.isEmpty()){
//            show_popup_downloader_meter_readers();
//        }

    }
    /**
     * This method is to initialize views
     */
    private void initViews() {

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);
//        popup_download_meter_readers_cancel = (AppCompatButton) findViewById(R.id.dialog_cancel);
//        popup_download_meter_readers_save = (AppCompatButton) findViewById(R.id.dialog_ok);

//        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.textViewName);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
//        popup_download_meter_readers_cancel.setOnClickListener(this);
//        popup_download_meter_readers_save.setOnClickListener(this);
//        textViewLinkRegister.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */

    private void initObjects() {

        dbHelper = new DBHelper(activity);
        inputValidation = new InputValidation(activity);

    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
            case R.id.dialog_cancel:
               Log.i("cancel: ","cancel popup");
                break;
            case R.id.dialog_ok:
                Log.i("save: ","save popup");
                break;
            case R.id.textViewName:
                // Navigate to RegisterActivity
//                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
//                startActivity(intentRegister);
//                break;
        }
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */

    int failed_attempts=0;
    private void verifyFromSQLite() {


        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_username))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
//        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
//            return;
//        }
//        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_email))) {
//            return;
//        }

        if (dbHelper.checkUser(textInputEditTextEmail.getText().toString().trim() , textInputEditTextPassword.getText().toString().trim())) {

            Intent accountsIntent = new Intent(activity, UsersListActivity.class);
            accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);

        } else {
            // Snack Bar to show success message that record is wrong
            if(failed_attempts==3){
                Toast.makeText(LoginActivity.this,"Username and password do not match!",Toast.LENGTH_SHORT).show();
                Intent meterReadersIntent = new Intent(activity, DownloadMeterReaderActivity.class);
                meterReadersIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
                emptyInputEditText();
                startActivity(meterReadersIntent);
                failed_attempts=0;
            }
            Toast.makeText(LoginActivity.this,"Username and password do not match!",Toast.LENGTH_SHORT).show();
//            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
            failed_attempts++;
        }

    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }


    private void show_popup_downloader_meter_readers(){
        final Dialog dialog = new Dialog(LoginActivity.this); // Context, this, etc.
        dialog.setContentView(R.layout.pop_download_meter_readers);
        dialog.setTitle(R.string.dialog_title_download);
        dialog.setCancelable(false);
        dialog.show();
    }
    private void check_if_settings_record_exists(){
        List<Setting> settings = dbSettings.geSettings();
        Log.e("Settings", "Settings data: " + settings.size());
        if(settings.isEmpty()){
            dbSettings.addSetting();
        }
    }
}
