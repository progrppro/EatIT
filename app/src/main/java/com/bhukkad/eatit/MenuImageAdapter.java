package com.bhukkad.eatit;

import android.content.Context;
import android.content.Intent;
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

public class MenuImageAdapter extends RecyclerView.Adapter<MenuImageAdapter.MenuImageViewHolder> {

    private Context mContext;
    private List<Upload> mUploads;

    public MenuImageAdapter(Context mContext, List<Upload> mUploads) {
        this.mContext = mContext;
        this.mUploads = mUploads;
    }

    @NonNull
    @Override
    public MenuImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item ,viewGroup,false);
        return new MenuImageAdapter.MenuImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuImageViewHolder menuImageViewHolder, int i) {
        final Upload upload = mUploads.get(i);
        menuImageViewHolder.TextViewName.setText(upload.getName());
        Picasso.get().load(upload.getImageuri()).fit().into(menuImageViewHolder.imageview);
        menuImageViewHolder.imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,menuImageViewHolder.TextViewName.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class MenuImageViewHolder extends RecyclerView.ViewHolder{
        private TextView TextViewName;
        private ImageView imageview;
        public MenuImageViewHolder(@NonNull View itemView) {
            super(itemView);
            TextViewName = itemView.findViewById(R.id.textView_name);
            imageview = itemView.findViewById(R.id.imageView_card );

        }
    }
}
