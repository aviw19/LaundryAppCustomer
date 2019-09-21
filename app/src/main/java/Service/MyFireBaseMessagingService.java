package Service;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFireBaseMessagingService extends FirebaseMessagingService  {
    private static final String TAG ="1" ;



    public MyFireBaseMessagingService() {
    }


    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

       /* Common.NotificationTitle =remoteMessage.getNotification().getTitle();
        Common.NotificationBody=remoteMessage.getNotification().getBody();
        MyDbHandler myDBHandler = new MyDbHandler(MyFireBaseMessagingService.this);
         NotificationPOJO notificationPOJO = new NotificationPOJO(1, Common.NotificationTitle,Common.NotificationBody);
        myDBHandler.addNotification(notificationPOJO);*/


    }

    private void sendRegistrationToServer(String token) {
    }
}
