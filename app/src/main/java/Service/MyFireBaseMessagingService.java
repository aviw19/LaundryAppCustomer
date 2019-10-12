package Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.laundryappcustomer.Common;
import com.example.laundryappcustomer.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

public class MyFireBaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMessagingServce";

    private static final String CHANNEL_ID ="1" ;
    private String ACTION_SNOOZE="1";
    String notificationTitle = null, notificationBody = null;
    final int min = 0;
    final int max = 9;
    final int random = new Random().nextInt((max - min) + 1) + min;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {



        // Check if message contains a notification payload.
        if (remoteMessage.getData() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getData());
            Map<String, String> data=remoteMessage.getData();
            notificationBody=data.get("body");
            notificationTitle=data.get("title");

        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        sendNotification(notificationTitle, notificationBody);

    }


    private void sendNotification(String notificationTitle, String notificationBody) {


        createNotificationChannel();

        Intent Accept = new Intent(this, MyBroadcastReceiver.class);

        Accept.setAction("Pay Now");
        Accept.putExtra("RequestId", notificationBody.substring(0,10));
        Accept.putExtra("notificationId",random);


        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(this, 0, Accept, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent Decline=new Intent(this,MyBroadcastReceiver.class);
        Decline.setAction("Pay Later");
        Decline.putExtra("RequestId",notificationBody.substring(0,10));
        Decline.putExtra("notificationId",random);
        PendingIntent snoozePendingIntent1=PendingIntent.getBroadcast(this,0,Decline,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID).
                setSmallIcon(R.mipmap.ic_launcher) //Notification icon
                .setContentTitle(notificationTitle)
                .setContentText(notificationBody).addAction(R.mipmap.ic_launcher, "Accept", snoozePendingIntent).setPriority(NotificationCompat.PRIORITY_HIGH).
                        setSound(defaultSoundUri).addAction(R.mipmap.ic_launcher,"Decline",snoozePendingIntent1);


        //notificationId is a unique int for each notification that you must define
        int notificationId=random;
        notificationManager.notify(notificationId, notificationBuilder.build());
    }
    private void createNotificationChannel () {
        //Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
