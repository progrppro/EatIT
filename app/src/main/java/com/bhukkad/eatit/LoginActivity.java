package com.bhukkad.eatit;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout ;

    private static final int time = 1500 ;
    private static long backpressed ;
    private String user;
    Intent intent ;
    private DatabaseReference databaseReference ;

    RecyclerView mRecyclerView;
    ImageAdapter mImageAdapter;
    List<Upload> mUploads;

    private TextView nav_header ;
    private ImageView nav_image ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        intent = getIntent() ;
        user = intent.getStringExtra("user") ;
        event_login() ;
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
                Toast.makeText(LoginActivity.this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            }
            backpressed = System.currentTimeMillis();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        if(id == R.id.nav_profile){
            Intent intent_userProfile = new Intent("com.bhukkad.eatit.UserProfileActivity");
            intent_userProfile.putExtra("user",user);
            startActivity(intent_userProfile);
        }
        else if(id == R.id.nav_logout){
            Intent intent_userLogout = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent_userLogout);
            System.exit(0);
        }
        else if(id == R.id.nav_location){
            Intent intent_userLocation = new Intent("com.bhukkad.eatit.UserMapsActivity");
            intent_userLocation.putExtra("user",user);
            intent_userLocation.putExtra("node","Users");
            startActivity(intent_userLocation);
        }

        return false;
    }

    public void event_login()
    {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(LoginActivity.this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_user);
        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUploads = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Eatit");
        databaseReference.child("Hotels").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    if(postSnapshot.hasChild("Image")) {
                        String name_img = postSnapshot.child("Name").getValue().toString();
                        String uri_img = postSnapshot.child("Image").getValue().toString();
                        Upload upload = new Upload(name_img, uri_img);
                        mUploads.add(upload);
                    }
                }
                mImageAdapter = new ImageAdapter(LoginActivity.this,mUploads,user);
                mRecyclerView.setAdapter(mImageAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "error "+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        databaseReference.child("Users").child(user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nav_header = (TextView) findViewById(R.id.textView5) ;
                nav_image = (ImageView) findViewById(R.id.imageView2);
                String name,pic;

                name = dataSnapshot.child("Name").getValue().toString() ;
                nav_header.setText(name);
                if(dataSnapshot.hasChild("Image")) {
                    pic = dataSnapshot.child("Image").getValue().toString();
                    Picasso.get().load(pic).fit().into(nav_image);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
