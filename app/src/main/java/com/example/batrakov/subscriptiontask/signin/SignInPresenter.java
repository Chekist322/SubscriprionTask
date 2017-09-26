package com.example.batrakov.subscriptiontask.signin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.example.batrakov.subscriptiontask.Subscription;
import com.example.batrakov.subscriptiontask.SubscriptionService;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by batrakov on 19.09.17.
 */

public class SignInPresenter implements SignInContract.Presenter {

    public static final String WRITE_TO_SERVICE = "write to service";
    public static final String GET_NAMES = "get names";
    public static final String NEW_SUB = "new sub";
    public static final String SERVICE_TASK = "service task";
    public static final String BROADCAST_ACTION = "filter sign in";
    public static final String SUB_INDEX = "sub index";
    private BroadcastReceiver mReceiver;

    private ArrayList<String> mNames;
    private SignInContract.View mView;

    public SignInPresenter(SignInContract.View aView) {
        mNames = new ArrayList<>();
        mView = aView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        buildReciever();
    }

    @Override
    public void writeToService(Subscription aSub) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(NEW_SUB, aSub);
        Intent intent = new Intent(mView.getCurrentActivity().getApplicationContext(), SubscriptionService.class)
                .putExtra(SERVICE_TASK, WRITE_TO_SERVICE)
                .putExtras(bundle);
        mView.getCurrentActivity().startService(intent);
    }

    @Override
    public void readFromService() {
        Intent intent = new Intent(mView.getCurrentActivity().getApplicationContext(), SubscriptionService.class)
                .putExtra(SERVICE_TASK, GET_NAMES);
        mView.getCurrentActivity().startService(intent);
    }

    @Override
    public void buildReciever() {
        Log.i("stage", "receive");
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getExtras() != null) {
                    mNames = intent.getStringArrayListExtra(GET_NAMES);
                }
            }
        };
        IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION);
        mView.getCurrentActivity().registerReceiver(mReceiver, intFilt);
    }

    @Override
    public boolean[] fullCheck(String aName, String aParkCode, String aAccessCode) {
        boolean[] checkList = {false, false, false};
        checkList[0] = checkParkCode(aParkCode);
        checkList[1] = checkAccessCode(aAccessCode);
        return checkList;
    }

    @Override
    public boolean checkParkCode(String aParkCode) {
        return aParkCode.matches("\\d{3}(-\\d\\d\\d){2}");
    }

    @Override
    public boolean checkAccessCode(String aAccessCode) {
        return aAccessCode.equals("1423");
    }

    @Override
    public void demolition() {
        mView.getCurrentActivity().unregisterReceiver(mReceiver);
    }
}
