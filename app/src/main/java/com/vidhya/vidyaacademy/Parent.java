package com.vidhya.vidyaacademy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class Parent extends FragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_parent_new );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        NavigationView navigationView = findViewById( R.id.nav_view );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener( this );


        Bundle bundle1 = new Bundle();
        addFragment( new F_Parent_Profile(), true, FragmentTransaction.TRANSIT_NONE, "Parent_Profile", bundle1 );
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 1) {
            super.onBackPressed();
            moveTaskToBack( true );
            finish();
            //additional code
            android.os.Process.killProcess( android.os.Process.myPid() );
            System.exit( 1 );
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.parent_new, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected( item );
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_about) {
           /* Intent i=new Intent(getApplicationContext(),Parent_About.class);
            startActivity(i);*/

            Bundle bundle2 = new Bundle();
            addFragment( new F_Parent_About(), true, FragmentTransaction.TRANSIT_NONE, "Parent_Profile", bundle2 );


        } else if (id == R.id.nav_help) {
            Bundle bundle2 = new Bundle();
            addFragment( new F_Parent_Help(), true, FragmentTransaction.TRANSIT_NONE, "Help", bundle2 );

        } else if (id == R.id.nav_LiveVideo) {

        } else if (id == R.id.nav_profilePa) {
           /* Intent i=new Intent(getApplicationContext(),Parent_Profile.class);
            startActivity(i);*/


            Bundle bundle1 = new Bundle();
            addFragment( new F_Parent_Profile(), true, FragmentTransaction.TRANSIT_NONE, "Parent_Profile", bundle1 );

        } else if (id == R.id.nav_logoutPa) {

            SharedPreferences preferences = getApplicationContext().getSharedPreferences( "MyShared", Context.MODE_PRIVATE );
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent( Parent.this, Login.class );
            intent.putExtra( "finish", true );
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK );
            startActivity( intent );

            finish();

        }

        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    public void addFragment(Fragment fragment, boolean addToBackStack,
                            int transition, String name, Bundle bndle) {
        FragmentTransaction ft = Parent.this
                .getSupportFragmentManager().beginTransaction();
        ft.replace( R.id.frame_parent, fragment );
        ft.setTransition( transition );
        fragment.setArguments( bndle );
        if (addToBackStack)
            ft.addToBackStack( name );
        ft.commit();
    }
}
