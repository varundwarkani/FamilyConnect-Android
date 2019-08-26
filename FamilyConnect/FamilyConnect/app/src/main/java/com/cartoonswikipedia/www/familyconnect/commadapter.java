package com.cartoonswikipedia.www.familyconnect;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Chetan on 30/11/17.
 */

class commadapter extends RecyclerView.Adapter<commadapter.ViewHolder> {

    ArrayList<String> comments = new ArrayList<>();
    ArrayList<String> imglink = new ArrayList<>();
    ArrayList<String> uid = new ArrayList<>();
    ArrayList<String> namelist = new ArrayList<>();

    public commadapter(ArrayList<String> name, ArrayList<String> imgurl,ArrayList<String> uid,ArrayList<String> comment){
        this.namelist = comment;
        this.comments = name;
        imglink = imgurl;
        this.uid = uid;
    }

    @Override
    public commadapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comm, parent, false);
            return new commadapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final commadapter.ViewHolder holder, final int position) {
        holder.comments.setText(comments.get(position));
        holder.name.setText(namelist.get(position));
        Picasso.with(holder.itemView.getContext()).load(imglink.get(position)).into(holder.profile);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), viewprofile.class);
                intent.putExtra("uid", uid.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name,comments;
        public ImageView profile;

        public ViewHolder(View itemView) {
            super(itemView);
            comments = (TextView) itemView.findViewById(R.id.comments);
            name = (TextView) itemView.findViewById(R.id.name);
            profile = (ImageView) itemView.findViewById(R.id.likeprofile);
        }
    }
}
