package com.example.batrakov.subscriptiontask;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;

/**
 * Created by batrakov on 19.09.17.
 */

public class SubscriptionService extends Service {

    private ArrayList<Subscription> mSubscription;
    private Notification mNotification;
    private static final int ID = 451;

    public SubscriptionService() {
    }



    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
                    if (mSubscription != null) {
                        buildNotification(mSubscription.size());
                    } else {
                        buildNotification(0);
                    }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void addSub(){

    }

    private ArrayList<Subscription> getSubs(){
        return mSubscription;
    }

    private void buildNotification(int aAmountofSubs){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("My notification")
                        .setContentText("Amount of subscriptions: " + aAmountofSubs);
        mNotification =  mBuilder.build();
        startForeground(ID,mNotification);
    }
}
