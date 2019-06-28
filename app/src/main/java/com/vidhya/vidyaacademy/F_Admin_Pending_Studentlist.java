package com.vidhya.vidyaacademy;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class F_Admin_Pending_Studentlist extends Fragment {


    ArrayList<Pending_Studlist_Adapter> arrayList;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recy_pending_studentlist, container, false);


        /*Intent i = getIntent();
        final String ClassID = i.getStringExtra("ClassID");
        final String AdminID = i.getStringExtra("AdminID");*/

        final String AdminID = getArguments().getString("AdminID");
        final String ClassID = getArguments().getString("ClassID");

        Log.e("Bundle_value",AdminID);


        final RecyclerView princi_recyclerforstudcard = (RecyclerView) view.findViewById(R.id.pending_studentlist_recy);

        princi_recyclerforstudcard.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        princi_recyclerforstudcard.setLayoutManager(layoutManager);




        sharedPreferences = getActivity().getSharedPreferences("MyShared", Context.MODE_PRIVATE);
        String userid = sharedPreferences.getString("userid", "");

        arrayList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("users/admin/" + AdminID + "/" + ClassID);

        Log.e("ClassID", ClassID);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Log.e("classlist", dataSnapshot1.getKey());

                    Log.e("status", dataSnapshot1.child("status").getValue().toString());

                    if (dataSnapshot1.child("status").getValue().toString().equals("request") || (dataSnapshot1.child("status").getValue().toString().equals("pending"))) {
                        Log.e("pending", dataSnapshot1.getChildren().toString());
                        Pending_Studlist_Adapter pending_studlist_adapter = new Pending_Studlist_Adapter(dataSnapshot1.child("name").getValue().toString(), dataSnapshot1.child("address").getValue().toString(), dataSnapshot1.child("parent_name").getValue().toString(), dataSnapshot1.child("image").getValue().toString(), dataSnapshot1.getKey().toString());
                        arrayList.add(pending_studlist_adapter);
                    } else {
                        continue;
                    }

                }
                RecyclerViewFor_PendingStudentList_Admin playAdapter1 = new RecyclerViewFor_PendingStudentList_Admin(getActivity(), arrayList, ClassID, AdminID);
                princi_recyclerforstudcard.setAdapter(playAdapter1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "there is some error found", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }
}
