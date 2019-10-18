package com.example.laundryappcustomer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;



public class Payment extends AppCompatActivity implements PaytmPaymentTransactionCallback {


    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=firebaseDatabase.getReference("Customer");
    private DatabaseReference databaseReference2=firebaseDatabase.getReference("Requests");

    String  mid="jvEiNC03754986614481";
    String orderId="";
    String custid="";
    String orderno="";


    String pay="200";
    String stat="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        //initOrderId();


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Intent intent=getIntent();


        orderId=intent.getStringExtra("orderid");
        custid=intent.getStringExtra("orderid").substring(0,10);
        stat=intent.getStringExtra("stat");
        orderno=intent.getStringExtra("orderno");


        mid = "jvEiNC03754986614481"; /// your marchant key
        sendUserDetailTOServerdd dl = new sendUserDetailTOServerdd();
        dl.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

// vollye , retrofit, asynch

    }

    public class sendUserDetailTOServerdd extends AsyncTask<ArrayList<String>, Void, String> {



        //private String orderId , mid, custid, amt;
        String url ="https://young-representativ.000webhostapp.com/pt/generateChecksum.php";
        String varifyurl = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";
        // "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID"+orderId;
        String CHECKSUMHASH ="";



        protected String doInBackground(ArrayList<String>... alldata) {
            jsonparse jsonParser = new jsonparse(Payment.this);
            String param=
                    "MID="+mid+
                            "&ORDER_ID=" + orderId+
                            "&CUST_ID="+custid+
                            "&CHANNEL_ID=WAP&TXN_AMOUNT="+pay+"&WEBSITE=WEBSTAGING"+
                            "&CALLBACK_URL="+ varifyurl+"&INDUSTRY_TYPE_ID=Retail";

            JSONObject jsonObject = jsonParser.makeHttpRequest(url,"POST",param);
            // yaha per checksum ke saht order id or status receive hoga..
           Log.e("CheckSum result >>",jsonObject.toString());
            if(jsonObject != null){
               // Log.e("CheckSum result >>",jsonObject.toString());
                try {

                    CHECKSUMHASH=jsonObject.has("CHECKSUMHASH")?jsonObject.getString("CHECKSUMHASH"):"";
                    Log.e("CheckSum result >>",CHECKSUMHASH);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return CHECKSUMHASH;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(" setup acc ","  signup result  " + result);


            PaytmPGService Service = PaytmPGService.getStagingService();
            // when app is ready to publish use production service
            //          PaytmPGService  Service = PaytmPGService.getProductionService();

            // now call paytm service here
            //below parameter map is required to construct PaytmOrder object, Merchant should replace below map values with his own values
            HashMap<String, String> paramMap = new HashMap<String, String>();
            //these are mandatory parameters
            paramMap.put("MID", mid); //MID provided by paytm
            paramMap.put("ORDER_ID", orderId);
            paramMap.put("CUST_ID", custid);
            paramMap.put("CHANNEL_ID", "WAP");
            paramMap.put("TXN_AMOUNT","200");
            paramMap.put("WEBSITE", "WEBSTAGING");
            paramMap.put("CALLBACK_URL" ,varifyurl);
            //paramMap.put( "EMAIL" , "abc@gmail.com");   // no need
            // paramMap.put( "MOBILE_NO" , "9144040888");  // no need
            paramMap.put("CHECKSUMHASH" ,CHECKSUMHASH);
            //paramMap.put("PAYMENT_TYPE_ID" ,"CC");    // no need
            paramMap.put("INDUSTRY_TYPE_ID", "Retail");

            PaytmOrder Order = new PaytmOrder(paramMap);
            Log.e("checksum ", "param "+ paramMap.toString());
            Service.initialize(Order,null);
            // start payment service call here
            Service.startPaymentTransaction(Payment.this, true, true,
                    Payment.this  );



        }

    }


    @Override
    public void onTransactionResponse(Bundle bundle) {

        Log.e("checksum ", " respon true " + bundle.toString());
        //Toast.makeText(Payment.this, bundle.toString(), Toast.LENGTH_SHORT).show();
        //System.exit(0);
        if(bundle.toString().contains("SUCCESS"))
        {
            databaseReference.child(custid).child("orderList").child(orderno).child("status").setValue("Paid");
        }
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
        //Intent intent= new Intent(Payment.this,HomeActivity.class);
        //startActivity(intent);


    }

    @Override
    public void networkNotAvailable() {
        Log.e("S","ss");
        //Toast.makeText(Payment.this,"NetWork Not available, please try again later", Toast.LENGTH_SHORT).show();
        //Intent intent= new Intent(Payment.this,HomeFragment.class);
        //startActivity(intent);

    }

    @Override
    public void clientAuthenticationFailed(String s) {
        Log.e("S",s);
        //Toast.makeText(Payment.this,"Authentication failed, please try again later", Toast.LENGTH_SHORT).show();
        //Intent intent= new Intent(Payment.this,HomeFragment.class);
        //startActivity(intent);

    }

    @Override
    public void someUIErrorOccurred(String s) {
        Log.e("checksum ", " ui fail respon  "+ s );
        //Toast.makeText(Payment.this,"Payment cannot pe proccessed right now, please try again later", Toast.LENGTH_SHORT).show();
       // Intent intent= new Intent(Payment.this,HomeFragment.class);
       // startActivity(intent);
    }

    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        Log.e("checksum ", " error loading pagerespon true "+ s + "  s1 " + s1);
        Toast.makeText(Payment.this,"Payment cannot pe proccessed right now, please try again later", Toast.LENGTH_SHORT).show();
        //Intent intent= new Intent(Payment.this,HomeFragment.class);
        //startActivity(intent);
    }

    @Override
    public void onBackPressedCancelTransaction() {
        Log.e("checksum ", " cancel call back respon  " );
        //Toast.makeText(Payment.this,"You cancelled the transaction please try again later", Toast.LENGTH_SHORT).show();
        //Intent intent= new Intent(Payment.this,HomeFragment.class);
        //startActivity(intent);

    }

    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        Log.e("checksum ", "  transaction cancel " );
       // Toast.makeText(Payment.this,"Transaction Cancelled, please try again later", Toast.LENGTH_SHORT).show();
        //Intent intent= new Intent(Payment.this,HomeFragment.class);
        //startActivity(intent);
    }


}
