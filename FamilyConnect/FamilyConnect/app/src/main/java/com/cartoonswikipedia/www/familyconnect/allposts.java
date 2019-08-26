package com.cartoonswikipedia.www.familyconnect;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class allposts extends AppCompatActivity {

    RecyclerView allpostsrecycler;
    String userid,famuid;
    ArrayList<String> imglist = new ArrayList<>();
    ArrayList<String> profileimg = new ArrayList<>();
    ArrayList<String> profilename = new ArrayList<>();
    ArrayList<String> captionlist = new ArrayList<>();
    ArrayList<String> postid = new ArrayList<>();
    ArrayList<String> likescount = new ArrayList<>();
    ArrayList<String> commentscount = new ArrayList<>();
    ArrayList<String> typelist = new ArrayList<>();
    ArrayList<String> timestamp = new ArrayList<>();
    ArrayList<String> visibility = new ArrayList<>();
    allpostsadapter adapter;
    String uid;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allposts);

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        }

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        userid  = extras.getString("userid");
        famuid  = extras.getString("famuid");
        allpostsrecycler = (RecyclerView) findViewById(R.id.allpostsrecycler);
        imglist.clear();
        visibility.clear();
        profileimg.clear();
        postid.clear();
        likescount.clear();
        profilename.clear();
        captionlist.clear();
        typelist.clear();
        commentscount.clear();
        timestamp.clear();
        allpostsrecycler.setHasFixedSize(true);
        allpostsrecycler.setLayoutManager(new LinearLayoutManager(allposts.this));
        adapter = new allpostsadapter(profileimg,profilename,imglist,userid,captionlist,typelist,likescount,postid,famuid,uid, commentscount,timestamp, visibility);
        allpostsrecycler.setAdapter(adapter);


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    uid = user.getUid();
                    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference(famuid+"/posts");
                    usersRef.orderByChild("uid").startAt(userid).endAt(userid+"\uf8ff").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (final DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                                DatabaseReference databaseref = FirebaseDatabase.getInstance().getReference("profile/"+userid);
                                databaseref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot){
                                        final String profileimgg = dataSnapshot.child("pic").getValue().toString();
                                        final String profilenamee = dataSnapshot.child("name").getValue().toString();
                                        final String img = postSnapshot.child("image").getValue().toString();
                                        final String caption = postSnapshot.child("caption").getValue().toString();
                                        final String timestampp = postSnapshot.child("timestamp").getValue().toString();
                                        final String type = postSnapshot.child("type").getValue().toString();
                                        final String postidd = postSnapshot.child("post").getValue().toString();

                                        final String postuid = postSnapshot.child("uid").getValue().toString();
                                        if (postuid.equals(uid))
                                        {
                                            visibility.add("show");
                                        }
                                        else
                                        {
                                            visibility.add("hide");
                                        }
                                        postid.add(postidd);

                                        DatabaseReference newref = FirebaseDatabase.getInstance().getReference().child(famuid+"/likes/"+postidd);
                                        newref.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                Long coun;
                                                coun = dataSnapshot.getChildrenCount();
                                                String count = coun.toString();
                                                likescount.add(count);
                                                imglist.add(img);
                                                captionlist.add(caption);
                                                timestamp.add(timestampp);
                                                typelist.add(type);
                                                profileimg.add(profileimgg);
                                                profilename.add(profilenamee);

                                                DatabaseReference newreff = FirebaseDatabase.getInstance().getReference().child(famuid+"/comments/"+postidd);
                                                newreff.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        Long countt = dataSnapshot.getChildrenCount();
                                                        String counttt = String.valueOf(countt);
                                                        commentscount.add(counttt);
                                                        adapter = new allpostsadapter(profileimg,profilename,imglist,userid,captionlist,typelist,likescount,postid,famuid,uid,commentscount,timestamp, visibility);
                                                        allpostsrecycler.setAdapter(adapter);
                                                    }
                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                    }
                                                });
                                            }
                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                            }
                                        });

                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                            }

                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                } else {
                }
            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }
}
