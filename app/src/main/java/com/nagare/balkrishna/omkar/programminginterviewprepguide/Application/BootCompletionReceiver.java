package com.nagare.balkrishna.omkar.programminginterviewprepguide.Application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by OMKARNAGARE on 5/30/2017.
 */

public class BootCompletionReceiver extends BroadcastReceiver
{

    @Override
    public
    void onReceive(Context context,
                   Intent intent)
    {
        if(!ProgrammingInterviewPrepGuideApp.isMyServiceRunning(context, NotificationService.class))
        {
            context.startService(new Intent(context,
                    NotificationService.class));
        }
    }

}