package com.cartoonswikipedia.www.familyconnect;

import android.widget.ImageView;

/**
 * Created by Chetan on 23/10/17.
 */

class User {

    String name,uid,pic;
    public User(){
    }

    public User(String name, String uid, String pic){
        this.name= name;
        this.uid = uid;
        this.pic = pic;

    }

    public String getUid(){
        return uid;
    }

    public String getPic(){
        return pic;
    }

    public String getname() {
        return name;
    }
}
