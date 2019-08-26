package com.cartoonswikipedia.www.familyconnect;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import static android.widget.Toast.LENGTH_SHORT;

public class viewprofile extends AppCompatActivity {

    String fatheruid,motheruid;
    TextView textfather,textmother,textsiblings,textchild;
    ImageView proimage,adduser,adduser1,adduser2,adduser3;
    TextView proname,progender,display5,proage,proreligion,procaste,proraasi,prostar,protime;
    String userid;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    String uid,famuid,father,mother,iden,height,weight;
    TextView proprofession,probloodgroup,promartialstatus,proheight,proweight;
    String[] link = new String[100];
    int i=0;
    ImageView imageview1,imageview2,imageview3,addall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewprofile);
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        }
        textfather = (TextView) findViewById(R.id.textfather);
        textmother = (TextView) findViewById(R.id.textmother);
        textsiblings = (TextView) findViewById(R.id.textsiblings);
        textchild = (TextView) findViewById(R.id.textchild);
        imageview1 = (ImageView) findViewById(R.id.display1);
        imageview2 = (ImageView) findViewById(R.id.display2);
        imageview3 = (ImageView) findViewById(R.id.display3);
        display5=(TextView)findViewById(R.id.textView5);
        addall = (ImageView) findViewById(R.id.display4);
        proage = (TextView) findViewById(R.id.proage);
        proheight = (TextView) findViewById(R.id.proheight);
        proweight = (TextView) findViewById(R.id.proweight);
        proreligion = (TextView) findViewById(R.id.proreligion);
        procaste = (TextView) findViewById(R.id.procaste);
        proraasi = (TextView) findViewById(R.id.proraasi);
        prostar = (TextView) findViewById(R.id.prostar);
        protime = (TextView) findViewById(R.id.protime);
        addall.setVisibility(View.GONE);
        imageview1.setVisibility(View.GONE);
        imageview2.setVisibility(View.GONE);
        imageview3.setVisibility(View.GONE);
        userid = getIntent().getStringExtra("uid");
        proimage = (ImageView) findViewById(R.id.proimage);
        adduser = (ImageView) findViewById(R.id.adduser);
        adduser.setVisibility(View.GONE);
        adduser1 = (ImageView) findViewById(R.id.adduser1);
        adduser1.setVisibility(View.GONE);
        adduser2 = (ImageView) findViewById(R.id.adduser2);
        adduser2.setVisibility(View.GONE);
        adduser3 = (ImageView) findViewById(R.id.adduser3);
        adduser3.setVisibility(View.GONE);
        proname = (TextView) findViewById(R.id.proname);
        progender = (TextView) findViewById(R.id.progender);
        probloodgroup = (TextView) findViewById(R.id.probloodgroup);
        promartialstatus = (TextView) findViewById(R.id.promartialstatus);
        proprofession = (TextView) findViewById(R.id.proprofession);
