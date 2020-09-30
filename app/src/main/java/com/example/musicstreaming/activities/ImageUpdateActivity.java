package com.example.musicstreaming.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.musicstreaming.R;
import com.example.musicstreaming.controllers.ImageUploadController;
import com.example.musicstreaming.models.User;
import com.example.musicstreaming.utils.Constants;
import com.example.musicstreaming.utils.Helpers;
import com.example.musicstreaming.views.UserImageView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ImageUpdateActivity extends AppCompatActivity implements View.OnClickListener, UserImageView {

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;
    SharedPreferences sharedPreferences;
    User user;
    ImageUploadController imageUploadController;
    String token;

    CircleImageView user_img;
    Button select_btn, upload_btn, cancel_btn;
    MultipartBody.Part image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_update);

        sharedPreferences = getSharedPreferences("userPref", Context.MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.TOKEN, null);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(Constants.USER, "");
        user = gson.fromJson(json, User.class);

        user_img = findViewById(R.id.user_img);
        select_btn = findViewById(R.id.select_btn);
        upload_btn = findViewById(R.id.upload_btn);
        cancel_btn = findViewById(R.id.cancel_btn);

        if(user.getImage() != null){
            Picasso.get()
                    .load(Constants.IMAGE_URL + user.getImage())
                    .into(user_img);
        }

        select_btn.setOnClickListener(this);
        upload_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);

        askPermission();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_btn:
                Helpers.selectImage(v.getContext(), ImageUpdateActivity.this);
                break;
            case R.id.upload_btn:
                addImage();
                break;
            case R.id.cancel_btn:
                finish();
        }
    }

    private void addImage() {

        imageUploadController = new ImageUploadController(this, sharedPreferences, this);
        imageUploadController.uploadImage(token, user.get_id(), image);
    }

    //-----------------PERMISSION REQUEST-----------------------//
    private void askPermission(){
        permissions.add(CAMERA);
        permissions.add(WRITE_EXTERNAL_STORAGE);
        permissions.add(READ_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(permissionsToRequest.size() > 0){
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
            }
        }
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }
                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                        }
                                    });
                            return;
                        }
                    }
                }
                break;
        }
    }
    //------------------PERMISSION REQUEST--------------------------//

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");

                        // METHOD TO GET THE URI FROM THE BITMAP
                        Uri tempUri = getImageUri(getApplicationContext(), selectedImage);

                        // METHOD TO GET THE ACTUAL PATH
                        File finalFile = new File(getRealPathFromURI(tempUri));
                        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), finalFile);
                        image = MultipartBody.Part.createFormData("image", finalFile.getName(), reqFile);
                        user_img.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                Log.d("imageUpdate", "cursor: "+ cursor);
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);

                                Log.d("imageUpdate", "picturePath: "+ picturePath);
                                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                                user_img.setImageBitmap(thumbnail);
                                cursor.close();

                                // METHOD TO GET THE ACTUAL PATH
                                File finalFile = new File(getRealPathFromURI(selectedImage));
                                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), finalFile);
                                Log.d("imageUpdate", "finalFile: "+ finalFile);
                                image = MultipartBody.Part.createFormData("image", finalFile.getName(), reqFile);
                            }
                        }

                    }
                    break;
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        Date currentTime;
        Bitmap OutImage = Bitmap.createScaledBitmap(inImage, 1000, 1000,true);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), OutImage, "Title" + " - " + (currentTime = Calendar.getInstance().getTime()), null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    @Override
    public void isAdded(Boolean isAdded) {
        if(isAdded){
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            Toast.makeText(this, "Image Uploaded Successfully.", Toast.LENGTH_SHORT).show();
        }
    }
}