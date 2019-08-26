package com.cartoonswikipedia.www.familyconnect;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class post extends AppCompatActivity {

    ImageView postimg;
    VideoView postvideo;
    EditText caption;
    String type;
    Button post;
    Uri uri;
    String uid,famuid,name,imgnonetext="none";
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    StorageReference storageReference;
    private static final int VIDEO_INTENT = 3;
    String timeStamp;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ProgressBar bar = (ProgressBar) findViewById(R.id.empty_progress_bar);
        bar.setVisibility(View.GONE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        timeStamp = sdf.format(new Date());
        postimg = (ImageView) findViewById(R.id.likeprofile);
        postvideo = (VideoView) findViewById(R.id.videoView);
        postvideo.setVisibility(View.GONE);
        postimg.setVisibility(View.GONE);
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        }
        caption = (EditText) findViewById(R.id.caption);
        post = (Button) findViewById(R.id.upload);
        famuid = getIntent().getStringExtra("famuid");

        mAuth = FirebaseAuth.getInstance();
        mAuthListener =
                new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                {
                    uid = user.getUid();
                }
                else
                {

                }
            }
        };
    }
    public void imgclick (View view) {

        Intent pickPhoto = new Intent(Intent.ACTION_GET_CONTENT);
        pickPhoto.setType("*/*");
        String[] mimetypes = {"image/*", "video/*"};
        pickPhoto.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        if (checkPermissionREAD_EXTERNAL_STORAGE(this)) {
            startActivityForResult(pickPhoto,8);
        }




    }

    public void videoclick(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("video/mp4");
        if (checkPermissionREAD_EXTERNAL_STORAGE(this)) {
            startActivityForResult(intent, VIDEO_INTENT);
        }
    }


        @Override
        protected void onActivityResult ( int requestcode, int resultcode, Intent data){

        super.onActivityResult(requestcode, resultcode, data);

            if (requestcode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultcode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    Picasso.with(getApplicationContext())
                            .load(resultUri)
                            .into(postimg);

                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                        Bitmap a = Bitmap.createScaledBitmap(bitmap,400,400,false);
                        // Bitmap a = resize(bitmap,400,362);
                        uri = getImageUri(this,a);
                        postimg.setImageBitmap(a);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ProgressBar bar = (ProgressBar) findViewById(R.id.empty_progress_bar);
                    bar.setVisibility(View.GONE);

                } else if (resultcode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }

            if (resultcode == RESULT_OK && requestcode == 8 && data.getData() != null) {
                String path = data.getData().getPath();
                if (path.contains("video") || path.contains("Video") || path.contains("VID") ) {


                    postimg.setVisibility(View.GONE);
                    postvideo.setVisibility(View.VISIBLE);
                    type = "video";
                    imgnonetext = " ";
                    ProgressBar bar = (ProgressBar) findViewById(R.id.empty_progress_bar);
                    bar.setVisibility(View.VISIBLE);
                    uri = data.getData();
                    postvideo.setVideoURI(uri);
                    postvideo.start();
                    bar.setVisibility(View.GONE);

                }
                else if (path.contains("image") || path.contains("Image") || path.contains("IMG") )
                {

                    postimg.setVisibility(View.VISIBLE);
                    postvideo.setVisibility(View.GONE);
                    imgnonetext = " ";
                    type = "image";
                    ProgressBar bar = (ProgressBar) findViewById(R.id.empty_progress_bar);
                    bar.setVisibility(View.VISIBLE);
                    uri = data.getData();

                    CropImage.activity(uri)
                            .setAspectRatio(1,1)
                            .start(this);

                }
            }

       /*     if (requestcode == VIDEO_INTENT && resultcode == RESULT_OK) {

                postimg.setVisibility(View.GONE);
                postvideo.setVisibility(View.VISIBLE);
                type = "video";
                Toast.makeText(this, type, Toast.LENGTH_SHORT).show();
                imgnonetext = " ";
                ProgressBar bar = (ProgressBar) findViewById(R.id.empty_progress_bar);
                bar.setVisibility(View.VISIBLE);
                uri = data.getData();
                postvideo.setVideoURI(uri);
                postvideo.start();
                bar.setVisibility(View.GONE);
            }

            */

         /*   if (requestcode == GALLERY_INTENT && resultcode == RESULT_OK)
            {
                postimg.setVisibility(View.VISIBLE);
                postvideo.setVisibility(View.GONE);
                imgnonetext = " ";
                type = "image";
                Toast.makeText(this, "first", Toast.LENGTH_SHORT).show();
                ProgressBar bar = (ProgressBar) findViewById(R.id.empty_progress_bar);
                bar.setVisibility(View.VISIBLE);
                uri = data.getData();

                UCrop.of(uri, uri)
                        .withAspectRatio(16, 9)
                        .withMaxResultSize(500, 500)
                        .start(this);

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    Bitmap a = Bitmap.createScaledBitmap(bitmap,400,400,false);
                    // Bitmap a = resize(bitmap,400,362);
                    uri = getImageUri(this,a);
                    Toast.makeText(getApplicationContext(), uri.toString(), Toast.LENGTH_SHORT).show();
                    postimg.setImageBitmap(a);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bar.setVisibility(View.GONE);
        }

        */
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
        }

    public void post(View view) {

        final String cap = caption.getText().toString();
        if(!TextUtils.isEmpty(cap))
        {

            if (type.equals("video"))
            {
                final ProgressBar bar = (ProgressBar) findViewById(R.id.empty_progress_bar);
                bar.setVisibility(View.VISIBLE);
                StorageMetadata metadata = new StorageMetadata.Builder()
                        .setContentType("video/mp4")
                        .build();
                name = "file:///" + uri.toString();
                storageReference = FirebaseStorage.getInstance().getReference();
                StorageReference filepath = storageReference.child(famuid + "/posts/" + uid + timeStamp);
                Toast.makeText(this, "Creating your post..", Toast.LENGTH_LONG).show();
                filepath.putFile(uri,metadata).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(post.this, "Successfully Created new post", Toast.LENGTH_SHORT).show();
                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                        storageRef.child(famuid + "/posts/" + uid  + timeStamp).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference databaseReference = database.getReference();
                                SimpleDateFormat sdff = new SimpleDateFormat("hh:mm a", Locale.US);
                                sdff.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
                                String timestampp = sdff.format(new Date());
                                databaseReference.child(famuid + "/posts/" + uid  + timeStamp + "/timestamp").setValue(timestampp);
                                databaseReference.child(famuid + "/posts/" + uid  + timeStamp + "/type").setValue("video");
                                databaseReference.child(famuid + "/posts/" + uid  + timeStamp + "/image").setValue(uri.toString());
                                databaseReference.child(famuid + "/posts/" + uid  + timeStamp + "/caption").setValue(cap);
                                databaseReference.child(famuid + "/posts/" + uid  + timeStamp + "/uid").setValue(uid);
                                databaseReference.child(famuid + "/posts/" + uid  + timeStamp + "/post").setValue(uid+timeStamp);
                                bar.setVisibility(View.GONE);
                                startActivity(new Intent(post.this, Main2Activity.class));

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(post.this, "Failed,some error occured.Please Try Later.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else
            {

                final ProgressBar bar = (ProgressBar) findViewById(R.id.empty_progress_bar);
                bar.setVisibility(View.VISIBLE);
                if(imgnonetext!="none") {
                    name = "file:///" + compressImage(uri.toString());
                    storageReference = FirebaseStorage.getInstance().getReference();
                    StorageReference filepath = storageReference.child(famuid + "/posts/" + uid + timeStamp);

                    filepath.putFile(Uri.parse(name)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(post.this, "Post Created!", Toast.LENGTH_LONG).show();
                            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                            storageRef.child(famuid + "/posts/" + uid  + timeStamp).getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri urilll) {
                                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                                            DatabaseReference databaseReference = database.getReference();
                                            SimpleDateFormat sdff = new SimpleDateFormat("hh:mm a", Locale.US);
                                            sdff.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
                                            String timestampp = sdff.format(new Date());
                                            databaseReference.child(famuid + "/posts/" + uid  + timeStamp + "/timestamp").setValue(timestampp);
                                            databaseReference.child(famuid + "/posts/" + uid  + timeStamp + "/type").setValue("image");
                                            databaseReference.child(famuid + "/posts/" + uid  + timeStamp + "/image").setValue(urilll.toString());
                                            databaseReference.child(famuid + "/posts/" + uid  + timeStamp + "/caption").setValue(cap);
                                            databaseReference.child(famuid + "/posts/" + uid  + timeStamp + "/uid").setValue(uid);
                                            databaseReference.child(famuid + "/posts/" + uid  + timeStamp + "/post").setValue(uid+timeStamp);
                                            bar.setVisibility(View.GONE);
                                            startActivity(new Intent(post.this, Main2Activity.class));
                                            finish();
                                        }
                                    });
                        }
                    });
                }

                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = database.getReference();
                    SimpleDateFormat sdff = new SimpleDateFormat("hh:mm a", Locale.US);
                    sdff.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
                    String timestampp = sdff.format(new Date());
                    databaseReference.child(famuid + "/posts/" + uid  + timeStamp + "/timestamp").setValue(timestampp);
                    databaseReference.child(famuid + "/posts/" + uid  + timeStamp + "/image").setValue("none");
                    databaseReference.child(famuid + "/posts/" + uid  + timeStamp + "/caption").setValue(cap);
                    databaseReference.child(famuid + "/posts/" + uid + timeStamp + "/uid").setValue(uid);
                    databaseReference.child(famuid + "/posts/" + uid  + timeStamp + "/post").setValue(uid+timeStamp);
                    bar.setVisibility(View.GONE);
                    startActivity(new Intent(post.this, profile.class));
                    finish();
                }
            }

        }

        else
        {
            Toast.makeText(this,"Enter Caption!",Toast.LENGTH_SHORT).show();
        }

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
                postimg.setImageBitmap(bitmap);
            }
        }
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

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
                    Toast.makeText(this, "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
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


    private static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }

    @Override
    public void onBackPressed() {
                                startActivity(new Intent(post.this,Main2Activity.class));
    }
}
