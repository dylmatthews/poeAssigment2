package am.dx.varsityspecials.www.varsityspecials;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.FirebaseDatabase;

public class signUp extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    SharedPreferences login;

    String prefName = "login";

    private  String email= "";
    private  String password="";
    EditText em;
    EditText pa;
    EditText cpa;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        em = (EditText) findViewById(R.id.etEmail) ;
        pa= (EditText) findViewById(R.id.etPassword);
        cpa = (EditText) findViewById(R.id.etComPassword);
        if (FirebaseDatabase.getInstance() != null) {
            //toast("Gone online onResume Area");

            FirebaseDatabase.getInstance().goOnline();



        }

    }



    public void toast(String t)
    {
        Toast output= Toast.makeText(this, t, Toast.LENGTH_SHORT);
        output.setGravity(Gravity.CENTER,0,0);
        output.show();
    }

    public void onSignUp(View view)
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
                                    Intent in = new Intent(signUp.this, login.class);
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



}
