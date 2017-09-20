package com.example.batrakov.subscriptiontask.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.batrakov.subscriptiontask.R;
import com.example.batrakov.subscriptiontask.subscriptionlist.SubscriptionListActivity;
import com.example.batrakov.subscriptiontask.subscriptionlist.SubscriptionListFragment;

/**
 * Created by batrakov on 19.09.17.
 */

public class SignInFragment extends Fragment implements SignInContract.View{

    public SignInFragment(){
    }

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.sign_in_frag, container, false);

        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sign_in_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.back_to_list:
                
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void setPresenter(SignInContract.Presenter presenter) {

    }
}
