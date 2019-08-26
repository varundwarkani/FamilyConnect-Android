package com.cartoonswikipedia.www.familyconnect;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Chetan on 4/3/18.
 */


class allpostsadapter extends RecyclerView.Adapter<allpostsadapter.ViewHolder> {


    ArrayList<String> imglist = new ArrayList<>();
    ArrayList<String> profileimage = new ArrayList<>();
    ArrayList<String> profilename = new ArrayList<>();
    ArrayList<String> captionlist = new ArrayList<>();
    ArrayList<String> typelist = new ArrayList<>();
    ArrayList<String> likescount = new ArrayList<>();
    ArrayList<String> postid = new ArrayList<>();
    ArrayList<String> commentscount = new ArrayList<>();
    ArrayList<String> timestamp = new ArrayList<>();
    ArrayList<String> visibility = new ArrayList<>();
    String userid,famuid,uid;
    int identity;

    public allpostsadapter(ArrayList<String> profileimage,ArrayList<String> profilename,ArrayList<String> imglink,String userid,ArrayList<String>captionlist,ArrayList<String> typelist,ArrayList<String> likescount,ArrayList<String> postid,String famuid,String uid,ArrayList<String> commentscount,ArrayList<String> timestamp,ArrayList<String> visibility) {
        this.imglist = imglink;
        this.profileimage = profileimage;
        this.profilename = profilename;
        this.captionlist = captionlist;
        this.userid = userid;
        this.typelist = typelist;
        this.likescount = likescount;
        this.postid = postid;
        this.famuid = famuid;
        this.uid = uid;
        this.commentscount = commentscount;
        this.timestamp = timestamp;
        this.visibility = visibility;
    }

    @Override
    public allpostsadapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allpostsrow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final allpostsadapter.ViewHolder holder, final int position) {
        holder.displaycaption.setText(captionlist.get(position));
        Picasso.with(holder.itemView.getContext()).load(profileimage.get(position)).into(holder.displayprofileimg);
        holder.displayprofilename.setText(profilename.get(position));
        if (typelist.get(position).equals("image"))
        {
            holder.displayvideo.setVisibility(View.GONE);
            Picasso.with(holder.itemView.getContext()).load(imglist.get(position)).into(holder.displayall);
        }
        else
        {
            holder.displayall.setVisibility(View.GONE);
            holder.displayvideo.setVideoPath(imglist.get(position));
            holder.displayvideo.setZOrderOnTop(true);
        }

        holder.alltimestamp.setText(timestamp.get(position));
        holder.commentcount.setText(commentscount.get(position));
        holder.displaylikescount.setText(likescount.get(position));
        holder.displaylikescount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), likeslist.class);
                Bundle extras = new Bundle();
                extras.putString("famuid",famuid);
                extras.putString("postid",postid.get(position));
                intent.putExtras(extras);
                v.getContext().startActivity(intent);
            }
        });

        holder.displaycomments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), comment.class);
                Bundle extras = new Bundle();
                extras.putString("famuid",famuid);
                extras.putString("postid",postid.get(position));
                intent.putExtras(extras);
                v.getContext().startActivity(intent);
            }
        });

        holder.displayvideo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                holder.displayvideo.start();
                return false;
            }
        });

        DatabaseReference newref = FirebaseDatabase.getInstance().getReference().child(famuid+"/likes/"+postid.get(position));
        newref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(uid)) {
                    holder.displaylikes.setImageResource(R.drawable.red);
                    identity=1;
                }
                else
                {
                    holder.displaylikes.setImageResource(R.drawable.white);
                    identity=0;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        if ((visibility.get(position)).equals("show"))
        {
            holder.delete.setVisibility(View.VISIBLE);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    //Alert Builder
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("DELETE")
                            .setMessage("Do you really want to delete the post?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {

                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference databaseReference = database.getReference();
                                    databaseReference.child(famuid+"/posts/"+postid.get(position)).setValue(null);
                                    Toast.makeText(v.getContext(), "Deleted post succesfully", Toast.LENGTH_SHORT).show();
                                    v.getContext().startActivity(new Intent(v.getContext(),Main2Activity.class));

                                }})
                            .setNegativeButton(android.R.string.no, null).show();

                }
            });
        }


        holder.displaylikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference newref = FirebaseDatabase.getInstance().getReference().child(famuid+"/likes/"+postid.get(position));
                newref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(uid)) {
                            holder.displaylikes.setImageResource(R.drawable.red);
                            identity=1;
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReference = database.getReference();
                            if(identity==1) {
                                holder.displaylikes.setImageResource(R.drawable.white);
                                databaseReference.child(famuid + "/likes/" + postid.get(position) + "/" + uid).removeValue();
                                identity = 0;
                            }
                            else
                            {
                                holder.displaylikes.setImageResource(R.drawable.red);
                                databaseReference.child(famuid + "/likes/" + postid.get(position) + "/" + uid).setValue("true");
                                identity = 1;
                            }
                        }
                        else
                        {
                            holder.displaylikes.setImageResource(R.drawable.white);
                            identity=0;
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReference = database.getReference();
                            if(identity==1) {
                                holder.displaylikes.setImageResource(R.drawable.white);
                                databaseReference.child(famuid + "/likes/" + postid.get(position) + "/" + uid).removeValue();
                                identity = 0;
                            }
                            else
                            {
                                holder.displaylikes.setImageResource(R.drawable.red);
                                databaseReference.child(famuid + "/likes/" + postid.get(position) + "/" + uid).setValue("true");
                                identity = 1;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return imglist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public VideoView displayvideo;
        public ImageView delete;
        public ImageView displayall,displayprofileimg,displaylikes,displaycomments;
        public TextView displayprofilename,displaycaption,displaylikescount,commentcount,alltimestamp;
        public ViewHolder(View itemView) {
            super(itemView);

            alltimestamp = itemView.findViewById(R.id.alltimestamp);
            commentcount = itemView.findViewById(R.id.commentcount);
            delete = itemView.findViewById(R.id.imageView5);
            displayall = itemView.findViewById(R.id.displayall);
            displayprofileimg = itemView.findViewById(R.id.displayprofileimg);
            displaylikes = itemView.findViewById(R.id.displaylikes);
            displaycomments = itemView.findViewById(R.id.displaycomments);
            displayvideo = (VideoView) itemView.findViewById(R.id.displayvideo);
            displaycaption = (TextView) itemView.findViewById(R.id.displaycaption);
            displayprofilename = (TextView) itemView.findViewById(R.id.displayprofilename);
            displaylikescount = (TextView) itemView.findViewById(R.id.displaylikescount);
        }
    }
}
