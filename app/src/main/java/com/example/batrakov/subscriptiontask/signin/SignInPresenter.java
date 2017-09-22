package com.example.batrakov.subscriptiontask.signin;

import android.content.Intent;
import android.os.Bundle;

import com.example.batrakov.subscriptiontask.Subscription;
import com.example.batrakov.subscriptiontask.SubscriptionService;

/**
 * Created by batrakov on 19.09.17.
 */

public class SignInPresenter implements SignInContract.Presenter {

    public static final String WRITE_TO_SERVICE = "write to service";
    public static final String NEW_SUB = "new sub";
    public static final String SERVICE_TASK = "service task";

    private SignInContract.View mView;

    public SignInPresenter(SignInContract.View aView){
        mView = aView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

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
}
