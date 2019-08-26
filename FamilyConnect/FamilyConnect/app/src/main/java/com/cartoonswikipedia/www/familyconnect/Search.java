package com.cartoonswikipedia.www.familyconnect;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;

public class Search extends AppCompatActivity {

    EditText search;
    String famuid,sear,uid,imgurl,url,uidd;
    ArrayList<String> namelist = new ArrayList<>();
    ArrayList<String> imglist = new ArrayList<>();
    ArrayList<String> uidlist = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layout;
    RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        }
        search = (EditText) findViewById(R.id.search);
        famuid = getIntent().getStringExtra("famuid");
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(Search.this));
        imglist.clear();
        namelist.clear();
        uidlist.clear();
        adapter = new  MainAdapterr(namelist,imglist,uidlist);
        recyclerView.setAdapter(adapter);
        sear = search.getText().toString();
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference(famuid+"/profile");
        usersRef.orderByChild("name").startAt(sear).endAt(sear+"\uf8ff").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    final User user = postSnapshot.getValue(User.class);
                    uid = user.getUid();
                    uidd = user.getUid();
                    uidlist.add(uidd);
                    imgurl = user.getPic();
                    imglist.add(imgurl);
                    namelist.add(user.getname());
                    recyclerView.setHasFixedSize(true);
                    layout = new LinearLayoutManager(Search.this);
                    recyclerView.setLayoutManager(layout);

                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        recyclerView.setHasFixedSize(true);
        layout = new LinearLayoutManager(Search.this);
        recyclerView.setLayoutManager(layout);


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                imglist.clear();
                namelist.clear();
                uidlist.clear();
                adapter = new  MainAdapterr(namelist,imglist,uidlist);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                imglist.clear();
                namelist.clear();
                uidlist.clear();
                adapter = new  MainAdapterr(namelist,imglist,uidlist);
                recyclerView.setAdapter(adapter);
                sear = search.getText().toString();
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference(famuid+"/profile");
                usersRef.orderByChild("name").startAt(sear).endAt(sear+"\uf8ff").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (final DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            final User user = postSnapshot.getValue(User.class);
                            uid = user.getUid();
                            imglist.clear();
                            namelist.clear();
                            uidlist.clear();
                            adapter = new  MainAdapterr(namelist,imglist,uidlist);
                            recyclerView.setAdapter(adapter);
                            uidd = user.getUid();
                            uidlist.add(uidd);
                            imgurl = user.getPic();
                            imglist.add(imgurl);
                            namelist.add(user.getname());
                            adapter = new  MainAdapterr(namelist,imglist,uidlist);
                            recyclerView.setAdapter(adapter);

                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
        recyclerView.setHasFixedSize(true);
        layout = new LinearLayoutManager(Search.this);
        recyclerView.setLayoutManager(layout);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Search.this,Main2Activity.class));
    }

}