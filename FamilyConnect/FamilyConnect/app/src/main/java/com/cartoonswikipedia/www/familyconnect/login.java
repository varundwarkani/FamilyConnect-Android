package com.cartoonswikipedia.www.familyconnect;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.widget.Toast.LENGTH_SHORT;
import static com.cartoonswikipedia.www.familyconnect.signup.MY_PREFS_NAME;
import static java.lang.Boolean.FALSE;

public class login extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    EditText email,pass;
    String usermaill,userpasss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        }

        ProgressBar bar = (ProgressBar) findViewById(R.id.empty_progress_bar);
        bar.setVisibility(View.GONE);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        mAuth = FirebaseAuth.getInstance();
    }

    public void logged(View view) {
        ProgressBar bar = (ProgressBar) findViewById(R.id.empty_progress_bar);
        bar.setVisibility(View.VISIBLE);
        usermaill = email.getText().toString().trim();
        userpasss = pass.getText().toString().trim();

        if(!TextUtils.isEmpty(usermaill) && !TextUtils.isEmpty(userpasss))
        {
            mAuth.signInWithEmailAndPassword(usermaill,userpasss).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        final ProgressBar bar = (ProgressBar) findViewById(R.id.empty_progress_bar);
                        bar.setVisibility(View.GONE);
                        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(login.this);
                        SharedPreferences.Editor myEditor = myPreferences.edit();
                        myEditor.putString("password", userpasss);
                        myEditor.apply();
                        Intent intent = new Intent(getBaseContext(), Main3Activity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        ProgressBar bar = (ProgressBar) findViewById(R.id.empty_progress_bar);
                        bar.setVisibility(View.GONE);
                        Toast.makeText(login.this,"Oops, some error occured.",Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
        else
        {
            Toast.makeText(this, "Please fill in Email ID/ Password", Toast.LENGTH_SHORT).show();
            bar.setVisibility(View.GONE);
        }
    }

    public void reset(View view) {
        ProgressBar bar = (ProgressBar) findViewById(R.id.empty_progress_bar);
        bar.setVisibility(View.VISIBLE);
        usermaill = email.getText().toString().trim();

        if(!TextUtils.isEmpty(usermaill) )
        {
            bar.setVisibility(View.GONE);
                mAuth.sendPasswordResetEmail(usermaill).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(login.this, "Mail Sent!", Toast.LENGTH_LONG).show();
                    }
                });
            }

        else
        {
            bar.setVisibility(View.GONE);
            Toast.makeText(login.this,"Enter Email!",Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(login.this,MainActivity.class));
        finish();
    }
}
