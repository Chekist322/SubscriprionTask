package com.example.batrakov.subscriptiontask.subscriptionlist;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.batrakov.subscriptiontask.R;
import com.example.batrakov.subscriptiontask.Subscription;
import com.example.batrakov.subscriptiontask.SubscriptionService;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by batrakov on 19.09.17.
 */

public class SubscriptionListPresenter implements SubscriptionListContract.Presenter {

    public static final String SERVICE_TASK = "service task";
    public static final String READ_FROM_SERVICE = "read from service";
    public static final String BROADCAST_ACTION = "filter";
    public static final String SUB_INDEX = "sub index";
    public static final String REMOVE_SUB = "remove sub";
    public static final String RENAME_SUB = "rename sub";
    public static final String NEW_NAME = "new name";
    public static final String RADIO_CHECK = "radio check";
    private static final int DELAY = 2000;
    private static final int LONG_DELAY = 10000;
    private SubscriptionListContract.View mView;

    public SubscriptionListPresenter(SubscriptionListContract.View aView) {
        mView = aView;
        mView.setPresenter(this);
    }

    @Override
    public void addNewSubscription() {

    }

    @Override
    public void unsubscribe(int aIndex) {
        Intent intent = new Intent(mView.getCurrentActivity().getApplicationContext(), SubscriptionService.class)
                .putExtra(SERVICE_TASK, REMOVE_SUB)
                .putExtra(SUB_INDEX, aIndex);
        mView.getCurrentActivity().startService(intent);
        mView.startDelay(DELAY);
        readFromService();
    }

    @Override
    public void unsubscribeError() {
        mView.startDelay(LONG_DELAY);
    }


    @Override
    public void readFromService() {
        Intent intent = new Intent(mView.getCurrentActivity().getApplicationContext(), SubscriptionService.class)
                .putExtra(SERVICE_TASK, READ_FROM_SERVICE);
        mView.getCurrentActivity().startService(intent);
    }

    @Override
    public void buildReciever() {
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getExtras() != null) {
                    Bundle bundle = intent.getExtras();
                    try {
                        ArrayList<Subscription> newList = (ArrayList<Subscription>) bundle.getSerializable(SUB_INDEX);
                        mView.showSubs(newList);
                    } catch (ClassCastException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION);
        mView.getCurrentActivity().registerReceiver(receiver, intFilt);
    }

    public void renameAlertDialogBuilder(final int aIndex) {

        AlertDialog.Builder alert = new AlertDialog.Builder(mView.getCurrentActivity(), R.style.RenameAlertDialogTheme);

        alert.setTitle("Rename item");

        final EditText input = new EditText(mView.getCurrentActivity());
        input.setTextColor(mView.getCurrentActivity().getResources().getColor(R.color.black));
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                Intent intent = new Intent(mView.getCurrentActivity().getApplicationContext(),
                        SubscriptionService.class).putExtra(SERVICE_TASK, RENAME_SUB);
                intent.putExtra(SUB_INDEX, aIndex);
                intent.putExtra(NEW_NAME, value);
                mView.getCurrentActivity().startService(intent);
                mView.startDelay(DELAY);
                readFromService();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
    }

    @Override
    public void unsubscribeAlertDialogBuilder(final int aIndex) {
        AlertDialog.Builder alert = new AlertDialog.Builder(mView.getCurrentActivity(), R.style.UnsubAlertDialogTheme);
        LayoutInflater inflater = mView.getCurrentActivity().getLayoutInflater();
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.unsubscribe_alert_dialog, null);
        alert.setView(view);
        final AlertDialog dialog = alert.create();


        Window window = dialog.getWindow();

        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);

        dialog.show();

        Button accept = view.findViewById(R.id.unsubAccept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int chance = random.nextInt(10);
                if (chance == 7) {
                    unsubscribeError();
                } else {
                    unsubscribe(aIndex);
                }
                dialog.cancel();
            }
        });

        Button cancel = view.findViewById(R.id.unsubCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View aView) {
                dialog.cancel();
            }
        });
    }

    @Override
    public void changeRadioButtons(int aIndex) {
        Intent intent = new Intent(mView.getCurrentActivity().getApplicationContext(), SubscriptionService.class)
                .putExtra(SERVICE_TASK, RADIO_CHECK);
        intent.putExtra(SUB_INDEX, aIndex);
        mView.getCurrentActivity().startService(intent);
        readFromService();
    }

    @Override
    public void start() {
        buildReciever();
    }

}
