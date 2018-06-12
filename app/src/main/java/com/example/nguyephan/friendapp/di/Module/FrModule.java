package com.example.nguyephan.friendapp.di.Module;

import com.example.nguyephan.friendapp.di.AcScope;
import com.example.nguyephan.friendapp.ui.chat.message_fr.DialogChatContract;
import com.example.nguyephan.friendapp.ui.chat.message_fr.DialogChatFrPresenter;
import com.example.nguyephan.friendapp.ui.login.LoginFr;
import com.example.nguyephan.friendapp.ui.login.LoginFrView;
import com.example.nguyephan.friendapp.ui.login.RegisterFr;
import com.example.nguyephan.friendapp.ui.login.RegisterFrView;
import com.example.nguyephan.friendapp.util.camera.CameraBuilder;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nguye phan on 5/11/2018.
 */
@Module
public class FrModule {

    @Provides
    @AcScope
    LoginFr provideLoginFr(){
        return LoginFr.getInstance();
    }

    @Provides
    @AcScope
    LoginFrView provideLoginFrView(LoginFr loginFr){
        return loginFr;
    }
    
    @Provides
    @AcScope
    RegisterFr provideRegisterFr(){
        return RegisterFr.getInstance();
    }

    @Provides
    @AcScope
    RegisterFrView provideRegisterFrView(RegisterFr registerFr){
        return registerFr;
    }

    @Provides
    @AcScope
    DialogChatContract.presenter<DialogChatContract.view>  provideDialogChatFrView(DialogChatFrPresenter<DialogChatContract.view> dialogChatFrPresenter){
        return dialogChatFrPresenter;
    }
    
    
}
