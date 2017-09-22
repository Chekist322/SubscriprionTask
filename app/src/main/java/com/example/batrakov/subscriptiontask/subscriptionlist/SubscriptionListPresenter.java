package com.example.batrakov.subscriptiontask.subscriptionlist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import com.example.batrakov.subscriptiontask.R;
import com.example.batrakov.subscriptiontask.Subscription;
import com.example.batrakov.subscriptiontask.SubscriptionService;

import java.util.ArrayList;

/**
 * Created by batrakov on 19.09.17.
 */

public class SubscriptionListPresenter implements SubscriptionListContract.Presenter {

    public static final String SERVICE_TASK = "service task";
    public static final String READ_FROM_SERVICE = "read from service";
    public static final String BROADCAST_ACTION = "filter";
    public static final String LIST_INDEX = "list index";
    public static final String REMOVE_SUB = "remove sub";
    private SubscriptionListContract.View mView;
    private BroadcastReceiver mBr;

    public SubscriptionListPresenter(SubscriptionListContract.View aView){
        mView = aView;
        mView.setPresenter(this);
    }

    @Override
    public void addNewSubscription() {

    }

    @Override
    public void unsubscripe(int aIndex) {
        Intent intent = new Intent(mView.getCurrentActivity().getApplicationContext(), SubscriptionService.class)
                .putExtra(SERVICE_TASK, REMOVE_SUB)
                .putExtra(LIST_INDEX, aIndex);
        mView.getCurrentActivity().startService(intent);
        readFromService();
    }



    @Override
    public void readFromService() {
        Intent intent = new Intent(mView.getCurrentActivity().getApplicationContext(), SubscriptionService.class).putExtra(SERVICE_TASK, READ_FROM_SERVICE);
        mView.getCurrentActivity().startService(intent);
    }

    @Override
    public void buildReciever() {
        mBr = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getExtras() != null){
                    Log.i("stage", "recieve");
                    Bundle bundle = intent.getExtras();
                    ArrayList<Subscription> newList = (ArrayList<Subscription>) bundle.getSerializable(LIST_INDEX);
                    if (newList.size() != 0) {
                        mView.showSubs(newList);
                    }
                }
            }
        };
        IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION);
        mView.getCurrentActivity().registerReceiver(mBr, intFilt);
    }

    @Override
    public void start() {
        buildReciever();
        readFromService();
    }

}
