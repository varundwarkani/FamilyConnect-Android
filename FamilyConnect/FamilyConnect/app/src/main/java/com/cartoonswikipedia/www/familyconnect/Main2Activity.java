package com.cartoonswikipedia.www.familyconnect;

import android.Manifest;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
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
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import fr.ganfra.materialspinner.MaterialSpinner;

import static android.widget.Toast.LENGTH_SHORT;
import static com.cartoonswikipedia.www.familyconnect.signup.MY_PREFS_NAME;

public class Main2Activity extends AppCompatActivity {

    MaterialSpinner bloodspinner;
    RelativeLayout req;
    ImageView profileimagee45;
    String heightval,weightval;
    String fatheruidname,motheruidname;
    TextView ss;
    TextView textView12,textView123;
    ImageView vieemsdypodst;
    TextView textView10,seaaarch;
    Calendar c;
    int year,month,day;
    String access;
    int numchilds;
    String bloodgroupp,raasii,starr,professionn,religionn,castee;
    String gendervalue,martialstatus;
    String re;
    String fat,mot,childuid;
    LinearLayout linearLayout;
    TextView ageeee;
    int j;
    int  looper = 0;
    int loopera = 1;
    EditText[] tv = new EditText[10];
    WebView mWebView;
    String password;
    TextView fathernamee,mothername,textview,txt,naaam;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    RecyclerView postrecycler;
    DatabaseReference myref;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    String pass;
    DatabaseReference databaseref;
    String usermail,uid,famuidd,referrer,post,father,mother;
    String count,name,profileimg,a;
    Long coun;
    int identity;
    ImageView like,comments;
    RecyclerView.LayoutManager layout;
    StorageReference storageReference;
    private static final int GALLERY_INTENT = 2;
    EditText editname,editgender;
    TextView editdob;
    TextView editemail;
    Button editprofile;
    ImageView profileimage;
    ProgressBar bar;
    Button logout;
    String n,d;
    SwipeRefreshLayout mswipe;
    EditText height,weight;
    TextView ssssss;
    RadioGroup genderradio,martialradio;
    MaterialSpinner religionspinner,castespinner,raasispinner,starspinner,professionspinner;
    TextView children,gen,mar,bloo,relig,cas,raa,sta,yo,profe;
    ImageView adddd;
    ScrollView scrollView;
    String ab,cd,ef,gh;
    Spinner dropdown,dropdown1,dropdown2,dropdown3,dropdown4,dropdown5;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_home:

                    naaam.setVisibility(View.GONE);

