package com.example.nguyephan.friendapp.util.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.yalantis.ucrop.UCrop;

import java.io.IOException;

/**
 * Created by nguye phan on 5/14/2018.
 */

public class CameraBuilder {
    public static int REQUEST_CODE_CAPTURE = 1;

    private static CameraDefault cameraDefault;
    public static CameraDefault build() {
        if (cameraDefault == null) {
            cameraDefault = new CameraDefault();
        }
        return cameraDefault;
    }

    public static Uri getUriPicture(){
        if(cameraDefault == null){
            throw new Error("must call build Camera first");
        }
        return cameraDefault.getUri();
    }

    public static class CameraDefault extends CameraBuilder {
        private static final String TAG = CameraDefault.class.getSimpleName();
        private CameraApp cameraApp;

        private CameraDefault(){

        }

        public void onLunchCamera(Fragment fragment, Context context){
            Log.e(TAG,"onLaunchCamera");
            cameraApp = new CameraApp(context);
            try {
                cameraApp.onLaunchCamera(fragment);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void onLunchCamera(Activity context) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivityForResult(intent, REQUEST_CODE_CAPTURE);
            }
        }

        Uri getUri() throws NullPointerException {
            if (cameraApp.getPhotoFile() == null) {
                return null;
            }

            return Uri.fromFile(cameraApp.getPhotoFile());
        }


    }

    public class CameraApi extends CameraBuilder{

    }

}
