package com.cartoonswikipedia.www.familyconnect;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class signup extends AppCompatActivity {

    Button signup;
    EditText email,pass;
    TextView famname;
    String uid;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Uri data = getIntent().getData();
        String scheme = data.getScheme(); // "http"
        List<String> params = data.getPathSegments();
        final String first = params.get(0); // famuid
        final String second = params.get(1); //referrer
        final String third = params.get(2); //post
        final String four = params.get(3); //father
        final String five = params.get(4);// mother

        ProgressBar bar = (ProgressBar) findViewById(R.id.empty_progress_bar);
        bar.setVisibility(View.GONE);
        signup = (Button) findViewById(R.id.sign);
        email = (EditText) findViewById(R.id.email);
        famname = (TextView) findViewById(R.id.famname);
        famname.setText(first);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    mAuth.signOut();

                } else {


                }

            }
        };




        ((MyApplication) this.getApplication()).setSomeVariable(famname.getText().toString());
        pass = (EditText) findViewById(R.id.pass);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    startActivity(new Intent(signup.this,Main2Activity.class));

                } else {
                   startActivity(new Intent(signup.this,login.class));

                }

            }
        };

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ProgressBar bar = (ProgressBar) findViewById(R.id.empty_progress_bar);
                bar.setVisibility(View.VISIBLE);
                final String usermail,userpass;
                usermail = email.getText().toString().trim();
                userpass = pass.getText().toString().trim();

                if(!TextUtils.isEmpty(usermail) && !TextUtils.isEmpty(userpass))
                {
                    Task<AuthResult> authResultTask = mAuth.createUserWithEmailAndPassword(usermail, userpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                ProgressBar bar = (ProgressBar) findViewById(R.id.empty_progress_bar);
                                bar.setVisibility(View.GONE);
                                Toast.makeText(signup.this, "User Created", Toast.LENGTH_LONG).show();
                                final Intent intent = new Intent(getBaseContext(), Main2Activity.class);
                                final SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                editor.putString("famuid", first);
                                editor.putString("referrer", second);
                                editor.putString("post", third);

                                DatabaseReference newref = FirebaseDatabase.getInstance().getReference();
                                newref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (four.equals("null") && five.equals("null"))
                                        {
                                            editor.putString("father", four);
                                            editor.putString("mother", five);
                                            editor.apply();
                                            startActivity(intent);
                                        }
                                        else
                                        {
                                            String a = dataSnapshot.child("profile/"+four+"/name") .getValue().toString();
                                            editor.putString("fathername", a);
                                            String ab = dataSnapshot.child("profile/"+five+"/name") .getValue().toString();
                                            editor.putString("mothername", ab);
                                            editor.putString("father", four);
                                            editor.putString("mother", five);
                                            editor.apply();
                                            startActivity(intent);
                                        }
                                        if (four.equals("null"))
                                        {
                                            if (five.equals("null"))
                                            {
                                                editor.putString("father", four);
                                                editor.putString("mother", five);
                                                editor.apply();
                                                startActivity(intent);
                                            }
                                            else
                                            {
                                                String ab = dataSnapshot.child("profile/"+five+"/name") .getValue().toString();
                                                editor.putString("mothername", ab);
                                                editor.putString("father", four);
                                                editor.putString("mother", five);
                                                editor.apply();
                                                startActivity(intent);
                                            }
                                        }

                                        if (five.equals("null"))
                                        {
                                            if (four.equals("null"))
                                            {
                                                editor.putString("father", four);
                                                editor.putString("mother", five);
                                                editor.apply();
                                                startActivity(intent);
                                            }
                                            else
                                            {
                                                String a = dataSnapshot.child("profile/"+four+"/name") .getValue().toString();
                                                editor.putString("fathername", a);
                                                editor.putString("father", four);
                                                editor.putString("mother", five);
                                                editor.apply();
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                            }
                            else {
                                ProgressBar bar = (ProgressBar) findViewById(R.id.empty_progress_bar);
                                bar.setVisibility(View.GONE);
                                Toast.makeText(signup.this, "Oops, some error occured.", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }

                else {
                    bar.setVisibility(View.GONE);
                    Toast.makeText(signup.this,"Oops, some error occured.",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(signup.this,MainActivity.class));
        finish();
    }

    public void request(View view) {
        startActivity(new Intent(signup.this,request.class));
        finish();
    }
}
