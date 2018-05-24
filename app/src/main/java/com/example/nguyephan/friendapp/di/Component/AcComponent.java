package com.example.nguyephan.friendapp.di.Component;

import com.example.nguyephan.friendapp.di.AcScope;
import com.example.nguyephan.friendapp.di.Module.AcModule;
import com.example.nguyephan.friendapp.di.Module.FrModule;
import com.example.nguyephan.friendapp.di.Module.GoogleModule;
import com.example.nguyephan.friendapp.ui.chat.ChatAc;
import com.example.nguyephan.friendapp.ui.login.LoginFr;
import com.example.nguyephan.friendapp.ui.login.RegisterFr;
import com.example.nguyephan.friendapp.ui.login.RegisterFrView;
import com.example.nguyephan.friendapp.ui.main.MainAc;

import dagger.Component;

/**
 * Created by nguye phan on 5/9/2018.
 */
@AcScope
@Component(dependencies = AppComponent.class,modules = {AcModule.class,GoogleModule.class, FrModule.class})
public interface AcComponent {

    void inject(MainAc mainAc);

    void inject(LoginFr loginFr);
    
    void inject(RegisterFr registerFr);

    void inject(ChatAc chatAc);

}
