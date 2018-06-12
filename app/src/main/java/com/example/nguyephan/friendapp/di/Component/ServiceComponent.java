package com.example.nguyephan.friendapp.di.Component;

import com.example.nguyephan.friendapp.di.Module.ServiceMoudle;
import com.example.nguyephan.friendapp.di.ServiceScope;
import com.example.nguyephan.friendapp.service.ConnectRemoteJobRegister;

import dagger.Component;

@ServiceScope
@Component(dependencies = AppComponent.class,modules = ServiceMoudle.class)
public interface ServiceComponent {

    void inject(ConnectRemoteJobRegister connectRemoteJobRegister);

}
