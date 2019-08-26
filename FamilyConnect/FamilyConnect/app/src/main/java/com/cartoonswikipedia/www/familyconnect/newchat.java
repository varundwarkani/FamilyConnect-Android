package com.cartoonswikipedia.www.familyconnect;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
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
import java.util.Stack;
import java.util.TimeZone;

public class newchat extends AppCompatActivity {


    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    ArrayList<String> messagelist = new ArrayList<>();
    ArrayList<String> chatnamelist = new ArrayList<>();
    ArrayList<String> timestamplist = new ArrayList<>();
    RecyclerView chatrec;
    RecyclerView.LayoutManager layout;
    RecyclerView.Adapter adapter;
    Button addnewchat;
    String famuid,uid;
    EditText addchat;
    long co;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newchat);
        addnewchat = (Button) findViewById(R.id.addnewchat);
        famuid = getIntent().getStringExtra("famuid");
        chatrec = (RecyclerView) findViewById(R.id.chatrec);
        chatrec.setLayoutManager(new LinearLayoutManager(this));
        messagelist.clear();
        chatnamelist.clear();
        timestamplist.clear();
        adapter = new newchatadapter(chatnamelist,messagelist,timestamplist);
        chatrec.setAdapter(adapter);
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

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(famuid+"/chat");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                co = dataSnapshot.getChildrenCount();
                for (DataSnapshot postsnapshot: dataSnapshot.getChildren())
                {
                    gettingchat gettingchat = postsnapshot.getValue(com.cartoonswikipedia.www.familyconnect.gettingchat.class);
                    String a = gettingchat.getChatuid();
                    final String b = gettingchat.getMessage();
                    final String c = gettingchat.getTimestamp();
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("profile/"+a);
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String name = dataSnapshot.child("name").getValue().toString();
                            chatnamelist.add(name);
                            messagelist.add(b);
                            timestamplist.add(c);
                            adapter = new newchatadapter(chatnamelist,messagelist,timestamplist);
                            chatrec.setAdapter(adapter);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });

                }
                chatrec.smoothScrollToPosition((int) co);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public void chatmessage(View view) {
        addchat = (EditText) findViewById(R.id.addchat);
        String chatmes = addchat.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String timestamp = sdf.format(new Date());
        String uidt = timestamp.concat(uid);
        SimpleDateFormat sdff = new SimpleDateFormat("HH-mm-ss  dd/MM/yyyy", Locale.US);
        sdff.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        String timestampp = sdff.format(new Date());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        databaseReference.child(famuid+"/chat/"+uidt+"/chatuid").setValue(uid);
        databaseReference.child(famuid+"/chat/"+uidt+"/timestamp").setValue(timestampp);
        databaseReference.child(famuid+"/chat/"+uidt+"/message").setValue(chatmes);
        finish();
        startActivity(getIntent());
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(newchat.this,Main2Activity.class));
    }
}
