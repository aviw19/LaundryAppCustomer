package com.example.laundryappcustomer;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.laundryappcustomer.HomeFragment;
import com.example.laundryappcustomer.NotificationFragment;
import com.example.laundryappcustomer.OrdersFragment;
import com.example.laundryappcustomer.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import static android.content.ContentValues.TAG;


public class HomeActivity extends AppCompatActivity implements EditBottomSheet.BottomSheetListener {
    Fragment selectedFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }


    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_orders:
                            selectedFragment = new OrdersFragment();
                            break;
                        case R.id.nav_notification:
                            selectedFragment = new NotificationFragment();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                    }

                    assert selectedFragment != null;
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    @Override
    public void onButtonClicked(String newroomno, String newemailid, String newhostel,String newID) {
        ((ProfileFragment)selectedFragment).mtextroomno.setText(newroomno);
        ((ProfileFragment)selectedFragment).mtextemailid.setText(newemailid);
        ((ProfileFragment)selectedFragment).mtexthostel.setText(newhostel);
        ((ProfileFragment)selectedFragment).mtextID.setText(newID);
        updateDetails();
    }

    private void updateDetails() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference db = firebaseDatabase.getReference("Customer").child(Common.currentUser.getPhoneno());
        db.child("emailId").setValue(((ProfileFragment)selectedFragment).mtextemailid.getText().toString());
        db.child("hostelNo").setValue(((ProfileFragment)selectedFragment).mtexthostel.getText().toString());
        db.child("roomNo").setValue(((ProfileFragment)selectedFragment).mtextroomno.getText().toString());
        db.child("collegeId").setValue(((ProfileFragment)selectedFragment).mtextID.getText().toString());
    }
}
