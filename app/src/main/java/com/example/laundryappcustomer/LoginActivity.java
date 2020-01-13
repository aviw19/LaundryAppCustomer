package com.example.laundryappcustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {
    int PERMISSION_ALL=1;
    private String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_SMS};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        while(!hasPermissions(LoginActivity.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(LoginActivity.this, PERMISSIONS, PERMISSION_ALL);
        }
        if (loadData()) {
            new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();
                    }
                }, 350);
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }, 350);

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
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
