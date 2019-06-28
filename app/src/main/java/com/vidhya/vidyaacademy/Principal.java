package com.vidhya.vidyaacademy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Principal extends FragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CircleImageView  iv_profile_princi;
    TextView tv_navhead_princiname,tv_navhead_princimail;
    String userid;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__principal);
        Toolbar toolbar = findViewById(R.id.toolbarP);
        setSupportActionBar(toolbar);
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.getHeaderView(0);
        iv_profile_princi=headerLayout.findViewById( R.id.iv_profile_princi );
        tv_navhead_princiname=headerLayout.findViewById( R.id.tv_navhead_princiname );
        tv_navhead_princimail=headerLayout.findViewById( R.id.tv_navhead_princimail );


        Bundle bundle1=new Bundle();
        addFragment(new F_Principal_Profile(),true, FragmentTransaction.TRANSIT_NONE,"Parent_Profile",bundle1);


        sharedPreferences = getApplicationContext().getSharedPreferences( "MyShared", Context.MODE_PRIVATE );
        userid = sharedPreferences.getString( "userid", "" );
        databaseReference = FirebaseDatabase.getInstance().getReference( "users/principal/" + userid );
        databaseReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    String Name = dataSnapshot.child( "name" ).getValue().toString();
                    String Email = dataSnapshot.child( "email" ).getValue().toString();
                    tv_navhead_princiname.setText( Name );
                    tv_navhead_princimail.setText( Email );

                    Glide.with( getApplicationContext() ).load( dataSnapshot.child( "image" ).getValue() ).into( iv_profile_princi );
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        /*Intent i=new Intent(getApplicationContext(),ProfileScreenXMLUIDesign.class);
        startActivity(i);*/
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
        getMenuInflater().inflate(R.menu.main_activity__principal, menu);
        return true;
    }

/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       */
/* if (id == R.id.action_settings) {
            return true;
        }*//*


        return super.onOptionsItemSelected(item);
    }
*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
           /* Intent i=new Intent(getApplicationContext(), Principal_Profile.class);
            startActivity(i);*/

            Bundle bundle1=new Bundle();
            addFragment(new F_Principal_Profile(),true, FragmentTransaction.TRANSIT_NONE,"Parent_Profile",bundle1);

            // Handle the camera action
        } else if (id == R.id.nav_addAdmin) {
           /* Intent i=new Intent(getApplicationContext(),AddAdmin.class);
            startActivity(i);*/

            Bundle bundle1=new Bundle();
            addFragment(new F_AddAdmin(),true, FragmentTransaction.TRANSIT_NONE,"Parent_Profile",bundle1);


        } else if (id == R.id.nav_approval) {
            /*Intent i=new Intent(getApplicationContext(), Princi_AdminList.class);
            startActivity(i);*/
            Bundle bundle1=new Bundle();
            addFragment(new F_Princi_AdminList(),true, FragmentTransaction.TRANSIT_NONE,"Parent_Profile",bundle1);


        } else if (id == R.id.nav_logoutP) {
            SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyShared", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(Principal.this, Login.class);
            intent.putExtra("finish", true);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            finish();


        } else if (id == R.id.nav_pending) {

          /*  Intent i=new Intent(getApplicationContext(),Pending_Request.class);
            startActivity(i);*/
            Bundle bundle1=new Bundle();
            bundle1.putString( "AdminID",userid );
            addFragment(new F_Princi_Pending(),true, FragmentTransaction.TRANSIT_NONE,"Parent_Profile",bundle1);


        } else if (id == R.id.nav_approved) {

           /* Intent i=new Intent(getApplicationContext(),Approved.class);
            startActivity(i);*/
            Bundle bundle1=new Bundle();
            bundle1.putString( "AdminID",userid );
           // addFragment(new F_Princi_Approved(),true, FragmentTransaction.TRANSIT_NONE,"Parent_Profile",bundle1);


        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void addFragment(Fragment fragment, boolean addToBackStack,
                            int transition, String name, Bundle bndle) {
        FragmentTransaction ft = Principal.this
                .getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_princi, fragment);
        ft.setTransition(transition);
        fragment.setArguments(bndle);
        if (addToBackStack)
            ft.addToBackStack(name);
        ft.commit();
    }
}
