package com.cartoonswikipedia.www.familyconnect;

/**
 * Created by Chetan on 18/12/17.
 */

public class chatobj {

    String message,chatuid,timestamp;
    public chatobj(){
    }
    public chatobj(String message, String chatuid, String timestamp){
        this.message= message;
        this.chatuid = chatuid;
        this.timestamp = timestamp;

    }
    public String getuid(){
        return chatuid;
    }
    public String getmessage(){
        return message;
    }
    public String gettimestamp(){
        return timestamp;
    }
}
