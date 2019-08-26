package com.cartoonswikipedia.www.familyconnect;

import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ResourceBundle;

public class network extends AppCompatActivity {

    WebView mWebView;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        mWebView = (WebView) findViewById(R.id.webview);
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(network.this);
        password = myPreferences.getString("password", "unknown");
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    email = user.getEmail();
                    WebSettings webSettings = mWebView.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    webSettings.setDomStorageEnabled(true);
                    webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        // chromium, enable hardware acceleration
                        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                    } else {
                        // older android version, disable hardware acceleration
                        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    }
                    webSettings.setLoadWithOverviewMode(true);
                    webSettings.setUseWideViewPort(true);
                    webSettings.setBuiltInZoomControls(true);
                    webSettings.setDisplayZoomControls(false);
                    webSettings.setSupportZoom(true);
                    webSettings.setDefaultTextEncodingName("utf-8");
                    mWebView.setWebViewClient(new WebViewClient());
                    mWebView.loadUrl("http://www.varundwarkani.esy.es?email="+email+"&password="+password);
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
}
