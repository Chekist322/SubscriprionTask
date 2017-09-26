package com.example.batrakov.subscriptiontask.signin;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.batrakov.subscriptiontask.R;
import com.example.batrakov.subscriptiontask.Subscription;


/**
 * Created by batrakov on 19.09.17.
 */

public class SignInFragment extends Fragment implements SignInContract.View {

    private EditText mName;
    private EditText mParkCode;
    private EditText mAccessCode;
    private Button mButton;
    private ProgressBar mProgressBar;
    private SignInContract.Presenter mPresenter;
    private Handler mHandler;
    private ImageView mBack;
    private TextView mParkCodeError;
    private TextView mAccessCodeError;

    private static final String CHECK_LIST = "check list";
    private static final int START_HANDLE = 1;
    private static final int FINISH_HANDLE = 2;
    private static final int DELAY = 2000;
    private static final int LONG_DELAY = 10000;


    public SignInFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);
    }

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater aInflater, @Nullable ViewGroup aContainer,
                             @Nullable Bundle aSavedInstanceState) {
        View root = aInflater.inflate(R.layout.sign_in_frag, aContainer, false);
        setHasOptionsMenu(true);

        mName = root.findViewById(R.id.nameItem);
        mName.setBackgroundResource(R.drawable.edit_text_sign_in);
        mParkCode = root.findViewById(R.id.parkCode);
        mParkCode.setBackgroundResource(R.drawable.edit_text_sign_in);
        mAccessCode = root.findViewById(R.id.accessCode);
        mAccessCode.setBackgroundResource(R.drawable.edit_text_sign_in);
        mButton = root.findViewById(R.id.subscribeButton);
        mProgressBar = root.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        mBack = root.findViewById(R.id.backToList);
        mParkCodeError = root.findViewById(R.id.parkCodeError);
        mAccessCodeError = root.findViewById(R.id.accessCodeError);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View aView) {
                getActivity().finish();
            }
        });

        mHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage (Message aMsg) {
                switch (aMsg.what) {
                    case START_HANDLE:
                        mBack.setVisibility(View.GONE);
                        mButton.setVisibility(View.GONE);
                        mProgressBar.setVisibility(View.VISIBLE);
                        break;
                    case FINISH_HANDLE:
                        boolean[] checkList;
                        checkList = aMsg.getData().getBooleanArray(CHECK_LIST);

                        assert checkList != null;
                        if (checkList[0] && checkList[1]) {
                            Subscription newSub = new Subscription(mName.getText().toString(),
                                    mParkCode.getText().toString(), mAccessCode.getText().toString(), false);
                            mPresenter.writeToService(newSub);
                            getActivity().finish();
                        } else {
                            mButton.setVisibility(View.VISIBLE);
                            mProgressBar.setVisibility(View.GONE);

                            if (!checkList[0]) {
                                mParkCodeError.setVisibility(View.VISIBLE);
                            } else {
                                mParkCodeError.setVisibility(View.INVISIBLE);
                            }

                            if (!checkList[1]) {
                                mAccessCodeError.setVisibility(View.VISIBLE);
                            } else {
                                mAccessCodeError.setVisibility(View.INVISIBLE);
                            }
                        }
                        break;
                    default:
                        super.handleMessage(aMsg);
                }
            }
        };

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View aView) {
                    boolean[] checkList = mPresenter.fullCheck(mName.getText().toString(),
                            mParkCode.getText().toString(), mAccessCode.getText().toString());
                    if (checkList[0] && checkList[1]) {
                        Subscription newSub = new Subscription(mName.getText().toString(),
                                mParkCode.getText().toString(), mAccessCode.getText().toString(), false);
                        mPresenter.writeToService(newSub);
                        startDelay(DELAY, checkList);
                    } else {
                        startDelay(LONG_DELAY, checkList);
                    }
                }
        });
        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
    public void startDelay(int aDelay, boolean[] aCheckList) {
        Thread loadingThread = new Thread(new Loading(aDelay, aCheckList));
        loadingThread.start();
    }

    private class Loading implements Runnable {

        private int mDelay;
        private boolean[] mCheckList;

        Loading(int aDelay, boolean[] aCheckList) {
            mCheckList = aCheckList;
            mDelay = aDelay;
        }

        @Override
        public void run() {

            mHandler.sendEmptyMessage(START_HANDLE);
            try {
                Thread.sleep(mDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Looper.prepare();
            Message msg = new Message();
            msg.what = FINISH_HANDLE;
            Bundle bundle = new Bundle();
            bundle.putBooleanArray(CHECK_LIST, mCheckList);
            msg.setData(bundle);
            mHandler.sendMessage(msg);
        }
    }


}
