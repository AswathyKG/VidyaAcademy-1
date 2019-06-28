package com.vidhya.vidyaacademy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.vidhya.vidyaacademy.Login.userid;

public class MainActivty_Tab extends AppCompatActivity  {
    TabLayout tabLayout;
    ViewPager viewPager;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activty__tab);







        Intent i = getIntent();
        String nadeem = i.getStringExtra("UID");
        String Student_name=i.getStringExtra("Student Name");
        sharedPreferences = getApplicationContext().getSharedPreferences("MyShared", Context.MODE_PRIVATE);
        String userid = sharedPreferences.getString("userid", "");
      //  Log.e("UID",nadeem);
      //  Log.e("student_name",Student_name);



       /* String userid = sharedPreferences.getString("userid", "");


        Intent i = getIntent();
        String uid = i.getStringExtra("Student Name");
        Log.e(uid,"intent value");

        Bundle UID = new Bundle();
        UID.putString(uid, "Student Name");
        PersonalDetails personalDetails = new PersonalDetails();
        personalDetails.setArguments(UID);

        Bundle usrid=new Bundle();
        usrid.putString(userid,"userid");
        PersonalDetails personalDetails1=new PersonalDetails();
        personalDetails1.setArguments(usrid);
*/



        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("PersonalDetails"));
        tabLayout.addTab(tabLayout.newTab().setText("MarkDetails"));
/*
        tabLayout.addTab(tabLayout.newTab().setText("Movie"));
*/
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        /*myRef = FirebaseDatabase.getInstance().getReference("users/admin/" + userid + "/" + UID);
        // Inflate the layout for this fragment
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot2:dataSnapshot1.getChildren()){
                        Log.e("classlist", dataSnapshot2.getKey());
                        Log.e("studentlist",dataSnapshot2.getChildren().toString());
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "there is some error found", Toast.LENGTH_SHORT).show();


            }
        });
*/

        final MyAdapter_Tab tabadapter = new MyAdapter_Tab(this, getSupportFragmentManager(), tabLayout.getTabCount(),nadeem,Student_name);
        viewPager.setAdapter(tabadapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void setSupportActionBar(Toolbar toolbar) {
    }


}