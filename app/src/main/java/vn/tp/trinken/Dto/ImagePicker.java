package vn.tp.trinken.Dto;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ImagePicker {
    private Activity mActivity;
    ActivityResultLauncher<Intent> mActivityResultLauncher;

    public ImagePicker(Activity activity, ActivityResultLauncher<Intent> activityResultLauncher) {
        mActivity = activity;
        mActivityResultLauncher = activityResultLauncher;
    }

    public static final int REQUEST_CODE_CHOOSE_IMAGE = 100;
    public static String[] storage_permissions = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
    };
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static String[] storage_permissions_33 = {
            android.Manifest.permission.READ_MEDIA_IMAGES,
            android.Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.READ_MEDIA_VIDEO
    };
    private String[] permissions() {
        String[] p;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            p = storage_permissions_33;
        } else {
            p = storage_permissions;
        }
        return p;
    }

    public void pickPhoto() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
        } else {
            if (ContextCompat.checkSelfPermission(mActivity,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                ActivityCompat.requestPermissions(mActivity, permissions(), REQUEST_CODE_CHOOSE_IMAGE);
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }
}
