package com.vidhya.vidyaacademy;

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

public class F_Princi_Pending_Classlist extends Fragment {


    ArrayList<Pending_Classlist_Adpter> arrayList1;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recy_pending_classlist, container, false);


        /*Intent i = getIntent();
        AdminID = i.getStringExtra("AdminID");
        Log.e("Admin", AdminID);*/

       final String AdminID = getArguments().getString("AdminID");

        Log.e("Bundle_value",AdminID);




        arrayList1 = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("users/admin/" + AdminID);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerforpendingclasslist);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManagern = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManagern);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Log.e("children", dataSnapshot1.getChildren().toString());

                    switch (dataSnapshot1.getKey().toString()) {


                        case "password":
                            continue;
                        case "name":
                            continue;
                        case "address":
                            continue;
                        case "email":
                            continue;
                        case "phno":
                            continue;
                        case "image":
                            continue;
                        case "pending":
                            continue;
                        default:
                            Pending_Classlist_Adpter pending_request_adapter = new Pending_Classlist_Adpter(dataSnapshot1.getChildren().toString(), dataSnapshot1.getKey().toString());
                            arrayList1.add(pending_request_adapter);


                    }


                }
                RecyclerViewFor_PendingClasslist_Princi recyclerViewForPendingClasslist = new RecyclerViewFor_PendingClasslist_Princi(getActivity(), arrayList1, AdminID);
                recyclerView.setAdapter(recyclerViewForPendingClasslist);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "there is some error found", Toast.LENGTH_SHORT).show();

            }
        });


        return view;
    }
}
