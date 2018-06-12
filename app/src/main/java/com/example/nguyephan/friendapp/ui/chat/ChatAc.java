package com.example.nguyephan.friendapp.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.nguyephan.friendapp.R;
import com.example.nguyephan.friendapp.data.api.firebase.FCFirestoreConnectImp;
import com.example.nguyephan.friendapp.data.pojo.Conversation;
import com.example.nguyephan.friendapp.data.pojo.chat.Dialog;
import com.example.nguyephan.friendapp.data.pojo.chat.Message;
import com.example.nguyephan.friendapp.data.pojo.chat.User;
import com.example.nguyephan.friendapp.di.Component.AcComponent;
import com.example.nguyephan.friendapp.ui.base.BaseAc;
import com.example.nguyephan.friendapp.ui.chat.adapter.ChatViewPagerAdapter;
import com.example.nguyephan.friendapp.ui.main.MainAc;
import com.example.nguyephan.friendapp.util.GlideApp;
import com.example.nguyephan.friendapp.util.navigation.Navigation;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatAc extends BaseAc implements ChatContract.View {
    private static final String TAG = ChatAc.class.getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.view_pager)
    ViewPager mViewpager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @Inject
    Navigation mNavigation;
    @Inject
    public ChatContract.Presenter<ChatContract.View> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        setSupportActionBar(mToolBar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Friends");

        mViewpager.setAdapter(new ChatViewPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewpager);
        for (int i = 0; i < imageResId.length; i++) {
            mTabLayout.getTabAt(i).setIcon(imageResId[i]);
        }


        presenter.onAttach(this);
//        presenter.getListConversation();
    }

    @Override
    public void build(AcComponent acComponent) {
        acComponent.inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat_ac,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out:
                signOut();
                break;
        }
        return true;
    }

    @Override
    public void startMainAc() {
        mNavigation.startMainAc(null,null);
        finish();
    }


    @Override
    public void signOut() {
        presenter.singOut();
        startMainAc();
    }

    private int[] imageResId = {
            R.drawable.ic_filter_1_black_24dp,
            R.drawable.ic_filter_2_black_24dp,
            R.drawable.ic_filter_3_black_24dp };
}
