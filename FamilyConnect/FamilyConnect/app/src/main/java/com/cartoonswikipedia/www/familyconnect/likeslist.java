package com.cartoonswikipedia.www.familyconnect;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class likeslist extends AppCompatActivity {

    String famuid, postid,name;
    ArrayList<String> namelist = new ArrayList<>();
    ArrayList<String> imglist = new ArrayList<>();
    ArrayList<String> uidlist = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layout;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likeslist);
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
        famuid = extras.getString("famuid");
        postid = extras.getString("postid");
        recyclerView = (RecyclerView) findViewById(R.id.commentecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        namelist.clear();
        imglist.clear();
        uidlist.clear();
        adapter = new likesadapter(namelist,imglist,uidlist);
        recyclerView.setAdapter(adapter);
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference(famuid +"/likes/"+postid);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    name = postSnapshot.getKey();
                    DatabaseReference databaseref = FirebaseDatabase.getInstance().getReference("profile/"+name);
                    databaseref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String name1 = dataSnapshot.child("name").getValue().toString();
                            String img = dataSnapshot.child("pic").getValue().toString();
                            String uidd = dataSnapshot.child("uid").getValue().toString();
                            namelist.add(name1);
                            uidlist.add(uidd);
                            imglist.add(img);
                            recyclerView.setHasFixedSize(true);
                            layout = new LinearLayoutManager(likeslist.this);
                            recyclerView.setLayoutManager(layout);
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

    }
}
