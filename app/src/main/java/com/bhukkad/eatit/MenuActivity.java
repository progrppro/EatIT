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
import android.util.Log;
import android.view.Menu;
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
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity /*implements NavigationView.OnNavigationItemSelectedListener*/ {
    private Intent intent;
    private String hotel_user, user;
    private TextView hotel;
    private DatabaseReference databaseReference;
    private Order order;

//    private DrawerLayout drawerLayout;

    RecyclerView mRecyclerView;
    MenuImageAdapter mImageAdapter;
    List<MenuUpload> mUploads;
    Button cartButton;

//    private TextView nav_header;
//    private ImageView nav_image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        intent = getIntent();
        hotel_user = intent.getStringExtra("hotel_user");
        order = (Order) intent.getSerializableExtra("order");
        user = intent.getStringExtra("user");
        event_menu();
    }

    @Override
    public void onBackPressed() {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
        Intent menuIntent = new Intent(MenuActivity.this, LoginActivity.class);
        menuIntent.putExtra("user",user);
//        startActivity(menuIntent);
//        finish();

        menuIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        menuIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        menuIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(menuIntent);
//        Intent menuIntent = new Intent(MenuActivity.this, LoginActivity.class);
//        menuIntent.putExtra("user",user);
//        startActivity(menuIntent);
//        }
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        int id = menuItem.getItemId();
//
//        if (id == R.id.nav_menu_profile) {
//            Intent intent_userProfile = new Intent("com.bhukkad.eatit.UserProfileActivity");
//            intent_userProfile.putExtra("user", user);
//            startActivity(intent_userProfile);
//        } else if (id == R.id.nav_menu_logout) {
//            Intent intent_userLogout = new Intent(MenuActivity.this, MainActivity.class);
//            startActivity(intent_userLogout);
//            System.exit(0);
//        } else if (id == R.id.nav_menu_location) {
//            Intent intent_userLocation = new Intent("com.bhukkad.eatit.UserMapsActivity");
//            intent_userLocation.putExtra("user", user);
//            intent_userLocation.putExtra("node", "Users");
//            startActivity(intent_userLocation);
//        }
//        return false;
//    }

    public void event_menu() {

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_menu);
//        setSupportActionBar(toolbar);
//
//        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_menu);
//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(MenuActivity.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_user_menu);
//        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerView = findViewById(R.id.recycler_view_menu);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUploads = new ArrayList<>();

        cartButton = (Button) findViewById(R.id.buttonCart_menu);

        cartButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(MenuActivity.this,CartActivity.class);
                cartIntent.putExtra("user",user);
                cartIntent.putExtra("hotel_user",hotel_user);
                cartIntent.putExtra("order",order);
                startActivity(cartIntent);

            }
        }));
        databaseReference = FirebaseDatabase.getInstance().getReference("Eatit");

        databaseReference.child("Hotels").child("Hotel_" + hotel_user).child("Menu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                int i = 1;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (postSnapshot.hasChild("Image")) {
                        String name_img = postSnapshot.child("Name").getValue().toString();
                        String uri_img = postSnapshot.child("Image").getValue().toString();
//                        Log.i("t" + Integer.toString(i), "onDataChange: " + name_img);
                        MenuUpload upload = new MenuUpload(name_img, uri_img);
                        mUploads.add(upload);
//                        i++;
                    } else Toast.makeText(MenuActivity.this,"lala",Toast.LENGTH_SHORT).show();
                }




                mImageAdapter = new MenuImageAdapter(MenuActivity.this, mUploads, user, hotel_user,order);
                mRecyclerView.setAdapter(mImageAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MenuActivity.this, "error " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


//        databaseReference.child("Users").child(user).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_user_menu);
//                View headerView = navigationView.getHeaderView(0);
//                nav_header = (TextView) headerView.findViewById(R.id.textView_nav_menu);
//                nav_image = (ImageView) headerView.findViewById(R.id.imageView_nav_menu);
//                String name, pic;
////                nav_image.setImageResource(R.drawable.background_eatit);
////                nav_header.setText("lala");
//                if (dataSnapshot.hasChild("Image")) {
//                    pic = dataSnapshot.child("Image").getValue().toString();
//                    Toast.makeText(MenuActivity.this, pic, Toast.LENGTH_SHORT).show();
//                    Picasso.get().load(pic).networkPolicy(NetworkPolicy.OFFLINE).fit().into(nav_image);
//                }
//                name = dataSnapshot.child("Name").getValue().toString();
////                Toast.makeText(MenuActivity.this, name, Toast.LENGTH_SHORT).show();
//                nav_header.setText(name);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

    }
}
