package com.bhukkad.eatit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CartImageAdapter extends RecyclerView.Adapter<CartImageAdapter.CartImageViewHolder> {
    private Context mContext;
    private List<CartUpload> mUploads;
    String user, hotel_user;
    private String name;
    private static int cnt;
    private Order order;
    private int total_price;

    public CartImageAdapter(Context mContext, List<CartUpload> mUploads, String user, String hotel_user, Order order) {
        this.mContext = mContext;
        this.mUploads = mUploads;
        this.hotel_user = hotel_user;
        this.user = user;
        this.order = order;
    }

    @NonNull
    @Override
    public CartImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.cart_image_item, viewGroup, false);
        return new CartImageAdapter.CartImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartImageViewHolder cartImageViewHolder, final int i) {
        final CartUpload upload = mUploads.get(i);
        cartImageViewHolder.TextViewName.setText(upload.getDish());
        cartImageViewHolder.TextViewCount.setText(Integer.toString(upload.getCount()));
        cartImageViewHolder.TextViewPrice.setText(upload.getPrice());
        Picasso.get().load(upload.getImageuri()).fit().into(cartImageViewHolder.imageview);
        cartImageViewHolder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total_price = 0;
                int ind = order.dishes_name.indexOf(cartImageViewHolder.TextViewName.getText());
                int x = order.dishes_count.get(ind);
                order.dishes_count.set(ind, x - 1);
                if (x > 1) {
                    for (int j = 0; j < order.getCount(); j++) {
                        total_price += Integer.parseInt(order.dishes_price.get(j).substring(1)) * order.dishes_count.get(j);
                    }
                    cartImageViewHolder.TextViewCount.setText(Integer.toString(x - 1));
                    TextView txtView = (TextView) ((Activity) mContext).findViewById(R.id.priceTextView);
                    txtView.setText("₹" + Integer.toString(total_price));
                } else {
                    Toast.makeText(mContext, order.dishes_name.toString() + "\n" + order.dishes_count.toString(), Toast.LENGTH_SHORT).show();
                    Intent cartIntentMinus = new Intent(mContext, CartActivity.class);
                    cartIntentMinus.putExtra("user", user);
                    cartIntentMinus.putExtra("hotel_user", hotel_user);
                    cartIntentMinus.putExtra("order", order);
                    mContext.startActivity(cartIntentMinus);
                }


//                Intent intent = new Intent(mContext, CatActivity.class);
//                intent.putExtra("hotel_user",hotel_user);
//                intent.putExtra("user",user);
//                intent.putExtra("category", catImageViewHolder.TextViewName.getText().toString());
//                mContext.startActivity(intent);
//                Toast.makeText(mContext,menuImageViewHolder.TextViewName.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });

        cartImageViewHolder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ind = order.dishes_name.indexOf(cartImageViewHolder.TextViewName.getText());
                int x = order.dishes_count.get(ind);
                order.dishes_count.set(ind, x + 1);
//                Toast.makeText(mContext, order.dishes_name.toString() + "\n" + order.dishes_count.toString(), Toast.LENGTH_SHORT).show();
//                Intent cartIntentPlus = new Intent(mContext, CartActivity.class);
//                cartIntentPlus.putExtra("user", user);
//                cartIntentPlus.putExtra("hotel_user", hotel_user);
//                cartIntentPlus.putExtra("order", order);
//                mContext.startActivity(cartIntentPlus);
                total_price = 0;


                order.dishes_count.set(ind, x + 1);
                for (int j = 0; j < order.getCount(); j++) {
                    total_price += Integer.parseInt(order.dishes_price.get(j).substring(1)) * order.dishes_count.get(j);
                }
                cartImageViewHolder.TextViewCount.setText(Integer.toString(x + 1));
                TextView txtView = (TextView) ((Activity) mContext).findViewById(R.id.priceTextView);
                txtView.setText("₹" + Integer.toString(total_price));

//                    Intent cartIntentMinus = new Intent(mContext,CartActivity.class);
//                Intent intent = new Intent(mContext, CatActivity.class);
//                intent.putExtra("hotel_user",hotel_user);
//                intent.putExtra("user",user);
//                intent.putExtra("category", catImageViewHolder.TextViewName.getText().toString());
//                mContext.startActivity(intent);
//                Toast.makeText(mContext,menuImageViewHolder.TextViewName.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class CartImageViewHolder extends RecyclerView.ViewHolder {
        private TextView TextViewName, TextViewCount, TextViewPrice;
        private ImageView imageview;
        private Button minusButton, plusButton;

        public CartImageViewHolder(@NonNull View itemView) {
            super(itemView);
            minusButton = itemView.findViewById(R.id.minus_cart);
            TextViewCount = itemView.findViewById(R.id.textView_amount);
            plusButton = itemView.findViewById(R.id.plus_cart);
            TextViewName = itemView.findViewById(R.id.textView_name_cart);
            TextViewPrice = itemView.findViewById(R.id.textView_price_cart);
            imageview = itemView.findViewById(R.id.imageView_card_cart);

        }
    }

}
