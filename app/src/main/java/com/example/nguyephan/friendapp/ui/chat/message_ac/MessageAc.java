package com.example.nguyephan.friendapp.ui.chat.message_ac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.nguyephan.friendapp.R;
import com.example.nguyephan.friendapp.data.pojo.Conversation;
import com.example.nguyephan.friendapp.data.pojo.chat.Message;
import com.example.nguyephan.friendapp.data.pojo.chat.User;
import com.example.nguyephan.friendapp.di.Component.AcComponent;
import com.example.nguyephan.friendapp.ui.base.BaseAc;
import com.example.nguyephan.friendapp.util.GlideApp;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageAc extends BaseAc implements MessageContact.View {
    private static final String TAG = MessageAc.class.getSimpleName();
    @BindView(R.id.messagesList)
    MessagesList mMessageList;


    @Inject
    MessageContact.Presenter<MessageContact.View>  presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        presenter.onAttach(this);
    }

    @Override
    public void build(AcComponent acComponent) {
        acComponent.inject(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        presenter.messageConversation();
    }

    private void test(){
        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                GlideApp.with(MessageAc.this)
                        .load(url)
                        .into(imageView);
            }
        };

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,17);
        cal.set(Calendar.MINUTE,30);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);

        Date d = cal.getTime();

        MessagesListAdapter<Message> adapter = new MessagesListAdapter<>("", imageLoader);

        User user1 = new User("2","Nguyen","http://i.imgur.com/pv1tBmT.png",false);
        User user2 = new User("","Hang","http://i.imgur.com/ROz4Jgh.png",true);

        Message message = new Message("1",user1,"Hello cac ban shdlfhsdlf",d);
        Message message2 = new Message("2",user2,"hello con tac",d);
        Message message3 = new Message("3",user2,"dm may",d);


        adapter.addToStart(message,true);
        adapter.addToStart(message2,true);
        adapter.addToStart(message3,true);

        mMessageList.setAdapter(adapter);

    }

    @Override
    public void replaceHomePage() {

    }

    @Override
    public Conversation getConversation() {
        Bundle bundle = getIntent().getParcelableExtra("conversations");

        if(bundle == null){
            return null;
        }
        return (Conversation)bundle.getParcelable("conversation");
    }

    @Override
    public void showMessage(List<Message> messages) {
        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                GlideApp.with(MessageAc.this)
                        .load(url)
                        .into(imageView);
            }
        };

        MessagesListAdapter<Message> adapter =
                new MessagesListAdapter<>(getConversation().getSenderId(), imageLoader);
        for (Message message : messages){
            adapter.addToStart(message,true);
        }
        mMessageList.setAdapter(adapter);
    }

}
