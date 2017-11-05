package am.dx.varsityspecials.www.varsityspecials;

import android.app.ProgressDialog;
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

public class navsignup extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private SharedPreferences login;

    private String prefName = "login";

    private  String email= "";
    private  String password="";
    private EditText em;
    private EditText pa;
    private EditText cpa;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) { //initializing
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navsignup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Sign Up...");



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        em = (EditText) findViewById(R.id.etEmail) ;
        pa= (EditText) findViewById(R.id.etPassword);
        cpa = (EditText) findViewById(R.id.etComPassword);
        if (FirebaseDatabase.getInstance() != null) {
            //toast("Gone online onResume Area");

            FirebaseDatabase.getInstance().goOnline();



        }
    }


    public void toast(String t) //method used for toasting
    {
        Toast output= Toast.makeText(this, t, Toast.LENGTH_SHORT);
        output.setGravity(Gravity.CENTER,0,0);
        output.show();
    }

    public void onSignUp(View view) //button click for login
    { try {
        email = em.getText().toString();
        password = pa.getText().toString();
        String cPassword = cpa.getText().toString();
        if (pa.length()>8)
        {
            if (email.equals("")|| password.equals("") || cPassword.equals("")) {

                toast("You left an input blank");
            }
            else {
                if (password.equals(cPassword)) {
                    // progressDialog = ProgressDialog.show(this, "Please wait.",
                    //       "Finding direction..!", true);
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d("test", "createUserWithEmail:onComplete:" + task.isSuccessful());

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        toast("Sign Up failed");
                                    } else {

                                        toast("Sign Up Successful");
                                        login = getSharedPreferences(prefName, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = login.edit();
                                        editor.clear();
                                        editor.putString("email", email);
                                        editor.putString("password", password);
                                        editor.apply();

                                        //progressDialog.dismiss();
                                        Intent in = new Intent(navsignup.this, navlogin.class);
                                        startActivity(in);

                                    }

                                    // ...
                                }
                            });
                } else {
                    toast("Passwords Don't match");
                }
            }
        }
        else {
            toast("Password must be longer than 8 characters");
        }
    }
    catch(Exception ex){
        toast("Something went wrong");
        FirebaseCrash.log("Activity created");
        FirebaseCrash.logcat(Log.ERROR, ex.getMessage(), "NPE caught");
        FirebaseCrash.report(ex);
        Log.e("shit", ex.getMessage());

    }
    }



    @Override
    public void onResume(){
        super.onResume();
        if (FirebaseDatabase.getInstance() != null) {
            //  toast("Gone online onResume Area");

            FirebaseDatabase.getInstance().goOnline();



        }
    }

    @Override
    public void onStop()
    {

        if (FirebaseDatabase.getInstance() != null) {
            FirebaseDatabase.getInstance().goOffline();
            //toast("Gone offline onStop Area");

        }
        super.onStop();
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
