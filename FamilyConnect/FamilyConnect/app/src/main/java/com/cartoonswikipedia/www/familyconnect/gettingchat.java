package com.cartoonswikipedia.www.familyconnect;

/**
 * Created by Chetan on 24/12/17.
 */

public class gettingchat {

    String chatuid,timestamp,message;
    public gettingchat(){
    }

    public gettingchat(String chatuid, String timestamp,String message){
        this.chatuid= chatuid;
        this.timestamp = timestamp;
        this.message = message;

    }

    public String getChatuid(){
        return chatuid;
    }
    public String getTimestamp(){
        return timestamp;
    }
    public String getMessage(){
        return message;
    }

}
