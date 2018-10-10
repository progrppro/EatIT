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
import android.view.Menu;
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

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Intent intent ;
    private String hotel_user,user ;
    private TextView hotel ;
    private DatabaseReference databaseReference ;

    private DrawerLayout drawerLayout ;

    RecyclerView mRecyclerView;
    ImageAdapter mImageAdapter;
    List<Upload> mUploads;

    private TextView nav_header ;
    private ImageView nav_image ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        intent = getIntent() ;
        hotel_user = intent.getStringExtra("hotel_user");
        user = intent.getStringExtra("user");
        event_menu();
    }
    @Override
    public void onBackPressed() {
            drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if(id == R.id.nav_menu_profile){
            Intent intent_userProfile = new Intent("com.bhukkad.eatit.UserProfileActivity");
            intent_userProfile.putExtra("user",user);
            startActivity(intent_userProfile);
        }
        else if(id == R.id.nav_menu_logout){
            Intent intent_userLogout = new Intent(MenuActivity.this,MainActivity.class);
            startActivity(intent_userLogout);
            System.exit(0);
        }
        else if(id == R.id.nav_menu_location){
            Intent intent_userLocation = new Intent("com.bhukkad.eatit.UserMapsActivity");
            intent_userLocation.putExtra("user",user);
            intent_userLocation.putExtra("node","Users");
            startActivity(intent_userLocation);
        }
        return false;
    }

    public void event_menu() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout_menu);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(MenuActivity.this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_user_menu);
        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerView = findViewById(R.id.recycler_view_menu);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUploads = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Eatit");

        databaseReference.child("Users").child(user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nav_header = (TextView) findViewById(R.id.textView_nav_menu) ;
                nav_image = (ImageView) findViewById(R.id.imageView_nav_menu);
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
