package com.example.batrakov.subscriptiontask.signin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.batrakov.subscriptiontask.R;
import com.example.batrakov.subscriptiontask.util.ActivityUtils;

/**
 * Created by batrakov on 19.09.17.
 */

public class SignInActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);
        setContentView(R.layout.sign_in_act);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SignInFragment signInFragment = (SignInFragment)
                getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (signInFragment == null) {
            signInFragment = signInFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), signInFragment, R.id.contentFrame
            );
        }
    }


}
