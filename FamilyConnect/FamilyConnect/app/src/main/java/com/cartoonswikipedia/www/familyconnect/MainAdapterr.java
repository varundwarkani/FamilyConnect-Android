package com.cartoonswikipedia.www.familyconnect;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.createDeviceProtectedStorageContext;
import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Chetan on 22/10/17.
 */

class MainAdapterr extends RecyclerView.Adapter<MainAdapterr.ViewHolder> {


    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> imglink = new ArrayList<>();
    ArrayList<String> uid = new ArrayList<>();

    public MainAdapterr(ArrayList<String> name, ArrayList<String> imgurl,ArrayList<String> uid){
        names = name;
        imglink = imgurl;
        this.uid = uid;
    }

    @Override
    public MainAdapterr.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MainAdapterr.ViewHolder holder, final int position) {
        holder.name.setText(names.get(position));
        Picasso.with(holder.itemView.getContext()).load(imglink.get(position)).into(holder.proimg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), viewprofile.class);
                intent.putExtra("uid", uid.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public ImageView proimg;

        public ViewHolder(View itemView) {
            super(itemView);

            proimg = itemView.findViewById(R.id.likeprofile);
            name = itemView.findViewById(R.id.namess);
        }
    }
}