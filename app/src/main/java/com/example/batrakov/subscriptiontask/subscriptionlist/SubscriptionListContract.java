package com.example.batrakov.subscriptiontask.subscriptionlist;


import android.app.Activity;

import com.example.batrakov.subscriptiontask.BasePresenter;
import com.example.batrakov.subscriptiontask.BaseView;
import com.example.batrakov.subscriptiontask.Subscription;

import java.util.ArrayList;

/**
 * Created by batrakov on 20.09.17.
 */

public interface SubscriptionListContract {

    interface View extends BaseView<Presenter> {

        void showSignIn();

        void showSubs(ArrayList<Subscription> aList);

        void checkRadioButtons(int aIndex);

        Activity getCurrentActivity();

        void startDelay(int aDelay);
    }
    interface Presenter extends BasePresenter {

        void addNewSubscription();

        void unsubscribe(int aIndex);

        void unsubscribeError();

        void readFromService();

        void buildReciever();

        void renameAlertDialogBuilder(int aIndex);

        void unsubscribeAlertDialogBuilder(int aIndex);

        void changeRadioButtons(int aIndex);
    }
}
