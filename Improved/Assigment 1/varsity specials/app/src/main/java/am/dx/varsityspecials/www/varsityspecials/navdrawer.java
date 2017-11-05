package am.dx.varsityspecials.www.varsityspecials;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class navdrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private CardArrayAdapter cardArrayAdapter;
    private ListView listView;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private NavigationView navigationView;
    private ActionBarDrawerToggle adbt;
    private DrawerLayout dl;
    private Toolbar toolbar;
    private FirebaseUser user;
    private FirebaseAuth myAuth;
    private DatabaseReference popUp;
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private ImageView iv;



    String area[];
    /// String [] days = {"Everyday (Monday to Friday)","Monday","Tuesday", "Wednesday", "Thursday", "Friday","Saturday", "Sunday"};
    TextView line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {  //initializing
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navdrawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myAuth = FirebaseAuth.getInstance();
        iv = new ImageView(this);
        try {
            // myAuth.getCurrentUser();
            user = myAuth.getCurrentUser();
        } catch (Exception ex) {
            Toast.makeText(this, "Please sign in or create an account", Toast.LENGTH_LONG).show();
        }

        setTitle("Select your area...");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listView = (ListView) findViewById(R.id.card_listView);
        area = new String[10];
        myRef = database.getReference("regions");
        String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
       // Toast.makeText(this, "day " + day, Toast.LENGTH_SHORT).show();
        String ref = "popup/" + day;
        popUp = database.getReference(ref);
        line = (TextView) findViewById(R.id.line1);
        listView.addHeaderView(new View(this));
        listView.addFooterView(new View(this));
        if (FirebaseDatabase.getInstance() != null) {


            FirebaseDatabase.getInstance().goOnline();


        } else {
            Toast.makeText(this, "Please sign in or create an account", Toast.LENGTH_LONG).show();
        }

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { //when datachanges

                showData(dataSnapshot);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        popUp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { //when datachanges

                showPopUp(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void showPopUp(DataSnapshot dataSnapshot) { //makes pop up



        Iterable<DataSnapshot> lstSnapshots = dataSnapshot.getChildren();
        int cnt = 0;
        ArrayList<DataSnapshot> ds = new ArrayList<>();
        for (DataSnapshot dataSnapshot1 : lstSnapshots) {

            ds.add(dataSnapshot1);
            cnt++;

        }
        if (cnt>0) {
            String tte = "";

            //  for (int i=0;i<100; i++){

            Random ran = new Random();


            int range = cnt;
            int random = ran.nextInt(range) + 0;
            tte = tte + "\t\t" + random;


            String title = ds.get(random).child("title").getValue().toString(); //gets title field
            String message = ds.get(random).child("message").getValue().toString(); //gets message field
            final String img = ds.get(random).child("img").getValue().toString(); //gets img field

            final AlertDialog.Builder popup = new AlertDialog.Builder(this);

            popup.setTitle(title);

            popup.setMessage(message);

            String ref = "popup/" + img;
            final File file = new File(getFilesDir(), img);
            File check = (getFilesDir());
            boolean imgNotExists = true;
            final File[] imgAr = check.listFiles();

            for (int i = 0; i < imgAr.length; i++) { //checks if image exits

                if (img.equals(imgAr[i].getName())) {
                 //   Toast.makeText(this, "Image exists", Toast.LENGTH_SHORT).show();
                    imgNotExists = false;

                   // iv.setImageBitmap(decodeSampledBitmap(imgAr[i].getName()));
                    Bitmap bit = BitmapFactory.decodeFile(imgAr[i].getAbsolutePath());
                    iv.setImageBitmap(bit);
                    i = imgAr.length;
                    popup.setIcon(iv.getDrawable());
                    popup.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    popup.show();





                }

            }


            if (imgNotExists) { //if it doesnt exist download image
                storageRef.child(ref).getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bit = BitmapFactory.decodeFile(file.getAbsolutePath());
                        iv.setImageBitmap(bit);
                        popup.setIcon(iv.getDrawable());
                        popup.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                       popup.show();

                    }


                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(navdrawer.this, "Failed to download image", Toast.LENGTH_SHORT).show();
                    }
                });

            }




        }




    }





    @Override
    public void onResume(){
        super.onResume();
        if (FirebaseDatabase.getInstance() != null) {
            //toast("Gone online onResume Area");

            FirebaseDatabase.getInstance().goOnline();



        }
    }



    @Override
    public void onStop(){
        super.onStop();
        if (FirebaseDatabase.getInstance() != null) {
            FirebaseDatabase.getInstance().goOffline();



        }
    }



    private void showData(DataSnapshot dataSnapshot) {
        // toast("hello show data");
        cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.list_item_card);
        //cardArrayAdapter.clear();

        int cnt =0;
        //  Iterable<DataSnapshot> lstSnapshots = ;
        try {

            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                if (!((ds.getKey().equals("Blog")) || (ds.getKey().equals("help")) || (ds.getKey().equals("copyright")) )) {
                    area[cnt] = ds.getKey();
                    logging("area " + area[cnt]);

                    Card card = new Card(area[cnt]);
                    cardArrayAdapter.add(card);

                    listView.setAdapter(cardArrayAdapter);

                    cnt++;


                    try {


                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                logging("shit about to happen");
                                // TODO Auto-generated method stub
                                //toast("Shit hello");
                                Intent intent = new Intent(navdrawer.this, navdays.class);
                                logging("shit about to happen part 2");
                                // toast("intenting");
                                intent.putExtra("area", area[position - 1]);

                                startActivity(intent);

                                logging("shit about to happen part 3");
                            }
                        });
                    } catch (Exception ex) {
                        Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                        logging("shit happened  " + ex.getMessage());
                    }


                }
            }

        }catch (Exception ex)
        {
            logging("shit\n" + ex.getMessage());
        }

    }

    public void logging(String t)
    {
        Log.i("Reading firebase", t);
    } //method used for logging


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        if (id==R.id.nav_welcome)
        {
            startActivity(new Intent(getApplicationContext(), navwelcome.class));
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (id==R.id.nav_resetPassword)
        {
            startActivity(new Intent(getApplicationContext(), navresetpassword.class));
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (id==R.id.nav_deleteUser)
        {
            startActivity(new Intent(getApplicationContext(), navdelete.class));
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        else if(id==R.id.nav_specials)
        {
            startActivity(new Intent(getApplicationContext(), navdrawer.class));
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        else if(id==R.id.nav_copyright)
        {
            startActivity(new Intent(getApplicationContext(), navcopyright.class));
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

        }
        else if (id==R.id.nav_submit)
        {
            startActivity(new Intent(getApplicationContext(), navsubmit.class));
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }

        else if (id==R.id.nav_1a)
        {
            startActivity(new Intent(getApplicationContext(), navQ1a.class));
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (id==R.id.nav_1B)
        {
            startActivity(new Intent(getApplicationContext(), navQ1B.class));
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }
}
