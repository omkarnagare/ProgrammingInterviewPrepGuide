package com.nagare.balkrishna.omkar.programminginterviewprepguide.Application;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.nagare.balkrishna.omkar.programminginterviewprepguide.Activity.MainActivity;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.Model.ReminderTiming;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.R;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.Utils.Constants;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by OMKARNAGARE on 5/30/2017.
 */

public class NotificationService extends Service {

    private static final String TAG = "NotificationService";

    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    private Runnable runnable = null;

    private ScheduledFuture<?> notificationHandle = null;

    private long delay = 0;

    public
    NotificationService()
    {

    }


    @Override
    public
    void onCreate()
    {
        super.onCreate();


        ReminderTiming reminderTiming = ProgrammingInterviewPrepGuideApp.getReminderTiming();

        Calendar alarmStartTime    = Calendar.getInstance();
        Calendar          now               = Calendar.getInstance();
        alarmStartTime.set(Calendar.HOUR_OF_DAY, reminderTiming.getHour());
        alarmStartTime.set(Calendar.MINUTE, reminderTiming.getMinute());
        alarmStartTime.set(Calendar.SECOND, 0);

        if (now.after(alarmStartTime)) {

            alarmStartTime.add(Calendar.DATE, 1);

        }

        delay = alarmStartTime.getTimeInMillis() - System.currentTimeMillis();

        runnable = new Runnable()
        {
            @Override
            public
            void run()
            {
                notifyLocally();
            }
        };

        if(notificationHandle != null){

            notificationHandle.cancel(true);
            notificationHandle = null;

        }

        if(delay < 0){
            delay = 0;
        }
        Log.d(TAG, "onCreate: delay : "+ delay);

        notificationHandle = scheduler.scheduleAtFixedRate(runnable, (delay/1000), Constants.DAILY,
                TimeUnit.SECONDS);
    }

    private
    void notifyLocally()
    {

        Intent notificationIntent = new Intent(getBaseContext(), MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), Constants.REQUEST_CODE , notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Context context = getApplicationContext();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Notification notification = builder.setContentTitle("Programming Interview Prep Guide")
                .setContentText("Reminder to read questions for your Preparation")
                .setTicker("New Message Alert!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);

    }


    @Override
    public
    int onStartCommand(Intent intent,
                       int flags,
                       int startId)
    {
        return super.onStartCommand(intent,
                flags,
                startId);
    }

    @Override
    public
    void onDestroy()
    {

        if(notificationHandle != null){

            notificationHandle.cancel(true);
            notificationHandle = null;

        }

        Intent intent =  new Intent(Constants.INTENT_FOR_SERVICE);
        sendBroadcast(intent);

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

}
