package com.example.batrakov.subscriptiontask.signin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.batrakov.subscriptiontask.R;
import com.example.batrakov.subscriptiontask.util.ActivityUtils;

/**
 * Created by batrakov on 19.09.17.
 */

public class SignInActivity extends AppCompatActivity {

    private SignInPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);

        setContentView(R.layout.sign_in_act);

        SignInFragment signInFragment = (SignInFragment)
                getSupportFragmentManager().findFragmentById(R.id.contentFrameSignIn);
        if (signInFragment == null) {
            signInFragment = signInFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), signInFragment, R.id.contentFrameSignIn
            );
        }

        mPresenter = new SignInPresenter(signInFragment);
    }
}
