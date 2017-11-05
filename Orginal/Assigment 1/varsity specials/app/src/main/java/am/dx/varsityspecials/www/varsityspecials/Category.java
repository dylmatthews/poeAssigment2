package am.dx.varsityspecials.www.varsityspecials;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Category extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CardArrayAdapter cardArrayAdapter;
    private ListView listView;
    private String day = "";
    private String area = "";
    private  String category[];
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        listView = (ListView) findViewById(R.id.card_listView);
        category = new String[10];
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        day = getIntent().getStringExtra("day");
        area = getIntent().getStringExtra("area");
        myRef = database.getReference("regions/"+area + "/" + day);
        setTitle(day.substring(1)+ " specials");
        listView.addHeaderView(new View(this));
        listView.addFooterView(new View(this));
        if (FirebaseDatabase.getInstance() != null) {
          //  toast("Gone online onResume Area");

            FirebaseDatabase.getInstance().goOnline();



        }

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

    public void toast(String t) {
        Toast.makeText(this, t, Toast.LENGTH_SHORT).show();
    }

    public void logging(String t) {
        Log.i("Reading firebase", t);
    }

    private void showData(DataSnapshot dataSnapshot) {
        // toast("hello show data");
        cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.list_item_card);
        //cardArrayAdapter.clear();

        int cnt = 0;
        //  Iterable<DataSnapshot> lstSnapshots = ;
        try {

            for (DataSnapshot ds : dataSnapshot.getChildren()) {

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
                   // Toast.makeText(this, "Counter was " + cntPos, Toast.LENGTH_SHORT).show();

                Card card = new Card(key.substring(cntPos));
                cardArrayAdapter.add(card);

                listView.setAdapter(cardArrayAdapter);


                try {


                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            logging("shit about to happen");
                            // TODO Auto-generated method stub
                            //toast("Shit hello");
                            Intent intent = new Intent(Category.this, day.class);
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
