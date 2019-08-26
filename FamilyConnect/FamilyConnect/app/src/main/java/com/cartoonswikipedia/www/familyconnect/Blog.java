package com.cartoonswikipedia.www.familyconnect;


public class Blog {
    private String uid,image,caption,post,type,timestamp;
    public Blog() {
    }

    public Blog(String uid, String image, String caption, String post,String type, String timestamp) {
        this.uid = uid;
        this.post = post;
        this.caption = caption;
        this.image = image;
        this.type = type;
        this.timestamp = timestamp;
    }

    public String getpost() {
        return post;
    }
    public String getCaption() {
        return caption;
    }
    public String getImage() {
        return image;
    }
    public String getUid() {
        return uid;
    }
    public String getType() {
        return type;
    }
    public String getTimestamp() {
        return timestamp;
    }
}