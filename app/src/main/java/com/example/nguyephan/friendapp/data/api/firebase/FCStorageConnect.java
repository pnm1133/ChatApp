package com.example.nguyephan.friendapp.data.api.firebase;

import android.net.Uri;

import com.example.nguyephan.friendapp.data.pojo.firebase.FireRequest;
import com.example.nguyephan.friendapp.data.pojo.firebase.FireResponse;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public interface FCStorageConnect {

    PublishSubject<FireResponse> saveAvatarToRemote(FireRequest fireRequest);

}
