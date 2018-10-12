package com.bhukkad.eatit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CatActivity extends AppCompatActivity /*implements NavigationView.OnNavigationItemSelectedListener */{

    Intent intent ;
    String user,category, hotel_user;
    Order order;
//    private DrawerLayout drawerLayout ;

    RecyclerView mRecyclerView;
    CatImageAdapter mImageAdapter;
    List<CatUpload> mUploads;
    Button cartButton;

//    private TextView nav_header ;
//    private ImageView nav_image ;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);
        intent = getIntent();
        user = intent.getStringExtra("user");
        category = intent.getStringExtra("category");
        hotel_user = intent.getStringExtra("hotel_user");
        order = (Order)intent.getSerializableExtra("order");
        Toast.makeText(CatActivity.this,Integer.toString(order.getCount()),Toast.LENGTH_SHORT).show();
        event_cat();
    }

    @Override
    public void onBackPressed() {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else{
            Intent menuIntent = new Intent(CatActivity.this, MenuActivity.class);
            menuIntent.putExtra("user",user);
            menuIntent.putExtra("hotel_user",hotel_user);
            menuIntent.putExtra("order",order);
            startActivity(menuIntent);
            finish();
//        Intent loginIntent = new Intent(CatActivity.this, LoginActivity.class);
//        loginIntent.putExtra("user",user);
//        startActivity(loginIntent);
//        }
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//
//        int id = menuItem.getItemId();
//
//        if(id == R.id.nav_profile){
//            Intent intent_userProfile = new Intent("com.bhukkad.eatit.UserProfileActivity");
//            intent_userProfile.putExtra("user",user);
//            startActivity(intent_userProfile);
//        }
//        else if(id == R.id.nav_logout){
//            Intent intent_userLogout = new Intent(CatActivity.this,MainActivity.class);
//            startActivity(intent_userLogout);
//            System.exit(0);
//        }
//        else if(id == R.id.nav_location){
//            Intent intent_userLocation = new Intent("com.bhukkad.eatit.UserMapsActivity");
//            intent_userLocation.putExtra("user",user);
//            intent_userLocation.putExtra("node","Users");
//            startActivity(intent_userLocation);
//        }
//        return false;
//    }

    public void event_cat(){

        mRecyclerView = findViewById(R.id.recycler_view_cat);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUploads = new ArrayList<>();
        cartButton = (Button) findViewById(R.id.buttonCart_cat);

        cartButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(CatActivity.this,CartActivity.class);
                cartIntent.putExtra("user",user);
                cartIntent.putExtra("hotel_user",hotel_user);
                cartIntent.putExtra("order",order);
                startActivity(cartIntent);

            }
        }));


        databaseReference = FirebaseDatabase.getInstance().getReference("Eatit");

        databaseReference.child("Hotels").child("Hotel_" + hotel_user).child("Menu").child(category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                int i = 1;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (postSnapshot.hasChild("Image")) {
                        String dish = postSnapshot.child("DishName").getValue().toString();
                        String price = postSnapshot.child("Price").getValue().toString();
                        String image = postSnapshot.child("Image").getValue().toString();
//                        Toast.makeText(CatActivity.this,dish +" / " + price +" /",Toast.LENGTH_SHORT).show();
                        String description = "";
                        if(postSnapshot.hasChild("Description"))
                            description = postSnapshot.child("Description").getValue().toString();
//                        Log.i("t" + Integer.toString(i), "onDataChange: " + name_img);
                        CatUpload upload = new CatUpload(dish, price, image, description);
                        mUploads.add(upload);
//                        i++;
                    }// else Toast.makeText(CatActivity.this,"lala",Toast.LENGTH_SHORT).show();
                }




                mImageAdapter = new CatImageAdapter(CatActivity.this, mUploads, user, hotel_user,order);
                mRecyclerView.setAdapter(mImageAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CatActivity.this, "error " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_cat);
//        setSupportActionBar(toolbar);
//
//        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout_cat);
//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(CatActivity.this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_user_cat);
//        navigationView.setNavigationItemSelectedListener(this);
    }
}
