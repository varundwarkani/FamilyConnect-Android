package com.cartoonswikipedia.www.familyconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class request extends AppCompatActivity {

    EditText requestfamilyname, requestmail,requestpassword;
    Button requestfamily;
    DatabaseReference databaseref;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    String mail,pass,famname,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
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
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        requestfamily = (Button) findViewById(R.id.requestfam);
        requestfamilyname = (EditText) findViewById(R.id.requestfamilyname);
        requestmail = (EditText) findViewById(R.id.requestmail);
        requestpassword = (EditText) findViewById(R.id.requestpassword);
        requestfamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mail = requestmail.getText().toString();
                pass = requestpassword.getText().toString();
                famname = requestfamilyname.getText().toString();

                if(!TextUtils.isEmpty(mail) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(famname))
                {
                    Task<AuthResult> authResultTask = mAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful())
                            {
                                Toast.makeText(request.this, "Family created Succesfully , Please Login to continue", Toast.LENGTH_SHORT).show();

                                mAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful())
                                        {
                                            mAuth = FirebaseAuth.getInstance();
                                            uid = mAuth.getCurrentUser().getUid();

                                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                                            DatabaseReference databaseReference = database.getReference();
                                            databaseReference.child("profile/"+uid+"/mail").setValue(mail);
                                            databaseReference.child(uid+"/profile/"+uid+"/referrer").setValue("root");
                                            databaseReference.child("profile/"+uid+"/famuid").setValue(uid);
                                            databaseReference.child("profile/"+uid+"/name").setValue("");
                                            databaseReference.child("profile/"+uid+"/dob").setValue("");
                                            databaseReference.child("profile/"+uid+"/gender").setValue("Male");
                                            databaseReference.child("profile/"+uid+"/year").setValue(0);
                                            databaseReference.child("profile/"+uid+"/month").setValue(0);
                                            databaseReference.child("profile/"+uid+"/day").setValue(0);
                                            databaseReference.child("profile/"+uid+"/father").setValue("");
                                            databaseReference.child("profile/"+uid+"/mother").setValue("");
                                            databaseReference.child("profile/"+uid+"/height").setValue("");
                                            databaseReference.child("profile/"+uid+"/birthtime").setValue("");
                                            databaseReference.child("profile/"+uid+"/weight").setValue("");
                                            databaseReference.child("profile/"+uid+"/blood").setValue("Not Selected");
                                            databaseReference.child("profile/"+uid+"/martial").setValue("Single");
                                            databaseReference.child("profile/"+uid+"/religion").setValue("Not Selected");
                                            databaseReference.child("profile/"+uid+"/raasi").setValue("Not Selected");
                                            databaseReference.child("profile/"+uid+"/star").setValue("Not Selected");
                                            databaseReference.child("profile/"+uid+"/profession").setValue("Not Selected");
                                            databaseReference.child("profile/"+uid+"/yoga").setValue("Not Selected");
                                            databaseReference.child("profile/"+uid+"/caste").setValue("Not Selected");
                                            databaseReference.child("profile/"+uid+"/uid").setValue(uid);
                                            databaseReference.child("profile/"+uid+"/pic").setValue("https://firebasestorage.googleapis.com/v0/b/family-connect-2087f.appspot.com/o/dummy.jpg?alt=media&token=b80ccf22-2057-4061-aa47-81120b026114");
                                            databaseReference.child(uid +"/profile/"+uid+"/uid").setValue(uid);
                                            databaseReference.child(uid +"/profile/"+uid+"/pic").setValue("https://firebasestorage.googleapis.com/v0/b/family-connect-2087f.appspot.com/o/dummy.jpg?alt=media&token=b80ccf22-2057-4061-aa47-81120b026114");
                                            databaseReference.child(uid+"/profile/"+uid+"/name").setValue("");
                                            databaseReference.child(uid+"/profile/"+uid+"/chataccess").setValue("no");
                                            databaseReference.child(uid+"/status").setValue("No");
                                            mAuth.signOut();
                                            Intent intent = new Intent(getBaseContext(), login.class);
                                            startActivity(intent);
                                        }
                                        else {
                                        }
                                    }
                                });


                            }
                            else {
                                Toast.makeText(request.this, "User exist in our database/ password is too weak.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

                else {
                    Toast.makeText(request.this, "Please fill in all the details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}