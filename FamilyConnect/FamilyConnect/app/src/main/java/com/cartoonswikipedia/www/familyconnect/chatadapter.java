package com.cartoonswikipedia.www.familyconnect;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Chetan on 16/12/17.
 */

public class chatadapter extends RecyclerView.Adapter<chatadapter.ViewHolder>{

    ArrayList<String> chatlist = new ArrayList<>();
    ArrayList<String> namelist = new ArrayList<>();
    ArrayList<String> timestamplist = new ArrayList<>();

    public chatadapter(ArrayList<String> chat, ArrayList<String> name, ArrayList<String> timestamplist){
        this.chatlist = chat;
        this.namelist = name;
        this.timestamplist = timestamplist;
    }

    @Override
    public chatadapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messagein, parent, false);
            return new chatadapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(chatadapter.ViewHolder holder, int position) {
        holder.message.setText(chatlist.get(position));
        holder.name.setText(namelist.get(position));
        holder.timestamp.setText(timestamplist.get(position));
    }
    @Override
    public int getItemCount() {
        return chatlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name,timestamp,message;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.messagename);
            timestamp = (TextView) itemView.findViewById(R.id.timestamp);
            message = (TextView) itemView.findViewById(R.id.messagee);
        }
    }
}
