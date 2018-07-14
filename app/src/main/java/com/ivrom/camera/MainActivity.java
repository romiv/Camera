package com.ivrom.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends Activity {

    private static final int TAKE_PICTURE_REQUEST = 1;
    private ImageView imageView;
    private Uri outputFileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
    }

    public void onClick(View view) {
        Toast.makeText(this, "Нажата кнопка", Toast.LENGTH_SHORT).show();
        getThumbnailPicture();
        //saveFullImage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TAKE_PICTURE_REQUEST && resultCode == RESULT_OK) {
            // Проверяем, содержит ли результат маленькую картинку
            if (data != null) {
                Bitmap thumbnailBitmap = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(thumbnailBitmap);
                Toast.makeText(this, "Результат содержит миниатюру", Toast.LENGTH_SHORT).show();
            }
            else {
                // Какие-то действия с полноценным изображением
                // Сохраняем  по адресу outputFileUri
                Toast.makeText(this, "Результат содержит полноценное изображение", Toast.LENGTH_SHORT).show();
                imageView.setImageURI(outputFileUri);
            }
        }
    }

    private void getThumbnailPicture() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PICTURE_REQUEST);
    }

    private void saveFullImage(){
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory(),"test.jpg");
        outputFileUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,outputFileUri);
        startActivityForResult(intent,TAKE_PICTURE_REQUEST);
    }
}
