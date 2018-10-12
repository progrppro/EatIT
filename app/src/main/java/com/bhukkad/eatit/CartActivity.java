package com.bhukkad.eatit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    Intent intent;
    int totalPrice;
    String user, hotel_user;
    Order order;
//    private DrawerLayout drawerLayout ;

    RecyclerView mRecyclerView;
    CartImageAdapter mImageAdapter;
    List<CartUpload> mUploads;
    TextView totalPriceTextView;

    //    private TextView nav_header ;
//    private ImageView nav_image ;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        intent = getIntent();
        user = intent.getStringExtra("user");
        hotel_user = intent.getStringExtra("hotel_user");
        order = (Order) intent.getSerializableExtra("order");
        Toast.makeText(CartActivity.this, Integer.toString(order.getCount()), Toast.LENGTH_SHORT).show();
        event_cart();
    }

    @Override
    public void onBackPressed() {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else{
//        Intent cartIntent = new Intent(CartActivity.this, MenuActivity.class);
//        cartIntent.putExtra("user", user);
//        cartIntent.putExtra("hotel_user", hotel_user);
//        cartIntent.putExtra("order", order);
//        startActivity(cartIntent);
//        finish();
        Intent menuIntent = new Intent(CartActivity.this, MenuActivity.class);
        menuIntent.putExtra("user", user);
        menuIntent.putExtra("hotel_user", hotel_user);
        menuIntent.putExtra("order", order);
        startActivity(menuIntent);
        finish();
//        }
    }

    public void event_cart() {
//        Toast.makeText(CartActivity.this,"CartActivity",Toast.LENGTH_SHORT).show();
        mRecyclerView = findViewById(R.id.recycler_view_cart);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
        totalPrice = 0;
        mUploads = new ArrayList<>();
        int i;
        for (i = 0; i < order.getCount(); i++) {
            String dish = order.dishes_name.get(i);
            String price = order.dishes_price.get(i);
            String image = order.dishes_image.get(i);
            int count = order.dishes_count.get(i);
            totalPrice += Integer.parseInt(price.substring(1)) * order.dishes_count.get(i);
//                        Toast.makeText(CartActivity.this,/*dish +" / " + price +" /"*/ image,Toast.LENGTH_SHORT).show();
////                        Log.i("t" + Integer.toString(i), "onDataChange: " + name_img);
            if (order.dishes_count.get(i) > 0) {
                CartUpload upload = new CartUpload(dish, price, image, count);
                mUploads.add(upload);
            }
////                        i++;
        }// else Toast.makeText(CatActivity.this,"lala",Toast.LENGTH_SHORT).show();
////
////
////
////
////
        mImageAdapter = new CartImageAdapter(CartActivity.this, mUploads, user, hotel_user, order);
        mRecyclerView.setAdapter(mImageAdapter);
        totalPriceTextView = (TextView) findViewById(R.id.priceTextView);
        totalPriceTextView.setText("₹" + Integer.toString(totalPrice));


//        CartUpload upload = new CartUpload(order.dishes_name.get(0),order.dishes_price.get(0),order.dishes_image.get(0));
//        Toast.makeText(CartActivity.this,upload.getDish()+"?"+upload.getPrice()+"?"+upload.getImageuri(),Toast.LENGTH_SHORT).show();
    }
}
