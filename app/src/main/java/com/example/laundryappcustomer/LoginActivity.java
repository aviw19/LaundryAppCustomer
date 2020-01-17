package com.example.laundryappcustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_INTERNET = 0;
    private static final int MY_PERMISSIONS_REQUEST_RECIEVE_SMS = 1;
    //private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 2;
    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkPermissionM();
        /*if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED ) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
                    Manifest.permission.RECEIVE_SMS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.RECEIVE_SMS},
                        MY_PERMISSIONS_REQUEST_RECIEVE_SMS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
        if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
        if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED ) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
                    Manifest.permission.INTERNET)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.INTERNET},
                        MY_PERMISSIONS_REQUEST_INTERNET);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }*/

    }

    private void checkPermissionM() {
        if(ContextCompat.checkSelfPermission(LoginActivity.this,Manifest.permission.RECEIVE_SMS)
                + ContextCompat.checkSelfPermission(
                LoginActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(
                    LoginActivity.this,
                    new String[]{
                            Manifest.permission.RECEIVE_SMS,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    MY_PERMISSIONS_REQUEST_CODE
            );
        }
        else
        {
            if (loadData())
            {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent i=new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(i);
                        finish();
                    }
                }, 350);

            }
            else
            {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent i=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }, 350);

            }
        }

    }

    public boolean loadData ()
        {
            Boolean state;
            SharedPreferences sharedPreferences = getSharedPreferences(Common.SHARED_PREFS, MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("CurrentUser", "");
            Common.currentUser = gson.fromJson(json, Customer.class);
            Common.phoneNo = sharedPreferences.getString(Common.PHONENO, "");
            state = sharedPreferences.getBoolean(Common.LOGIN, false);
            return state;
        }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.length) > 0
                        && (grantResults[0] +grantResults[1])== PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    if (loadData())
                    {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent i=new Intent(LoginActivity.this,HomeActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }, 350);

                    }
            else
                    {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent i=new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }, 350);

                    }
                } else {
                    for (int i = 0, len = permissions.length; i < len; i++) {
                        String permission = permissions[i];
                        if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                            boolean showRationale = shouldShowRequestPermissionRationale(permission);
                            if (!showRationale) {
                                Toast.makeText(LoginActivity.this,"Please Grant All Permissions",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent, MY_PERMISSIONS_REQUEST_CODE);
                            } else {
                                Toast.makeText(LoginActivity.this,"Please Grant All Permissions",Toast.LENGTH_SHORT).show();
                                checkPermissionM();

                                // user did NOT check "never ask again"
                                // this is a good place to explain the user
                                // why you need the permission and ask if he wants
                                // to accept it (the rationale)
                            }
                        }
                    }

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}

