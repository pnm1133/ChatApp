package com.example.nguyephan.friendapp.util.camera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.example.nguyephan.friendapp.R;
import com.example.nguyephan.friendapp.ui.chat.ChatAc;
import com.example.nguyephan.friendapp.ui.login.RegisterFr;
import com.example.nguyephan.friendapp.ui.main.MainAc;
import com.example.nguyephan.friendapp.util.page.Page;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropFragment;
import com.yalantis.ucrop.UCropFragmentCallback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nguye phan on 5/15/2018.
 */

public class GalleryCameraApp implements UCropFragmentCallback {

    public static final String SAMPLE_CROPPED_IMAGE_NAME = "SampleCropImage.png";
    public static final int REQUEST_MODE = 2;
    public static final int REQUEST_SELECT_PICTURE_FRAGMENT = 3;
    public static final int REQUEST_SELECT_PICTURE_ACTIVITY = 4;
    public static final int PNG_FORMAT = 5;
    public static final int JPG_FORMAT = 6;


    public static GalleryBuilder builder(Context context, int imageFormat) {
        return new GalleryBuilder(context,imageFormat);
    }

    @Override
    public void loadingProgress(boolean showLoader) {

    }

    @Override
    public void onCropFinish(UCropFragment.UCropResult result) {

    }

    public static class GalleryBuilder {
        private Context context;
        private int imageFormat;

        private GalleryBuilder(Context context,int imageFormat) {
            this.context = context;
            this.imageFormat = imageFormat;
        }

        public void startCrop(Uri uri) {
            String destinationFileName = fileName(imageFormat);
            UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(context.getCacheDir(), destinationFileName)));
            uCrop = basisConfig(uCrop);
            uCrop = advancedConfig(uCrop);
            Fragment fragment = ((MainAc)context).getSupportFragmentManager().findFragmentByTag(Page.Register.getTag());
            uCrop.start(context, fragment,REQUEST_MODE);
        }

        private UCrop basisConfig(@NonNull UCrop uCrop) {
            uCrop = uCrop.withAspectRatio(1, 1);
            return uCrop;
        }

        private UCrop advancedConfig(@NonNull UCrop uCrop) {
            UCrop.Options options = new UCrop.Options();
            options.setCircleDimmedLayer(true);
            options.setCompressionQuality(100);
            options.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
            options.setStatusBarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            return uCrop.withOptions(options);
        }

        private String fileName(int imageFormat) {
            @SuppressLint("SimpleDateFormat")
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String destinationFileName = null;
            switch (imageFormat) {
                case PNG_FORMAT:
                    destinationFileName = "PNG_"+timeStamp+"_.png";
                    break;
                case JPG_FORMAT:
                    destinationFileName = "JPEG_"+timeStamp+"_.jpg";
                    break;
                default:{
                    destinationFileName = "JPEG_"+timeStamp+"_.jpg";
                }
            }
            return  destinationFileName;
        }
    }

}
