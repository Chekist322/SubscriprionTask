package com.example.batrakov.subscriptiontask;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.batrakov.subscriptiontask.signin.SignInPresenter;
import com.example.batrakov.subscriptiontask.subscriptionlist.SubscriptionListPresenter;

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
                outBundle.putSerializable(SubscriptionListPresenter.SUB_INDEX, mSubscription);
                Intent out = new Intent(SubscriptionListPresenter.BROADCAST_ACTION);
                out.putExtras(outBundle);
                sendBroadcast(out);
                break;
            case SignInPresenter.WRITE_TO_SERVICE:
                Bundle inBundle = intent.getExtras();
                Subscription inSubscription = (Subscription) inBundle.getSerializable(SignInPresenter.NEW_SUB);
                addSub(inSubscription);
                break;

            case SignInPresenter.GET_NAMES:
                ArrayList<String> names = new ArrayList<>();
                for (int i = 0; i < mSubscription.size(); i++) {
                    names.add(mSubscription.get(0).getName());
                }
                Intent namesIntent = new Intent(SignInPresenter.BROADCAST_ACTION);
                namesIntent.putStringArrayListExtra(SignInPresenter.GET_NAMES, names);
                sendBroadcast(namesIntent);
                break;
            case SubscriptionListPresenter.REMOVE_SUB:
                mSubscription.remove(intent.getIntExtra(SubscriptionListPresenter.SUB_INDEX, 0));
                break;
            case SubscriptionListPresenter.RENAME_SUB:
                rename(intent.getIntExtra(SubscriptionListPresenter.SUB_INDEX, 0), intent.getStringExtra(SubscriptionListPresenter.NEW_NAME));
                break;
            case SubscriptionListPresenter.RADIO_CHECK:
                radioChange(intent.getIntExtra(SubscriptionListPresenter.SUB_INDEX, 0));
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

    private void rename(int aIndex, String aName){
        mSubscription.get(aIndex).setName(aName);
    }
    
    private void radioChange(int aIndex){
        for (int i = 0; i < mSubscription.size(); i++) {
            mSubscription.get(i).setEnableState(false);
        }
        mSubscription.get(aIndex).setEnableState(true);

        for (int i = 0; i < mSubscription.size(); i++) {

            System.out.println(mSubscription.get(i).isEnabled());
        }
    }

    private ArrayList<Subscription> getSubs(){
        return mSubscription;
    }

}
