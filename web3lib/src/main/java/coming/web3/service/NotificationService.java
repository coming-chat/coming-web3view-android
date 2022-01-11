package coming.web3.service;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import coming.web3.C;
import coming.web3.R;


/**
 * Created by James on 25/04/2019.
 * Stormbird in Sydney
 */
public class NotificationService
{
    private final Context context;
    private final String CHANNEL_ID = "ALPHAWALLET CHANNEL";
    private final int NOTIFICATION_ID = 314151024;
    public static final String AWSTARTUP = "AW://";

    public NotificationService(Context ctx)
    {
        context = ctx;
        createNotificationChannel();
    }

    private void createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            AudioAttributes attr = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            CharSequence name = context.getString(R.string.app_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.setSound(notification, attr);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    void DisplayNotification(String title, String content, int priority)
    {

    }

    private void checkNotificationPermission()
    {
        if (!(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NOTIFICATION_POLICY)
                == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NOTIFICATION_POLICY)
                        != PackageManager.PERMISSION_DENIED))
        {
            Intent intent = new Intent(C.REQUEST_NOTIFICATION_ACCESS);
            context.sendBroadcast(intent);
        }
    }
}
