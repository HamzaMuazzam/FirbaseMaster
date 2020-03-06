package com.example.firbasemaster;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FBSorageActivity extends AppCompatActivity {

    public static final int PICK_CODE = 99;
    private static final int STORAGE_PERMISSION_CODE = 100;
    private boolean mGranted;
    private StorageReference mStorageRef;
    public static final String TAG = "MyTag";

    private ImageView iv_test;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_b_sorage);
        intiviews();
        mStorageRef = FirebaseStorage.getInstance().getReference("docs/");
        getpermission();

    }

    private void intiviews() {
        mProgressBar = findViewById(R.id.progress_horizontal2);
        iv_test = findViewById(R.id.iv_test);
    }

    public void uploadtextfile(View view) {
        StorageReference child = mStorageRef.child("textfiles/abcd.txt");
        String s = "this is demo data";
        UploadTask uploadTask = child.putBytes(s.getBytes());
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(FBSorageActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FBSorageActivity.this, "OnFailure: " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void upload_file_Using_putbyte(View view) {

        Bitmap bitmap = readimage();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);

        byte[] byteArray = byteArrayOutputStream.toByteArray();
        UploadTask uploadTask = mStorageRef.child("images/lahore.jpg").putBytes(byteArray);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(FBSorageActivity.this
                        , "Image Uploaded : "
                        , Toast.LENGTH_SHORT).show();
                ;
            }
        });


    }


    private Bitmap readimage() {
        InputStream inputStream = null;
        try {
            inputStream = getAssets().open("lahorelow.jpg");
            BitmapDrawable bitmapDrawable = (BitmapDrawable) Drawable.createFromStream(inputStream, null);
            return bitmapDrawable.getBitmap();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return null;
    }

    public void uploadfile_inputstream(View view) {
        InputStream inputStream = null;
//        File file = new File(getFilesDir(), "lahorelow.jpg");
//        String absolutePath = file.getAbsolutePath();
        try {
            inputStream = new FileInputStream(new File(getFilesDir(), "lahorelow.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("MYTAG", "uploadimage_inputstream: " + e.getMessage());
        }

        if (inputStream == null) {
            Toast.makeText(FBSorageActivity.this, "null", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(FBSorageActivity.this, "not null", Toast.LENGTH_SHORT).show();
            UploadTask uploadTask = mStorageRef.child("images/lahorelow.jpg").putStream(inputStream);
            final InputStream finalInputStream = inputStream;
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    if (finalInputStream != null) {
                        try {
                            finalInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(FBSorageActivity.this, "Image Uploaded Successfully",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    public void local_storage_file_uploading(View view) {


        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select an Image"), PICK_CODE);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CODE && data != null) {
            Uri imageUri = data.getData();
            UploadTask uploadTask = mStorageRef.child("localfiles/" + imageUri.getLastPathSegment())
                    .putFile(imageUri);
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.setIndeterminate(false);
            uploadTask

                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            mProgressBar.setProgress((int) progress, true);

                        }
                    })

                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(FBSorageActivity.this, "File Uploaded"
                                    , Toast.LENGTH_SHORT).show();
                        }
                    });


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                mGranted = true;
            } else {
                Toast.makeText(this, "Please allow the permission to read data", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void getpermission() {
        String externalReadPermission = Manifest.permission.READ_EXTERNAL_STORAGE.toString();
        String externalWritePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE.toString();

        if (ContextCompat.checkSelfPermission(this, externalReadPermission) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, externalWritePermission) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{externalReadPermission, externalWritePermission}, STORAGE_PERMISSION_CODE);
            }
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void dwonload_andsaveUsingByteArray(View view) {

        File outputfile = new File(FBSorageActivity.this.getExternalFilesDir(Environment.DIRECTORY_DCIM), "mynewimage.jpeg");

        long ONE_MEGABYTE = 1024 * 1024;

        mStorageRef.child("images/lahorelow.jpg").getBytes(ONE_MEGABYTE)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {

                        iv_test.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                        Toast.makeText(FBSorageActivity.this, "File Downloaded", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Before trycatch" + outputfile);

                        try {
                            FileOutputStream fos = new FileOutputStream(outputfile);
                            fos.write(bytes);
                            fos.close();
                            Log.d(TAG, "onSuccess: File saved on device: " + outputfile);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d(TAG, "Exception: " + e.getMessage());

                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FBSorageActivity.this, "Download error", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: Error: " + e.getMessage());
                    }
                });


    }

    public void downoadFileUsing_getFile(View view) {
        File outputfile = new File(FBSorageActivity.this.getExternalFilesDir(Environment.DIRECTORY_DCIM), "mynewimage.jpeg");
        Log.d(TAG, "path: " + outputfile);

        mStorageRef.child("images/lahore.jpg").getFile(outputfile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(FBSorageActivity.this, "File Downloaded", Toast.LENGTH_LONG).show();
                        iv_test.setImageURI(Uri.fromFile(outputfile));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
                Toast.makeText(FBSorageActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    public void downoad_File_Using_getDownloadUrl(View view) {

        mStorageRef.child("localfiles/video:44596").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d(TAG, "onSuccess: " + uri.toString());
                downloadmanager(uri);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }
        });
    }

    private void downloadmanager(Uri uri) {
        File file = new File(String.valueOf(FBSorageActivity.this.getExternalFilesDir(Environment.DIRECTORY_DCIM)),"file.mp4");
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle("Title")
                .setDescription("Description")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setDestinationUri(Uri.fromFile(file));
        if (downloadManager != null) {
            downloadManager.enqueue(request);
        }
    }
}
