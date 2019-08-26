package com.cartoonswikipedia.www.familyconnect;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.widget.Toast.LENGTH_SHORT;

public class profile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    DatabaseReference myref;
    FirebaseAuth mAuth;
    DatabaseReference databaseref;
    FirebaseAuth.AuthStateListener mAuthListener;
    String usermail,uid;
    String name1,s;
    String count,name,profileimg;
    Long coun;
    int identity;
    ImageView like,comments;
    TextView famuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        ProgressBar bar = (ProgressBar) findViewById(R.id.empty_progress_bar);
        bar.setVisibility(View.GONE);
        like = (ImageView) findViewById(R.id.likes);
        comments = (ImageView) findViewById(R.id.comments);
        s = getIntent().getStringExtra("TextValue");
        famuid = (TextView) findViewById(R.id.famnam);
        famuid.setText(s);
        recyclerView = (RecyclerView) findViewById(R.id.commentecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(profile.this));
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        }


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    uid = user.getUid();
                    usermail = user.getEmail();

                } else {
                    startActivity(new Intent(profile.this,login.class));
                    finish();
                }

            }
        };

        databaseref = FirebaseDatabase.getInstance().getReference();
        databaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                if (dataSnapshot.hasChild("profile/"+uid) && dataSnapshot.hasChild("profile/"+uid+"/name")  && dataSnapshot.hasChild("profile/"+uid+"/famuid") && dataSnapshot.hasChild("profile/"+uid+"/dob") && dataSnapshot.hasChild("profile/"+uid+"/gender")) {
                    name1 = dataSnapshot.child("profile/"+uid+"/famuid").getValue().toString();
                    myref = FirebaseDatabase.getInstance().getReference().child(name1+"/posts");
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
                            DatabaseReference newref = FirebaseDatabase.getInstance().getReference().child(name1+"/likes/"+t);
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
                                    DatabaseReference newref = FirebaseDatabase.getInstance().getReference().child(name1+"/likes/"+t);
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
                                        databaseReference.child(name1+"/likes/"+t+"/"+uid).removeValue();
                                    }
                                    else
                                    {
                                        viewHolder.setlikes();
                                        databaseReference.child(name1+"/likes/"+t+"/"+uid).setValue("true");
                                    }
                                }
                            });
                        }
                    };
                    recyclerView.setAdapter(adapter);

                    ((MyApplication) getApplication()).setSomeVariable(name1);
                    if(dataSnapshot.hasChild(name1+"/profile/"+uid))
                    {
                    }
                    else
                    {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = database.getReference();
                        databaseReference.child(name1+"/profile/"+uid+"/uid").setValue(uid);
                        databaseReference.child(name1+"/profile/"+uid+"/pic").setValue("https://firebasestorage.googleapis.com/v0/b/family-connect-2087f.appspot.com/o/no%20image.jpg?alt=media&token=c85ded2f-4f7c-4153-bf8d-d600437d4149");
                        databaseReference.child(name1+"/profile/"+uid+"/name").setValue("Enter Name");
                    }
                }
                else {
                    String a = ((MyApplication) getApplication()).getSomeVariable();
                    ProgressBar bar = (ProgressBar) findViewById(R.id.empty_progress_bar);
                    bar.setVisibility(View.VISIBLE);
                    //Create child
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = database.getReference();
                    databaseReference.child("profile/"+uid+"/mail").setValue(usermail);
                    databaseReference.child("profile/"+uid+"/famuid").setValue(a);
                    databaseReference.child("profile/"+uid+"/name").setValue("Enter Name");
                    databaseReference.child("profile/"+uid+"/dob").setValue("Enter DOB");
                    databaseReference.child("profile/"+uid+"/gender").setValue("Enter Gender");
                    databaseReference.child("profile/"+uid+"/pic").setValue("https://firebasestorage.googleapis.com/v0/b/family-connect-2087f.appspot.com/o/no%20image.jpg?alt=media&token=c85ded2f-4f7c-4153-bf8d-d600437d4149");
                    Toast.makeText(profile.this,"Success!", LENGTH_SHORT).show();
                    bar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(profile.this,"Cannot Connect with Database!", LENGTH_SHORT).show();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(profile.this,post.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(profile.this,MainActivity.class));

                    }
                })
                .setNegativeButton("No", null)
                .show();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(this,editprofile.class));
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(this,display.class));
        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(this,recycler.class));
        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(this,Search.class));

        } else if (id == R.id.nav_share) {

            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String sAux = "\nLet me recommend you this application\n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=Orion.Soft \n\n";
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "choose one"));
            } catch (Exception e) {
                //e.toString();
            }

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.logout) {
            ProgressBar bar = (ProgressBar) findViewById(R.id.empty_progress_bar);
            bar.setVisibility(View.VISIBLE);
            mAuth.signOut();
            Toast.makeText(profile.this,"Logged Out!",Toast.LENGTH_LONG).show();
            startActivity(new Intent(profile.this,MainActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
