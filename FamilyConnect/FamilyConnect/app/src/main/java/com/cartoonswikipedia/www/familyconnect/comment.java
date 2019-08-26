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
import android.widget.Button;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class comment extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    String famuid, postid,uid,commentuid;
    ArrayList<String> commentlist = new ArrayList<>();
    ArrayList<String> imglist = new ArrayList<>();
    ArrayList<String> uidlist = new ArrayList<>();
    ArrayList<String> namelist = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layout;
    RecyclerView.Adapter adapter;
    EditText commented;
    Button added;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        }
        added = (Button) findViewById(R.id.addcomment);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        famuid  = extras.getString("famuid");
        postid = extras.getString("postid");
        Toast.makeText(this, famuid+postid, Toast.LENGTH_SHORT).show();
        recyclerView = (RecyclerView) findViewById(R.id.commentecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imglist.clear();
        commentlist.clear();
        uidlist.clear();
        namelist.clear();
        adapter = new commadapter(commentlist, imglist, uidlist, namelist);
        recyclerView.setAdapter(adapter);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    uid = user.getUid();
                }
            }
        };
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference(famuid + "/comments/" + postid);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    final comm comm = postSnapshot.getValue(comm.class);
                    commentuid = comm.getuid();
                    final String uidd = comm.getuid();
                    DatabaseReference databasereff = FirebaseDatabase.getInstance().getReference("profile/"+commentuid);
                    databasereff.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String imgurl = dataSnapshot.child("pic").getValue().toString();
                            String name = dataSnapshot.child("name").getValue().toString();
                            namelist.add(name);
                            imglist.add(imgurl);
                            uidlist.add(uidd);
                            commentlist.add(comm.getcomment());
                            recyclerView.setHasFixedSize(true);
                            layout = new LinearLayoutManager(comment.this);
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

    public void commented(View view) {
        commented = (EditText) findViewById(R.id.add);
        String comment = commented.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String timestamp = sdf.format(new Date());
        String uidt = uid.concat(timestamp);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        databaseReference.child(famuid+"/comments/"+postid+"/"+uidt+"/comment").setValue(comment);
        databaseReference.child(famuid+"/comments/"+postid+"/"+uidt+"/uid").setValue(uid);
        finish();
        startActivity(getIntent());
    }
}
