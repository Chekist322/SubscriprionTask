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
import android.widget.EditText;

import com.example.batrakov.subscriptiontask.R;
import com.example.batrakov.subscriptiontask.subscriptionlist.SubscriptionListActivity;
import com.example.batrakov.subscriptiontask.subscriptionlist.SubscriptionListFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by batrakov on 19.09.17.
 */

public class SignInFragment extends Fragment implements SignInContract.View{

    ArrayList<String> mData;
    private EditText mName;
    private EditText mParkCode;
    private EditText mAccessCode;
    private SignInContract.Presenter mPresenter;

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
        mName = root.findViewById(R.id.name);
        mParkCode = root.findViewById(R.id.parkCode);
        mAccessCode = root.findViewById(R.id.accessCode);
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
//                getFragmentManager().saveFragmentInstanceState(this);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void setPresenter(SignInContract.Presenter presenter) {
        mPresenter = presenter;
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        if (savedInstanceState != null) {
//            mName.setText(savedInstanceState.getStringArrayList("list").get(0));
//            mParkCode.setText(savedInstanceState.getStringArrayList("list").get(1));
//            mAccessCode.setText(savedInstanceState.getStringArrayList("list").get(2));
//            //Restore the fragment's state here
//        }
//    }
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mData.add(mName.getText().toString());
//        mData.add(mParkCode.getText().toString());
//        mData.add(mAccessCode.getText().toString());
//        outState.putStringArrayList("fields", mData);
//        //Save the fragment's state here
//    }
}
