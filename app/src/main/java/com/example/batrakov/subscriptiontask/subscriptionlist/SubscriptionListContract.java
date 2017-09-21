package com.example.batrakov.subscriptiontask.subscriptionlist;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.batrakov.subscriptiontask.BasePresenter;
import com.example.batrakov.subscriptiontask.BaseView;

/**
 * Created by batrakov on 20.09.17.
 */

public interface SubscriptionListContract {

    interface View extends BaseView<Presenter>{

        void setLoadingIndicator(boolean aActive);

        void showSignIn();

        void startServiceTask(Intent aIntent);

        Activity getCurrentActivity();
    }
    interface Presenter extends BasePresenter{

        void addNewSubscription();

        void unsubscripe();

        void readFromService();
    }
}
