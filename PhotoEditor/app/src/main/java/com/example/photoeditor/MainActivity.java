package com.example.photoeditor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 0;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 0;
    private static final int CROP_FACTOR = 1;
    private ImageView image_view;
    private Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image_view = findViewById(R.id.image_view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.CAMERA}, 1);
            }
        }
    }

    public void onClick(View view) {
        getFullPhoto();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode == CAMERA_REQUEST) {
                image_view.setImageURI(photoUri);
                crop();
            }
            if (requestCode == CROP_FACTOR) {
                Bundle extras = data.getExtras();
                Bitmap picture = extras.getParcelable("data");
                image_view.setImageBitmap(picture);
            }
        }
    }

    private void crop() {
        try {
            Intent cropIntent = new Intent();
            cropIntent.setAction("com.android.camera.action.CROP");
            cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            cropIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            cropIntent.setDataAndType(photoUri,"image/*");
            cropIntent.putExtra("crop", true);
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            cropIntent.putExtra("outputFormat", "JPEG" );
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, CROP_FACTOR);
        } catch (ActivityNotFoundException error) {
            Toast.makeText(this, "Ошибка при кадрировании", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {

            } else {
                Toast.makeText(this, "Камера недоступна", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void getFullPhoto() {
        Intent cameraIntent = new Intent();
        cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            File file = null;
            try {
                file = createPhotoFile();
            } catch (IOException error) {
                Toast.makeText(this, "Ошибка при создании файла", Toast.LENGTH_LONG).show();
            }
            if (file != null){
                try {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        photoUri = FileProvider.getUriForFile(
                                this, "com.example.android.provider", file);
                    } else {
                        photoUri = Uri.fromFile(file);
                    }
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
                catch (Exception ex)
                {
                    Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    private File createPhotoFile() throws IOException {
        String timeStamp = new SimpleDateFormat("YYYY-MM-dd_HH.mm.ss").format(new Date());
        File file = File.createTempFile(timeStamp, ".jpg", getExternalFilesDir(Environment.DIRECTORY_DCIM));
        return  file;
    }
}
