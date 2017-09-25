package com.example.batrakov.subscriptiontask.signin;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.batrakov.subscriptiontask.R;
import com.example.batrakov.subscriptiontask.Subscription;

import java.util.ArrayList;


/**
 * Created by batrakov on 19.09.17.
 */

public class SignInFragment extends Fragment implements SignInContract.View{

    private EditText mName;
    private EditText mParkCode;
    private EditText mAccessCode;
    private Button mButton;
    private ProgressBar mProgressBar;
    private SignInContract.Presenter mPresenter;
    private Handler mHandler;
    private ImageView mBack;
    private TextView mNameError;
    private TextView mParkCodeError;
    private TextView mAccessCodeError;

    private static final int START_HANDLE = 1;
    private static final int FINISH_HANDLE = 2;


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

        mName = root.findViewById(R.id.nameItem);
        mParkCode = root.findViewById(R.id.parkCode);
        mAccessCode = root.findViewById(R.id.accessCode);
        mButton = root.findViewById(R.id.subscribeButton);
        mProgressBar = root.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        mBack = root.findViewById(R.id.backToList);
        mNameError = root.findViewById(R.id.nameError);
        mParkCodeError = root.findViewById(R.id.parkCodeError);
        mAccessCodeError = root.findViewById(R.id.accessCodeError);

        mPresenter.readFromService();


        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        mHandler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case START_HANDLE:
                        mBack.setVisibility(View.GONE);
                        mButton.setVisibility(View.GONE);
                        mProgressBar.setVisibility(View.VISIBLE);
                        break;
                    case FINISH_HANDLE:
                        mButton.setVisibility(View.VISIBLE);
                        mProgressBar.setVisibility(View.GONE);
                        getActivity().finish();
                        break;
                    default:
                        super.handleMessage(msg);
                }
            }
        };

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    boolean[] checkList = mPresenter.fullCheck(mName.getText().toString(), mParkCode.getText().toString(), mAccessCode.getText().toString());


                    Subscription newSub = new Subscription(mName.getText().toString(), mParkCode.getText().toString(), mAccessCode.getText().toString(), false);
                    mPresenter.writeToService(newSub);
                    startDelay();
                }
        });

        return root;
    }


    @Override
    public void setPresenter(SignInContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public Activity getCurrentActivity() {
        return getActivity();
    }

    @Override
    public void startDelay() {
        Thread loadingThread = new Thread(new Loading());
        loadingThread.start();
    }

    private class Loading implements Runnable{

        @Override
        public void run() {

            mHandler.sendEmptyMessage(START_HANDLE);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mHandler.sendEmptyMessage(FINISH_HANDLE);
        }
    }


}
