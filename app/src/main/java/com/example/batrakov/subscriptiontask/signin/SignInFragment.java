package com.example.batrakov.subscriptiontask.signin;

import android.support.v4.app.Fragment;

import com.example.batrakov.subscriptiontask.subscriptionlist.SubscriptionListFragment;

/**
 * Created by batrakov on 19.09.17.
 */

public class SignInFragment extends Fragment implements SignInContract.View{

    public SignInFragment() {
    }

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public void setPresenter(SignInContract.Presenter presenter) {

    }
}
