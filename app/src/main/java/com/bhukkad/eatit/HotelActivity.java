package com.bhukkad.eatit;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class HotelActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int time = 1500 ;
    private static long backpressed ;
    private Intent intent;
    private String user ;
    private TextView name_hotel;
    private DatabaseReference databaseReference ;
    private StorageReference storageReference;
    private ImageView imageView,nav_hotelimage;
    private Button upload_dish;
    private EditText category_hotel,dishname_hotel,price_hotel,description_hotel;
    private static int Permission_value = 1 ;
    private static final int Pick_Image_Request = 1 ;
    private Uri imageUri ;
    private StorageTask storageTask ;
    private DrawerLayout drawerLayout ;
    private boolean category ;
    private String cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
        intent = getIntent() ;
        user = intent.getStringExtra("user") ;
        event_hotel();
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            if (time + backpressed > System.currentTimeMillis()) {
                super.onBackPressed();
            } else {
                Toast.makeText(HotelActivity.this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            }
            backpressed = System.currentTimeMillis();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.nav_profile_hotel){

        }
        else if(R.id.nav_image_hotel == id){
            Intent intent_hotelImage = new Intent("com.bhukkad.eatit.HotelImageActivity");
            intent_hotelImage.putExtra("user",user);
            startActivity(intent_hotelImage);
        }
        else if(id == R.id.nav_location_hotel){
            Intent intent_hotelLocation = new Intent("com.bhukkad.eatit.UserMapsActivity");
            intent_hotelLocation.putExtra("user",user);
            intent_hotelLocation.putExtra("node","Hotels");
            startActivity(intent_hotelLocation);
        }
        else if(id == R.id.nav_logout_hotel){
            Intent intent2 = new Intent(HotelActivity.this,MainActivity.class);
            startActivity(intent2);
            System.exit(0);
        }
        return true ;
    }

    public void event_hotel(){
        category = false ;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout2);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(HotelActivity.this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_hotel);
        navigationView.setNavigationItemSelectedListener(this);

        name_hotel = (TextView) findViewById(R.id.textView10);
        imageView = (ImageView)findViewById(R.id.imageView3);
        upload_dish = (Button)findViewById(R.id.button3);
        category_hotel = (EditText)findViewById(R.id.editText12);
        dishname_hotel = (EditText)findViewById(R.id.editText3);
        description_hotel = (EditText) findViewById(R.id.editText6);
        price_hotel = (EditText)findViewById(R.id.editText5) ;

        databaseReference = FirebaseDatabase.getInstance().getReference("Eatit");

        databaseReference.child("Hotels").child(user).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name,image_hotel ;
                name_hotel = (TextView) findViewById(R.id.textView10);
                nav_hotelimage = (ImageView) findViewById(R.id.imageView5) ;

                name = dataSnapshot.child("Name").getValue().toString() ;
                name_hotel.setText(name);
                if(dataSnapshot.hasChild("Image")){
                    image_hotel = dataSnapshot.child("Image").getValue().toString();
                    Picasso.get().load(image_hotel).fit().into(nav_hotelimage);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        storageReference = FirebaseStorage.getInstance().getReference("Eatit/Hotels/"+user);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,Pick_Image_Request);
            }
        });

        upload_dish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = false;
                if(category_hotel.getText().toString().equals("") || category_hotel.getText().toString().equals(null) || dishname_hotel.getText().toString().equals("")
                        || dishname_hotel.getText().toString().equals(null) || price_hotel.getText().toString().equals("") || price_hotel.getText().toString().equals(null)){
                    if(!category_hotel.getText().toString().equals("")){
                        category = true ;
                        databaseReference.child("Hotels").child(user).child("Menu").child(category_hotel.getText().toString()).child("Name").setValue(category_hotel.getText().toString());
                        cat = category_hotel.getText().toString();
                        if (storageTask != null && storageTask.isInProgress()) {
                            Toast.makeText(HotelActivity.this, "Upload in Progress", Toast.LENGTH_SHORT).show();
                        } else {
                            UploadFile();
                        }
                    }
                    else{
                        Toast.makeText(HotelActivity.this,"Category/Dish Name/Price fields cannot be empty",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    databaseReference.child("Hotels").child(user).child("Menu").child(category_hotel.getText().toString()).child(dishname_hotel.getText().toString())
                            .child("DishName").setValue(dishname_hotel.getText().toString());
                    databaseReference.child("Hotels").child(user).child("Menu").child(category_hotel.getText().toString()).child(dishname_hotel.getText().toString())
                            .child("Description").setValue(description_hotel.getText().toString());
                    databaseReference.child("Hotels").child(user).child("Menu").child(category_hotel.getText().toString()).child(dishname_hotel.getText().toString())
                            .child("Price").setValue(price_hotel.getText().toString());

                    if (storageTask != null && storageTask.isInProgress()) {
                        Toast.makeText(HotelActivity.this, "Upload in Progress", Toast.LENGTH_SHORT).show();
                    } else {
                        UploadFile();
                    }
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
            String imgname = "";
            if(!dishname_hotel.getText().toString().trim().equals(""))
                imgname = dishname_hotel.getText().toString();
            else
                imgname = cat;
            StorageReference ref = storageReference.child(imgname + "." + getFileExtension(imageUri));
            storageTask = ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    },500);

                    Toast.makeText(HotelActivity.this,"Upload Successful",Toast.LENGTH_SHORT).show();

                    if(category){
                        databaseReference.child("Hotels").child(user).child("Menu").child(category_hotel.getText().toString())
                                .child("Image").setValue(taskSnapshot.getDownloadUrl().toString());
                    }
                    else {
                        databaseReference.child("Hotels").child(user).child("Menu").child(category_hotel.getText().toString()).child(dishname_hotel.getText().toString())
                                .child("Image").setValue(taskSnapshot.getDownloadUrl().toString());
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(HotelActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
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
