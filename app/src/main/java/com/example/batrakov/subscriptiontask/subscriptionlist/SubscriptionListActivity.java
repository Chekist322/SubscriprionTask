package com.example.batrakov.subscriptiontask.subscriptionlist;

import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.batrakov.subscriptiontask.R;
import com.example.batrakov.subscriptiontask.util.ActivityUtils;

public class SubscriptionListActivity extends AppCompatActivity {

//    private DrawerLayout mDrawerLayout;

    private SubscriptionListPresenter mSubscriptionListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscription_list_act);

//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      //  ActionBar ab = getSupportActionBar();

        SubscriptionListFragment subscriptionListFragment =
                (SubscriptionListFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (subscriptionListFragment == null) {
            subscriptionListFragment = SubscriptionListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), subscriptionListFragment, R.id.contentFrame);
        }
    }
}
