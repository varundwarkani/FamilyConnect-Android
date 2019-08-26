package com.cartoonswikipedia.www.familyconnect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class BlogViewHolder extends RecyclerView.ViewHolder{
    View mView;
    TextView captiontext,likescount,nametext,commentcount;
    ImageView imageView,profileimg;
    ImageView like;
    TextView timestamp;
    VideoView video;


    public BlogViewHolder(final View itemView) {
        super(itemView);
        mView = itemView;
        timestamp = (TextView) itemView.findViewById(R.id.timestampppp);
        commentcount = (TextView)itemView.findViewById(R.id.commentcount);
        nametext = (TextView)itemView.findViewById(R.id.name);
        captiontext = (TextView) itemView.findViewById(R.id.caption);
        video = (VideoView) itemView.findViewById(R.id.videoView);
        video.setVisibility(View.GONE);
        imageView=(ImageView)itemView.findViewById(R.id.postimg1);
        imageView.setVisibility(View.GONE);
        likescount = (TextView) itemView.findViewById(R.id.likescount);
        like = (ImageView) itemView.findViewById(R.id.likes);
        profileimg = (ImageView) itemView.findViewById(R.id.likeprofile);
    }

    public void setName(String name) {
        nametext.setText(name);
    }

    public View getviewww() {
        return this.mView;
    }
    public View getviewwww() {
        return this.mView;
    }
    public void setCaption(String caption) {
        captiontext.setText(caption);
    }
    public void setImage(String image,String type) {
        String c = "none";
        if(type.equals("image"))
        {
            if (image.equals(c))
            {
                imageView.setVisibility(View.GONE);
            }
            else {
                imageView.setVisibility(View.VISIBLE);
                BlogViewHolder.getBitmap loadBitmap = new BlogViewHolder.getBitmap();
                loadBitmap.execute(image);
            }
        }
        else
        {
            if (image.equals(c))
            {
                video.setVisibility(View.GONE);
            }
            else {

                video.setVisibility(View.VISIBLE);
                video.setVideoPath(image);
                video.setZOrderOnTop(true);
            }
        }
    }

    public void setprofileimg(String proimg){
            BlogViewHolder.getBitmapp loadBitmap = new BlogViewHolder.getBitmapp();
            loadBitmap.execute(proimg);
    }

    public void setTimestamp(String time) {
        timestamp.setText(time);
    }


    public void setlikes() {
        like.setImageResource(R.drawable.red);
    }
    public void setnolikes() {
        like.setImageResource(R.drawable.white);
    }
    public  void setlikescount(String count)
    {
        likescount.setText(count);
    }

    public  void setcommentscount(String count)
    {
        commentcount.setText(count);
    }

    public View getvieww() {
        return this.mView;
    }

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
                imageView = (ImageView) itemView.findViewById(R.id.postimg1);
                imageView.setImageBitmap(bitmap);
            }
        }
    }
    public class getBitmapp extends AsyncTask<String, Void, Bitmap> {
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
                profileimg = (ImageView) itemView.findViewById(R.id.likeprofile);
                profileimg.setImageBitmap(bitmap);
            }
        }
    }

}
