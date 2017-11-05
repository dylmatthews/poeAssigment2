package am.dx.varsityspecials.www.varsityspecials;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class Welcome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected( MenuItem item) {

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
