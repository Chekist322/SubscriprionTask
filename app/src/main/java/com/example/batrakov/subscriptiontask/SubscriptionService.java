package com.example.batrakov.subscriptiontask;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.batrakov.subscriptiontask.signin.SignInPresenter;
import com.example.batrakov.subscriptiontask.subscriptionlist.SubscriptionListPresenter;

import java.io.Serializable;
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
        mSubscription = new ArrayList<>();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (mSubscription != null) {
//            buildNotification(mSubscription.size());
//        } else {
//            buildNotification(0);
//        }

        switch (intent.getStringExtra(SubscriptionListPresenter.SERVICE_TASK)){
            case SubscriptionListPresenter.READ_FROM_SERVICE:
                Bundle outBundle = new Bundle();
                outBundle.putSerializable(SubscriptionListPresenter.LIST_INDEX, mSubscription);
                Intent out = new Intent(SubscriptionListPresenter.BROADCAST_ACTION);
                out.putExtras(outBundle);
                sendBroadcast(out);
                break;
            case SignInPresenter.WRITE_TO_SERVICE:
                Bundle inBundle = intent.getExtras();
                Subscription inSubscription = (Subscription) inBundle.getSerializable(SignInPresenter.NEW_SUB);
                addSub(inSubscription);
                break;
            case SubscriptionListPresenter.REMOVE_SUB:
                mSubscription.remove(intent.getIntExtra(SubscriptionListPresenter.LIST_INDEX, 0));
                break;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void addSub(Subscription aSub){
        mSubscription.add(aSub);
    }

    private ArrayList<Subscription> getSubs(){
        return mSubscription;
    }

//    private void buildNotification(int aAmountofSubs){
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.mipmap.ic_launcher_round)
//                        .setContentTitle("Subscription monitor")
//                        .setContentText("Amount of subscriptions: " + aAmountofSubs);
//        mNotification =  mBuilder.build();
//        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        nManager.notify(ID, mNotification);
////        startForeground(ID,mNotification);
//    }
}
