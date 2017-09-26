package com.example.batrakov.subscriptiontask.subscriptionlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.batrakov.subscriptiontask.R;
import com.example.batrakov.subscriptiontask.Subscription;
import com.example.batrakov.subscriptiontask.signin.SignInActivity;

import java.util.ArrayList;

/**
 * Created by batrakov on 19.09.17.
 */

public class SubscriptionListFragment extends Fragment implements SubscriptionListContract.View {

    private TextView mNoSubView;
    private TextView mNoSubViewPlus;
    private ListView mListView;
    private SubAdapter mListAdapter;
    private MenuItem mAddSub;
    private Handler mHandler;
    private ProgressBar mProgressBar;
    private View mNoSubLayout;
    private View mLowOpacityBlack;

    private static final int START_HANDLE = 0;
    private static final int FINISH_HANDLE = 1;

    private static final int SUCCESS = 0;
    private static final int FAULT = 1;

    private SubscriptionListContract.Presenter mSubscriptionListPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new SubAdapter(new ArrayList<Subscription>(0));
        mSubscriptionListPresenter.start();
    }

    public SubscriptionListFragment() {
    }

    public static SubscriptionListFragment newInstance() {
        return new SubscriptionListFragment();
    }


    @Override
    public void onResume() {
        super.onResume();
        mSubscriptionListPresenter.readFromService();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.subscription_list_frag, container, false);

        mNoSubView = root.findViewById(R.id.noSubsView);
        mNoSubViewPlus = root.findViewById(R.id.noSubsViewPlus);
        mListView = root.findViewById(R.id.subList);
        mProgressBar = root.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        mNoSubLayout = root.findViewById(R.id.words);
        mLowOpacityBlack = root.findViewById(R.id.lowOpacityBlack);

        mListView.setAdapter(mListAdapter);

        setHasOptionsMenu(true);

        mHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message aMsg) {
                switch (aMsg.what) {
                    case START_HANDLE:
                        mNoSubLayout.setVisibility(View.GONE);
                        mListView.setVisibility(View.GONE);
                        mLowOpacityBlack.setVisibility(View.VISIBLE);
                        mProgressBar.setVisibility(View.VISIBLE);
                        break;
                    case FINISH_HANDLE:
                        mNoSubLayout.setVisibility(View.VISIBLE);
                        mListView.setVisibility(View.VISIBLE);
                        mLowOpacityBlack.setVisibility(View.GONE);
                        mProgressBar.setVisibility(View.GONE);
                        if (aMsg.arg1 == FAULT) {
                            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                    "Unsubscription failed, please try again.", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        break;
                    default:
                        super.handleMessage(aMsg);
                }
            }
        };


        return root;
    }

    private void showPopUpMenu(ImageView imageView, final int index) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), imageView);
        popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.rename_item:
                        mSubscriptionListPresenter.renameAlertDialogBuilder(index);
                        break;
                    case R.id.unsubscribe_item:
                        mSubscriptionListPresenter.unsubscribeAlertDialogBuilder(index);
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sub_list_menu, menu);
        mAddSub = menu.findItem(R.id.add_new_sub);
        if (mListAdapter.getCount() > 2) {
            mAddSub.setVisible(false);
        } else {
            mAddSub.setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_new_sub:
                showSignIn();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showSignIn() {
        Intent intent = new Intent(getContext(), SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public void showSubs(ArrayList<Subscription> aList) {
        if (!aList.isEmpty()) {
            mNoSubView.setVisibility(View.GONE);
            mNoSubViewPlus.setVisibility(View.GONE);
        } else {
            mNoSubView.setVisibility(View.VISIBLE);
            mNoSubViewPlus.setVisibility(View.VISIBLE);
        }
        mListAdapter.replaceData(aList);
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void checkRadioButtons(int aIndex) {
        mSubscriptionListPresenter.changeRadioButtons(aIndex);
    }

    @Override
    public Activity getCurrentActivity() {
        return getActivity();
    }

    @Override
    public void setPresenter(SubscriptionListContract.Presenter presenter) {
        mSubscriptionListPresenter = presenter;
    }

    public class SubAdapter extends BaseAdapter {

        private ArrayList<Subscription> mSubList;

        SubAdapter(ArrayList<Subscription> aSubList) {
            mSubList = aSubList;
        }

        public void replaceData(ArrayList<Subscription> aSubList) {
            setList(aSubList);
            notifyDataSetChanged();
        }

        public void setList(@NonNull ArrayList<Subscription> aSubList) {
            mSubList = aSubList;
        }

        @Override
        public int getCount() {
            return mSubList.size();
        }

        @Override
        public Subscription getItem(int i) {
            return mSubList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            View rowView = view;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.sub_item, viewGroup, false);
            }

            final Subscription subscription = getItem(i);
            final TextView name = rowView.findViewById(R.id.nameItem);
            name.setText(subscription.getName());
            final RadioButton button = rowView.findViewById(R.id.radioButton);
            if (subscription.isEnabled()) {
                button.setChecked(true);
            } else {
                button.setChecked(false);
            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkRadioButtons(i);
                }
            });

            final ImageView imageView = rowView.findViewById(R.id.item_info);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopUpMenu(imageView, i);
                }
            });
            return rowView;
        }
    }

    private class Loading implements Runnable {

        private int mDelay;
        private static final int LONG_DELAY = 10000;

        Loading(int aDelay) {
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
            mHandler.sendEmptyMessage(FINISH_HANDLE);
            Message msg = new Message();
            msg.what = FINISH_HANDLE;
            msg.arg1 = SUCCESS;
            if (mDelay == LONG_DELAY) {
                msg.arg1 = FAULT;
            }
            mHandler.sendMessage(msg);
        }
    }

    public void startDelay(int aDelay) {
        Thread thread = new Thread(new Loading(aDelay));
        thread.start();
    }
}
