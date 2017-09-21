package com.example.batrakov.subscriptiontask.subscriptionlist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.batrakov.subscriptiontask.Subscription;
import com.example.batrakov.subscriptiontask.SubscriptionService;

/**
 * Created by batrakov on 19.09.17.
 */

public class SubscriptionListPresenter implements SubscriptionListContract.Presenter {

    private static final String SERVICE_TASK = "service task";
    private static final String READ_FROM_SERVICE = "read from service";
    private static final String WRITE_TO_SERVICE = "write to service";
    private static final String BUILD = "BUILD";
    private SubscriptionListContract.View mView;
    private BroadcastReceiver mBr;

    public SubscriptionListPresenter(SubscriptionListContract.View aView){
        mView = aView;
    }

    @Override
    public void addNewSubscription() {

    }

    @Override
    public void unsubscripe() {

    }

    @Override
    public void readFromService() {
        Intent intent = new Intent(mView.getCurrentActivity().getApplicationContext(), SubscriptionService.class).putExtra(SERVICE_TASK, BUILD);
 //       intent.putExtra(SERVICE_TASK, READ_FROM_SERVICE);
        mView.getCurrentActivity().startService(intent);
    }

    @Override
    public void start() {

//        mBr = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
////                if(intent.getStringExtra(STR_INDEX)!=null) {
////                    if(editText.getText().toString().equals("")) {
////                        editText.setText(intent.getStringExtra(STR_INDEX));
////                    }
////                }
//            }
//        };
        IntentFilter intFilt = new IntentFilter(SERVICE_TASK);
        readFromService();
    }

}
