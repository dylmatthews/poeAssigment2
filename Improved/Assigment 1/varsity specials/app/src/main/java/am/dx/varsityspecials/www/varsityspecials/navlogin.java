package am.dx.varsityspecials.www.varsityspecials;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.FirebaseDatabase;

public class navlogin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText em;
    private EditText pa;
    private SharedPreferences loginpref;
    private SharedPreferences login;
    private String prefName = "login";
    private  String email= "";
    private  String password="";
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) { //initializing
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navlogin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

            em = (EditText) findViewById(R.id.etEmail) ;
            pa= (EditText) findViewById(R.id.etPassword);
           // navigationView = (NavigationView) findViewById(R.id.nav_view);
          //  navigationView.setNavigationItemSelectedListener(this);
            mAuth = FirebaseAuth.getInstance();
            FirebaseCrash.log("Activity created");

            if (FirebaseDatabase.getInstance() != null) {
                // toast("Gone online onResume Area");
                //toast("about to");
                loginshared();
                //FirebaseDatabase.getInstance().goOnline();


            } else
            {
                // toast("about to login");
                loginshared();
            }
    }

    private void loginshared() { //checks if a user saved data is there
        try{
            loginpref = getSharedPreferences(prefName,MODE_PRIVATE);
            email = loginpref.getString("email", "");
            //toast(email);
            password = loginpref.getString("password","");
          //  toast(password);
            if (email.isEmpty()||password.isEmpty()) {
            }
            else {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("test", "signInWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Log.w("test", "signInWithEmail", task.getException());

                                    toast("Login Error");

                                } else {
                                    Log.w("test", "signInWithEmail", task.getException());


                                    toast("Login Successful");
                                    Intent intent = new Intent(navlogin.this, navdrawer.class);
                                    startActivity(intent);

                                }

                                // ...
                            }
                        });
            }
        }
        catch(Exception ex) {
            toast(ex.getMessage());
        }

    }

    public void onSignUp(View view) //button click
    {

        Intent in = new Intent(navlogin.this,navsignup.class);
        startActivity(in);
    }


    public void toast(String t) // method used for toasting
    {
        Toast output= Toast.makeText(this, t, Toast.LENGTH_SHORT);
        output.setGravity(Gravity.CENTER,0,0);
        output.show();
    }

    public void onLoginSaved(View view)
    {
        loginshared();

    }

    public void onLogin(View view) //button click for login
    {


        email=em.getText().toString();
        password = pa.getText().toString();


        if (email.isEmpty()||password.isEmpty())
        {
            Toast.makeText(this, "You left a field blank", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("test", "signInWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w("test", "signInWithEmail", task.getException());


                                toast("Login Error");

                            } else {
                                Log.w("test", "signInWithEmail", task.getException());
                                login = getSharedPreferences(prefName, MODE_PRIVATE);
                                SharedPreferences.Editor editor = login.edit();
                                editor.clear();
                                editor.putString("email", email);
                                editor.putString("password", password);
                                editor.apply();


                                toast("Login Successful");
                                Intent intent = new Intent(navlogin.this, navdrawer.class);

                                startActivity(intent);

                            }


                        }
                    });
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
    public void onPause()
    {

        if (FirebaseDatabase.getInstance() != null) {
            FirebaseDatabase.getInstance().goOffline();
            // toast("Gone offline onDestoy Area");

        }
        super.onPause();
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
