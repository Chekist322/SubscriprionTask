package com.example.batrakov.subscriptiontask.subscriptionlist;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.batrakov.subscriptiontask.R;
import com.example.batrakov.subscriptiontask.signin.SignInActivity;

/**
 * Created by batrakov on 19.09.17.
 */

public class SubscriptionListFragment extends Fragment implements SubscriptionListContract.View {

    private TextView mNoSubView;
    private TextView mNoSubViewPlus;

    public SubscriptionListFragment() {
    }

    public static SubscriptionListFragment newInstance() {
        return new SubscriptionListFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.subscription_list_frag, container, false);

        mNoSubView = (TextView) root.findViewById(R.id.noSubsView);
        mNoSubViewPlus = (TextView) root.findViewById(R.id.noSubsViewPlus);

        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sub_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_new_sub:
                showSignIn();
                    return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showSignIn() {
        Intent intent = new Intent(getContext(), SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public void setPresenter(SubscriptionListContract.Presenter presenter) {

    }
}
