package com.example.batrakov.subscriptiontask.subscriptionlist;


import com.example.batrakov.subscriptiontask.BasePresenter;
import com.example.batrakov.subscriptiontask.BaseView;

/**
 * Created by batrakov on 20.09.17.
 */

public interface SubscriptionListContract {

    interface View extends BaseView<Presenter>{

        void setLoadingIndicator(boolean active);

        void showSignIn();

    }
    interface Presenter extends BasePresenter{

        void addNewSubscription();

        void unsubscripe();


    }
}
