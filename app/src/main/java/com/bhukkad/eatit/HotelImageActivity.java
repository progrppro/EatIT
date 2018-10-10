package com.bhukkad.eatit;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class HotelImageActivity extends AppCompatActivity {

    private Intent intent;
    private String user;
    private ImageView imageView;
    private Button select_image,upload_image ;
    private DatabaseReference databaseReference;
    private StorageReference storageReference ;
    private static int Permission_value = 1 ;
    private static final int Pick_Image_Request = 1 ;
    private Uri imageUri ;
    private StorageTask storageTask ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_image);
        intent = getIntent();
        user = intent.getStringExtra("user");
        event_hotelimage();
    }

    public void event_hotelimage(){
        imageView = (ImageView) findViewById(R.id.imageView4);
        select_image = (Button) findViewById(R.id.button5);
        upload_image = (Button) findViewById(R.id.button6);

        databaseReference = FirebaseDatabase.getInstance().getReference("Eatit");

        storageReference = FirebaseStorage.getInstance().getReference("Eatit/Hotels/"+user);

        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,Pick_Image_Request);
            }
        });

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (storageTask != null && storageTask.isInProgress()) {
                        Toast.makeText(HotelImageActivity.this, "Upload in Progress", Toast.LENGTH_SHORT).show();
                    } else {
                        UploadFile();
                    }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Pick_Image_Request && resultCode == RESULT_OK && data != null
                && data.getData() != null){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }


    public void UploadFile(){
        if(imageUri != null){
            StorageReference ref = storageReference.child(user + "." + getFileExtension(imageUri));
            storageTask = ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    },500);

                    Toast.makeText(HotelImageActivity.this,"Upload Successful",Toast.LENGTH_SHORT).show();

                    databaseReference.child("Hotels").child(user).child("Image").setValue(taskSnapshot.getDownloadUrl().toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(HotelImageActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                }
            });
        }
        else{
            Toast.makeText(this,"No Image selected",Toast.LENGTH_SHORT).show();
        }
    }

    public String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

}
