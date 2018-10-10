package com.bhukkad.eatit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CatActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Intent intent ;
    String user,category ;
    private DrawerLayout drawerLayout ;

    RecyclerView mRecyclerView;
    ImageAdapter mImageAdapter;
    List<Upload> mUploads;

    private TextView nav_header ;
    private ImageView nav_image ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);
        intent = getIntent();
        user = intent.getStringExtra("user");
        category = intent.getStringExtra("category");
        event_cat();
    }

    @Override
    public void onBackPressed() {
        drawerLayout.closeDrawer(GravityCompat.START);
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
            Intent intent_userLogout = new Intent(CatActivity.this,MainActivity.class);
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

    public void event_cat(){
        TextView abc = findViewById(R.id.qwe);
        abc.setText(user);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_cat);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout_cat);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(CatActivity.this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_user_cat);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
