package com.example.nguyephan.friendapp.data.repository;

import io.reactivex.subjects.PublishSubject;

public interface Pres {

    String currentToken();

    PublishSubject<Boolean> saveToken();

    void resetToken();
}
