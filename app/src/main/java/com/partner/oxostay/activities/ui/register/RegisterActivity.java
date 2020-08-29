package com.partner.oxostay.activities.ui.register;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.partner.oxostay.R;
import com.partner.oxostay.dtos.RegisterDto;
import com.partner.oxostay.utils.Constants;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private ImageView ivBack;
    private Button btAadhaar, btPanCard, btGstCertificate, btSubmit;
    private String[] cameraPermissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private String docType, strFullName, strPhNumber, strAddress;
    private int CHOOSE_FILE_REQUEST = 1;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private AppCompatEditText etFullname, etPhNumber, etAddress;
    private String aadhaarCard;
    private String panCard;
    private String gstCert;
    private RegisterDto registerDto;
    private DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setUpViews();
    }


    private void setUpViews() {
        registerDto = new RegisterDto();
        dbRef = FirebaseDatabase.getInstance().getReference();
        ivBack = findViewById(R.id.ivBack);
//        btAadhaarBack = findViewById(R.id.btAadhaarBack);
        btAadhaar = findViewById(R.id.btAadhaar);
        btPanCard = findViewById(R.id.btPanCard);
        btGstCertificate = findViewById(R.id.btGstCertificate);
        etFullname = findViewById(R.id.etFullname);
        etPhNumber = findViewById(R.id.etPhoneNumber);
        etAddress = findViewById(R.id.etAddress);
        btSubmit = findViewById(R.id.btSubmit);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        /**Aadhaar card front button click**/
        btAadhaar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                strFullName = etFullname.getText().toString();
                strPhNumber = etPhNumber.getText().toString();
                strAddress = etAddress.getText().toString();
                if (!TextUtils.isEmpty(strFullName) && !TextUtils.isEmpty(strPhNumber) && !TextUtils.isEmpty(strAddress)) {
                    if (!hasPermissions(getApplicationContext(), cameraPermissions)) {
                        requestPermissions(cameraPermissions,
                                CHOOSE_FILE_REQUEST);
                    } else {
                        docType = Constants.AADHAAR;
                        imageIntent();
                        registerDto.setFullName(strFullName);
                        registerDto.setPhNumber(strPhNumber);
                        registerDto.setAddress(strAddress);

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }


            }
        });


        btPanCard.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                strFullName = etFullname.getText().toString();
                strPhNumber = etPhNumber.getText().toString();
                strAddress = etAddress.getText().toString();
                if (!TextUtils.isEmpty(strFullName) && !TextUtils.isEmpty(strPhNumber) && !TextUtils.isEmpty(strAddress)) {
                    if (!hasPermissions(getApplicationContext(), cameraPermissions)) {
                        requestPermissions(cameraPermissions,
                                CHOOSE_FILE_REQUEST);
                    } else {
                        docType = Constants.PAN_CARD;
                        imageIntent();

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btGstCertificate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                strFullName = etFullname.getText().toString();
                strPhNumber = etPhNumber.getText().toString();
                strAddress = etAddress.getText().toString();
                if (!TextUtils.isEmpty(strFullName) && !TextUtils.isEmpty(strPhNumber) && !TextUtils.isEmpty(strAddress)) {
                    if (!hasPermissions(getApplicationContext(), cameraPermissions)) {
                        requestPermissions(cameraPermissions,
                                CHOOSE_FILE_REQUEST);
                    } else {
                        docType = Constants.GST_CERT;
                        imageIntent();

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }


            }
        });
        /**End**/

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(registerDto.getFullName() != null && registerDto.getPhNumber() != null && registerDto.getAddress() != null
                && registerDto.getAadhaarCard() != null && registerDto.getPanCard() != null && registerDto.getGstCert() != null)
                {
                    registerDto.setApprovedOrNot(false);
                    dbRef.child(Constants.OXO_STAY_PARTNER).child("hotelstobeapproved").push().setValue(registerDto);

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please fill all the fields and select all the documents required.", Toast.LENGTH_SHORT).show();
                }

            }
        });
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                filePath = folder + "/" + docType + ".jpeg";
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

        final String userKeyy = this.getSharedPreferences(Constants.ACCESS_PREFS, MODE_PRIVATE).getString(
                Constants.USER_KEY, "defaultValueUserKey");
        StorageReference ref = null;

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            ref = storageReference.child("hotels/tobeapproved/" + strPhNumber + "/" + docType + ".jpg");

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

//                                    DatabaseReference databaseReferenceproduct;
//
//                                    databaseReferenceproduct = FirebaseDatabase.getInstance().getReference();
//                                    Log.e(TAG, "onSuccess: " + userKeyy);
//                                    databaseReferenceproduct.child("users/" + accountType + "s").child(userKeyy).child(aadharBackOrFront).setValue(uri.toString());
//                                    showToast("Uploaded");

                                    if(TextUtils.equals(docType, Constants.AADHAAR))
                                    {
                                        registerDto.setAadhaarCard(uri.toString());
                                    }
                                    if(TextUtils.equals(docType, Constants.PAN_CARD))
                                    {
                                        registerDto.setPanCard(uri.toString());
                                    }
                                    if(TextUtils.equals(docType, Constants.GST_CERT))
                                    {
                                        registerDto.setGstCert(uri.toString());
                                    }
                                    docType = null;

//                                databaseReferenceproduct.setValue(uri.toString());

                                }
                            });


//                        Log.e(TAG, "uploadImage: " + taskSnapshot.ge);


                            Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();


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

                }
            });
        }
    }

    private void imageIntent() {
        Bitmap.CompressFormat outputCompressFormat;
        outputCompressFormat = Bitmap.CompressFormat.JPEG;
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.OFF).setCropShape(CropImageView.CropShape.RECTANGLE).setFixAspectRatio(false).setOutputCompressFormat(outputCompressFormat)
                .start(this);
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }
}