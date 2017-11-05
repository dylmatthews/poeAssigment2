package am.dx.varsityspecials.www.varsityspecials;


import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

/**
 * Created by dylanmatthews on 2017/08/09.
 */

public class base_activity extends Activity implements NavigationView.OnNavigationItemSelectedListener {


    private NavigationView navigationView;

    protected void onCreateDrawer() { //sets drawer
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

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
        return true;
    }


}
