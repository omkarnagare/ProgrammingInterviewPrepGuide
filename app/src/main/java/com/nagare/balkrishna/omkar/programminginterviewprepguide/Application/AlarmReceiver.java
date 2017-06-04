package com.nagare.balkrishna.omkar.programminginterviewprepguide.Application;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.nagare.balkrishna.omkar.programminginterviewprepguide.Activity.MainActivity;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.Activity.SettingsActivity;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.R;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.Utils.Constants;

import static android.content.ContentValues.TAG;

/**
 * Created by OMKARNAGARE on 6/4/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent notificationIntent = new Intent(context, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(Constants.REQUEST_CODE_NOTIFICATION,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);

        if(ProgrammingInterviewPrepGuideApp.getBooleanSetting(SettingsActivity.KEY_ENABLE_SOUND)){
            String sound = ProgrammingInterviewPrepGuideApp.getStringSetting(SettingsActivity.KEY_SOUND);
            if(sound.equals("default")) {

                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                notificationBuilder.setSound(uri);

            }else{
                Log.d(TAG, "onReceive: sound : "+ sound);
                notificationBuilder.setSound(Uri.parse(sound));
            }
        }

        if(ProgrammingInterviewPrepGuideApp.getBooleanSetting(SettingsActivity.KEY_ENABLE_VIBRATION)){
            long[] vibratePattern = {500,1000};
            notificationBuilder.setVibrate(vibratePattern);
        }

        Notification notification = notificationBuilder.setContentTitle("Programming Interview Prep Guide")
                .setContentText("Reminder to read questions for your Preparation")
                .setTicker("New Message Alert!")
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent).build();




        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Constants.NOTIFICATION_ID, notification);

    }
}
