package com.cartoonswikipedia.www.familyconnect;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class chat extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    String famuid,uid,chatuid,message,timestampp;
    ArrayList<String> chatlist = new ArrayList<>();
    ArrayList<String> timestamplist = new ArrayList<>();
    ArrayList<String> namelist = new ArrayList<>();
    RecyclerView chatrecycler;
    RecyclerView.LayoutManager layout;
    RecyclerView.Adapter adapter;
    EditText addmessage;
    Button postmessage;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        postmessage = (Button) findViewById(R.id.postmessage);
        famuid = "217forakpiYNTdXobi2aUF6fhtd2";
        chatrecycler = (RecyclerView) findViewById(R.id.chatrecycler);
        chatlist.clear();
        namelist.clear();
        timestamplist.clear();
        chatrecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new chatadapter(chatlist, timestamplist, namelist);
        chatrecycler.setAdapter(adapter);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    uid = user.getUid();
                    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference(famuid + "/chat");
                    usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                chatobj chatobj = postSnapshot.getValue(com.cartoonswikipedia.www.familyconnect.chatobj.class);
                                chatuid = chatobj.getuid();
                                chatlist.add(chatobj.getmessage());
                                timestamplist.add(chatobj.gettimestamp());
                                DatabaseReference databasereff = FirebaseDatabase.getInstance().getReference("profile/"+chatuid);
                                databasereff.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        name = dataSnapshot.child("name").getValue().toString();
                                        namelist.add(name);
                                        adapter = new chatadapter(chatlist,namelist,timestamplist);
                                        chatrecycler.setAdapter(adapter);
                                        chatrecycler.setHasFixedSize(true);
                                        layout = new LinearLayoutManager(chat.this);
                                        chatrecycler.setLayoutManager(layout);
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
    public void sendmessage(View view) {
        addmessage = (EditText) findViewById(R.id.addmessage);
        message = addmessage.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("IST"));
        String timestamp = sdf.format(new Date());
        String uidt = uid.concat(timestamp);
        SimpleDateFormat sdff = new SimpleDateFormat("HH-mm-ss  dd/MM/yyyy", Locale.US);
        sdff.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        timestampp = sdff.format(new Date());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        databaseReference.child(famuid+"/chat/"+uidt+"/chatuid").setValue(uid);
        databaseReference.child(famuid+"/chat/"+uidt+"/timestamp").setValue(timestampp);
        databaseReference.child(famuid+"/chat/"+uidt+"/message").setValue(message);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(chat.this,Main2Activity.class));
    }
}
