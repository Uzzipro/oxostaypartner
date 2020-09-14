package com.partner.oxostay.activities.ui.profile;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.partner.oxostay.R;
import com.partner.oxostay.activities.ImageZoomActivity;
import com.partner.oxostay.activities.ui.editprofileinfo.EditProfileInfo;
import com.partner.oxostay.dtos.RegisterDto;
import com.partner.oxostay.utils.Constants;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private ImageView ivBack, ivHotelPic1, ivHotelPic2, ivHotelPic3, ivHotelPic4, ivHotelPic5;
    private Button btEdit;
    private AppCompatTextView tvHotelName, tvLocation, tvHotelContact, tvManagerName, tvHotelAddress, tvHotelEmail, tvHotelSecondaryEmail;
    private DatabaseReference dbRef;
    private int CHOOSE_FILE_REQUEST = 1;
    private ProgressDialog progressDialog;
    private FirebaseStorage storage;
    private String imageNumber;
    private StorageReference storageReference;
    private String[] cameraPermissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading your Account Information");
        progressDialog.setCancelable(false);
        progressDialog.show();
        setUpViews();

    }

    private void setUpViews() {
        ivBack = findViewById(R.id.ivBack);
        btEdit = findViewById(R.id.btEdit);
        tvHotelName = findViewById(R.id.tvHotelName);
        tvLocation = findViewById(R.id.tvLocation);
        tvHotelContact = findViewById(R.id.tvHotelContactNumber);
        tvHotelAddress = findViewById(R.id.tvHotelAddress);
        tvHotelEmail = findViewById(R.id.tvHotelEmailAddress);
        tvHotelSecondaryEmail = findViewById(R.id.tvHotelSecondaryEmailAddress);
        ivHotelPic1 = findViewById(R.id.ivHotelPic1);
        ivHotelPic2 = findViewById(R.id.ivHotelPic2);
        ivHotelPic3 = findViewById(R.id.ivHotelPic3);
        ivHotelPic4 = findViewById(R.id.ivHotelPic4);
        ivHotelPic5 = findViewById(R.id.ivHotelPic5);
        dbRef = FirebaseDatabase.getInstance().getReference(Constants.OXO_STAY_PARTNER);
        btEdit.setOnClickListener(v -> {
            Intent n = new Intent(ProfileActivity.this, EditProfileInfo.class);
            startActivity(n);
        });
        ivBack.setOnClickListener(v -> onBackPressed());

        ivHotelPic1.setOnClickListener(v -> {

            imageNumber = Constants.HOTEL_IMG_1;
            if (!hasPermissions(getApplicationContext(), cameraPermissions)) {
                requestPermissions(cameraPermissions,
                        CHOOSE_FILE_REQUEST);
            } else {
                imageIntent();
            }

        });

        ivHotelPic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageNumber = Constants.HOTEL_IMG_2;
                if (!hasPermissions(getApplicationContext(), cameraPermissions)) {
                    requestPermissions(cameraPermissions,
                            CHOOSE_FILE_REQUEST);
                } else {
                    imageIntent();
                }


            }
        });

        ivHotelPic3.setOnClickListener(v -> {

            imageNumber = Constants.HOTEL_IMG_3;
            if (!hasPermissions(getApplicationContext(), cameraPermissions)) {
                requestPermissions(cameraPermissions,
                        CHOOSE_FILE_REQUEST);
            } else {
                imageIntent();
            }

        });

        ivHotelPic4.setOnClickListener(v -> {

            imageNumber = Constants.HOTEL_IMG_4;
            if (!hasPermissions(getApplicationContext(), cameraPermissions)) {
                requestPermissions(cameraPermissions,
                        CHOOSE_FILE_REQUEST);
            } else {
                imageIntent();
            }

        });

        ivHotelPic5.setOnClickListener(v -> {

            imageNumber = Constants.HOTEL_IMG_5;
            if (!hasPermissions(getApplicationContext(), cameraPermissions)) {
                requestPermissions(cameraPermissions,
                        CHOOSE_FILE_REQUEST);
            } else {
                imageIntent();
            }

        });
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


    }

    private void imageIntent() {
        Bitmap.CompressFormat outputCompressFormat;
        outputCompressFormat = Bitmap.CompressFormat.JPEG;
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.OFF).setCropShape(CropImageView.CropShape.RECTANGLE).setFixAspectRatio(true).setAspectRatio(4,3).setOutputCompressFormat(outputCompressFormat)
                .start(this);
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    imageIntent();
                } else {
                    Toast.makeText(this, "Permission denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {

//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                    saveFile(resultUri);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void saveFile(Uri imageUri) {
        try {
            InputStream in = this.getContentResolver().openInputStream(imageUri);
            File dir = Environment.getExternalStorageDirectory();
            File folder = new File(dir, "OxoStay");
            boolean folderExists = folder.exists() || folder.mkdirs();
            folder.mkdirs();
            if (folderExists) {
                String filePath;
                Date currentDate = new Date(System.currentTimeMillis());
                filePath = folder + "/" + "HOTELIMAGE" + ".jpeg";
                OutputStream out = new FileOutputStream(filePath);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();

                uploadImage(imageUri);

            } else {
                Toast.makeText(getApplicationContext(), "Cannot make folder", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadImage(Uri filePath) {
        storage = storage.getReference().getStorage();
        String hotel_id = this.getSharedPreferences(Constants.ACCESS_PREFS, MODE_PRIVATE).getString(
                Constants.HOTEL_ID, "notfound");

        StorageReference ref = null;

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            ref = storageReference.child("hotels/approved/" + hotel_id + "/" + imageNumber + ".jpg");

            StorageReference finalRef = ref;
            final StorageReference finalRef1 = ref;
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            finalRef1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    Log.e(TAG, "onSuccess: " + uri.toString());

                                    DatabaseReference databaseReferenceproduct;

                                    databaseReferenceproduct = FirebaseDatabase.getInstance().getReference();
//                                    Log.e(TAG, "onSuccess: " + userKeyy);
//                                    databaseReferenceproduct.child("users/" + accountType + "s").child(userKeyy).child(aadharBackOrFront).setValue(uri.toString());
                                    databaseReferenceproduct.child(Constants.OXO_STAY_PARTNER).child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child(imageNumber).setValue(uri.toString());
                                    Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_LONG).show();
                                    imageNumber = "";

                                }
                            });

                            Toast.makeText(getApplicationContext(), "Uploaded " + imageNumber, Toast.LENGTH_SHORT).show();

                            onResume();
//                        Log.e(TAG, "uploadImage: " + taskSnapshot.ge);


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                    if(TextUtils.equals(docType, Constants.AADHAAR))
//                    {
//                        btAadhaar.setBackgroundColor(Color.parseColor("#00FF00"));
//                        btAadhaar.setTextColor(Color.parseColor("#000000"));
//                        btAadhaar.setText("Aadhaar card uploaded");
//                        btAadhaar.setOnClickListener(null);
//
//                    }
//                    if(TextUtils.equals(docType, Constants.PAN_CARD))
//                    {
//                        btPanCard.setBackgroundColor(Color.parseColor("#00FF00"));
//                        btPanCard.setTextColor(Color.parseColor("#000000"));
//                        btPanCard.setText("PAN card uploaded");
//                        btPanCard.setOnClickListener(null);
//
//                    }
//                    if(TextUtils.equals(docType, Constants.GST_CERT))
//                    {
//                        btGstCertificate.setBackgroundColor(Color.parseColor("#00FF00"));
//                        btGstCertificate.setTextColor(Color.parseColor("#000000"));
//                        btGstCertificate.setText("GST Certificate uploaded");
//                        btGstCertificate.setOnClickListener(null);
//
//                    }
//                    docType = null;
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String hotel_id = this.getSharedPreferences(Constants.ACCESS_PREFS, MODE_PRIVATE).getString(
                Constants.HOTEL_ID, "noHotelId");

        dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) ;
                {
                    RegisterDto registerDto = dataSnapshot.getValue(RegisterDto.class);
                    tvHotelName.setText(registerDto.getHotel_name());
                    tvLocation.setText(registerDto.getHotel_address());
                    tvHotelContact.setText(registerDto.getPhNumber());
                    tvHotelEmail.setText(registerDto.getHotel_email());
                    tvHotelSecondaryEmail.setText(registerDto.getHotel_secondary_email());
                    tvHotelAddress.setText(registerDto.getHotel_address());



                    if(registerDto.getHotel_img_1() != null)
                    {
                        Picasso.get().load(registerDto.getHotel_img_1()).into(ivHotelPic1);
                        ivHotelPic1.setOnClickListener(v -> {
                            Intent intent = new Intent(ProfileActivity.this, ImageZoomActivity.class);
                            ivHotelPic1.buildDrawingCache();
                            Bitmap image= ivHotelPic1.getDrawingCache();

                            Bundle extras = new Bundle();
//                                extras.putParcelable("imagebitmap", image);
                            extras.putString("imageUrl", registerDto.getHotel_img_1());
                            intent.putExtras(extras);
                            startActivity(intent);

                        });

                    }
                    if(registerDto.getHotel_img_2() != null)
                    {
                        Picasso.get().load(registerDto.getHotel_img_2()).into(ivHotelPic2);
                        ivHotelPic2.setOnClickListener(v -> {
                            Intent intent = new Intent(ProfileActivity.this, ImageZoomActivity.class);
                            ivHotelPic2.buildDrawingCache();
                            Bitmap image= ivHotelPic1.getDrawingCache();

                            Bundle extras = new Bundle();
//                                extras.putParcelable("imagebitmap", image);
                            extras.putString("imageUrl", registerDto.getHotel_img_2());
                            intent.putExtras(extras);
                            startActivity(intent);

                        });


                    }

                    if(registerDto.getHotel_img_3() != null)
                    {
                        Picasso.get().load(registerDto.getHotel_img_3()).into(ivHotelPic3);
                        ivHotelPic3.setOnClickListener(v -> {
                            Intent intent = new Intent(ProfileActivity.this, ImageZoomActivity.class);
                            ivHotelPic3.buildDrawingCache();
                            Bitmap image= ivHotelPic3.getDrawingCache();

                            Bundle extras = new Bundle();
//                                extras.putParcelable("imagebitmap", image);
                            extras.putString("imageUrl", registerDto.getHotel_img_3());
                            intent.putExtras(extras);
                            startActivity(intent);

                        });

                    }

                    if(registerDto.getHotel_img_4() != null)
                    {
                        Picasso.get().load(registerDto.getHotel_img_4()).into(ivHotelPic4);

                        ivHotelPic4.setOnClickListener(v -> {
                            Intent intent = new Intent(ProfileActivity.this, ImageZoomActivity.class);
                            ivHotelPic4.buildDrawingCache();
                            Bitmap image= ivHotelPic4.getDrawingCache();

                            Bundle extras = new Bundle();
//                                extras.putParcelable("imagebitmap", image);
                            extras.putString("imageUrl", registerDto.getHotel_img_4());
                            intent.putExtras(extras);
                            startActivity(intent);

                        });

                    }

                    if(registerDto.getHotel_img_5() != null)
                    {
                        Picasso.get().load(registerDto.getHotel_img_5()).into(ivHotelPic5);
                        ivHotelPic5.setOnClickListener(v -> {
                            Intent intent = new Intent(ProfileActivity.this, ImageZoomActivity.class);
                            ivHotelPic5.buildDrawingCache();
                            Bitmap image= ivHotelPic5.getDrawingCache();

                            Bundle extras = new Bundle();
//                                extras.putParcelable("imagebitmap", image);
                            extras.putString("imageUrl", registerDto.getHotel_img_5());
                            intent.putExtras(extras);
                            startActivity(intent);

                        });

                    }
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}