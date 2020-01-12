package com.example.laundryappcustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
}
