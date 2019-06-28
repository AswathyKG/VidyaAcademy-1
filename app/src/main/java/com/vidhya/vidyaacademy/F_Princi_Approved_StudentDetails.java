package com.vidhya.vidyaacademy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class F_Princi_Approved_StudentDetails extends Fragment {

    TextView tv_stud_name_approved, tv_stud_address_approved, tv_stud_pname_approved;
    Button btn_cancel_princi_studdetails, btn_approve_princi_studdetails;
    CircleImageView tv_stud_photo_approved;
    String Name, Address, ParentName,Status,Image;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_approved_student_details_princi, container, false);

        tv_stud_name_approved = view.findViewById(R.id.tv_stud_name_approved);
        tv_stud_address_approved = view.findViewById(R.id.tv_stud_address_approved);
        tv_stud_pname_approved = view.findViewById(R.id.tv_stud_pname_approved);
         btn_cancel_princi_studdetails = view.findViewById( R.id.btn_cancel_princi_studdetails );
        //btn_approve_princi_studdetails = view.findViewById( R.id.btn_approve_princi_studdetails );
        tv_stud_photo_approved = view.findViewById(R.id.tv_stud_photo_approved);
/*
        Intent i = getIntent();
        final String ClassID = i.getStringExtra("ClassID");
        final String AdminID = i.getStringExtra("AdminID");
        final String RegNo = i.getStringExtra("RegNo");*/

        final String AdminID = getArguments().getString("AdminID");
        final String ClassID = getArguments().getString("ClassID");
        final String RegNo = getArguments().getString("StudID");
        Log.e("Bundle_value",RegNo);




        databaseReference = FirebaseDatabase.getInstance().getReference("users/admin/" + AdminID + "/" + ClassID + "/" + RegNo);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    //Log.e(  "Approval_Data",dataSnapshot.child( "name" ).getValue().toString());

                    Name = dataSnapshot.child("name").getValue().toString();
                    Address = dataSnapshot.child("address").getValue().toString();
                    ParentName = dataSnapshot.child("parent_name").getValue().toString();

                    tv_stud_name_approved.setText(Name);
                    tv_stud_address_approved.setText(Address);
                    tv_stud_pname_approved.setText(ParentName);

                    Glide.with(getActivity()).load(dataSnapshot.child("image").getValue().toString()).into(tv_stud_photo_approved);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Log.e("ClassID_Approval", ClassID);
        Log.e("AdminID_Approval", AdminID);
        Log.e("RegNo_Approval", RegNo);

       /* btn_approve_princi_studdetails.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference = database.getReference( "users/parent/" );

                databaseReference.addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ApproveStudentDetails_adapter studentDetailsAdapter = new ApproveStudentDetails_adapter( Name, Address, ParentName, RegNo, ClassID, AdminID );

                        databaseReference.child( ParentName ).setValue( studentDetailsAdapter );
                        Toast.makeText( getActivity(), "Data Approved" , Toast.LENGTH_LONG ).show();


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                } );


            }
        } );
*/
        btn_cancel_princi_studdetails.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference1 = database.getReference( "users/parent/" );
                databaseReference1.addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        ApproveStudentDetails_adapter approveStudentDetails_adapter = new ApproveStudentDetails_adapter( Name, Address, ParentName, RegNo, ClassID, AdminID, Status, Image );

                        databaseReference1.child( ParentName ).setValue( approveStudentDetails_adapter );
                        Toast.makeText( getActivity(), "Data Approved", Toast.LENGTH_LONG ).show();
                        databaseReference.child( "status" ).setValue( "pending" );

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                } );
            }
        } );

        return view;
    }
}
