package am.dx.varsityspecials.www.varsityspecials;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class navsubmit extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private CheckBox cbMonday;
    private CheckBox cbTuesday;
    private CheckBox cbWednesday;
    private CheckBox cbThurssday;
    private CheckBox cbFriday;
    private CheckBox cbSaturday;
    private CheckBox cbSunday;

    private EditText areaos;
    private EditText restaurant;
    private EditText tod;
    private EditText des;
    private EditText address;

    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) { //initializing
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navsubmit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Submit A Special");
        areaos = (EditText) findViewById(R.id.etSubmitArea);
        restaurant = (EditText) findViewById(R.id.etSubmitNameOFPlace) ;
        tod = (EditText) findViewById(R.id.etSubmitTOD);
        des = (EditText) findViewById(R.id.etSubmitDES);
        address=(EditText) findViewById(R.id.etSubmitAddress);
        cbMonday = (CheckBox) findViewById(R.id.cbMonday) ;
        cbTuesday = (CheckBox) findViewById(R.id.cbTuesday);
        cbWednesday = (CheckBox) findViewById(R.id.cbWednesday);
        cbThurssday = (CheckBox) findViewById(R.id.cbThursday);
        cbFriday= (CheckBox) findViewById(R.id.cbFriday);
        cbSaturday = (CheckBox) findViewById(R.id.cbSaturday);
        cbSunday = (CheckBox) findViewById(R.id.cbSunday);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    }

    public void onSubmitSpecial(View view) //button click for submititng
    {

        String message ="My special submission is available on:\n";



        String nameOfPlace = restaurant.getText().toString();
        String description = des.getText().toString();
        String time = tod.getText().toString();
        String a = address.getText().toString();
        String area = areaos.getText().toString();

        if (nameOfPlace.isEmpty()||description.isEmpty()||time.isEmpty()||a.isEmpty()||area.isEmpty())
        {
            Toast.makeText(this, "You left a field empty", Toast.LENGTH_SHORT).show();

        }
        else {

            if (cbMonday.isChecked()||cbTuesday.isChecked() ||cbWednesday.isChecked()||cbThurssday.isChecked()
                    || cbFriday.isChecked() ||cbSaturday.isChecked()||cbSunday.isChecked()) {

                if(cbMonday.isChecked())
                {
                    message+= "Monday\n";
                }
                if (cbTuesday.isChecked())
                {
                    message+= "Tuesday\n";
                }

                if (cbWednesday.isChecked())
                {
                    message+= "Wednesday\n";
                }

                if(cbThurssday.isChecked())
                {
                    message+= "Thursday\n";
                }

                if (cbFriday.isChecked())
                {
                    message+= "Friday\n";
                }

                if (cbSaturday.isChecked())
                {
                    message+= "Saturday\n";
                }

                message+= "The special is at " + nameOfPlace + "\n";
                message+= "It is on during this time of the day: " + time + "\n";
                message+= "The place situated in : " + area + "\n";
                message+= "The address is: " + a;

                Intent emailIntent = new Intent(android.content.Intent.ACTION_SENDTO);





                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String []{ "eatingspecails@gmail.com"});

                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "I'd like to submit a special");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);

                startActivity(emailIntent);



            }
            else{
                Toast.makeText(this, "You didn't select a day of the week...", Toast.LENGTH_SHORT).show();
            }




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
        else if (id==R.id.nav_1B)
        {
            startActivity(new Intent(getApplicationContext(), Question1B.class));
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }

        return true;
    }
}
