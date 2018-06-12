package com.example.nguyephan.friendapp.ui.chat.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.nguyephan.friendapp.ui.chat.gift_fr.GiftFr;
import com.example.nguyephan.friendapp.ui.chat.location_fr.LocationFr;
import com.example.nguyephan.friendapp.ui.chat.message_fr.DialogChatFr;

public class ChatViewPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 3;


    public ChatViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DialogChatFr.getInstance();
            case 1:
                return GiftFr.getInstance();
            case 2:
                return LocationFr.getInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
