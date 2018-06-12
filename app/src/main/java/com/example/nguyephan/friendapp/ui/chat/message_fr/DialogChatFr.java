package com.example.nguyephan.friendapp.ui.chat.message_fr;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.nguyephan.friendapp.R;
import com.example.nguyephan.friendapp.data.pojo.Conversation;
import com.example.nguyephan.friendapp.data.pojo.Friend;
import com.example.nguyephan.friendapp.data.pojo.chat.Dialog;
import com.example.nguyephan.friendapp.data.pojo.chat.Message;
import com.example.nguyephan.friendapp.data.pojo.chat.User;
import com.example.nguyephan.friendapp.ui.base.BaseFr;
import com.example.nguyephan.friendapp.ui.chat.adapter.AvatarAdapter;
import com.example.nguyephan.friendapp.util.GlideApp;
import com.example.nguyephan.friendapp.util.navigation.Navigation;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogChatFr extends BaseFr implements DialogChatContract.view {

    private static final String TAG = DialogChatFr.class.getSimpleName();

    public static DialogChatFr getInstance(){
        return new DialogChatFr();
    }
    @BindView(R.id.dialog_chat)
    DialogsList mDialogsList;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.shimmer)
    ShimmerFrameLayout mShimmerFrameLayout;
    @BindView(R.id.shimmer_friend)
    ShimmerFrameLayout mShimmerFriend;

    @Inject
    DialogChatContract.presenter<DialogChatContract.view> presenter;
    @Inject
    Navigation mNavigation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_fr,container,false);
        if(view != null){
            ButterKnife.bind(this,view);
            getActivityComponent().inject(this);

        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.onAttach(this);
        presenter.getListConversation();
        presenter.getListFriends();

    }

    @Override
    public void showDialogChat(List<Conversation> conversations) {
        if(conversations == null){
            stopChatShimmer();
            return;
        }

        DialogsListAdapter dialogsListAdapter =
                new DialogsListAdapter<>((imageView, url) -> GlideApp.with(DialogChatFr.this)
                    .load(url)
                    .into(imageView));

        List<Dialog> list = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();

        try {
            for (int i=0;i<conversations.size();i++){
                users.add(conversations.get(i).getSender());
                Message message = new Message(null,users.get(i),conversations.get(i).getLastMessage());
                list.add(new Dialog(conversations.get(i).
                        getId(),
                        conversations.get(i).getSender().getAvatar(),
                        conversations.get(i).getConversationTitle(),
                        users,
                        message,
                        conversations.get(i).getUnSeenCount()));
            }

        }catch (NullPointerException e){
            Log.e(TAG,"sender not have avatar");
        }



        dialogsListAdapter.addItems(list);
        mDialogsList.setAdapter(dialogsListAdapter);
        dialogsListAdapter.setOnDialogClickListener(dialog -> {
            for (Conversation conversation : conversations){
                if(TextUtils.equals(conversation.getId(),dialog.getId())){
                    mNavigation.startMessageAc(conversation);
                }
            }

        });

        stopChatShimmer();
    }

    @Override
    public void showChatShimmer() {
        mShimmerFrameLayout.setVisibility(View.VISIBLE);
        mShimmerFrameLayout.startShimmer();
        mShimmerFriend.setVisibility(View.VISIBLE);
        mShimmerFriend.startShimmer();
    }

    @Override
    public void stopChatShimmer() {
        mShimmerFrameLayout.stopShimmer();
        mShimmerFrameLayout.setVisibility(View.GONE);
        mShimmerFriend.setVisibility(View.GONE);
        mShimmerFriend.stopShimmer();
    }

    @Override
    public void showFriendDialog(List<Friend> friends) {
        mShimmerFriend.setVisibility(View.GONE);
        mShimmerFriend.stopShimmer();
        if(friends == null){
            return;
        }
        mRecyclerView.setAdapter(new AvatarAdapter(friends,DialogChatFr.this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL,false));
    }

}
