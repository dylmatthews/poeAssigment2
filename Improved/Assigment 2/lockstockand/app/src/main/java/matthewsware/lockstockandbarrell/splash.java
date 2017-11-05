package matthewsware.lockstockandbarrell;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

public class splash extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent HelpIntent = new Intent(splash.this,help.class);
                startActivity(HelpIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
