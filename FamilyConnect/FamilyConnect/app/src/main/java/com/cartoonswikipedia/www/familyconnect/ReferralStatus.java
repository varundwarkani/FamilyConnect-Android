package com.cartoonswikipedia.www.familyconnect;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class ReferralStatus extends AppCompatActivity {

    String uid,famuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral_status);
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        }
        String count = getIntent().getStringExtra("numchild");
        uid = getIntent().getStringExtra("uid");
        famuid = getIntent().getStringExtra("famuid");
        TextView s=(TextView)findViewById(R.id.countvall);
        s.setText(count+" / 5");
        TextView sp=(TextView)findViewById(R.id.textView201);
        int co=Integer.parseInt(count);
        co=5-co;
       // sp.setText("Invite " + String.valueOf(co)  +" more members in your Family Connect to unlock the chat system");
      //  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        //    sp.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        // }

    }

    public void upgradee(View view) {
startActivity(new Intent(this,upgradepro.class));
    }

    public void invite(View view) {
        String message = "http://www.varundwarkani.esy.es/?famuid="+famuid+"&referrer="+uid+"&post=new&father=null&mother=null";
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(share, "Invite!"));
    }
}
