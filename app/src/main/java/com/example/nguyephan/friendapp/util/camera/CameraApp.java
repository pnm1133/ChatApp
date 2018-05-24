package com.example.nguyephan.friendapp.util.camera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nguye phan on 5/14/2018.
 */

public class CameraApp {

    private final static String APP_TAG = "FriendsApp";
    private String photoFileName = "photo.jpg";
    private File photoFile;
    private AppCompatActivity context;

    CameraApp(Context context) {
        this.context = (AppCompatActivity) context;
    }

    void onLaunchCamera(Fragment fragment) throws IOException {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = getPhotoFileUri();
        Uri fileProvider =
                FileProvider.getUriForFile(context, "com.example.nguyephan.friendapp.util.camera", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            fragment.startActivityForResult(intent, CameraBuilder.REQUEST_CODE_CAPTURE);
        }
    }

    private File getPhotoFileUri() throws IOException {
        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
    /*
    private thì dùng context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    public thig dùng getExternalStoragePublicDirectory()
    */

        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        photoFileName = image.getAbsolutePath();
        return image;
    }

    public File getPhotoFile() {
        return photoFile;
    }

    public String getPhotoFileName() {
        return photoFileName;
    }

}
