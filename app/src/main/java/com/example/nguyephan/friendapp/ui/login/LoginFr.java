package com.example.nguyephan.friendapp.ui.login;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nguyephan.friendapp.R;
import com.example.nguyephan.friendapp.data.pojo.firebase.FireRequest;
import com.example.nguyephan.friendapp.ui.base.BaseFr;
import com.example.nguyephan.friendapp.ui.chat.ChatAc;
import com.example.nguyephan.friendapp.ui.main.MainAc;
import com.example.nguyephan.friendapp.ui.main.MainContract;
import com.example.nguyephan.friendapp.ui.main.MainModel;
import com.example.nguyephan.friendapp.util.view.FontView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by nguye phan on 5/11/2018.
 */

public class LoginFr extends BaseFr
        implements LoginFrView, View.OnClickListener{

    private static final String TAG = "LoginFr";

    @BindView(R.id.tv_forgot_password)
    TextView mTvForgotPassword;
    @BindView(R.id.edt_email)
    TextInputEditText mTextInputEmail;
    @BindView(R.id.edt_password)
    TextInputEditText mTextInputPassword;
    @BindView(R.id.text_input_email)
    TextInputLayout mTextInputLayoutEmail;
    @BindView(R.id.text_input_password)
    TextInputLayout mTextInputLayoutPassword;
    @BindView(R.id.btn_get_started)
    Button mBtnSignIn;
    @BindView(R.id.btn_new)
    ImageButton mBtnNew;

//    @Inject
    public MainContract.Presenter<MainContract.View> presenter;
    private Unbinder unbinder;


    public static LoginFr getInstance() {
        return new LoginFr();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_started:
                presenter.startLogin(getFirebaseRequest());
                break;
            case R.id.btn_facebook:
                break;
            case R.id.btn_twitter:
                break;
            case R.id.btn_google:
                break;
            case R.id.btn_new:
                mOnLoginFrListener.onLoginNewAccListener();
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG,"onAttach");
        try {
            mOnLoginFrListener = (OnLoginFrListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.e(TAG,"onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_page, container, false);
        Log.e(TAG,"onCreateView");
        if (getActivity() instanceof MainAc) {
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
        Log.e(TAG,"onViewCreated");
        FontView.setFontFamilyTextView(getContext(), mTextInputEmail, mTextInputPassword, mTvForgotPassword);
        FontView.setFontFamilyTextInputLayout(getContext(), mTextInputLayoutEmail, mTextInputLayoutPassword);

        mBtnSignIn.setOnClickListener(this);
        mBtnNew.setOnClickListener(this);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        showKeyboard();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG,"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG,"onStop");
    }

    @Override
    public void showChatPage() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if(activity instanceof MainAc){
            ((MainAc) activity).replaceChatPage();
        }
    }


    @Override
    public void showErrorEmail(@NonNull String error) {
        mTextInputEmail.setError(error);
        mTextInputEmail.requestFocus();
    }

    @Override
    public void showErrorPassword(@NonNull String error) {
        mTextInputPassword.setError(error);
        mTextInputPassword.requestFocus();
    }

    @Override
    public FireRequest getFirebaseRequest() {
        String email = mTextInputEmail.getText().toString();
        String password = mTextInputPassword.getText().toString();
        return FireRequest.getInStance(email,password,null,null);
    }

    private OnLoginFrListener mOnLoginFrListener;

    public interface OnLoginFrListener{
        void onLoginNewAccListener();
    }

    public void setBtnRegisterClick(OnLoginFrListener onLoginFrListener){
        this.mOnLoginFrListener = onLoginFrListener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Log.e(TAG,"onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy");
        mOnLoginFrListener = null;
    }
}
