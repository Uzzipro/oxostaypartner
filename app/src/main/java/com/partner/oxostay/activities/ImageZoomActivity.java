package com.partner.oxostay.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.partner.oxostay.R;
import com.squareup.picasso.Picasso;

public class ImageZoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_zoom);


        Bundle extras = getIntent().getExtras();
//        Bitmap bmp = (Bitmap) extras.getParcelable("imagebitmap");
        String imageUrl = extras.getString("imageUrl");

        ImageView imgDisplay;
        Button btnClose;


        imgDisplay = (ImageView) findViewById(R.id.imgDisplay);
        btnClose = (Button) findViewById(R.id.btnClose);


        btnClose.setOnClickListener(v -> ImageZoomActivity.this.finish());
        Picasso.get().load(imageUrl).into(imgDisplay);

//        imgDisplay.setImageBitmap(bmp );
    }

}