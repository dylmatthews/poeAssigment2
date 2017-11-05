package am.dx.varsityspecials.www.varsityspecials;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
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

public class login extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    EditText em;
    EditText pa;
    SharedPreferences login;
    String prefName = "login";
    private  String email= "";
    private  String password="";
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            em = (EditText) findViewById(R.id.etEmail) ;
            pa= (EditText) findViewById(R.id.etPassword);
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            mAuth = FirebaseAuth.getInstance();
            FirebaseCrash.log("Activity created");

            if (FirebaseDatabase.getInstance() != null) {
               // toast("Gone online onResume Area");
                toast("about to");
                loginshared();
                FirebaseDatabase.getInstance().goOnline();


            } else
            {
                toast("about to login");
                loginshared();
            }



        }
        catch(Exception ex) {
            FirebaseCrash.log("Activity created");
            FirebaseCrash.logcat(Log.ERROR, ex.getMessage(), "NPE caught");
            FirebaseCrash.report(ex);
            Log.e("shit" , ex.getMessage());
        }



    }

    private void loginshared() {
        try{
            login = getSharedPreferences(prefName,MODE_PRIVATE);
            email = login.getString("email", "");
            toast(email);
            password = login.getString("password","");
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
                                    Intent intent = new Intent(login.this, Location.class);
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

    public void onSignUp(View view)
    {

        Intent in = new Intent(login.this,signUp.class);
        startActivity(in);
    }


        public void toast(String t)
    {
        Toast output= Toast.makeText(this, t, Toast.LENGTH_SHORT);
        output.setGravity(Gravity.CENTER,0,0);
        output.show();
    }

    public void onLoginSaved(View view)
    {
      loginshared();

    }

    public void onLogin(View view)
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


                                toast("Login Successful");
                                Intent intent = new Intent(login.this, Location.class);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Handle navigation view item clicks here.

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navbar, menu);
        return true;
    }


}
