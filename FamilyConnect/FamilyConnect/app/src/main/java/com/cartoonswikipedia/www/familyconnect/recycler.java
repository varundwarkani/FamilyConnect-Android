package com.cartoonswikipedia.www.familyconnect;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class recycler extends AppCompatActivity{
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    RecyclerView recyclerView;
    DatabaseReference myref,newref;
    String famuid,uid,count,name,profileimg;
    Long coun;
    int identity;
    ImageView like,comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        famuid = ((MyApplication) getApplication()).getSomeVariable();
        like = (ImageView) findViewById(R.id.likes);
        comments = (ImageView) findViewById(R.id.comments);
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

        recyclerView = (RecyclerView) findViewById(R.id.commentecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myref = FirebaseDatabase.getInstance().getReference().child(famuid+"/posts");
        FirebaseRecyclerAdapter<Blog, BlogViewHolder> adapter = new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(
                Blog.class,
                R.layout.individual_row,
                BlogViewHolder.class,
                myref
        ) {
            @Override
            protected void populateViewHolder(final BlogViewHolder viewHolder, Blog model, int position)
            {
                final String t = model.getpost();
                newref = FirebaseDatabase.getInstance().getReference().child(famuid+"/likes/"+t);
                newref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild(uid)) {
                                    viewHolder.setlikes();
                                    identity=1;
                                }
                                else
                                {
                                    viewHolder.setnolikes();
                                    identity=0;
                                }
                        coun = dataSnapshot.getChildrenCount();
                        count = coun.toString();
                        viewHolder.setlikescount(count);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                String userid = model.getUid();
                DatabaseReference databaseref;
                databaseref = FirebaseDatabase.getInstance().getReference("profile/"+userid);
                databaseref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot){
                        name = dataSnapshot.child("name").getValue().toString();
                        profileimg = dataSnapshot.child("pic").getValue().toString();
                        viewHolder.setprofileimg(profileimg);
                        viewHolder.setName(name);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                viewHolder.setCaption(model.getCaption());
                viewHolder.setImage(model.getImage(),model.getType());

                viewHolder.getvieww().findViewById(R.id.comments).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getBaseContext(), comment.class);
                        intent.putExtra("postid", t);
                        startActivity(intent);
                    }
                });

                viewHolder.getviewwww().findViewById(R.id.likescount).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getBaseContext(), likeslist.class);
                        intent.putExtra("postid", t);
                        startActivity(intent);
                    }
                });



                viewHolder.getviewww().findViewById(R.id.likes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        newref = FirebaseDatabase.getInstance().getReference().child(famuid+"/likes/"+t);
                        newref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild(uid)) {
                                    viewHolder.setlikes();
                                    identity=1;
                                }
                                else
                                {
                                    viewHolder.setnolikes();
                                    identity=0;
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = database.getReference();
                        if(identity==1)
                        {
                            viewHolder.setnolikes();
                            databaseReference.child(famuid+"/likes/"+t+"/"+uid).removeValue();
                        }
                        else
                        {
                            viewHolder.setlikes();
                            databaseReference.child(famuid+"/likes/"+t+"/"+uid).setValue("true");
                        }
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);

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
