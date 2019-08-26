package com.cartoonswikipedia.www.familyconnect;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Chetan on 24/12/17.
 */

public class newchatadapter extends RecyclerView.Adapter<newchatadapter.ViewHolder> {

    ArrayList<String> messagelist = new ArrayList<>();
    ArrayList<String> chatnamelist = new ArrayList<>();
    ArrayList<String> timestamplist = new ArrayList<>();

    public newchatadapter(ArrayList<String> chatnamelist, ArrayList<String> messagelist,ArrayList<String> timestamplist)
    {
        this.chatnamelist = chatnamelist;
        this.messagelist = messagelist;
        this.timestamplist = timestamplist;
    }

    @Override
    public newchatadapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newchatlayout, parent, false);
        return new newchatadapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.chatmesname.setText(chatnamelist.get(position));
        holder.chatmessage.setText(messagelist.get(position));
        holder.chattime.setText(timestamplist.get(position));
    }

    @Override
    public int getItemCount() {
        return chatnamelist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView chatmesname,chattime,chatmessage;

        public ViewHolder(View itemView) {
            super(itemView);
            chatmesname = (TextView) itemView.findViewById(R.id.chatmesname);
            chatmessage = (TextView) itemView.findViewById(R.id.chatmessage);
            chattime = (TextView) itemView.findViewById(R.id.chattime);
        }
    }
}
