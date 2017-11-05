package am.dx.varsityspecials.www.varsityspecials;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class navday extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

   private FirebaseDatabase database = FirebaseDatabase.getInstance();
   private DatabaseReference myRef;

    private cardarray2 cardArrayAdapter;
    private ListView listView;
    private String text[][];
    private  String day ="";
    private String area="";
    private String category = "";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {  //initializing
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navdrawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
      drawer.setDrawerListener(toggle);
       toggle.syncState();

        listView = (ListView) findViewById(R.id.card_listView);

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        mAuth = FirebaseAuth.getInstance();
        //tv=(TextView) findViewById(R.id.line1);
        text= new String[10][10];
        day = getIntent().getStringExtra("day");
        category = getIntent().getStringExtra("category");

        area= getIntent().getStringExtra("area");
        myRef = database.getReference("regions/"+area +"/"+day +"/"+category );
        setTitle(category);
        listView.addHeaderView(new View(this));
        listView.addFooterView(new View(this));
        if (FirebaseDatabase.getInstance() != null) {

            FirebaseDatabase.getInstance().goOnline();



        }

        // day = "Durban North and Umhlanga/Monday/Connors public house";



        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { //when datachanges

                showData(dataSnapshot);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public void toast(String t) {
        Toast.makeText(this, t, Toast.LENGTH_SHORT).show();
    } //method used for toasting

    public void logging(String t) {
        Log.i("Reading firebase", t);
    } //method used for logging

    private void showData(DataSnapshot dataSnapshot) { //method used for showing/getting data
        cardArrayAdapter = new cardarray2(getApplicationContext(), R.layout.list_item_card);
        //cardArrayAdapter.clear();

        int cnt = 0;
        //  Iterable<DataSnapshot> lstSnapshots = ;

        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            String key = ds.getKey();
            logging("key " + key);

            String des = ds.child("description").getValue().toString(); //gets description field
            des = des.replace("#", "\n\n"); //replaces
            String number = ds.child("number").getValue().toString(); //gets number field
            String location = ds.child("location").getValue().toString(); //gets location field
            String price = ds.child("Price").getValue().toString(); //gets price field
            String time = ds.child("time").getValue().toString(); //gets time field

            text[cnt][0] = key;
            text[cnt][1] = des;
            text[cnt][2] = number;
            text[cnt][3] = location;
            text[cnt][4] = price;
            text[cnt][5] = time;
            //toast(text[cnt]);
            // tv.setText(text[cnt]);
            cnt++;
            Card card = new Card(key,des,"Time :\t" +time); //pasing data to class
            cardArrayAdapter.add(card);

            listView.setAdapter(cardArrayAdapter); //sets adapater


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) { //onclick for list view
                    logging("shit about to happen");
                    // TODO Auto-generated method stub
                    //toast("Shit hello");
                    Intent intent = new Intent(navday.this, ScrollingActivity.class);
                    logging("shit about to happen part 2");
                    // toast("intenting");
                    intent.putExtra("name", text[position - 1][0]);
                    intent.putExtra("description", text[position - 1][1]);
                    intent.putExtra("number", text[position - 1][2]);
                    intent.putExtra("location", text[position - 1][3]);
                    intent.putExtra("area", area);
                    intent.putExtra("day", day.substring(1));
                    intent.putExtra("price", text[position - 1][4]);
                    intent.putExtra("time", text[position-1][5]);
                    startActivity(intent);

                    logging("shit about to happen part 69");
                }
            });
        }




    }
    @Override
    public void onResume(){
        super.onResume();
        if (FirebaseDatabase.getInstance() != null) {
            // toast("Gone online onResume Area");

            FirebaseDatabase.getInstance().goOnline();



        }
    }


    @Override
    public void onStop(){
        super.onStop();
        if (FirebaseDatabase.getInstance() != null) {
            FirebaseDatabase.getInstance().goOffline();
            // toast("Gone offline onStop Area");

        }
    }




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
        else if (id==R.id.content_nav_q1_b)
        {
            startActivity(new Intent(getApplicationContext(), Question1B.class));
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }
}
