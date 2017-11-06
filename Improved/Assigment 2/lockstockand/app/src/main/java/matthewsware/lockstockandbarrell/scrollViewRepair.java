package matthewsware.lockstockandbarrell;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class scrollViewRepair extends AppCompatActivity {

    private String name;
    private String repair;
    private String repairOther;
    private String numberItems;
    private String cost;
    private String cellphone;
    private String imgUrl;
    private String date;
    private TextView tvMEssage ;
    private String ticketNum;;
    private ImageView ivRepair;
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    private int thumbnailTop;
    private int thumbnailLeft;
    private int thumbnailWidth;
    private int thumbnailHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Initializing variables
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view_repair);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        thumbnailTop = bundle.getInt("top");
        thumbnailLeft = bundle.getInt("left");
        thumbnailWidth = bundle.getInt("width");
        thumbnailHeight = bundle.getInt("height");
        name =  bundle.getString("name");
        repair = bundle.getString("repair");
        repairOther = bundle.getString("repairOther");
        numberItems = bundle.getString("numberItems");
        cost = bundle.getString("cost");
        cellphone = bundle.getString("cellphone");
        imgUrl = bundle.getString("image");
        date =  bundle.getString("date");
        ticketNum = bundle.getString("ticketNum");
        Toast.makeText(this, ticketNum, Toast.LENGTH_SHORT).show();

        setTitle("ticket number " + ticketNum);

        tvMEssage = (TextView) findViewById(R.id.tvMessage);
        ivRepair = (ImageView) findViewById(R.id.imageScroll);


        String message = name + " came in on the\n" + date + " to get " + repair + " and other "

                + repairOther + ".\nThere were " + numberItems + " number of items.\n" + "The cost is R" + cost +
                ".\nThe persons contact details are "+ cellphone;
        tvMEssage.setText(message);

        final String ref = "repairs/" + imgUrl;
        final File file = new File(getApplicationContext().getFilesDir(), imgUrl);
        File check = (this.getFilesDir());
        boolean imgNotExists = true;
        final File[] imgAr = check.listFiles();

        for (int i = 0; i < imgAr.length; i++) {

            if (imgUrl.equals(imgAr[i].getName())) { //checks if image exists

                imgNotExists = false;

                ivRepair.setImageURI(Uri.parse(imgAr[i].getAbsolutePath()));
                i = imgAr.length;


            }

        }


        if (imgNotExists) { //if it doesnt, download image here
            storageRef.child(ref).getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bit = BitmapFactory.decodeFile(file.getAbsolutePath());
                    ivRepair.setImageBitmap(bit);
                    Toast.makeText(scrollViewRepair.this, "download image", Toast.LENGTH_SHORT).show();


                }


            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Toast.makeText(co, "Failed to download image", Toast.LENGTH_SHORT).show();
                    Toast.makeText(scrollViewRepair.this, "Failed to download image" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });



        }

        FloatingActionButton phone = (FloatingActionButton) findViewById(R.id.call);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isPermissionGranted()) {
                    call_action();
                } else {
                    try {
                        call_action();
                    } catch (Exception ex) {
                        Toast.makeText(scrollViewRepair.this, "Accept the calling permission to call", Toast.LENGTH_SHORT).show();
                    }

                }


                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

            }


        });

        FloatingActionButton nav = (FloatingActionButton) findViewById(R.id.nav); //button for nav
        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {



                    if (isLocationPermissionGranted()) {
                        locationg();
                    } else {
                        try {
                            locationg();
                        } catch (Exception ex) {
                            Toast.makeText(scrollViewRepair.this, "Accept the Location permission", Toast.LENGTH_SHORT).show();
                        }

                    }


                } catch (Exception e) {
                    Toast.makeText(scrollViewRepair.this, "Test " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });






    }
    private void locationg() {
       Intent intent = new Intent(scrollViewRepair.this, MapsActivity.class);
        intent.putExtra("area", "la lucai, durban");
       intent.putExtra("name", "la lucia mall");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }



    private boolean isLocationPermissionGranted() { //checks permissions
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                locationg();
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(scrollViewRepair.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }

    }

    public  boolean isPermissionGranted() {  //checks permissions
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                call_action();
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(scrollViewRepair.this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }


    public void call_action() {//button for calling
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.setData(Uri.parse("tel:" + cellphone));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(callIntent);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else {
            //;
        }
    }
}
