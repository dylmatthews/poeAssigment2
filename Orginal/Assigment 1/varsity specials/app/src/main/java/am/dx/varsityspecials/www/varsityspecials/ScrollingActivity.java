package am.dx.varsityspecials.www.varsityspecials;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ScrollingActivity extends AppCompatActivity {//implements NavigationView.OnNavigationItemSelectedListener {

    private String number = "";
   private String name="";
    private  String des ="";
    private String area="";
    private String day="";
    private  String time="";
    private  String p ="";
    private  String l = "";
    private static final int RESULT_PICK_CONTACT = 5; //can be any number above 1
    private NavigationView navigationView;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
      //  navigationView.setNavigationItemSelectedListener(this);
        tv = (TextView) findViewById(R.id.tvScroll) ;
        setSupportActionBar(toolbar);
        name = getIntent().getStringExtra("name");
       des = getIntent().getStringExtra("description");
        number = getIntent().getStringExtra("number");
        area = getIntent().getStringExtra("area");
       time = getIntent().getStringExtra("time");
        final String location = getIntent().getStringExtra("location");
        final String price =getIntent().getStringExtra("price");
        p = price;
        l = location;
        day = getIntent().getStringExtra("day");
        setTitle(name);
        tv.setText("The time for this special, for "+ name + " is " + time +"\nYou'll find " + name + " at\n " + location+ ".\n\nThe specials are :\n" + des +"\n\n\nCurrently the specials are submitted by users.\n\nIt is recommended that you phone the place before hand and ask if the special is still on.\n\nAlso to book a table");


        FloatingActionButton nav = (FloatingActionButton) findViewById(R.id.nav);
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
                            Toast.makeText(ScrollingActivity.this, "Accept the Location permission", Toast.LENGTH_SHORT).show();
                        }

                    }


                } catch (Exception e) {
                    Toast.makeText(ScrollingActivity.this, "Test " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        FloatingActionButton whatsapp = (FloatingActionButton) findViewById(R.id.whatsapp);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out this crazy special on " + day + ". \nThe special is: " + des + ". \nAt " + name + ". \n\nTime: " + time +". \n\nThe price is: "+price+". \n\nThe location is " + location );
                    sendIntent.setType("text/plain");
                    sendIntent.setPackage("com.whatsapp");
                    startActivity(sendIntent);
                }
                catch (Exception e)
                {
                    Toast.makeText(ScrollingActivity.this, "You dont have whatsapp installed", Toast.LENGTH_SHORT).show();
                }
            }
            });



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
                        Toast.makeText(ScrollingActivity.this, "Accept the calling permission to call", Toast.LENGTH_SHORT).show();
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

        FloatingActionButton sms = (FloatingActionButton) findViewById(R.id.sms);
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isSMSPermissionGranted()) {
                    sendSMS();
                } else {
                    try {
                       // sendSMS();
                    } catch (Exception ex) {
                        Toast.makeText(ScrollingActivity.this, "Accept the sms permission to call", Toast.LENGTH_SHORT).show();
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


    }

    private void sendSMS() {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

                    contactPicked(data);



        } else {
            Log.e("Scrolling", "Failed to pick contact");
        }
    }

    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            String phoneNo = null ;
            String nam = null;
            Uri uri = data.getData();
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();

            int  number =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

            phoneNo = cursor.getString(number);
            if (phoneNo.contains("+27")) {

            } else {
                phoneNo = "+27" + phoneNo.substring(1);


            }
String mess = des + ".\nOn " + day+"\nAt " + name + ". \n\nTime: " + time +".\nThe location is " + l ;
            Toast.makeText(this, "Length is " +mess.length(), Toast.LENGTH_SHORT).show();
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, mess , null, null);
            Toast.makeText(this, "Sms has been sent", Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private boolean isSMSPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_GRANTED) {
                sendSMS();
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(ScrollingActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }

    private boolean isLocationPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
               locationg();
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(ScrollingActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }

    }

    private void locationg() {
        Intent intent = new Intent(ScrollingActivity.this, MapsActivity.class);
        intent.putExtra("area", area);
        intent.putExtra("name", name);
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


    public void call_action() {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.setData(Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(callIntent);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        else {
            //;
        }
    }

    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
               call_action();
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(ScrollingActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                  // call_action();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }

            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


  //  @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return false;
    }
}
