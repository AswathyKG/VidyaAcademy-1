package com.vidhya.vidyaacademy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pl.droidsonroids.gif.GifImageView;

import static com.vidhya.vidyaacademy.Login.userid;

public class PersonalDetails extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference( "users" );

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    EditText tv_profile_stud_name, tv_profile_stud_address, tv_profile_stud_parentname;
    GifImageView iv_profile_student;

    String nadeem;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate( R.layout.fragment_personaldetails, container, false );
        tv_profile_stud_name = v.findViewById( R.id.et_profile_stud_name );
        tv_profile_stud_address = v.findViewById( R.id.et_profile_stud_address );
        tv_profile_stud_parentname = v.findViewById( R.id.et_profile_stud_parentname );
        iv_profile_student = v.findViewById( R.id.iv_profile_student );
        String UID = getArguments().getString( "nadeem" );
        String Student_name = getArguments().getString( "student_name" );
        Log.e( "Bundle_value", UID );
        Log.e( "Bundle_value", Student_name );

        sharedPreferences = getActivity().getSharedPreferences( "MyShared", Context.MODE_PRIVATE );
        String userid = sharedPreferences.getString( "userid", "" );

        databaseReference = FirebaseDatabase.getInstance().getReference( "users/admin/" + userid + "/" + UID + "/" + Student_name );

        databaseReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    /*for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {*/
                    Log.e( "classlist", dataSnapshot1.getKey() );
                    Log.e( "studentlist", dataSnapshot.getChildren().toString() );

                    nadeem = dataSnapshot.child( "image" ).getValue().toString();


                    String stud_name = dataSnapshot.child( "name" ).getValue().toString();
                    String stud_address = dataSnapshot.child( "address" ).getValue().toString();
                    String stud_parentname = dataSnapshot.child( "parent_name" ).getValue().toString();


                    Log.e( "image_url", dataSnapshot.child( "image" ).toString() );
                    /* Glide.with(getContext()).load(dataSnapshot.child("image")).into(iv_profile_student);*/
                    tv_profile_stud_name.setText( stud_name );
                    tv_profile_stud_address.setText( stud_address );
                    tv_profile_stud_parentname.setText( stud_parentname );


                    /*}*/

                }
                Glide.with( getActivity() ).load( nadeem ).into( iv_profile_student );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText( getActivity(), "there is some error found", Toast.LENGTH_SHORT ).show();


            }
        } );


       /* sharedPreferences = getActivity().getSharedPreferences("MyShared", Context.MODE_PRIVATE);
        String userid = sharedPreferences.getString("userid", "");




*/
        return v;


    }

}
