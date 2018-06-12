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
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyephan.friendapp.R;
import com.example.nguyephan.friendapp.data.pojo.firebase.FireRequest;
import com.example.nguyephan.friendapp.service.ConnectRemoteJobRegister;
import com.example.nguyephan.friendapp.ui.base.BaseFr;
import com.example.nguyephan.friendapp.ui.main.MainAc;
import com.example.nguyephan.friendapp.ui.main.MainContract;
import com.example.nguyephan.friendapp.ui.main.MainModel;
import com.example.nguyephan.friendapp.util.GlideApp;
import com.example.nguyephan.friendapp.util.camera.CameraBuilder;
import com.example.nguyephan.friendapp.util.camera.GalleryCameraApp;
import com.example.nguyephan.friendapp.util.view.FontView;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yalantis.ucrop.UCrop;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * Created by nguye phan on 5/13/2018.
 */

public class RegisterFr extends BaseFr
        implements RegisterFrView, View.OnClickListener {

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
    private Uri uriAvatar;
    private FireRequest fireRequest;
    @Inject
    FirebaseJobDispatcher dispatcher;

    /*this is primary actions of register page*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_started:
                if(mCbCondition.isChecked()){
                    getStarted();
                }else {
                    Toast.makeText(getContext(), R.string.error_condition_checkbox_register,Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_avatar:
                setAvatar();
                break;
            case R.id.btn_close:
                listener.closeRegisterPage();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CameraBuilder.REQUEST_CODE_CAPTURE) {
                GalleryCameraApp
                        .builder(getContext(), GalleryCameraApp.JPG_FORMAT)
                        .startCrop(CameraBuilder.getUriPicture());
            } else if (requestCode == GalleryCameraApp.REQUEST_MODE) {
                uriAvatar = UCrop.getOutput(data);
                GlideApp.with(this)
                        .load(uriAvatar)
                        .centerCrop()
                        .circleCrop()
                        .into(mBtnAddAvatar);
            }else {
                Toast.makeText(getContext(), R.string.error_crop,Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(getContext(), R.string.error_capture_failed,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnRegisterFrListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + getString(R.string.error_attach_fragment));
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
        View view = inflater.inflate(R.layout.register_page, container, false);
        if (getActivity() instanceof MainAc) {
            unbinder = ButterKnife.bind(this, view);
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
        FontView.setFontFamilyTextView(
                        getContext(), mTvAvatar, mEdtEmail, mEdtPassword, mEdtUsername, mBtnGetStarted, mCbCondition);
        FontView.setFontFamilyTextInputLayout(
                        getActivity(), mTextInputEmail, mTextInputPassword, mTextInputUsername);

        //show keyboard fist
        mEdtEmail.requestFocus();
        showKeyboard();

        mBtnGetStarted.setOnClickListener(this);
        mBtnClose.setOnClickListener(this);
        mBtnAddAvatar.setOnClickListener(this);

    }

    @Override
    public void showErrorEmail(@NonNull String error) {
        mEdtEmail.setError(error);
        mEdtEmail.requestFocus();
        showKeyboard();
    }

    @Override
    public void showErrorUnknown(String error) {
        Toast.makeText(getContext(), R.string.error_unknown,Toast.LENGTH_LONG).show();
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
    public void setAvatar() {
        CameraBuilder
                .build()
                .onLunchCamera(this, getContext());
    }

    @Override
    public void getStarted() {
        String email = mEdtEmail.getText().toString();
        String userName = mEdtUsername.getText().toString();
        String password = mEdtPassword.getText().toString();
        fireRequest = FireRequest.getInStance(email,password,userName,null);
        if(uriAvatar != null){
            fireRequest.setUriAvatar(uriAvatar.toString());
        }
        presenter.startRegister(fireRequest);
    }

    @Override
    public void showChatPage() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if(activity instanceof MainAc){
            ((MainAc) activity).replaceChatPage();
        }
    }

    @Override
    public void startUploadService() {
        Bundle bundle = new Bundle();
        Gson gson = new GsonBuilder().create();
        String jsonFirebaseRequest = gson.toJson(fireRequest);
        bundle.putString(String.valueOf(R.string.firebaseRequest_key),jsonFirebaseRequest);
        Job myJob = dispatcher.newJobBuilder()
                .setService(ConnectRemoteJobRegister.class)
                .setTag(getContext().getString(R.string.firebase_storage_job_service_tag))
                .setRecurring(false)
                .setExtras(bundle)
                .build();
        dispatcher.mustSchedule(myJob);
    }

    @Override
    public void startSaveInforUser() {

    }

    private OnRegisterFrListener listener;

    public interface OnRegisterFrListener {
        void closeRegisterPage();
    }

    public void setRegisterFrListener(OnRegisterFrListener registerFrListener) {
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