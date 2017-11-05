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

public class navcategory extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CardArrayAdapter cardArrayAdapter;
    private ListView listView;
    private String day = "";
    private String area = "";
    private  String category[];
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) { //initializing
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
        category = new String[10];
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        day = getIntent().getStringExtra("day");
        area = getIntent().getStringExtra("area");
        myRef = database.getReference("regions/"+area + "/" + day);
        setTitle(day.substring(1)+ " specials"); //sets title
        listView.addHeaderView(new View(this));
        listView.addFooterView(new View(this));
        if (FirebaseDatabase.getInstance() != null) {

            FirebaseDatabase.getInstance().goOnline();
        }

        myRef.addValueEventListener(new ValueEventListener() { //sets listening on database

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { //when datachanges

                showData(dataSnapshot); //sends  data to method

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

    private void showData(DataSnapshot dataSnapshot) {
        // toast("hello show data");
        cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.list_item_card); //sends context and layout file to use
        //cardArrayAdapter.clear();

        int cnt = 0;
        //  Iterable<DataSnapshot> lstSnapshots = ;
        try {

            for (DataSnapshot ds : dataSnapshot.getChildren()) { //for each loop to get data

                String key = ds.getKey();
                logging("key " + key);


                category[cnt] = key;

                cnt++;
                int cntPos=0;
                StringBuilder sb = new StringBuilder();
                boolean found = false;
                for(char c : key.toCharArray()) {
                    if (Character.isDigit(c)) {
                        sb.append(c);
                        cntPos++;

                    } else if (found == true) {


                        break;

                    } else {
                        found = true;
                        // If already found a digit before and this char is not a digit, stop looping

                    }
                }


                Card card = new Card(key.substring(cntPos));
                cardArrayAdapter.add(card);

                listView.setAdapter(cardArrayAdapter); //adds adapater


                try {


                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            logging("shit about to happen");
                            // TODO Auto-generated method stub
                            //toast("Shit hello");
                            Intent intent = new Intent(navcategory.this, navday.class);
                            logging("shit about to happen part 6969");
                            // toast("intenting");
                            //Toast.makeText(Category.this, category + area + day, Toast.LENGTH_SHORT).show();

                            intent.putExtra("category", category[position - 1]);
                            intent.putExtra("area", area);
                            intent.putExtra("day", day);
                            logging("shit about to happen part 69693");
                            startActivity(intent);



                        }
                    });
                } catch (Exception ex) {
                    logging("poes 1  " + ex.getMessage());
                }


            }


        } catch (Exception ex) {
            logging("poes 2\n" + ex.getMessage());
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
