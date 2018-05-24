package com.example.nguyephan.friendapp.ui.login;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nguyephan.friendapp.R;
import com.example.nguyephan.friendapp.data.pojo.firebase.FireRequestAuth;
import com.example.nguyephan.friendapp.ui.base.BaseFr;
import com.example.nguyephan.friendapp.ui.main.MainAc;
import com.example.nguyephan.friendapp.ui.main.MainContract;
import com.example.nguyephan.friendapp.ui.main.MainModel;
import com.example.nguyephan.friendapp.util.GlideApp;
import com.example.nguyephan.friendapp.util.camera.CameraBuilder;
import com.example.nguyephan.friendapp.util.camera.GalleryCameraApp;
import com.example.nguyephan.friendapp.util.view.FontView;
import com.yalantis.ucrop.UCrop;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * Created by nguye phan on 5/13/2018.
 */

public class RegisterFr extends BaseFr
        implements RegisterFrView ,View.OnClickListener{

    private static final String TAG = "RegisterFr";

    public static RegisterFr getInstance() {
        return new RegisterFr();
    }

    @BindView(R.id.btn_get_started)
    Button mBtnGetStarted;
    @BindView(R.id.btn_close)
    ImageButton mBtnClose;
    @BindView(R.id.btn_avatar)
    FloatingActionButton mBtnAddAvatar;
    @BindView(R.id.edt_email)
    TextInputEditText mEdtEmail;
    @BindView(R.id.edt_username)
    TextInputEditText mEdtUsername;
    @BindView(R.id.edt_password)
    TextInputEditText mEdtPassword;
    @BindView(R.id.tv_avatar)
    TextView mTvAvatar;
    @BindView(R.id.cb_condition)
    CheckBox mCbCondition;
    @BindView(R.id.text_input_email)
    TextInputLayout mTextInputEmail;
    @BindView(R.id.text_input_username)
    TextInputLayout mTextInputUsername;
    @BindView(R.id.text_input_password)
    TextInputLayout mTextInputPassword;

    private MainContract.Presenter<MainContract.View> presenter;
    private Unbinder unbinder;
    private FireRequestAuth fireRequestAuth = FireRequestAuth.getInStance();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if (requestCode == CameraBuilder.REQUEST_CODE_CAPTURE) {
                GalleryCameraApp
                        .builder(getContext(),GalleryCameraApp.JPG_FORMAT)
                        .startCrop(CameraBuilder.getUriPicture());
            }else if(requestCode == GalleryCameraApp.REQUEST_MODE){
                Uri uri = UCrop.getOutput(data);
                GlideApp.with(this)
                        .load(uri)
                        .centerCrop()
                        .circleCrop()
                        .into(mBtnAddAvatar);
            }

        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (OnRegisterFrListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_page,container,false);
        if(getActivity() instanceof MainAc){
            unbinder = ButterKnife.bind(this,view);
            getActivityComponent().inject(this);
            MainModel mainModel = ViewModelProviders.of(getActivity()).get(MainModel.class);
            presenter = mainModel.getPresenter();
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FontView.setFontFamilyTextView(getContext(),mTvAvatar,mEdtEmail,mEdtPassword,mEdtUsername,mBtnGetStarted,mCbCondition);
        FontView.setFontFamilyTextInputLayout(getActivity(),mTextInputEmail,mTextInputPassword,mTextInputUsername);

        //show keyboard fist
        mEdtEmail.requestFocus();
        showKeyboard();

        mBtnGetStarted.setOnClickListener(this);
        mBtnClose.setOnClickListener(this);
        mBtnAddAvatar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_get_started:
                getStarted();
                break;
            case R.id.btn_avatar:
                try {
                    setAvatar();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_close:
                listener.closeRegisterPage();
                break;
        }
    }

    @Override
    public void showErrorEmail(@NonNull String error) {
        mEdtEmail.setError(error);
        mEdtEmail.requestFocus();
        showKeyboard();
    }

    @Override
    public void showErrorPassword(@NonNull String error) {
        mEdtPassword.setError(error);
        mEdtPassword.requestFocus();
        showKeyboard();
    }

    @Override
    public void showErrorName(@NonNull String error) {
        mEdtUsername.setError(error);
        mEdtUsername.requestFocus();
        showKeyboard();
    }

    @Override
    public void setAvatar() throws IOException {
        //start camera
        CameraBuilder
                .build()
                .onLunchCamera(this,getContext());
        // get results in onActivityResults()

//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        startActivityForResult(intent,3);

    }

    @Override
    public void getStarted() {
        //send firebaseRequest Authory to firebase server
        fireRequestAuth.setEmail(mEdtEmail.getText().toString());
        fireRequestAuth.setUserName(mEdtUsername.getText().toString());
        fireRequestAuth.setPassword(mEdtPassword.getText().toString());
        try {
            fireRequestAuth.setUriAvatar(CameraBuilder.getUriPicture());
        }catch (Error error){
            Log.e(TAG,error.toString());
        }
        presenter.startRegister(fireRequestAuth);
    }

    private OnRegisterFrListener listener;

    public interface OnRegisterFrListener {
        void closeRegisterPage();
    }

    public void setRegisterFrListener(OnRegisterFrListener registerFrListener){
        this.listener = registerFrListener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listener = null;
    }
}