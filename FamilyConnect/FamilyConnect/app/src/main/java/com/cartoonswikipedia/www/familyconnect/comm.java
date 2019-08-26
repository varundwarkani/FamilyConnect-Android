package com.cartoonswikipedia.www.familyconnect;

/**
 * Created by Chetan on 30/11/17.
 */

public class comm {

    String comment,uid;
    public comm(){
    }

    public comm(String comment, String uid){
        this.comment= comment;
        this.uid = uid;

    }

    public String getuid(){
        return uid;
    }
    public String getcomment(){
        return comment;
    }
}
