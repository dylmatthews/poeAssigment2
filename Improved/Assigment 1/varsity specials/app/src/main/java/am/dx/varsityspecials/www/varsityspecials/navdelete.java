package am.dx.varsityspecials.www.varsityspecials;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class navdelete extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) { //initializing
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navdelete);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Delete your account");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void onDelete(View view) //on click
    {
        try {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            user.delete() //deletes user here
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(navdelete.this, "Your account has been deleted", Toast.LENGTH_SHORT).show();
                                Log.d("delete", "User account deleted.");
                            } else {
                                Toast.makeText(navdelete.this, "There was problem deleting your account", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        catch(Exception ex)
        {
            Toast.makeText(this, "Sign in, to delete your account", Toast.LENGTH_SHORT).show();
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
