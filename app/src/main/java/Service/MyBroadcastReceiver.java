package Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference=firebaseDatabase.getReference();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        String requestid=intent.getStringExtra("RequestId");
        String number=requestid.substring(0,10);
        String order=requestid.substring(10);

        if(action.equals("Accept"))
        {

            databaseReference.child("Requests").child(requestid).child("status").setValue("Accepted");

            databaseReference.child("Customer").child(number).child("orderList").child(order).child("status").setValue("Accepted");
            NotificationManagerCompat nm =  NotificationManagerCompat.from(context);
            nm.cancel(1);
        }
        if(action.equals("Decline"))
        {
            databaseReference.child("Requests").child(requestid).child("status").setValue("Decline");

            databaseReference.child("Customer").child(number).child("orderList").child(order).child("status").setValue("Decline");
            NotificationManagerCompat nm =  NotificationManagerCompat.from(context);
            nm.cancel(1);
        }


    }
}