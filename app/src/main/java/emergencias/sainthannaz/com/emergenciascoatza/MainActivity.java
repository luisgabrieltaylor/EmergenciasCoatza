package emergencias.sainthannaz.com.emergenciascoatza;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import emergencias.sainthannaz.com.emergenciascoatza.app.MyApplication;


public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FragmentManager FM;
    FragmentTransaction FT;
    private long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MyApplication.getInstance().trackScreenView("Main Screen");
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView= (NavigationView) findViewById(R.id.shitstuff);
        View hView = navigationView.getHeaderView(0);

        FM= getSupportFragmentManager();
        FT= FM.beginTransaction();
        FT.replace(R.id.containerView, new TabFragment()).commit();

        navigationView.setItemIconTintList(null);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();

                if (item.getItemId()== R.id.nav_item_home) {
                    FragmentTransaction fragmentTransaction1=FM.beginTransaction();
                    fragmentTransaction1.replace(R.id.containerView,new TabFragment()).commit();
                }

                if (item.getItemId()== R.id.nav_item_about) {

                    FragmentTransaction fragmentTransaction= FM.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, new AboutFragment()).commit();
                }

                if (item.getItemId()== R.id.nav_item_share) {
                    try {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(Intent.EXTRA_SUBJECT, "Emergencias Coatza");
                        String sAux = "\nPermiteme recomendarte esta aplicación, contiene los números de emergencia en la ciudad de Coatzacoalcos.\n\n";
                        sAux = sAux + "https://play.google.com/store/apps/details?id=emergencias.sainthannaz.com.emergenciascoatza&hl=es_419 \n\n";
                        i.putExtra(Intent.EXTRA_TEXT, sAux);
                        startActivity(Intent.createChooser(i, "¡Comparte la app!"));
                    } catch(Exception e) {
                        //e.toString();
                    }
                }

                if (item.getItemId()==R.id.nav_item_settings)
                {

                    //FragmentTransaction fragmentTransaction1=FM.beginTransaction();
                    //fragmentTransaction1.replace(R.id.containerView,new TabFragment()).commit();
                }
                if (item.getItemId()==R.id.nav_item_help)
                {

                    //FragmentTransaction fragmentTransaction1=FM.beginTransaction();
                    //fragmentTransaction1.replace(R.id.containerView,new TabFragment()).commit();
                }
                return false;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();


    }

    @Override
    public void onBackPressed() {

        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), getString(R.string.toast_PressAgain), Toast.LENGTH_SHORT)
                    .show();
        }
        back_pressed = System.currentTimeMillis();
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
    */

    @Override
    public void onResume() {
        super.onResume();
        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("Main Acivity");
    }

}
