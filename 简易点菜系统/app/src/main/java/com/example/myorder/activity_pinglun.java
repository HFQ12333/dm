package com.example.myorder;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUriExposedException;
import android.provider.Browser;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;

public class activity_pinglun extends AppCompatActivity implements View.OnClickListener {
    Button b1,b2,b3;
    ImageView imageView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitvity_pinglun);

        b1 = findViewById(R.id.button6);
        b2 = findViewById(R.id.button7);
        b3 = findViewById(R.id.button8);
        imageView = findViewById(R.id.imageView);

        b2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }
    public void onXiangji(View v){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,2);
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    ContentResolver cr = this.getContentResolver();
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                        imageView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    super.onActivityResult(requestCode, resultCode, data);
                }
                break;
            case 2:
                super.onActivityResult(requestCode,resultCode,data);
                if(resultCode == RESULT_OK && requestCode == 2){
                    Bundle dbl = data.getExtras();
                    Bitmap bmp = (Bitmap) dbl.get("data");
                    ImageView imv = findViewById(R.id.imageView);
                    imv.setImageBitmap(bmp);
                }else{
                    Toast.makeText(this,"没有拍到照片",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    public void pinglun(View v){
        Intent intent = new Intent();
        Toast.makeText(this,"评论成功",Toast.LENGTH_SHORT).show();
        intent.setClass(this,com.example.myorder.Actitvity_User.class);
        startActivity(intent);
        finish();
    }
}
