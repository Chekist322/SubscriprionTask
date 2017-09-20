package com.example.batrakov.subscriptiontask;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by batrakov on 19.09.17.
 */

public class SubscriptionService extends IntentService {
    public SubscriptionService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
