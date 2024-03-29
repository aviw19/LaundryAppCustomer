package Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

import com.example.laundryappcustomer.Payment;
import com.example.laundryappcustomer.UpiPayment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference=firebaseDatabase.getReference();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        String orderid=intent.getStringExtra("OrderId");
        String orderno=intent.getStringExtra("orderno");
        String price=intent.getStringExtra("pay");

        int notificationid=intent.getIntExtra("notificationId",0);

        if(action.equals("Pay Now"))
        {
            NotificationManagerCompat nm =  NotificationManagerCompat.from(context);
            nm.cancel(notificationid);
            Intent i = new Intent(context.getApplicationContext(), UpiPayment.class);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("orderid",orderid);
            i.putExtra("stat","frombroadcast");
            i.putExtra("orderno",orderno);
            i.putExtra("price",price);
            context.startActivity(i);

        }
        if(action.equals("Pay Later"))
        {

            NotificationManagerCompat nm =  NotificationManagerCompat.from(context);
            nm.cancel(notificationid);
        }
        if(action.equals("Ok"))
        {
            NotificationManagerCompat nm =  NotificationManagerCompat.from(context);
            //nm.cancel(notificationid);
            nm.cancel(notificationid);
        }


    }
}