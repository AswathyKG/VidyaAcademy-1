package com.vidhya.vidyaacademy;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class F_Admin_Profile extends Fragment implements IOnBackPressed {


    TextView tv_profile_a_name,tv_profile_a_address,tv_profile_a_phno,tv_profile_a_email;
    CircleImageView iv_profile_admin;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_profile, container, false);


        tv_profile_a_name=view.findViewById(R.id.tv_profile_a_name);
        tv_profile_a_address=view.findViewById(R.id.tv_profile_a_address);
        tv_profile_a_phno=view.findViewById(R.id.tv_profile_a_phno);
        tv_profile_a_email=view.findViewById(R.id.tv_profile_a_email);
        iv_profile_admin=view.findViewById(R.id.iv_profile_admin);


        sharedPreferences = getActivity().getSharedPreferences("MyShared", Context.MODE_PRIVATE);
        String userid=sharedPreferences.getString("userid","");
        databaseReference= FirebaseDatabase.getInstance().getReference("users/admin/"+ userid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Log.e("Admin Profile", dataSnapshot1.getKey());

                    if (dataSnapshot.getKey().equals("password")){
                        continue;
                    }
                    else {
                        String Name=dataSnapshot.child("name").getValue().toString();
                        String Address=dataSnapshot.child("address").getValue().toString();
                        String Email=dataSnapshot.child("email").getValue().toString();
                        String Phno=dataSnapshot.child("phno").getValue().toString();



                        tv_profile_a_name.setText(Name);
                        tv_profile_a_address.setText(Address);
                        tv_profile_a_email.setText(Email);
                        tv_profile_a_phno.setText(Phno);

                        Glide.with( getActivity() ).load( dataSnapshot.child( "image" ).getValue() ).into( iv_profile_admin );

                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"there is some error found",Toast.LENGTH_SHORT).show();

            }
        });
        return  view;


    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}
