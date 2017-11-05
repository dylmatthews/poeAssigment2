package am.dx.varsityspecials.www.varsityspecials;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class updatePassword extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText etEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        etEmail = (EditText) findViewById(R.id.etEmailReset);
        mAuth=FirebaseAuth.getInstance();
    }



    public void onPassReset(View view)
    {
        String email = etEmail.getText().toString();
        if (!email.isEmpty()) {
            if (mAuth != null) {
                Log.w(" if Email authenticated", "Recovery Email has been  sent to " + email);
                Toast.makeText(this, "Password email sent", Toast.LENGTH_SHORT).show();
                mAuth.sendPasswordResetEmail(email);
            } else {
                Log.w(" error ", " something went wrong ");
            }
        }
    }
}