display5=(TextView)findViewById(R.id.display5);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                {
                    uid = user.getUid();
                    if(uid.equals(userid))
                    {
                        adduser.setVisibility(View.VISIBLE);
                        adduser1.setVisibility(View.VISIBLE);
                        adduser2.setVisibility(View.VISIBLE);
                        adduser3.setVisibility(View.VISIBLE);
                        textfather.setVisibility(View.VISIBLE);
                        textmother.setVisibility(View.VISIBLE);
                        textsiblings.setVisibility(View.VISIBLE);
                        textchild.setVisibility(View.VISIBLE);

                        DatabaseReference databaseref = FirebaseDatabase.getInstance().getReference("profile/"+uid);
                        databaseref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot){
                                int year = Integer.parseInt(dataSnapshot.child("year").getValue().toString());
                                int month = Integer.parseInt(dataSnapshot.child("month").getValue().toString());
                                int day = Integer.parseInt(dataSnapshot.child("day").getValue().toString());
                                Calendar dob = Calendar.getInstance();
                                Calendar today = Calendar.getInstance();

                                dob.set(year, month, day);

                                int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

                                if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
                                    age--;
                                }

                                Integer ageInt = new Integer(age);
                                String val = String.valueOf(ageInt);
                                proage.setText(val);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                    }
                    DatabaseReference databaseref;
                    databaseref = FirebaseDatabase.getInstance().getReference();
                    databaseref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot){
                            i=0;


                            father = dataSnapshot.child("profile/"+uid+"/father").getValue().toString();
                            iden = dataSnapshot.child("profile/"+uid+"/gender").getValue().toString();
                            mother = dataSnapshot.child("profile/"+uid+"/mother").getValue().toString();
                            famuid = dataSnapshot.child("profile/"+uid+"/famuid").getValue().toString();

                            if (dataSnapshot.hasChild(famuid+"/family/"+uid+"/parent/1"))
                            {
                                DatabaseReference newref = FirebaseDatabase.getInstance().getReference();
                                newref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        fatheruid = dataSnapshot.child(famuid+"/family/"+uid+"/parent/1") .getValue().toString();
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                            }

                            if (dataSnapshot.hasChild(famuid+"/family/"+uid+"/parent/2"))
                            {
                                DatabaseReference newref = FirebaseDatabase.getInstance().getReference();
                                newref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        motheruid = dataSnapshot.child(famuid+"/family/"+uid+"/parent/2") .getValue().toString();
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                            }

                            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference(famuid+"/posts");
                            usersRef.orderByChild("uid").startAt(userid).endAt(userid+"\uf8ff").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                        if ((postSnapshot.child("type").getValue().toString()).equals("image"))
                                        {
                                            i++;
                                            link[i] = postSnapshot.child("image").getValue().toString();




                                        }
                                    }

                                    if(i==0)
                                    {
                                        imageview1.setVisibility(View.INVISIBLE);
                                        imageview2.setVisibility(View.INVISIBLE);
                                        imageview3.setVisibility(View.INVISIBLE);
                                        display5.setVisibility(View.INVISIBLE);
                                        addall.setVisibility(View.INVISIBLE);
                                        display5.setVisibility(View.GONE);



                                    }
                                    if(i==1)
                                    {
                                        imageview1.setVisibility(View.VISIBLE);
                                        imageview2.setVisibility(View.GONE);
                                        imageview3.setVisibility(View.GONE);
                                        addall.setVisibility(View.GONE);
                                        display5.setVisibility(View.GONE);
                                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(100,100);
                                        lp.setMargins(610,140,50,0);

                                        addall.setLayoutParams(lp);
                                        RelativeLayout.LayoutParams lpS = new RelativeLayout.LayoutParams(200,100);
                                        lpS.setMargins(603,274,50,0);

                                        display5.setLayoutParams(lpS);

                                        Picasso.with(getApplicationContext()).load(link[1]).into(imageview1);

                                    }
                                    if(i==2)
                                    {
                                        imageview1.setVisibility(View.VISIBLE);
                                        imageview2.setVisibility(View.VISIBLE);
                                        imageview3.setVisibility(View.GONE);
                                        addall.setVisibility(View.GONE);
                                        display5.setVisibility(View.GONE);

                                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(100,100);
                                        lp.setMargins(900,100,50,0);
                                        addall.setLayoutParams(lp);
                                        RelativeLayout.LayoutParams lpS = new RelativeLayout.LayoutParams(200,100);
                                        lpS.setMargins(885,224,50,0);

                                        display5.setLayoutParams(lpS);
                                        Picasso.with(getApplicationContext()).load(link[1]).into(imageview1);
                                        Picasso.with(getApplicationContext()).load(link[2]).into(imageview2);
                                    }
                                    if(i==3)
                                    {
                                        imageview1.setVisibility(View.VISIBLE);
                                        imageview2.setVisibility(View.VISIBLE);
                                        imageview3.setVisibility(View.VISIBLE);
                                        addall.setVisibility(View.GONE);
                                        display5.setVisibility(View.GONE);
                                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(100,100);
                                        lp.setMargins(1350,100,50,0);
                                        addall.setLayoutParams(lp);
                                        RelativeLayout.LayoutParams lpS = new RelativeLayout.LayoutParams(200,100);
                                        lpS.setMargins(1328,224,50,0);

                                        display5.setLayoutParams(lpS);
                                        Picasso.with(getApplicationContext()).load(link[1]).into(imageview1);
                                        Picasso.with(getApplicationContext()).load(link[2]).into(imageview2);
                                        Picasso.with(getApplicationContext()).load(link[3]).into(imageview3);
                                    }

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
        };

        DatabaseReference databaseref = FirebaseDatabase.getInstance().getReference("profile/"+userid);
        databaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                proname.setText(name);
                String gender = dataSnapshot.child("gender").getValue().toString();
                progender.setText(gender);
                String bloodgroup = dataSnapshot.child("blood").getValue().toString();
                probloodgroup.setText(bloodgroup);
                String martialstatus = dataSnapshot.child("martial").getValue().toString();
                promartialstatus.setText(martialstatus);
                String profession = dataSnapshot.child("profession").getValue().toString();
                proprofession.setText(profession);
                String religion = dataSnapshot.child("religion").getValue().toString();
                proreligion.setText(religion);
                String caste = dataSnapshot.child("caste").getValue().toString();
                procaste.setText(caste);
                String raasi = dataSnapshot.child("raasi").getValue().toString();
                proraasi.setText(raasi);
                String star = dataSnapshot.child("star").getValue().toString();
                prostar.setText(star);
                String height = dataSnapshot.child("height").getValue().toString();
                proheight.setText(height);
                String weight = dataSnapshot.child("weight").getValue().toString();
                proweight.setText(weight);
                String time = dataSnapshot.child("birthtime").getValue().toString();
                protime.setText(time);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        storageRef.child("Profile/"+userid).getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri urilll) {
                        getBitmap loadBitmap = new getBitmap();
                        loadBitmap.execute(urilll.toString());
                    }
                });

    }








    public void shareintentparent(View view) {
        Toast.makeText(this, "parent", Toast.LENGTH_SHORT).show();
        String message = "http://www.varundwarkani.esy.es/?famuid="+famuid+"&referrer="+uid+"&post=child&father=null&mother=null";
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(share, "Invite your parent"));
    }

    public void shareintentchild(View view) {
        Toast.makeText(this, "child", Toast.LENGTH_SHORT).show();
        if (iden.equals("Male"))
        {
            String message = "http://www.varundwarkani.esy.es/?famuid="+famuid+"&referrer="+uid+"&post="+iden+"&father="+uid+"&mother=null";
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(share, "Invite your Child"));
        }
        if (iden.equals("Female"))
        {
            String message = "http://www.varundwarkani.esy.es/?famuid="+famuid+"&referrer="+uid+"&post="+iden+"&father=null&mother="+uid;
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(share, "Invite your Child"));
        }
    }

    public void shareintentsiblings(View view) {

        Toast.makeText(this, "siblings", Toast.LENGTH_SHORT).show();
        if (father.equals("") && mother.equals(""))
        {
            String message = "http://www.varundwarkani.esy.es/?famuid="+famuid+"&referrer="+uid+"&post="+iden+"&father=null&mother=null";
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(share, "Invite your Child"));
        }
        else
        {
            String message = "http://www.varundwarkani.esy.es/?famuid="+famuid+"&referrer="+uid+"&post=sibling"+"&father="+fatheruid+"&mother="+motheruid;
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(share, "Invite your Siblings"));
        }
        if (father.equals(""))
        {
            if (mother.equals(""))
            {
                String message = "http://www.varundwarkani.esy.es/?famuid="+famuid+"&referrer="+uid+"&post=sibling"+"&father=null&mother=null";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(share, "Invite your Siblings"));
            }
            else
            {
                String message = "http://www.varundwarkani.esy.es/?famuid="+famuid+"&referrer="+uid+"&post=sibling"+"&father=null&mother="+motheruid;
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(share, "Invite your Siblings"));
            }
        }
        else
        {

        }
        if (mother.equals(""))
        {
            if (father.equals(""))
            {
                String message = "http://www.varundwarkani.esy.es/?famuid="+famuid+"&referrer="+uid+"&post=sibling"+"&father=null&mother=null";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(share, "Invite your Siblings"));
            }
            else
            {
                String message = "http://www.varundwarkani.esy.es/?famuid="+famuid+"&referrer="+uid+"&post=sibling"+"&father="+fatheruid+"&mother=null";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(share, "Invite your Siblings"));
            }
        }
        else
        {

        }
    }


    public class getBitmap extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bit = null;
            if (params.length == 0)
                throw new IllegalArgumentException("You should pass me a url!!");
            final String src = params[0];
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bit = BitmapFactory.decodeStream(input);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bit;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap !=null){
                proimage.setImageBitmap(bitmap);

            }
        }
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
        super.onBackPressed();
    }



    public void allposts(View view) {
        Intent intentt = new Intent(getBaseContext(), allposts.class);
        intentt.putExtra("userid", userid);
        intentt.putExtra("famuid", famuid);
        startActivity(intentt);
    }

}