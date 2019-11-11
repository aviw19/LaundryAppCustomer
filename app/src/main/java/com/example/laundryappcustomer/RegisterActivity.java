package com.example.laundryappcustomer;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
    TextView mTextUsername;
    EditText mTextEmailId;
    EditText mTextBitsId;
    EditText mTextFullName;
    EditText mTextRoomNo;
    Button mSignUp;
    TextView mTextViewLogin;
    String FullName;
    String Email;
    Spinner spinner;
    Spinner spinner2;
    String hostel;
    String token;
    private static final int RC_SIGN_IN = 9001;
    final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    final DatabaseReference table_user = firebaseDatabase.getReference("Customer");

    GoogleSignInClient mGoogleSignInClient;
    String[] mHostelList = new String[]{"SELECT ONE","AH1", "AH2", "AH3", "AH4", "AH5", "AH6", "AH7", "AH8", "AH9", "CH1", "CH2", "CH3", "CH4", "CH5", "CH6", "CH7", "DH1", "DH2", "DH3", "DH4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mTextEmailId = findViewById(R.id.emailid);
        mTextFullName = findViewById(R.id.register_name);
        mSignUp = findViewById(R.id.register_SIGNUP);
        mTextBitsId = findViewById(R.id.bitd_id);
        mTextUsername = findViewById(R.id.register_username);
        mTextViewLogin = findViewById(R.id.register_login);
        mTextRoomNo = findViewById(R.id.room_no);
        spinner = findViewById(R.id.hostel);
        mTextUsername.setText(Common.phoneNo);
        buttonsWork();
        spinnersWork();
    }

    private void spinnersWork() {
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_item, mHostelList);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);
        spinner.setOnItemSelectedListener(RegisterActivity.this);
    }

    private void buttonsWork() {
        //google sign in Button
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setColorScheme(SignInButton.COLOR_DARK);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        // already have an account
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginIntent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(LoginIntent);
            }
        });
        //sign up button
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(RegisterActivity.this);
                mDialog.setMessage("please Wait...");
                mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(mTextUsername.getText().toString()).exists()) {
                                mDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "USER ALREADY REGISTERED", Toast.LENGTH_SHORT).show();
                                mTextBitsId.getText().clear();
                                mTextEmailId.getText().clear();
                                mTextFullName.getText().clear();
                                mTextRoomNo.getText().clear();
                            }
                            else {
                                mDialog.dismiss();

                                FirebaseTokeGeneration.main();

                                token=FirebaseTokeGeneration.token;

                                Customer user = new Customer(mTextFullName.getText().toString(), mTextBitsId.getText().toString(), mTextEmailId.getText().toString(), hostel, mTextRoomNo.getText().toString(),mTextUsername.getText().toString(),"0",null,token);
                                table_user.child(mTextUsername.getText().toString()).setValue(user);
                                Toast.makeText(RegisterActivity.this, "REGISTERED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                                mTextRoomNo.getText().clear();
                                mTextBitsId.getText().clear();
                                mTextEmailId.getText().clear();
                                mTextFullName.getText().clear();
                                finish();
                                Intent toLogin = new Intent(RegisterActivity.this, HomeActivity.class);
                                startActivity(toLogin);

                            }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("YO", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {

            final AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            final View alertLayout = inflater.inflate(R.layout.completedetails, null);
            spinner2 = alertLayout.findViewById(R.id.googlespinner);


            FullName = account.getDisplayName();
            Email = account.getEmail();
            mTextBitsId = alertLayout.findViewById(R.id.bitsid_Google);
            mTextUsername = alertLayout.findViewById(R.id.ContactNumber);
            mTextUsername.setText(Common.phoneNo);
            mTextRoomNo = alertLayout.findViewById(R.id.Room_No_google);
            ArrayAdapter adapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_item, mHostelList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner2.setAdapter(adapter);
            spinner2.setOnItemSelectedListener(RegisterActivity.this);


            alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child(mTextUsername.getText().toString()).exists()) {

                                    Toast.makeText(RegisterActivity.this, "USER ALREADY REGISTERED", Toast.LENGTH_SHORT).show();
                                    mTextBitsId.getText().clear();
                                    mTextEmailId.getText().clear();
                                    mTextFullName.getText().clear();
                                    mTextRoomNo.getText().clear();


                                } else {
                                    FirebaseTokeGeneration.main();

                                    token=FirebaseTokeGeneration.token;

                                    Customer user = new Customer(FullName, mTextBitsId.getText().toString(), Email, hostel, mTextRoomNo.getText().toString(),mTextUsername.getText().toString(),"0",null,token);
                                    table_user.child(mTextUsername.getText().toString()).setValue(user);
                                    Toast.makeText(RegisterActivity.this, "REGISTERED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                                    mTextBitsId.getText().clear();
                                    mTextEmailId.getText().clear();
                                    mTextFullName.getText().clear();
                                    mTextRoomNo.getText().clear();

                                    Intent toLogin = new Intent(RegisterActivity.this, HomeActivity.class);
                                    startActivity(toLogin);
                                    finish();

                                }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            });
            alert.setView(alertLayout);
            alert.show();

        } else {
            Log.e("mk", "pkmkb");
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                hostel = spinner.getItemAtPosition(0).toString();
                break;
            case 1:
                hostel = spinner.getItemAtPosition(1).toString();
                break;
            case 2:
                hostel = spinner.getItemAtPosition(2).toString();
                break;
            case 3:
                hostel = spinner.getItemAtPosition(3).toString();
                break;
            case 4:
                hostel = spinner.getItemAtPosition(4).toString();
                break;
            case 5:
                hostel = spinner.getItemAtPosition(5).toString();
                break;
            case 6:
                hostel = spinner.getItemAtPosition(6).toString();
                break;
            case 7:
                hostel = spinner.getItemAtPosition(7).toString();
                break;
            case 8:
                hostel = spinner.getItemAtPosition(8).toString();
                break;
            case 9:
                hostel = spinner.getItemAtPosition(9).toString();
                break;
            case 10:
                hostel = spinner.getItemAtPosition(10).toString();
                break;
            case 11:
                hostel = spinner.getItemAtPosition(11).toString();
                break;
            case 12:
                hostel = spinner.getItemAtPosition(12).toString();
                break;
            case 13:
                hostel = spinner.getItemAtPosition(13).toString();
                break;
            case 14:
                hostel = spinner.getItemAtPosition(14).toString();
                break;
            case 15:
                hostel = spinner.getItemAtPosition(15).toString();
                break;
            case 16:
                hostel = spinner.getItemAtPosition(16).toString();
                break;
            case 17:
                hostel = spinner.getItemAtPosition(17).toString();
                break;
            case 18:
                hostel = spinner.getItemAtPosition(18).toString();
                break;
            case 19:
                hostel = spinner.getItemAtPosition(19).toString();
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


