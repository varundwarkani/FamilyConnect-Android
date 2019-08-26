package com.cartoonswikipedia.www.familyconnect;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class selectfathername extends AppCompatActivity {

    Button invitefather;
    EditText search;
    String famuid, sear, uid, imgurl, url, uidd;
    ArrayList<String> namelist = new ArrayList<>();
    ArrayList<String> imglist = new ArrayList<>();
    ArrayList<String> uidlist = new ArrayList<>();
    RecyclerView recyclerView;
    String myuid;
    RecyclerView.LayoutManager layout;
    RecyclerView.Adapter adapter;
String msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecttab);
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        }
        invitefather = (Button) findViewById(R.id.invitefather);
        invitefather.setVisibility(View.VISIBLE);
        search = (EditText) findViewById(R.id.search);
        famuid = getIntent().getStringExtra("famuid");
        myuid = getIntent().getStringExtra("myuid");
        msg = getIntent().getStringExtra("message");


        TextView ss=(TextView)findViewById(R.id.textView1133);
        ss.setText("Select "+msg.toString()+" name");


        String fatheeeer = getIntent().getStringExtra("message");
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imglist.clear();
        namelist.clear();
        uidlist.clear();
        adapter = new MainAdapterrs(namelist, imglist, uidlist, famuid, myuid);
        recyclerView.setAdapter(adapter);
        sear = search.getText().toString();
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference(famuid + "/profile");
        usersRef.orderByChild("name").startAt(sear).endAt(sear + "\uf8ff").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    final User user = postSnapshot.getValue(User.class);
                    uid = user.getUid();
                    uidd = user.getUid();
                    uidlist.add(uidd);
                    imgurl = user.getPic();
                    imglist.add(imgurl);
                    namelist.add(user.getname());
                    recyclerView.setHasFixedSize(true);
                    layout = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layout);

                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        recyclerView.setHasFixedSize(true);
        layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                imglist.clear();
                namelist.clear();
                uidlist.clear();
                adapter = new MainAdapterrs(namelist, imglist, uidlist,famuid, myuid);
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
                adapter = new MainAdapterrs(namelist, imglist, uidlist, famuid, myuid);
                recyclerView.setAdapter(adapter);
                sear = search.getText().toString();
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference(famuid + "/profile");
                usersRef.orderByChild("name").startAt(sear).endAt(sear + "\uf8ff").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            final User user = postSnapshot.getValue(User.class);
                            uid = user.getUid();
                            imglist.clear();
                            namelist.clear();
                            uidlist.clear();
                            adapter = new MainAdapterrs(namelist, imglist, uidlist, famuid, myuid);
                            recyclerView.setAdapter(adapter);
                            uidd = user.getUid();
                            uidlist.add(uidd);
                            imgurl = user.getPic();
                            imglist.add(imgurl);
                            namelist.add(user.getname());
                            adapter = new MainAdapterrs(namelist, imglist, uidlist, famuid, myuid);
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
        layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, Main2Activity.class));

    }

    public void invitefather(View view) {
        String message = "http://www.varundwarkani.esy.es/?famuid="+famuid+"&referrer="+uid+"&post=child&father=null&mother=null";
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));
    }
}

class MainAdapterrs extends RecyclerView.Adapter<MainAdapterrs.ViewHolder> {


    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> imglink = new ArrayList<>();
    ArrayList<String> uid = new ArrayList<>();
    String famuid,myuid;

    public MainAdapterrs(ArrayList<String> name, ArrayList<String> imgurl,ArrayList<String> uid, String famuid, String myuid){
        names = name;
        imglink = imgurl;
        this.uid = uid;
        this.famuid = famuid;
        this.myuid = myuid;
    }

    @Override
     public MainAdapterrs.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MainAdapterrs.ViewHolder holder, final int position) {
        holder.name.setText(names.get(position));
        Picasso.with(holder.itemView.getContext()).load(imglink.get(position)).into(holder.proimg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (myuid.equals(uid.get(position)))
                {
                    Toast.makeText(v.getContext(), "Cannot make yourself as father!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DatabaseReference databaseref = FirebaseDatabase.getInstance().getReference(famuid+"/profile/"+uid.get(position));
                    databaseref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot){
                            String name = dataSnapshot.child("name").getValue().toString();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReference = database.getReference();
                            databaseReference.child("profile/" + myuid + "/father").setValue(name);
                            databaseReference.child(famuid+"/family/"+myuid+"/parent/1").setValue(uid.get(position));

                            ((Activity)v.getContext()).finish();

                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public ImageView proimg;

        public ViewHolder(View itemView) {
            super(itemView);

            proimg = itemView.findViewById(R.id.likeprofile);
            name = itemView.findViewById(R.id.namess);
        }
    }
}