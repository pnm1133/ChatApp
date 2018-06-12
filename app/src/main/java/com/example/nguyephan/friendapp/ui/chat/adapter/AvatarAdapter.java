package com.example.nguyephan.friendapp.ui.chat.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nguyephan.friendapp.R;
import com.example.nguyephan.friendapp.data.pojo.Friend;
import com.example.nguyephan.friendapp.data.pojo.chat.User;
import com.example.nguyephan.friendapp.util.view.AvatarLayout;

import java.util.List;

public class AvatarAdapter  extends RecyclerView.Adapter<AvatarAdapter.Holder>{

    private List<Friend> friends;

    private Fragment mFragment;

    public AvatarAdapter(List<Friend> friends, Fragment fragment) {
        this.friends = friends;
        this.mFragment = fragment;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.items_avatar_recycler,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        String avatar = friends.get(position).getAvatar();
        String name = friends.get(position).getName();
        boolean isOnline = friends.get(position).getIsOnline();
        holder.avatarLayout.showImage(avatar,mFragment);
        holder.tv.setText(name);
        holder.avatarLayout.isOnline(isOnline);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        private AvatarLayout avatarLayout;
        private TextView tv;
        public Holder(View itemView) {
            super(itemView);
            avatarLayout = itemView.findViewById(R.id.item);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
