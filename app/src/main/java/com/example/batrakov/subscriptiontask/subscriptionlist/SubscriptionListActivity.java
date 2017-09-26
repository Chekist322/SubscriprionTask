package com.example.batrakov.subscriptiontask.subscriptionlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.batrakov.subscriptiontask.R;
import com.example.batrakov.subscriptiontask.util.ActivityUtils;

public class SubscriptionListActivity extends AppCompatActivity {

    private SubscriptionListPresenter mSubscriptionListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscription_list_act);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SubscriptionListFragment subscriptionListFragment =
                (SubscriptionListFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (subscriptionListFragment == null) {
            subscriptionListFragment = SubscriptionListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), subscriptionListFragment, R.id.contentFrame);
        }

        mSubscriptionListPresenter = new SubscriptionListPresenter(subscriptionListFragment);
    }
}
