package am.dx.varsityspecials.www.varsityspecials;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class day extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    TextView tv ;
    TextView tv2;
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
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.listview);
            listView = (ListView) findViewById(R.id.card_listView);

            navigationView = (NavigationView) findViewById(R.id.nav_view);

            navigationView.setNavigationItemSelectedListener(this);
            mAuth = FirebaseAuth.getInstance();
            //tv=(TextView) findViewById(R.id.line1);
            text= new String[10][10];
           day = getIntent().getStringExtra("day");
            category = getIntent().getStringExtra("category");

            area= getIntent().getStringExtra("area");
            myRef = database.getReference("regions/" + area +"/"+day +"/"+category );
            setTitle(category);
            listView.addHeaderView(new View(this));
            listView.addFooterView(new View(this));
            if (FirebaseDatabase.getInstance() != null) {

                FirebaseDatabase.getInstance().goOnline();



            }

           // day = "Durban North and Umhlanga/Monday/Connors public house";



            myRef.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                                      showData(dataSnapshot);

                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
        catch (Exception ex)
        {
            logging("shit happened poes  "+ ex.getMessage());
        }
    }


    public void toast(String t) {
        Toast.makeText(this, t, Toast.LENGTH_SHORT).show();
    }

    public void logging(String t)
    {
        Log.i("Reading firebase", t);
    }

    private void showData(DataSnapshot dataSnapshot) {
        cardArrayAdapter = new cardarray2(getApplicationContext(), R.layout.list_item_card);
        //cardArrayAdapter.clear();

        int cnt = 0;
        //  Iterable<DataSnapshot> lstSnapshots = ;

            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                String key = ds.getKey();
                logging("key " + key);
                // key = key+"/description";
                //logging("key " + key);
                //toast(key);
                //logging("Before desciption");
                //logging(ds.child(key).toString());
                String des = ds.child("description").getValue().toString();
                des = des.replace("#", "\n\n");
                String number = ds.child("number").getValue().toString();
                String location = ds.child("location").getValue().toString();
                String price = ds.child("Price").getValue().toString();
                String time = ds.child("time").getValue().toString();
                logging("description hhhhh\t" + des);
                logging("number hhhhh\t" + number);
                logging("location hhh\t" + location);
                logging("price hhhh\t" + price);

                text[cnt][0] = key;
                text[cnt][1] = des;
                text[cnt][2] = number;
                text[cnt][3] = location;
                text[cnt][4] = price;
                text[cnt][5] = time;
                //toast(text[cnt]);
                // tv.setText(text[cnt]);
                cnt++;
                Card card = new Card(key,des,"Time :\t" +time);
                cardArrayAdapter.add(card);

                listView.setAdapter(cardArrayAdapter);


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        logging("shit about to happen");
                        // TODO Auto-generated method stub
                        //toast("Shit hello");
                        Intent intent = new Intent(day.this, ScrollingActivity.class);
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
