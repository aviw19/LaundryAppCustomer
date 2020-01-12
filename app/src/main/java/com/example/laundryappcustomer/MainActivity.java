package com.example.laundryappcustomer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private EditText mPhoneNo;
    private Button mNextButton;
    private EditText mTextOtp;
    private String phoneNumber;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    private String verificationCode;
    private FirebaseAuth mAuth;
    private boolean userExists = false;
    private boolean userLoggedIn = false;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPhoneNo = findViewById(R.id.phonenumber);
        mNextButton = findViewById(R.id.nextButton);

        //Firebase Initialization
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = firebaseDatabase.getReference("Customer");
        // Read from the database
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = mPhoneNo.getText().toString();
                boolean x = firebaseChecking();
                if (!(x)) {
                    settingCallBack();
                    sendSms();
                    final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    final View alertLayout = inflater.inflate(R.layout.otp, null);
                    mTextOtp = alertLayout.findViewById(R.id.Otptext);
                    alert.setPositiveButton("Verify", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                            String code = mTextOtp.getText().toString().trim();
                            if (code.isEmpty() || code.length() != 6) {
                                mTextOtp.setError("Enter valid code");
                                mTextOtp.requestFocus();
                                return;
                            }

                            //verifying the code entered manually
                            verify(code);
                        }
                    });

                    alert.setView(alertLayout);
                    alert.show();
                } else {
                    Toast.makeText(MainActivity.this, "Already Logged in from Another device,please logout  from there", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public boolean firebaseChecking() {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                if (dataSnapshot.child(phoneNumber).exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        Common.currentUser = userSnapshot.getValue(Customer.class);
                        if (Common.currentUser.getPhoneno().equals(phoneNumber))
                            break;
                    }
                    if (Common.currentUser.getLoggedIn().equalsIgnoreCase("false")) {
                        userLoggedIn = false;
                        userExists = true;
                    } else {
                        userLoggedIn = true;
                    }
                } else {
                    userExists = false;
                    userLoggedIn = false;
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
            }
        });
        return userLoggedIn;
    }




    private void loginUser(){
        myRef.child(Common.currentUser.getPhoneno()).child("loggedIn").setValue("true");
        Intent intent= new Intent(MainActivity.this,HomeActivity.class);
        SharedPreferences sharedPreferences= getSharedPreferences(Common.SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Customer saveUser = Common.currentUser;
        Gson gson = new Gson();
        String json = gson.toJson(saveUser);
        editor.putString("CurrentUser", json);
        editor.putString(Common.PHONENO,phoneNumber);
        editor.putBoolean(Common.LOGIN,true);
        editor.apply();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void registerUser() {
        Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
        Common.phoneNo=phoneNumber;
        startActivity(intent);
        finish();
    }

    private void settingCallBack() {
        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                String code = phoneAuthCredential.getSmsCode();
                if (code != null) {
                    mTextOtp.setText(code);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCodeSent(String s,@NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
            {
                super.onCodeSent(s,forceResendingToken);
                verificationCode = s;
                Toast.makeText(MainActivity.this, "Incoming OTP PLEASE VERIFY", Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void signIn(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(MainActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if(userExists)
                    {
                        loginUser();
                    }
                    else
                    {
                        registerUser();
                    }
                } else {

                    //verification unsuccessful.. display an error message

                    String message = "Something is wrong, we will fix it soon...";

                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        message = "Invalid code entered...";
                    }

                    Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                    snackbar.setAction("Dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                    snackbar.show();
                }
            }
        });
    }

    public void verify(String Otp){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode,Otp);
        signIn(credential);
    }

    public void sendSms(){
        String number=mPhoneNo.getText().toString();
        number = "+91"+number;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number,60, TimeUnit.SECONDS,this,mCallBacks);
        Toast.makeText(MainActivity.this, "OTP Sent Please Wait for 60s before trying again", Toast.LENGTH_SHORT).show();
    }

    private void showHelp() {
        Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
}
