package com.bhukkad.eatit;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CatImageAdapter extends RecyclerView.Adapter<CatImageAdapter.CatImageViewHolder> {

    private Context mContext;
    private List<CatUpload> mUploads;
    String user, hotel_user;
    private String name ;
    private static int cnt ;
    private Order order;

    public CatImageAdapter(Context mContext, List<CatUpload> mUploads, String user, String hotel_user,Order order) {
        this.mContext = mContext;
        this.mUploads = mUploads;
        this.hotel_user = hotel_user;
        this.user = user;
        this.order = order;
    }

    @NonNull
    @Override
    public CatImageAdapter.CatImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.cat_image_item ,viewGroup,false);
        return new CatImageAdapter.CatImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CatImageViewHolder catImageViewHolder, int i) {
        final CatUpload upload = mUploads.get(i);
        catImageViewHolder.TextViewName.setText(upload.getDish());
        catImageViewHolder.TextViewImage.setText(upload.getImageuri());
        catImageViewHolder.TextViewPrice.setText("₹" + upload.getPrice());
        catImageViewHolder.TextViewDesc.setText(upload.getDescription());
        Picasso.get().load(upload.getImageuri()).fit().into(catImageViewHolder.imageview);

        catImageViewHolder.buttonCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = catImageViewHolder.TextViewName.getText().toString() ;
                order.addDish(catImageViewHolder.TextViewName.getText().toString(),catImageViewHolder.TextViewPrice.getText().toString(),catImageViewHolder.TextViewImage.getText().toString());
                order.getCount();
                Toast.makeText(mContext,order.dishes_name.toString() + "\n" + order.dishes_count.toString(),Toast.LENGTH_SHORT).show();
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

    public class CatImageViewHolder extends RecyclerView.ViewHolder{
        private TextView TextViewName,TextViewDesc,TextViewPrice,TextViewImage;
        private ImageView imageview;
        private Button buttonCat;
        public CatImageViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonCat = itemView.findViewById(R.id.button_cat);
            TextViewName = itemView.findViewById(R.id.textView_name_cat);
            TextViewImage = itemView.findViewById(R.id.textView_image_cat);
            TextViewPrice = itemView.findViewById(R.id.textView_price_cat);
            TextViewDesc = itemView.findViewById(R.id.textView_description_cat);
            imageview = itemView.findViewById(R.id.imageView_card_cat );

        }
    }
}
