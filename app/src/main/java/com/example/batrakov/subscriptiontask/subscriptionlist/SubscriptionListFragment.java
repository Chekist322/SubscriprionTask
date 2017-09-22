package com.example.batrakov.subscriptiontask.subscriptionlist;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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
import android.widget.TextView;

import com.example.batrakov.subscriptiontask.R;
import com.example.batrakov.subscriptiontask.Subscription;
import com.example.batrakov.subscriptiontask.signin.SignInActivity;

import java.util.ArrayList;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by batrakov on 19.09.17.
 */

public class SubscriptionListFragment extends Fragment implements SubscriptionListContract.View {

    private TextView mNoSubView;
    private TextView mNoSubViewPlus;
    private ListView mListView;
    private SubAdapter mListAdapter;
    private MenuItem mAddSub;
    private ImageView mSubInfo;

    private SubscriptionListContract.Presenter mSubscriptionListPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("stage", "onCreate");
        super.onCreate(savedInstanceState);
        mListAdapter = new SubAdapter(new ArrayList<Subscription>(0));
    }

    public SubscriptionListFragment() {
    }

    public static SubscriptionListFragment newInstance() {
        return new SubscriptionListFragment();
    }


    @Override
    public void onResume() {
        Log.i("stage", "onResume");
        super.onResume();
        mSubscriptionListPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.i("stage", "onCreateView");
        View root = inflater.inflate(R.layout.subscription_list_frag, container, false);

        mNoSubView = (TextView) root.findViewById(R.id.noSubsView);
        mNoSubViewPlus = (TextView) root.findViewById(R.id.noSubsViewPlus);
        mListView = (ListView) root.findViewById(R.id.subList);
        mSubInfo = (ImageView) root.findViewById(R.id.item_info);

        mListView.setAdapter(mListAdapter);

        setHasOptionsMenu(true);
        return root;
    }

    private void showPopUpMenu(ImageView imageView, final int index){
        PopupMenu popupMenu = new PopupMenu(getActivity(), imageView);
        popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.rename_item:
                        break;
                    case R.id.unsubscribe_item:
                        mSubscriptionListPresenter.unsubscripe(index);
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.i("stage", "onCreateOptionsMenu");
        inflater.inflate(R.menu.sub_list_menu, menu);
        mAddSub = menu.findItem(R.id.add_new_sub);
    }

    @Override
    public void setLoadingIndicator(boolean active) {

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
    public void showSignIn() {
        Intent intent = new Intent(getContext(), SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public void showSubs(ArrayList<Subscription> aList) {
        Log.i("stage", String.valueOf(aList.size()));
        if (!aList.isEmpty()){
            mNoSubView.setVisibility(View.GONE);
            mNoSubViewPlus.setVisibility(View.GONE);
        } else {
            mNoSubView.setVisibility(View.VISIBLE);
            mNoSubViewPlus.setVisibility(View.VISIBLE);
        }
        mListAdapter.replaceData(aList);
    }

    @Override
    public Activity getCurrentActivity() {
        return getActivity();
    }

    @Override
    public void setPresenter(SubscriptionListContract.Presenter presenter) {
        Log.i("stage", "setPresenter");
        mSubscriptionListPresenter = presenter;
    }

    public class SubAdapter extends BaseAdapter {

        private ArrayList<Subscription> mSubList;

        SubAdapter(ArrayList<Subscription> aSubList){
            mSubList = aSubList;
        }

        public void replaceData(ArrayList<Subscription> aSubList){
            setList(aSubList);
            notifyDataSetChanged();
        }

        public void setList(@NonNull ArrayList<Subscription> aSubList){
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rowView = view;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.sub_item, viewGroup, false);
            }

            final Subscription subscription = getItem(i);
            Log.i("test", "test");
            System.out.println(subscription.getName());
            System.out.println(subscription.getParkCode());
            System.out.println(subscription.getAccessCode());
            final TextView name = (TextView) rowView.findViewById(R.id.nameItem);
            name.setText(subscription.getName());
            final TextView parkCode = (TextView) rowView.findViewById(R.id.parkCodeItem);
            parkCode.setText(subscription.getParkCode());
            final TextView accessCode = (TextView) rowView.findViewById(R.id.accessCodeItem);
            accessCode.setText(subscription.getAccessCode());
            final ImageView imageView = (ImageView) rowView.findViewById(R.id.item_info);
            final int index = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopUpMenu(imageView, index);
                }
            });





            return rowView;
        }
    }
}