                    scrollView.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.INVISIBLE);
                    children.setVisibility(View.INVISIBLE);
                    gen.setVisibility(View.INVISIBLE);
                    mar.setVisibility(View.INVISIBLE);
                    relig.setVisibility(View.INVISIBLE);
                    cas.setVisibility(View.INVISIBLE);
                    raa.setVisibility(View.INVISIBLE);
                    sta.setVisibility(View.INVISIBLE);
                    profe.setVisibility(View.INVISIBLE);
                    fathernamee.setVisibility(View.INVISIBLE);
                    mothername.setVisibility(View.INVISIBLE);
                    height.setVisibility(View.GONE);
                    weight.setVisibility(View.GONE);
                    genderradio.setVisibility(View.GONE);
                    martialradio.setVisibility(View.INVISIBLE);
                    bloodspinner.setVisibility(View.INVISIBLE);
                    religionspinner.setVisibility(View.INVISIBLE);
                    castespinner.setVisibility(View.INVISIBLE);
                    raasispinner.setVisibility(View.INVISIBLE);
                    starspinner.setVisibility(View.INVISIBLE);
                    professionspinner.setVisibility(View.INVISIBLE);
                    adddd.setVisibility(View.INVISIBLE);
                    mWebView.setVisibility(View.GONE);
                    logout.setVisibility(View.GONE);
                    bar.setVisibility(View.GONE);
                    editname.setVisibility(View.GONE);
                    editdob.setVisibility(View.GONE);
                    editemail.setVisibility(View.GONE);
                    profileimage.setVisibility(View.GONE);

                    editprofile.setVisibility(View.GONE);
                    postrecycler.setAlpha(1);
                    return true;

                case R.id.navigation_dashboard:

                    naaam.setVisibility(View.GONE);

                    scrollView.setVisibility(View.GONE);
                    mWebView.setVisibility(View.GONE);
                    postrecycler.setAlpha(0);
                    linearLayout.setVisibility(View.GONE);
                    logout.setVisibility(View.GONE);
                    editname.setVisibility(View.GONE);
                    editdob.setVisibility(View.GONE);
                    height.setVisibility(View.GONE);
                    weight.setVisibility(View.GONE);
                    editemail.setVisibility(View.GONE);
                    profileimage.setVisibility(View.GONE);



                    editprofile.setVisibility(View.GONE);
                    children.setVisibility(View.INVISIBLE);
                    gen.setVisibility(View.GONE);
                    mar.setVisibility(View.GONE);

                    relig.setVisibility(View.GONE);
                    cas.setVisibility(View.GONE);
                    raa.setVisibility(View.GONE);
                    sta.setVisibility(View.GONE);
                    profe.setVisibility(View.GONE);
                    fathernamee.setVisibility(View.GONE);

                    mothername.setVisibility(View.GONE);
                    genderradio.setVisibility(View.GONE);
                    martialradio.setVisibility(View.GONE);
                    bloodspinner.setVisibility(View.GONE);
                    religionspinner.setVisibility(View.GONE);
                    castespinner.setVisibility(View.GONE);
                    raasispinner.setVisibility(View.GONE);
                    starspinner.setVisibility(View.GONE);
                    professionspinner.setVisibility(View.GONE);
                    adddd.setVisibility(View.GONE);






               //     Intent intentt = new Intent(getBaseContext(), Search.class);
                 //   intentt.putExtra("famuid", famuidd);
                   // startActivity(intentt);






                    return true;

                case R.id.addpost:

                    Intent intent = new Intent(getBaseContext(), post.class);
                    intent.putExtra("famuid", famuidd);
                    startActivity(intent);
                    return  true;

                case R.id.navigation_notifications:

                    naaam.setVisibility(View.GONE);

                    scrollView.setVisibility(View.VISIBLE);
                    mWebView.setVisibility(View.GONE);
                    postrecycler.setAlpha(0);
                    linearLayout.setVisibility(View.VISIBLE);
                    logout.setVisibility(View.VISIBLE);
                    editname.setVisibility(View.VISIBLE);
                    editdob.setVisibility(View.VISIBLE);
                    height.setVisibility(View.VISIBLE);
                    weight.setVisibility(View.VISIBLE);
                    editemail.setVisibility(View.VISIBLE);
                    profileimage.setVisibility(View.VISIBLE);



                    editprofile.setVisibility(View.VISIBLE);
                    children.setVisibility(View.INVISIBLE);
                    gen.setVisibility(View.INVISIBLE);
                    mar.setVisibility(View.INVISIBLE);

                    relig.setVisibility(View.VISIBLE);
                    cas.setVisibility(View.VISIBLE);
                    raa.setVisibility(View.VISIBLE);
                    sta.setVisibility(View.VISIBLE);
                    profe.setVisibility(View.VISIBLE);
                    fathernamee.setVisibility(View.VISIBLE);

                    mothername.setVisibility(View.VISIBLE);
                    genderradio.setVisibility(View.VISIBLE);
                    martialradio.setVisibility(View.VISIBLE);
                    bloodspinner.setVisibility(View.VISIBLE);
                    religionspinner.setVisibility(View.VISIBLE);
                    castespinner.setVisibility(View.VISIBLE);
                    raasispinner.setVisibility(View.VISIBLE);
                    starspinner.setVisibility(View.VISIBLE);
                    professionspinner.setVisibility(View.VISIBLE);
                    adddd.setVisibility(View.VISIBLE);

                    storageReference = FirebaseStorage.getInstance().getReference();
                    editemail.setText(usermail);
                    databaseref = FirebaseDatabase.getInstance().getReference("profile/"+uid);
                    databaseref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot){
                            String dob = dataSnapshot.child("dob").getValue().toString();
                            editdob.setText(dob);
                            String name = dataSnapshot.child("name").getValue().toString();
                            editname.setText(name);
                            String father = dataSnapshot.child("father").getValue().toString();
                            fathernamee.setText(father);
                            String mother = dataSnapshot.child("mother").getValue().toString();
                            mothername.setText(mother);
                            String height1 = dataSnapshot.child("height").getValue().toString();
                            height.setText(height1);
                            String weight1 = dataSnapshot.child("weight").getValue().toString();
                            weight.setText(weight1);
                            String timeq = dataSnapshot.child("birthtime").getValue().toString();
                            Toast.makeText(Main2Activity.this, timeq, Toast.LENGTH_SHORT).show();

                            year = Integer.parseInt(dataSnapshot.child("year").getValue().toString());
                            month = Integer.parseInt(dataSnapshot.child("month").getValue().toString());
                            day = Integer.parseInt(dataSnapshot.child("day").getValue().toString());
                            c.set(year,month,day);



                            String pic = dataSnapshot.child("pic").getValue().toString();
                            Picasso.with(getApplicationContext())
                                    .load(pic)
                                    .into(profileimage);
                            bar.setVisibility(View.GONE);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(Main2Activity.this,"Cannot Connect with Database!",Toast.LENGTH_SHORT).show();
                        }
                    });
                    return true;
                case R.id.network:

                    linearLayout.setVisibility(View.INVISIBLE);
                    children.setVisibility(View.GONE);
                    gen.setVisibility(View.GONE);
                    mar.setVisibility(View.GONE);

                    relig.setVisibility(View.GONE);
                    cas.setVisibility(View.GONE);
                    raa.setVisibility(View.GONE);
                    sta.setVisibility(View.GONE);
                    profe.setVisibility(View.GONE);
                    fathernamee.setVisibility(View.GONE);





                    mothername.setVisibility(View.GONE);
                    height.setVisibility(View.GONE);
                    weight.setVisibility(View.GONE);
                    genderradio.setVisibility(View.GONE);
                    martialradio.setVisibility(View.GONE);
                    bloodspinner.setVisibility(View.GONE);
                    religionspinner.setVisibility(View.GONE);
                    castespinner.setVisibility(View.GONE);
                    raasispinner.setVisibility(View.GONE);
                    starspinner.setVisibility(View.GONE);
                    professionspinner.setVisibility(View.GONE);
                    adddd.setVisibility(View.GONE);

                    logout.setVisibility(View.GONE);
                    bar.setVisibility(View.GONE);
                    editname.setVisibility(View.GONE);
                    editdob.setVisibility(View.GONE);
                    editemail.setVisibility(View.GONE);
                    profileimage.setVisibility(View.GONE);







                    editprofile.setVisibility(View.GONE);
                    postrecycler.setAlpha(0);
                        mWebView.setVisibility(View.VISIBLE);
                        mWebView.getSettings().setJavaScriptEnabled(true);

                        mWebView.getSettings().setDomStorageEnabled(true);
                        mWebView.getSettings().setSaveFormData(true);
                        mWebView.getSettings().setAllowContentAccess(true);
                        mWebView.getSettings().setAllowFileAccess(true);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        mWebView.getSettings().setAllowFileAccessFromFileURLs(true);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        mWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
                    }
                    mWebView.getSettings().setSupportZoom(true);
                        mWebView.setWebViewClient(new WebViewClient());
                        mWebView.setClickable(true);
                        mWebView.setWebChromeClient(new WebChromeClient());
                        mWebView.clearCache(true);
                        mWebView.loadUrl("http://www.varundwarkani.esy.es/toast.html?famuid="+famuidd+"&uid="+uid);
                        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(Main2Activity.this);
                        password = myPreferences.getString("password", "unknown");
                        mWebView.addJavascriptInterface(new WebViewJavaScriptInterface(Main2Activity.this), "app");
                    return  true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        c = Calendar.getInstance();

        req = (RelativeLayout) findViewById(R.id.qwerty);
        textView12 = (TextView) findViewById(R.id.textView12);
        vieemsdypodst=(ImageView) findViewById(R.id.profileimage);
        textView123 = (TextView) findViewById(R.id.textView123);
        textView10 = (TextView) findViewById(R.id.textView10);
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        }

        ss=(TextView)findViewById(R.id.ssed);
        profileimagee45 = (ImageView) findViewById(R.id.profileimagee45);
        fathernamee = (TextView) findViewById(R.id.father);
        naaam = (TextView) findViewById(R.id.textView1233);



        mothername = (TextView) findViewById(R.id.mother);
        height = (EditText) findViewById(R.id.heightt);
        weight = (EditText) findViewById(R.id.weightt);
        genderradio = (RadioGroup) findViewById(R.id.genderradio);
        genderradio.clearCheck();
        martialradio = (RadioGroup) findViewById(R.id.martialradio);
        martialradio.clearCheck();
        bloodspinner = (MaterialSpinner) findViewById(R.id.bloodspinner);
        religionspinner = (MaterialSpinner) findViewById(R.id.religionspinner);
        castespinner  = (MaterialSpinner) findViewById(R.id.castespinner);
        raasispinner = (MaterialSpinner) findViewById(R.id.raasispinner);
        starspinner = (MaterialSpinner) findViewById(R.id.starspinner);
        professionspinner = (MaterialSpinner) findViewById(R.id.professionspinner);


        children = (TextView) findViewById(R.id.children);
        gen = (TextView) findViewById(R.id.gender);
        mar = (TextView) findViewById(R.id.martial);

        relig = (TextView) findViewById(R.id.religion);
        cas = (TextView) findViewById(R.id.caste);
        raa = (TextView) findViewById(R.id.raasi);
        sta = (TextView) findViewById(R.id.star);
        profe = (TextView) findViewById(R.id.profession);
        adddd = (ImageView) findViewById(R.id.adddd);
        scrollView = (ScrollView) findViewById(R.id.scrollview);
        scrollView.setVisibility(View.GONE);
        children.setVisibility(View.INVISIBLE);
        gen.setVisibility(View.INVISIBLE);
        mar.setVisibility(View.INVISIBLE);

        relig.setVisibility(View.INVISIBLE);
        cas.setVisibility(View.INVISIBLE);
        raa.setVisibility(View.INVISIBLE);
        sta.setVisibility(View.INVISIBLE);
        profe.setVisibility(View.INVISIBLE);
        fathernamee.setVisibility(View.INVISIBLE);



       // birthtime.setVisibility(View.GONE);
        genderradio.setVisibility(View.INVISIBLE);
        martialradio.setVisibility(View.INVISIBLE);
        bloodspinner.setVisibility(View.INVISIBLE);
        religionspinner.setVisibility(View.INVISIBLE);
        castespinner.setVisibility(View.INVISIBLE);
        raasispinner.setVisibility(View.INVISIBLE);
        starspinner.setVisibility(View.INVISIBLE);
        professionspinner.setVisibility(View.INVISIBLE);
        adddd.setVisibility(View.INVISIBLE);
        linearLayout = (LinearLayout) findViewById(R.id.abcdef);
        genderradio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.male:
                        gendervalue = "Male";
                        break;
                    case R.id.female:
                        gendervalue = "Female";
                        break;
                }
            }
        });

        martialradio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.single:
                        martialstatus = "Single";
                        break;
                    case R.id.married:
                        martialstatus = "Married";
                        break;
                }
            }
        });



        dropdown = (Spinner) findViewById(R.id.bloodspinner);
        String[] items = new String[]{"Not Selected","O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        dropdown1 = (Spinner) findViewById(R.id.religionspinner);
        String[] itemsa = new String[]{"Not Selected","Muslim - Shia", "Muslim - Sunni", "Muslim - Others", "Christian","Sikh","Jain - Digambar","Jain - Shwetambar","Jain - Others","Parsi","Buddhist","Jewish"};
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsa);

        dropdown2 = (Spinner) findViewById(R.id.castespinner);
        String[] items12 = new String[]{"Not Selected","OC", "BC", "OBC", "ST"};
        final ArrayAdapter<String> adapter12 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items12);

        dropdown3 = (Spinner) findViewById(R.id.professionspinner);
        String[] items123 = new String[]{"Not Selected","Arts and entertainment", "Business", "Industrial and manufacturing", "Law Enforcement and Armed Forces", "Science and technology", "Service","Student", "Other"};
        final ArrayAdapter<String> adapter123 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items123);

        dropdown4 = (Spinner) findViewById(R.id.raasispinner);
        String[] items1234 = new String[]{"Not Selected","Aries","Taurus","Gemini","Cancer","Leo","Vigro","Libra","Scorpius","Sagittarius","Capricorn","Aquarius","Pisces"};
        final ArrayAdapter<String> adapter1234 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1234);

        dropdown5 = (Spinner) findViewById(R.id.starspinner);
        String[] items1235 = new String[]{"Not Selected","Ashvini","Bharani","Krittika","Rohini","Mrigashīrsha","Ardra","Punarvasu","Pushya","Āshleshā","Maghā","Pūrva Phalgunī","Uttara Phalgunī","Hasta","Chitra","Swāti","Vishakha","Anuradha","Jyeshtha","Mula","Purva Ashadha","Uttara Ashadha","Sravana","Dhanishta","Shatabhisha","Purva Bhadrapada","Uttara Bhādrapadā","Revat"};
        final ArrayAdapter<String> adapter1235 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1235);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                bloodgroupp = (String) item;
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        dropdown1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                religionn = (String) item;
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        dropdown2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                castee = (String) item;
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        dropdown3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                professionn = (String) item;
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        dropdown4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                raasii = (String) item;
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        dropdown5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                starr = (String) item;
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });




        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Courgette-Regular.ttf");
        txt = (TextView) findViewById(R.id.toolbar_subtitle) ;
        txt.setTypeface(type);



        if (checkPermissionLOCATION(this)) {
                   }
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.setVisibility(View.GONE);


        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        famuidd = prefs.getString("famuid", null);
        referrer = prefs.getString("referrer", null);
        post = prefs.getString("post", null);
        father = prefs.getString("father", null);
        mother = prefs.getString("mother", null);
        fatheruidname = prefs.getString("fathername", null);
        motheruidname = prefs.getString("mothername", null);

        mswipe = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        ssssss =(TextView)findViewById(R.id.cscs);
        ssssss.setVisibility(View.GONE);
        mswipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        logout = (Button) findViewById(R.id.logout);
        logout.setVisibility(View.GONE);
        like = (ImageView) findViewById(R.id.likes);
        comments = (ImageView) findViewById(R.id.comments);
        postrecycler = (RecyclerView) findViewById(R.id.postrecycler);
        postrecycler.setHasFixedSize(true);
        postrecycler.setLayoutManager(new LinearLayoutManager(Main2Activity.this));
        editname = (EditText) findViewById(R.id.editname);
        editdob = (TextView) findViewById(R.id.editdob);
        editgender = (EditText) findViewById(R.id.editgender);
        editemail = (TextView) findViewById(R.id.editemail);
        editprofile = (Button) findViewById(R.id.editprofile);
        profileimage = (ImageView) findViewById(R.id.profileimage);
        bar = (ProgressBar) findViewById(R.id.empty_progress_bar);
        bar.setVisibility(View.GONE);
        editname.setVisibility(View.GONE);
        editdob.setVisibility(View.GONE);
        editemail.setVisibility(View.GONE);
        profileimage.setVisibility(View.GONE);
        editprofile.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                {
                    uid = user.getUid();

                    databaseref = FirebaseDatabase.getInstance().getReference();
                    databaseref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild("profile/"+uid) && dataSnapshot.hasChild("profile/"+uid + "/blood") && dataSnapshot.hasChild("profile/"+uid + "/birthtime") && dataSnapshot.hasChild("profile/"+uid + "/year") && dataSnapshot.hasChild("profile/"+uid + "/month") && dataSnapshot.hasChild("profile/"+uid + "/day") && dataSnapshot.hasChild("profile/"+uid + "/pic") && dataSnapshot.hasChild("profile/"+uid + "/mail") && dataSnapshot.hasChild("profile/"+uid + "/name") && dataSnapshot.hasChild("profile/"+uid + "/famuid") && dataSnapshot.hasChild("profile/"+uid + "/dob") && dataSnapshot.hasChild("profile/"+uid + "/mother") && dataSnapshot.hasChild("profile/"+uid + "/father")  ) {
                                DatabaseReference databaseref123;
                                databaseref123 = FirebaseDatabase.getInstance().getReference();
                                databaseref123.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot){
                                        String blood = dataSnapshot.child("profile/"+uid+"/blood").getValue().toString();
                                        String religion = dataSnapshot.child("profile/"+uid+"/religion").getValue().toString();
                                        String caste = dataSnapshot.child("profile/"+uid+"/caste").getValue().toString();
                                        String raasi = dataSnapshot.child("profile/"+uid+"/raasi").getValue().toString();
                                        String star = dataSnapshot.child("profile/"+uid+"/star").getValue().toString();
                                        String profession = dataSnapshot.child("profile/"+uid+"/profession").getValue().toString();
                                        String gender = dataSnapshot.child("profile/"+uid+"/gender").getValue().toString();
                                        String martial = dataSnapshot.child("profile/"+uid+"/martial").getValue().toString();

                                        if(gender.equals("Male"))
                                        {
                                            ((RadioButton)genderradio.getChildAt(0)).setChecked(true);
                                        }
                                        else
                                        {
                                            ((RadioButton)genderradio.getChildAt(1)).setChecked(true);
                                        }

                                        if(martial.equals("Single"))
                                        {
                                            ((RadioButton)martialradio.getChildAt(0)).setChecked(true);
                                        }
                                        else
                                        {
                                            ((RadioButton)martialradio.getChildAt(1)).setChecked(true);
                                        }

                                        dropdown.setAdapter(adapter);
                                        int spinnerPosition = adapter.getPosition(blood);
                                        dropdown.setSelection(spinnerPosition);

                                        dropdown1.setAdapter(adapter1);
                                        int spinnerPosition1 = adapter1.getPosition(religion);
                                        dropdown1.setSelection(spinnerPosition1);

                                        dropdown2.setAdapter(adapter12);
                                        int spinnerPosition12 = adapter12.getPosition(caste);
                                        dropdown2.setSelection(spinnerPosition12);

                                        dropdown3.setAdapter(adapter123);
                                        int spinnerPosition123 = adapter123.getPosition(profession);
                                        dropdown3.setSelection(spinnerPosition123);

                                        dropdown4.setAdapter(adapter1234);
                                        int spinnerPosition1234 = adapter1234.getPosition(raasi);
                                        dropdown4.setSelection(spinnerPosition1234);

                                        dropdown5.setAdapter(adapter1235);
                                        int spinnerPosition1235 = adapter1235.getPosition(star);
                                        dropdown5.setSelection(spinnerPosition1235);


                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });

                                if( dataSnapshot.hasChild(famuidd+"/profile/"+uid) && dataSnapshot.hasChild(famuidd+"/profile/"+uid+"/referrer") && dataSnapshot.hasChild(famuidd+"/profile/"+uid+"/name") && dataSnapshot.hasChild(famuidd+"/profile/"+uid+"/pic") && dataSnapshot.hasChild(famuidd+"/profile/"+uid+"/uid") && dataSnapshot.hasChild(famuidd+"/profile/"+uid+"/chataccess"))
                                {
                                    DatabaseReference databaseref1233;
                                    databaseref1233 = FirebaseDatabase.getInstance().getReference(famuidd+"/profile/"+uid);
                                    databaseref1233.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot){
                                            access = dataSnapshot.child("chataccess") .getValue().toString();
                                            profileimagee45.setVisibility(View.VISIBLE);

                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });
                                }
                                else
                                {
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference databaseReference = database.getReference();
                                    databaseReference.child(famuidd+"/profile/"+uid+"/uid").setValue(uid);
                                    databaseReference.child(famuidd+"/profile/"+uid+"/pic").setValue("https://firebasestorage.googleapis.com/v0/b/family-connect-2087f.appspot.com/o/dummy.jpg?alt=media&token=b80ccf22-2057-4061-aa47-81120b026114");
                                    databaseReference.child(famuidd+"/family/"+uid+"/pic").setValue("https://firebasestorage.googleapis.com/v0/b/family-connect-2087f.appspot.com/o/dummy.jpg?alt=media&token=b80ccf22-2057-4061-aa47-81120b026114");
                                    databaseReference.child(famuidd+"/profile/"+uid+"/name").setValue("");

                                    databaseReference.child(famuidd+"/profile/"+uid+"/chataccess").setValue("no");
                                }
                            }
                            else {
                                
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference databaseReference = database.getReference();
                                if(post.equals("new"))
                                {
                                    databaseReference.child("profile/"+uid+"/father").setValue("");
                                    databaseReference.child("profile/"+uid+"/mother").setValue("");
                                }
                                if(post.equals("child"))
                                {
                                    databaseReference.child("profile/"+uid+"/child/child1").setValue(referrer);
                                    databaseReference.child("profile/"+uid+"/father").setValue("");
                                    databaseReference.child("profile/"+uid+"/mother").setValue("");
                                }
                                if (post.equals("Male"))
                                {
                                    databaseReference.child("profile/"+uid+"/father").setValue(referrer);
                                    databaseReference.child(famuidd+"/family/"+uid+"/parent/1").setValue(referrer);
                                    databaseReference.child("profile/"+uid+"/mother").setValue("");
                                }
                                if (post.equals("Female"))
                                {
                                    databaseReference.child(famuidd+"/family/"+uid+"/parent/2").setValue(referrer);
                                    databaseReference.child("profile/"+uid+"/mother").setValue(referrer);
                                    databaseReference.child("profile/"+uid+"/father").setValue("");
                                }
                                if(post.equals("sibling"))
                                {
                                    if (father.equals("null"))
                                    {
                                        if (mother.equals("null"))
                                        {
                                            databaseReference.child("profile/"+uid+"/father").setValue("");
                                            databaseReference.child("profile/"+uid+"/mother").setValue("");
                                        }
                                        else
                                        {
                                            databaseReference.child("profile/"+uid+"/father").setValue("");
                                            databaseReference.child("profile/"+uid+"/mother").setValue(motheruidname);
                                            databaseReference.child(famuidd+"/family/"+uid+"/parent/2").setValue(mother);
                                        }
                                    }
                                    else
                                    {
                                    }
                                    if (mother.equals("null"))
                                    {
                                        if (father.equals("null"))
                                        {
                                            databaseReference.child("profile/"+uid+"/father").setValue("");
                                            databaseReference.child("profile/"+uid+"/mother").setValue("");
                                        }
                                        else
                                        {
                                            databaseReference.child("profile/"+uid+"/father").setValue(fatheruidname);
                                            databaseReference.child("profile/"+uid+"/mother").setValue("");
                                            databaseReference.child(famuidd+"/family/"+uid+"/parent/1").setValue(father);
                                        }
                                    }
                                    else
                                    {

                                    }
                                    if (father.equals("null") && mother.equals("null"))
                                    {
                                        databaseReference.child("profile/"+uid+"/father").setValue("");
                                        databaseReference.child("profile/"+uid+"/mother").setValue("");
                                    }
                                    else
                                    {
                                        databaseReference.child("profile/"+uid+"/father").setValue(fatheruidname);
                                        databaseReference.child("profile/"+uid+"/mother").setValue(motheruidname);
                                        databaseReference.child(famuidd+"/family/"+uid+"/parent/1").setValue(father);
                                        databaseReference.child(famuidd+"/family/"+uid+"/parent/2").setValue(mother);
                                    }
                                }
                                databaseReference.child("profile/"+uid+"/mail").setValue(usermail);
                                databaseReference.child("profile/"+uid+"/uid").setValue(uid);
                                databaseReference.child(famuidd+"/profile/"+uid+"/referrer").setValue(referrer);
                                databaseReference.child(famuidd+"/profile/"+uid+"/chataccess").setValue("no");
                                databaseReference.child("profile/"+uid+"/famuid").setValue(famuidd);
                                databaseReference.child("profile/"+uid+"/name").setValue("");
                                databaseReference.child("profile/"+uid+"/dob").setValue("");
                                databaseReference.child("profile/"+uid+"/gender").setValue("Male");
                                databaseReference.child("profile/"+uid+"/height").setValue("");
                                databaseReference.child("profile/"+uid+"/weight").setValue("");
                                databaseReference.child("profile/"+uid+"/birthtime").setValue("");
                                databaseReference.child("profile/"+uid+"/blood").setValue("Not Selected");
                                databaseReference.child("profile/"+uid+"/martial").setValue("Single");
                                databaseReference.child("profile/"+uid+"/religion").setValue("Not Selected");
                                databaseReference.child("profile/"+uid+"/raasi").setValue("Not Selected");
                                databaseReference.child("profile/"+uid+"/star").setValue("Not Selected");
                                databaseReference.child("profile/"+uid+"/profession").setValue("Not Selected");
                                databaseReference.child("profile/"+uid+"/caste").setValue("Not Selected");
                                databaseReference.child(famuidd+"/profile/"+uid+"/chataccess").setValue("no");
                                databaseReference.child("profile/"+uid+"/year").setValue(0);
                                databaseReference.child("profile/"+uid+"/month").setValue(0);
                                databaseReference.child("profile/"+uid+"/day").setValue(0);
                                databaseReference.child("profile/"+uid+"/pic").setValue("https://firebasestorage.googleapis.com/v0/b/family-connect-2087f.appspot.com/o/dummy.jpg?alt=media&token=b80ccf22-2057-4061-aa47-81120b026114");
                                databaseReference.child(famuidd+"/family/"+uid+"/pic").setValue("https://firebasestorage.googleapis.com/v0/b/family-connect-2087f.appspot.com/o/dummy.jpg?alt=media&token=b80ccf22-2057-4061-aa47-81120b026114");


                                DatabaseReference databaseref123;
                                databaseref123 = FirebaseDatabase.getInstance().getReference("profile/"+uid);
                                databaseref123.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot){
                                        String blood = dataSnapshot.child("blood").getValue().toString();
                                        String religion = dataSnapshot.child("religion").getValue().toString();
                                        String caste = dataSnapshot.child("caste").getValue().toString();
                                        String raasi = dataSnapshot.child("raasi").getValue().toString();
                                        String star = dataSnapshot.child("star").getValue().toString();
                                        String profession = dataSnapshot.child("profession").getValue().toString();
                                        String gender = dataSnapshot.child("gender").getValue().toString();
                                        String martial = dataSnapshot.child("martial").getValue().toString();
                                        if(gender.equals("Male"))
                                        {
                                            ((RadioButton)genderradio.getChildAt(0)).setChecked(true);
                                        }
                                        else
                                        {
                                            ((RadioButton)genderradio.getChildAt(1)).setChecked(true);
                                        }

                                        if(martial.equals("Single"))
                                        {
                                            ((RadioButton)martialradio.getChildAt(0)).setChecked(true);
                                        }
                                        else
                                        {
                                            ((RadioButton)martialradio.getChildAt(1)).setChecked(true);
                                        }

                                        dropdown.setAdapter(adapter);
                                        int spinnerPosition = adapter.getPosition(blood);
                                        dropdown.setSelection(spinnerPosition);

                                        dropdown1.setAdapter(adapter1);
                                        int spinnerPosition1 = adapter1.getPosition(religion);
                                        dropdown1.setSelection(spinnerPosition1);

                                        dropdown2.setAdapter(adapter12);
                                        int spinnerPosition12 = adapter12.getPosition(caste);
                                        dropdown2.setSelection(spinnerPosition12);

                                        dropdown3.setAdapter(adapter123);
                                        int spinnerPosition123 = adapter123.getPosition(profession);
                                        dropdown3.setSelection(spinnerPosition123);

                                        dropdown4.setAdapter(adapter1234);
                                        int spinnerPosition1234 = adapter1234.getPosition(raasi);
                                        dropdown4.setSelection(spinnerPosition1234);

                                        dropdown5.setAdapter(adapter1235);
                                        int spinnerPosition1235 = adapter1235.getPosition(star);
                                        dropdown5.setSelection(spinnerPosition1235);

                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                                if(dataSnapshot.hasChild(famuidd+"/profile/"+uid) && dataSnapshot.hasChild(famuidd+"/profile/"+uid+"/chataccess") && dataSnapshot.hasChild(famuidd+"/profile/"+uid+"/name") && dataSnapshot.hasChild(famuidd+"/profile/"+uid+"/pic") && dataSnapshot.hasChild(famuidd+"/profile/"+uid+"/uid"))
                                {
                                    DatabaseReference databaseref1233;
                                    databaseref1233 = FirebaseDatabase.getInstance().getReference(famuidd+"/profile/"+uid);
                                    databaseref1233.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot){
                                            access = dataSnapshot.child("chataccess") .getValue().toString();
                                            profileimagee45.setVisibility(View.VISIBLE);

                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });
                                }
                                else
                                {
                                    database = FirebaseDatabase.getInstance();
                                    databaseReference = database.getReference();
                                    databaseReference.child(famuidd+"/profile/"+uid+"/uid").setValue(uid);
                                    databaseReference.child(famuidd+"/family/"+uid+"/pic").setValue("https://firebasestorage.googleapis.com/v0/b/family-connect-2087f.appspot.com/o/dummy.jpg?alt=media&token=b80ccf22-2057-4061-aa47-81120b026114");
                                    databaseReference.child(famuidd+"/profile/"+uid+"/pic").setValue("https://firebasestorage.googleapis.com/v0/b/family-connect-2087f.appspot.com/o/dummy.jpg?alt=media&token=b80ccf22-2057-4061-aa47-81120b026114");
                                    databaseReference.child(famuidd+"/profile/"+uid+"/name").setValue("");
                                    databaseReference.child(famuidd+"/profile/"+uid+"/chataccess").setValue("no");
                                }
                            }


                            DatabaseReference newref = FirebaseDatabase.getInstance().getReference().child("profile/"+uid);
                            newref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (
                                            ((dataSnapshot.child("name").getValue().toString()).equals(""))
                                                    ||
                                                    ((dataSnapshot.child("height").getValue().toString()).equals(""))
                                        ||
                                                    ((dataSnapshot.child("weight").getValue().toString()).equals(""))
                                        ||
                                                    ((dataSnapshot.child("birthtime").getValue().toString()).equals(""))
                                                    ||
                                                    ((dataSnapshot.child("dob").getValue().toString()).equals(""))
                                            )
                                    {
                                        RelativeLayout r;
                                        naaam.setVisibility(View.VISIBLE);

                                        textView12.setVisibility(View.GONE);
                                        textView123.setVisibility(View.GONE);
                                        vieemsdypodst.setVisibility(View.GONE);
                                        fathernamee.setVisibility(View.GONE);
                                        Button vieemypost=(Button)findViewById(R.id.vieewpost);
                                        vieemypost.setVisibility(View.GONE);
                                        TextView vieemdypost=(TextView) findViewById(R.id.textView10);
                                        vieemdypost.setVisibility(View.GONE);
                                        ImageView vieemsdypodst=(ImageView) findViewById(R.id.profileimage);
                                        vieemsdypodst.setVisibility(View.GONE);
                                        mothername.setVisibility(View.GONE );
                                        textView10.setVisibility(View.GONE);
                                        req.setVisibility(View.GONE);
                                        RelativeLayout des=(RelativeLayout)findViewById(R.id.yoyo);
                                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                                RelativeLayout.LayoutParams.WRAP_CONTENT,
                                                RelativeLayout.LayoutParams.WRAP_CONTENT
                                        );
                                        params.setMargins(0,-285 ,0,0);
                                        des.setLayoutParams(params);


                                        r = (RelativeLayout) findViewById(R.id.bottomrelative);
                                        r.setVisibility(View.GONE);

                                        ss.setVisibility(View.VISIBLE);

                                        Toast.makeText(Main2Activity.this, "Please fill in your details to get started!", Toast.LENGTH_SHORT).show();
                                        String dob = dataSnapshot.child("dob").getValue().toString();
                                        editdob.setText(dob);
                                        String name = dataSnapshot.child("name").getValue().toString();
                                        editname.setText(name);
                                        String father = dataSnapshot.child("father").getValue().toString();
                                        fathernamee.setText(father);
                                        String mother = dataSnapshot.child("mother").getValue().toString();
                                        mothername.setText(mother);
                                        String heightq = dataSnapshot.child("height").getValue().toString();
                                        height.setText(heightq);
                                        String weightq = dataSnapshot.child("weight").getValue().toString();
                                        weight.setText(weightq);
                                        String time1 = dataSnapshot.child("birthtime").getValue().toString();
                                        Toast.makeText(Main2Activity.this, time1, Toast.LENGTH_SHORT).show();
                                        year = Integer.parseInt(dataSnapshot.child("year").getValue().toString());
                                        month = Integer.parseInt(dataSnapshot.child("month").getValue().toString());
                                        day = Integer.parseInt(dataSnapshot.child("day").getValue().toString());
                                        c.set(year,month,day);
                                        String pic = dataSnapshot.child("pic").getValue().toString();
                                        Picasso.with(getApplicationContext())
                                                .load(pic)
                                                .into(profileimage);


                                        bar.setVisibility(View.GONE);
                                        scrollView.setVisibility(View.VISIBLE);
                                        mWebView.setVisibility(View.GONE);
                                        postrecycler.setAlpha(0);
                                        linearLayout.setVisibility(View.VISIBLE);
                                        logout.setVisibility(View.VISIBLE);
                                        editname.setVisibility(View.VISIBLE);
                                        editdob.setVisibility(View.VISIBLE);
                                        height.setVisibility(View.VISIBLE);
                                        weight.setVisibility(View.VISIBLE);

                                        editemail.setVisibility(View.VISIBLE);

                                        editprofile.setVisibility(View.VISIBLE);
                                        children.setVisibility(View.INVISIBLE);
                                        gen.setVisibility(View.INVISIBLE);
                                        mar.setVisibility(View.INVISIBLE);

                                        relig.setVisibility(View.VISIBLE);
                                        cas.setVisibility(View.VISIBLE);
                                        raa.setVisibility(View.VISIBLE);
                                        sta.setVisibility(View.VISIBLE);
                                        profe.setVisibility(View.VISIBLE);
                                        fathernamee.setVisibility(View.VISIBLE);





                                        mothername.setVisibility(View.VISIBLE);
                                        genderradio.setVisibility(View.VISIBLE);
                                        martialradio.setVisibility(View.VISIBLE);
                                        bloodspinner.setVisibility(View.VISIBLE);
                                        religionspinner.setVisibility(View.VISIBLE);
                                        castespinner.setVisibility(View.VISIBLE);
                                        raasispinner.setVisibility(View.VISIBLE);
                                        starspinner.setVisibility(View.VISIBLE);
                                        professionspinner.setVisibility(View.VISIBLE);
                                        adddd.setVisibility(View.VISIBLE);
                                    }
                                    else
                                    {
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });


                            numchilds = 0;
                            DatabaseReference usersReff = FirebaseDatabase.getInstance().getReference(famuidd+"/profile");
                            usersReff.orderByChild("name").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                        String name = postSnapshot.child("referrer").getValue().toString();
                                        if (name.equals(uid))
                                        {
                                            numchilds++;
                                        }
                                    }

                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });


                            DatabaseReference databaseref1234;
                            databaseref1234 = FirebaseDatabase.getInstance().getReference(famuidd);
                            databaseref1234.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot){
                                    String verification = dataSnapshot.child("status").getValue().toString();

                                    if (verification.equals("No"))
                                    {
                                        Intent intent = new Intent(getBaseContext(), verify.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        //Continue
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                        }

                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(Main2Activity.this, "Cannot Connect with Database!", LENGTH_SHORT).show();
                        }
                    });


                    usermail = user.getEmail();
                    DatabaseReference mRefreg;
                    mRefreg = FirebaseDatabase.getInstance().getReference("profile/"+uid+"/child");
                    mRefreg.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final int count = (int) dataSnapshot.getChildrenCount();
                            looper = count;
                            j = count+1;
                            if (count > 1)
                            {
                                    DatabaseReference databaserefab;
                                    databaserefab = FirebaseDatabase.getInstance().getReference("profile/" + uid + "/child");
                                    databaserefab.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            do {
                                            String p = "child"+loopera;
                                            String qw = dataSnapshot.child(p).getValue().toString();
                                            EditText temp;
                                            temp = new EditText(Main2Activity.this);
                                            temp.setText(qw);
                                            linearLayout.addView(temp);
                                            linearLayout.setVisibility(View.GONE);
                                            loopera++;
                                            } while (loopera <= count);
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
                else
                {
                    Toast.makeText(Main2Activity.this, "Try again!", Toast.LENGTH_SHORT).show();
                }
            }
        };


        databaseref = FirebaseDatabase.getInstance().getReference();
        databaseref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){

                myref = FirebaseDatabase.getInstance().getReference().child(famuidd+"/posts");
                    FirebaseRecyclerAdapter<Blog, BlogViewHolder> adapter = new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(
                            Blog.class,
                            R.layout.individual_row,
                            BlogViewHolder.class,
                            myref
                    ) {
                        @Override
                        protected void populateViewHolder(final BlogViewHolder viewHolder, final Blog model, int position)
                        {
                            final String t = model.getpost();
                            DatabaseReference newref = FirebaseDatabase.getInstance().getReference().child(famuidd+"/likes/"+t);
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

                            DatabaseReference newreff = FirebaseDatabase.getInstance().getReference().child(famuidd+"/comments/"+t);
                            newreff.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    coun = dataSnapshot.getChildrenCount();
                                    count = coun.toString();
                                    viewHolder.setTimestamp(model.getTimestamp());
                                    viewHolder.setcommentscount(count);
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

                            viewHolder.setCaption("Caption : "+model.getCaption());
                            viewHolder.setImage(model.getImage(),model.getType());

                            viewHolder.getviewwww().findViewById(R.id.videoView).setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {

                                    viewHolder.video.start();
                                    return false;
                                }
                            });

                            viewHolder.getviewww().findViewById(R.id.comments).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Intent intent = new Intent(getBaseContext(), comment.class);
                                    Bundle extras = new Bundle();
                                    extras.putString("famuid",famuidd);
                                    extras.putString("postid",t);
                                    intent.putExtras(extras);
                                    startActivity(intent);
                                }
                            });

                            viewHolder.getviewww().findViewById(R.id.likescount).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Intent intent = new Intent(getBaseContext(), likeslist.class);
                                    Bundle extras = new Bundle();
                                    extras.putString("famuid",famuidd);
                                    extras.putString("postid",t);
                                    intent.putExtras(extras);
                                    startActivity(intent);
                                }
                            });

                            viewHolder.getviewww().findViewById(R.id.likeprofile).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                   DatabaseReference databaseref = FirebaseDatabase.getInstance().getReference(famuidd+"/posts/"+t);
                                    databaseref.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot){
                                            String uiddddd = dataSnapshot.child("uid").getValue().toString();
                                            Intent intent = new Intent(getBaseContext(), viewprofile.class);
                                            Bundle extras = new Bundle();
                                            extras.putString("uid",uiddddd);
                                            intent.putExtras(extras);
                                            startActivity(intent);
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });
                                }
                            });


                            viewHolder.getviewww().findViewById(R.id.name).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    DatabaseReference databaseref = FirebaseDatabase.getInstance().getReference(famuidd+"/posts/"+t);
                                    databaseref.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot){
                                            String uiddddd = dataSnapshot.child("uid").getValue().toString();
                                            Intent intent = new Intent(getBaseContext(), viewprofile.class);
                                            Bundle extras = new Bundle();
                                            extras.putString("uid",uiddddd);
                                            intent.putExtras(extras);
                                            startActivity(intent);
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });
                                }
                            });



                            viewHolder.getviewww().findViewById(R.id.likes).setOnTouchListener(new View.OnTouchListener()
                            {
                                @Override
                                public boolean onTouch(View view, MotionEvent event)
                                {
                                    DatabaseReference newref = FirebaseDatabase.getInstance().getReference().child(famuidd+"/likes/"+t);
                                    newref.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.hasChild(uid)) {
                                                viewHolder.setlikes();
                                                identity=1;
                                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                DatabaseReference databaseReference = database.getReference();
                                                if(identity==1) {
                                                    viewHolder.setnolikes();
                                                    databaseReference.child(famuidd + "/likes/" + t + "/" + uid).removeValue();
                                                    identity = 0;
                                                }
                                                else
                                                {
                                                    viewHolder.setlikes();
                                                    databaseReference.child(famuidd+"/likes/"+t+"/"+uid).setValue("true");
                                                    identity = 1;
                                                }
                                            }
                                            else
                                            {
                                                viewHolder.setnolikes();
                                                identity=0;
                                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                DatabaseReference databaseReference = database.getReference();
                                                if(identity==1) {
                                                    viewHolder.setnolikes();
                                                    databaseReference.child(famuidd + "/likes/" + t + "/" + uid).removeValue();
                                                    identity = 0;
                                                }
                                                else
                                                {
                                                    viewHolder.setlikes();
                                                    databaseReference.child(famuidd+"/likes/"+t+"/"+uid).setValue("true");
                                                    identity = 1;
                                                }
                                            }
                                            coun = dataSnapshot.getChildrenCount();
                                            count = coun.toString();
                                            viewHolder.setlikescount(count);
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });
                                    return false;
                                }
                            });
                        }
                    };
                    postrecycler.setAdapter(adapter);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Main2Activity.this,"Cannot Connect with Database!", LENGTH_SHORT).show();
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
    }

    public void logout(View view) {
        ProgressBar bar = (ProgressBar) findViewById(R.id.empty_progress_bar);
        bar.setVisibility(View.VISIBLE);
        mAuth.signOut();
        Toast.makeText(Main2Activity.this,"Logged Out!",Toast.LENGTH_LONG).show();
        startActivity(new Intent(Main2Activity.this,MainActivity.class));
        finish();
    }

    public void hi(View view) {

        if (access.equals("yes"))
        {
            Intent intent = new Intent(getBaseContext(), newchat.class);
            intent.putExtra("famuid", famuidd);
            startActivity(intent);
        }
        else
        {
            if (numchilds<5)
            {
                String ss= String.valueOf(numchilds);
                Intent intent = new Intent(getBaseContext(), ReferralStatus.class);
                intent.putExtra("numchild", ss);
                intent.putExtra("famuid", famuidd);
                intent.putExtra("uid", uid);
                startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(getBaseContext(), newchat.class);
                intent.putExtra("famuid", famuidd);
                startActivity(intent);
                //Continue
            }
        }
    }

    public void addchild(View view) {
        looper++;
        EditText temp;
        String ab[] = new String[10];
        Map<String, Integer> idsMap = new Map<String, Integer>() {
            @Override
            public int size() {
                return 0;
            }
            @Override
            public boolean isEmpty() {
                return false;
            }
            @Override
            public boolean containsKey(Object key) {
                return false;
            }
            @Override
            public boolean containsValue(Object value) {
                return false;
            }
            @Override
            public Integer get(Object key) {
                return null;
            }
            @Override
            public Integer put(String key, Integer value) {
                return null;
            }
            @Override
            public Integer remove(Object key) {
                return null;
            }
            @Override
            public void putAll(@NonNull Map<? extends String, ? extends Integer> m) {
            }
            @Override
            public void clear() {
            }
            @NonNull
            @Override
            public Set<String> keySet() {
                return null;
            }
            @NonNull
            @Override
            public Collection<Integer> values() {
                return null;
            }
            @NonNull
            @Override
            public Set<Entry<String, Integer>> entrySet() {
                return null;
            }
        };
            temp = new EditText(Main2Activity.this);
        temp.setText("Child"+looper);
        String tname = "task" + Integer.toString(looper);
        ab[looper] = temp.getText().toString();
        linearLayout.addView(temp);
        tv[looper] = temp;
        tv[looper].setId(looper);
        idsMap.put(tname, looper);
    }

    public void fatheernamee(View view) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {

            Intent intent = new Intent(this, selectfathername.class);
            intent.putExtra("famuid", famuidd);
            intent.putExtra("myuid", user.getUid());
            intent.putExtra("message", "Father");
            startActivity(intent);
        }
    }

    public void motheernamee(View view) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(this, Selectmothername.class);
            intent.putExtra("famuid", famuidd);
            intent.putExtra("myuid", user.getUid());
            intent.putExtra("message", "Mother");
            startActivity(intent);
        }
    }

    public void viewalllpost(View view) {

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {

            Intent intent = new Intent(getApplicationContext(), viewprofile.class);
            intent.putExtra("uid", user.getUid());
            startActivity(intent);



        }

    }

    public void selectdate(View view) {
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        showDialog(21);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 21:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener, year, month,day);
        }
        return null;
    }


    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;

            // Show selected date
            editdob.setText(new StringBuilder()
                    .append(day)
                    .append("/")
                    .append(month + 1)
                    .append("/")
                    .append(year)
                    .append(" "));
        }
    };



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
                profileimage.setImageBitmap(bitmap);
                bar.setVisibility(View.GONE);
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);
        if (requestcode == GALLERY_INTENT && resultcode == RESULT_OK) {
            Uri uri = data.getData();

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                Bitmap a = Bitmap.createScaledBitmap(bitmap,400,400,false);
                // Bitmap a = resize(bitmap,400,362);
                uri = getImageUri(this,a);
                String name = "file:///"+compressImage(uri.toString());
                final StorageReference filepath = storageReference.child("Profile/"+uid);
                filepath.putFile(Uri.parse(name)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                        ProgressBar bar = (ProgressBar) findViewById(R.id.empty_progress_bar);
                        bar.setVisibility(View.VISIBLE);
                        Toast.makeText(Main2Activity.this,"Done Uploading!",Toast.LENGTH_LONG).show();
                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                        storageRef.child("Profile/"+uid).getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(final Uri urilll) {


                                        DatabaseReference databaserefff = FirebaseDatabase.getInstance().getReference("profile/"+uid);
                                        databaserefff.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                String link = urilll.toString();
                                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                DatabaseReference databaseReference = database.getReference();
                                                databaseReference.child("profile/"+uid+"/pic").setValue(link);
                                                databaseReference.child(famuidd+"/family/"+uid+"/pic").setValue(link);
                                                databaseReference.child(famuidd+"/profile/"+uid+"/pic").setValue(link);
                                                getBitmap loadBitmap = new getBitmap();
                                                loadBitmap.execute(urilll.toString());
                                            }
                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                            }
                                        });
                                    }
                                });

                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void edit(View view)
    {

        ss.setVisibility(View.GONE);
        heightval = height.getText().toString();
        weightval = weight.getText().toString();
        n = editname.getText().toString();
        d = editdob.getText().toString();
        ab = fathernamee.getText().toString();
        cd = mothername.getText().toString();
        bar.setVisibility(View.VISIBLE);

        if(!TextUtils.isEmpty(n) && !TextUtils.isEmpty(d) && !TextUtils.isEmpty(heightval) && !TextUtils.isEmpty(weightval)  )
        {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference databaseReference = database.getReference();
            databaseReference.child(famuidd+"/profile/"+uid+"/name").setValue(n);
            databaseReference.child("profile/"+uid+"/blood").setValue(bloodgroupp);
            databaseReference.child("profile/"+uid+"/gender").setValue(gendervalue);
            databaseReference.child("profile/"+uid+"/martial").setValue(martialstatus);
            databaseReference.child(famuidd+"/family/"+uid+"/name").setValue(n);
            databaseReference.child("profile/"+uid+"/mail").setValue(usermail);
            databaseReference.child("profile/"+uid+"/height").setValue(heightval);
            databaseReference.child("profile/"+uid+"/weight").setValue(weightval);
            databaseReference.child("profile/"+uid+"/name").setValue(n);
            databaseReference.child("profile/"+uid+"/year").setValue(year);
            databaseReference.child("profile/"+uid+"/month").setValue(month);
            databaseReference.child("profile/"+uid+"/day").setValue(day);
            databaseReference.child("profile/"+uid+"/dob").setValue(d);
            databaseReference.child("profile/"+uid+"/religion").setValue(religionn);
            databaseReference.child("profile/"+uid+"/caste").setValue(castee);
            databaseReference.child("profile/"+uid+"/raasi").setValue(raasii);
            databaseReference.child("profile/"+uid+"/profession").setValue(professionn);
            databaseReference.child("profile/"+uid+"/star").setValue(starr);

            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference(famuidd+"/profile");
            usersRef.orderByChild("name").startAt(ab).endAt(ab).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (final DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        final User user = postSnapshot.getValue(User.class);
                        fat = user.getUid();
                        databaseReference.child(famuidd+"/family/"+uid+"/parent/1").setValue(fat);
                    }

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            DatabaseReference usersReff = FirebaseDatabase.getInstance().getReference(famuidd+"/profile");
            usersReff.orderByChild("name").startAt(cd).endAt(cd).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (final DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        final User user = postSnapshot.getValue(User.class);
                        mot = user.getUid();
                        databaseReference.child(famuidd+"/family/"+uid+"/parent/2").setValue(mot);
                    }

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });


            databaseReference.child("profile/"+uid+"/father").setValue(ab);
            databaseReference.child("profile/"+uid+"/mother").setValue(cd);
            int b = j-1;
            if(b==looper)
            {
            }
            else
            {
                do {
                    final String a = tv[j].getText().toString();

                    DatabaseReference usersRefff = FirebaseDatabase.getInstance().getReference(famuidd+"/profile");
                    usersRefff.orderByChild("name").startAt(a).endAt(a).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (final DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                final User user = postSnapshot.getValue(User.class);
                                childuid = user.getUid();
                                databaseReference.child(famuidd+"/family/"+uid+"/child/"+j).setValue(childuid);
                                databaseReference.child("profile/"+uid+"/child/"+"child"+j).setValue(a);
                                j++;
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                } while (j<=looper);
            }

            bar.setVisibility(View.GONE);
        }
        else
        {
            Toast.makeText(this,"Failed!",Toast.LENGTH_SHORT).show();
            bar.setVisibility(View.GONE);
        }

        finish();
        startActivity(getIntent());
    }
    public void proimg(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        if (checkPermissionREAD_EXTERNAL_STORAGE(this)) {
            startActivityForResult(intent, GALLERY_INTENT);
        }
    }
    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {               imgRatio = maxHeight / actualHeight;                actualWidth = (int) (imgRatio * actualWidth);               actualHeight = (int) maxHeight;             } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight,Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            } else if (orientation == 3) {
                matrix.postRotate(180);
            } else if (orientation == 8) {
                matrix.postRotate(270);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return filename;

    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "FamilyConnect/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;
    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;      }       final float totalPixels = width * height;       final float totalReqPixelsCap = reqWidth * reqHeight * 2;       while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }





    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        moveTaskToBack(true);
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }


    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External Storage",context,Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { android.Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
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


    public boolean checkPermissionLOCATION(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    showDialogg("External Storage",context,Manifest.permission.ACCESS_COARSE_LOCATION);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
                                    MY_PERMISSIONS_REQUEST_LOCATION);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialogg(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_LOCATION);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    public class WebViewJavaScriptInterface{

        private Context context;

        /*
         * Need a reference to the context in order to sent a post message
         */
        public WebViewJavaScriptInterface(Context context){
            this.context = context;
        }

        /*
         * This method can be called from Android. @JavascriptInterface
         * required after SDK version 17.
         */
        @JavascriptInterface
        public void makeToast(String message)
        {
            Intent intent = new Intent(context, viewprofile.class);
            intent.putExtra("uid",message);
            startActivity(intent);
        }
    }
}

class BottomNavigationViewHelper {
    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException e) {

        }
    }
}