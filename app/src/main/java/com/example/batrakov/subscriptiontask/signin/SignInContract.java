package com.example.batrakov.subscriptiontask.signin;

import android.app.Activity;

import com.example.batrakov.subscriptiontask.BasePresenter;
import com.example.batrakov.subscriptiontask.BaseView;
import com.example.batrakov.subscriptiontask.Subscription;

/**
 * Created by batrakov on 20.09.17.
 */

public interface SignInContract {

    interface View extends BaseView<Presenter>{

        Activity getCurrentActivity();

    }

    interface Presenter extends BasePresenter{

        void writeToService(Subscription aSub);

    }
}
