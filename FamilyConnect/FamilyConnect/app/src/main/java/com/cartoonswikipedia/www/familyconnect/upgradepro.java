package com.cartoonswikipedia.www.familyconnect;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class upgradepro extends AppCompatActivity {

    RelativeLayout payrelative;
    Button paybutton;
    EditText paytranscation,payname,payphone;
    TextView paystatus;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    String uid,famuid,name,phone,transcation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgradepro);
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        }

        payrelative = (RelativeLayout) findViewById(R.id.payrelative);
        paystatus = (TextView) findViewById(R.id.paystatus);
        paytranscation = (EditText) findViewById(R.id.paytranscation);
        payphone = (EditText) findViewById(R.id.payphone);
        payname = (EditText) findViewById(R.id.payname);
        paybutton = (Button) findViewById(R.id.paybutton);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener =
                new FirebaseAuth.AuthStateListener()
                {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
                    {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null)
                        {
                            uid = user.getUid();

                            DatabaseReference databaseref123;
                            databaseref123 = FirebaseDatabase.getInstance().getReference("profile/"+uid);
                            databaseref123.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot){
                                    famuid = dataSnapshot.child("famuid").getValue().toString();

                                    DatabaseReference databaseref123;
                                    databaseref123 = FirebaseDatabase.getInstance().getReference();
                                    databaseref123.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot){

                                            if (dataSnapshot.hasChild("notifications/"+uid))
                                            {

                                                DatabaseReference databaseref123;
                                                databaseref123 = FirebaseDatabase.getInstance().getReference("notifications/"+uid);
                                                databaseref123.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot){

                                                        if (dataSnapshot.hasChild("status"))
                                                        {
                                                            String status = dataSnapshot.child("status").getValue().toString();
                                                            if (status.equals("pending"))
                                                            {
                                                                paystatus.setVisibility(View.VISIBLE);
                                                                paystatus.setText("Your details has been submitted. Please wait for approval");
                                                                payrelative.setVisibility(View.GONE);
                                                            }

                                                            if(status.equals("rejected"))
                                                            {
                                                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                                final DatabaseReference databaseReference = database.getReference();
                                                                databaseReference.child("notifications/"+uid+"/status").setValue("");
                                                                Toast.makeText(upgradepro.this, "Your details has been rejected. Please try again", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
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
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });

                        }
                    }
                };
    }

    public void upgradepro(View view) {
        phone = payphone.getText().toString();
        name = payname.getText().toString();
        transcation = paytranscation.getText().toString();

        if(!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(transcation) )
        {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference databaseReference = database.getReference();
            databaseReference.child("notifications/"+uid+"/uid").setValue(uid);
            databaseReference.child("notifications/"+uid+"/famuid").setValue(famuid);
            databaseReference.child("notifications/"+uid+"/phone").setValue(phone);
            databaseReference.child("notifications/"+uid+"/name").setValue(name);
            databaseReference.child("notifications/"+uid+"/transcation").setValue(transcation);
            databaseReference.child("notifications/"+uid+"/status").setValue("pending");
            Toast.makeText(this, "Success! Please wait for confirmation.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Please fill in details", Toast.LENGTH_SHORT).show();
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
}
