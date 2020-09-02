package com.partner.oxostay.activities.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.partner.oxostay.R;
import com.partner.oxostay.activities.ui.editprofileinfo.EditProfileInfo;

public class ProfileActivity extends AppCompatActivity {
    private ImageView ivBack;
    private Button btEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setUpViews();
    }

    private void setUpViews() {
        ivBack = findViewById(R.id.ivBack);
        btEdit = findViewById(R.id.btEdit);
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(ProfileActivity.this, EditProfileInfo.class);
                startActivity(n);

            }
        });
        ivBack.setOnClickListener(v -> onBackPressed());
    }
}