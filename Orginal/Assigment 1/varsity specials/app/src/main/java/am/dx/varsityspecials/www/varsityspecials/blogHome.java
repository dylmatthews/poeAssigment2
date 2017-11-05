package am.dx.varsityspecials.www.varsityspecials;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class blogHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ImageButton imageButton;
    private static final int Gallery_Request = 1;
    private EditText title;
    private EditText des;
    private Uri imageUri=null;
    private StorageReference storageReference;
    private ProgressDialog pb;
    private DatabaseReference mdref;
    private RatingBar rb;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_home);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        storageReference = FirebaseStorage.getInstance().getReference();
        mdref = FirebaseDatabase.getInstance().getReference().child("Blog");
        imageButton = (ImageButton) findViewById(R.id.ibImageSelect);
title = (EditText) findViewById(R.id.NOP);
        des = (EditText) findViewById(R.id.DES) ;
        pb = new ProgressDialog (this);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, Gallery_Request);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Gallery_Request && resultCode==RESULT_OK)
        {
            imageUri  = data.getData();
            imageButton.setImageURI(imageUri);
        }
    }

    public void onSubmit(View view)
    {

        try {
            startPosting();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }





    public void startPosting() throws IOException

    {

        final String titl= title.getText().toString();
        final String desc = des.getText().toString();
        StorageReference upload = storageReference.child("blog");

        try {

            if (!titl.isEmpty() && !desc.isEmpty() && imageUri != null) {
                pb.setMessage("Image is uploading...");
                pb.show();
                //String file = imageUri.toString();
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                byte[] data = baos.toByteArray();
                Toast.makeText(this, imageUri.getLastPathSegment(), Toast.LENGTH_SHORT).show();
                UploadTask filePath = upload.child(imageUri.getLastPathSegment()).putBytes(data);

                pb.show();
                final FirebaseUser cu = FirebaseAuth.getInstance().getCurrentUser();

                filePath.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        //  Uri downloadurl = taskSnapshot.getDownloadUrl();
                        DatabaseReference newPost = mdref.push();
                        newPost.child("title").setValue(titl);
                        newPost.child("desc").setValue(desc);
                        newPost.child("image").setValue(imageUri.getLastPathSegment());
                        newPost.child("uid").setValue(cu.getUid());
                        pb.dismiss();



                    }
                });

            }
        }catch (Exception ex)
        {
            Toast.makeText(this, "An error happened, image probably still got uplaoded\n" + ex.getMessage() , Toast.LENGTH_SHORT).show();
            pb.dismiss();
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id==R.id.nav_login)
        {
            startActivity(new Intent(getApplicationContext(), login.class));
        }
        else if (id==R.id.nav_addBlog)
        {
            startActivity(new Intent(getApplicationContext(), blogHome.class));
        }
        else if (id==R.id.nav_viewBlog)
        {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        else if (id==R.id.nav_question1)
        {
            startActivity(new Intent(getApplicationContext(), Question1.class));

        }
        else if (id==R.id.nav_question1B)
        {
            startActivity(new Intent(getApplicationContext(), Question1B.class));
        }
        else if (id==R.id.nav_welcome)
        {
            startActivity(new Intent(getApplicationContext(), Welcome.class));
        }
        else if (id==R.id.nav_resetPassword)
        {
            startActivity(new Intent(getApplicationContext(), updatePassword.class));
        }
        else if (id==R.id.nav_deleteUser)
        {
            startActivity(new Intent(getApplicationContext(), DeleteUser.class));
        }

        return false;
    }
}
