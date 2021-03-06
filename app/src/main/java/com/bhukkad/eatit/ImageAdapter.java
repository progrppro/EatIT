package com.bhukkad.eatit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter <ImageAdapter.ImageViewHolder>{
    private Context mContext;
    private List<Upload> mUploads;
    private String hotel,user ;
    private Order order;

    public ImageAdapter(Context mContext, List<Upload> mUploads,String hotel,String user) {
        this.mContext = mContext;
        this.mUploads = mUploads;
        this.hotel = hotel ;
        this.user = user;
        order = new Order();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item ,viewGroup,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder imageViewHolder, int i) {
        final Upload upload = mUploads.get(i);
        imageViewHolder.TextViewName.setText(upload.getName());
        imageViewHolder.TextViewId.setText(upload.getHotel_user());
        Picasso.get().load(upload.getImageuri()).fit().into(imageViewHolder.imageview);
        imageViewHolder.imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.bhukkad.eatit.MenuActivity");
                intent.putExtra("hotel_user",imageViewHolder.TextViewId.getText().toString());
                intent.putExtra("order",order);
                intent.putExtra("user",user);
                mContext.startActivity(intent);
                ((Activity) mContext).finish();
//                Toast.makeText(mContext,imageViewHolder.TextViewName.getText().toString(),Toast.LENGTH_SHORT).show();
//                Toast.makeText(mContext,imageViewHolder.TextViewId.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        private TextView TextViewName;
        private TextView TextViewId;
        private ImageView imageview;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            TextViewName = itemView.findViewById(R.id.textView_name);
            TextViewId = itemView.findViewById(R.id.textView_id);
            imageview = itemView.findViewById(R.id.imageView_card );

        }
    }


}
