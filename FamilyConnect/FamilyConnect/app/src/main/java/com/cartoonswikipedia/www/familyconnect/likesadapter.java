package com.cartoonswikipedia.www.familyconnect;

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

/**
 * Created by Chetan on 1/12/17.
 */

class likesadapter extends RecyclerView.Adapter<likesadapter.ViewHolder> {

    ArrayList<String> namelist = new ArrayList<>();
    ArrayList<String> imglist = new ArrayList<>();
    ArrayList<String> uidlist = new ArrayList<>();

    public likesadapter(ArrayList<String> name,ArrayList<String> img,ArrayList<String> uid){
        this.namelist = name;
        this.imglist = img;
        this.uidlist = uid;
    }
    @Override
    public likesadapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlikes, parent, false);
        return new likesadapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final likesadapter.ViewHolder holder, final int position) {
        holder.likesname.setText(namelist.get(position));
        Picasso.with(holder.itemView.getContext()).load(imglist.get(position)).into(holder.imgprofile);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), viewprofile.class);
                intent.putExtra("uid", uidlist.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return namelist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView likesname;
        ImageView imgprofile;
        public ViewHolder(View itemView) {
            super(itemView);
            imgprofile = (ImageView) itemView.findViewById(R.id.likeprofile);
            likesname = (TextView) itemView.findViewById(R.id.likename);

        }
    }
}
