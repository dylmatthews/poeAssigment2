package am.dx.varsityspecials.www.varsityspecials;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DatabaseReference mData;
    private blod_array cardArrayAdapter;
    private ListView listView;
    private ImageView im;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        listView = (ListView) findViewById(R.id.card_listView);
        im = (ImageView) findViewById(R.id.post_image) ;
        listView.addHeaderView(new View(this));
        listView.addFooterView(new View(this));

        mData = FirebaseDatabase.getInstance().getReference().child("Blog");



        mData.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                showData(dataSnapshot);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    private void showData(DataSnapshot dataSnapshot)  {


        cardArrayAdapter = new blod_array(getApplicationContext(), R.layout.blow_row);
        cardArrayAdapter.clear();

        Iterable<DataSnapshot> lstSnapshots =  dataSnapshot.getChildren();
        ArrayList<DataSnapshot> lstDataSnapshots = new ArrayList<>();
        for(DataSnapshot dataSnapshot1 : lstSnapshots){
            lstDataSnapshots.add(dataSnapshot1);

        }
        Log.i("shitTest", lstDataSnapshots.size()+"");

        for(int  i = lstDataSnapshots.size() - 1; i >= 0; i--){



           final  String des = lstDataSnapshots.get(i).child("desc").getValue().toString();
           final String location = lstDataSnapshots.get(i).child("title").getValue().toString();
           final String imgURl = lstDataSnapshots.get(i).child("image").getValue().toString();
            final int num = 1;


            Log.i("shitTesti", i+"");
                    Card card = new Card(location, des, imgURl, num);
                    cardArrayAdapter.add(card);




        }

        listView.setAdapter(cardArrayAdapter);



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
