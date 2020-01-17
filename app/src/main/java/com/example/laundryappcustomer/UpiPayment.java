package com.example.laundryappcustomer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

public class UpiPayment extends AppCompatActivity {

    final int UPI_PAYMENT =0;
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=firebaseDatabase.getReference("Customer");
    private DatabaseReference databaseReference2=firebaseDatabase.getReference("Requests").child(Common.merchantphone);
    String orderno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upi_payment);
        Intent intent=getIntent();
        String price=intent.getStringExtra("price");
        String stat=intent.getStringExtra("stats");
        orderno=intent.getStringExtra("orderno");
        String merchantUpi=Common.merchantUpi;
        String merchantName=Common.merchantname;
        payUsingUpi(price, merchantName, "paying against order no : " + orderno, merchantUpi);

    }
    private void payUsingUpi(String amount,String name,String note,String upiid){
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa",upiid)
                .appendQueryParameter("pn",name)
                .appendQueryParameter("tn",note)
                .appendQueryParameter("am",amount)
                .appendQueryParameter("cu","INR")
                .build();
        Intent upiPaymentIntent = new Intent(Intent.ACTION_VIEW);
        upiPaymentIntent.setData(uri);

        Intent chooser = Intent.createChooser(upiPaymentIntent,"Paywith");

        if(null != chooser.resolveActivity(getPackageManager())){
            startActivityForResult(chooser,UPI_PAYMENT);
        }
        else {
            Toast.makeText(UpiPayment.this,"NO UPI app FOUND, PLEASE INSTALL ONE TO CONTINUE",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if(isConnectionAvailable(UpiPayment.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(UpiPayment.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                    databaseReference.child(Common.phoneNo).child("orderList").child(String.valueOf(Integer.parseInt(orderno))).child("paymentstatus").setValue("Paid");
                    databaseReference2.child(Common.phoneNo+orderno).child("paymentstatus").setValue("Paid");
                    updateOrderList("PAID");
                Log.d("UPI", "responseStr: "+approvalRefNo);
                taketohome();
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(UpiPayment.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                updateOrderList("Payment cancelled by user");
                taketohome();
            }
            else {
                Toast.makeText(UpiPayment.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                updateOrderList("transaction failed try again");
                taketohome();
            }
        } else {
            Toast.makeText(UpiPayment.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
            taketohome();
        }
    }

    private void taketohome() {
        Intent intent=new Intent(UpiPayment.this,HomeActivity.class);
        SharedPreferences sharedPreferences= getSharedPreferences(Common.SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Customer saveUser = Common.currentUser;
        Gson gson = new Gson();
        String json = gson.toJson(saveUser);
        editor.putString("CurrentUser", json);
        editor.putString(Common.PHONENO,Common.currentUser.getPhoneno());
        editor.putBoolean(Common.LOGIN,true);
        editor.apply();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }
    private void updateOrderList(String paymentstatus) {
        ArrayList<Order> orderList = Common.currentUser.getOrderList();
        int position = orderList.size()-Integer.parseInt(orderno)-1;
        Order currentItem = orderList.get(position);
        currentItem.setPaymentstatus(paymentstatus);
        orderList.set(position,currentItem);
        Common.currentUser.setOrderList(orderList);
    }
}
