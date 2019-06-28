package com.vidhya.vidyaacademy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class F_Admin_ClassList_list extends Fragment {


    DatabaseReference databaseReference;
    ListView list_classlist;
    MyAdapter adapter;
    String[] default_items=new String[]{"ClassA","ClassB"};
    private final static String TAG="Aswathy";
    List<String> itemlist;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    ArrayList<String> arrayList=new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classlist_list_admin, container, false);

        list_classlist=view.findViewById(R.id.list_classlist);
        sharedPreferences = getActivity().getSharedPreferences("MyShared", Context.MODE_PRIVATE);
        String userid=sharedPreferences.getString("userid","");



        itemlist=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("users/admin/"+ userid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemlist.clear();
                arrayList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    /*for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {*/

                    Log.e(TAG, dataSnapshot1.getKey());
                    /*}*/

                    switch (dataSnapshot1.getKey().toString()){

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
                            arrayList.add(dataSnapshot1.getKey());

                    }

                   /* if (dataSnapshot1.getKey().equals("password")){
                        continue;
                    }
                    else {
                        arrayList.add(dataSnapshot1.getKey());
                    }*/

                }
                adapter = new MyAdapter(getActivity(), R.layout.fragment_classlist_list_admin,arrayList);
                list_classlist.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"there is some error found",Toast.LENGTH_SHORT).show();

            }
        });
        list_classlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),"clicked on"+position,Toast.LENGTH_SHORT).show();

                list_classlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getActivity(),"clicked on"+position,Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(getActivity(), MainActivty_Tab.class);
                        i.putExtra("student_list",arrayList.get(position));
                        Log.e("position",arrayList.get(position));
                        startActivity(i);
/*
                        Bundle bundle3=new Bundle();
                        bundle3.putString("student_list",arrayList.get(position));

                        Log.e("student_list",arrayList.get(position));

                        Fragment fragment = new F_StudentList_Admin();
                        //FragmentManager fragmentManager = getFragmentManager();;
                        fragment.setArguments( bundle3 );
                        FragmentManager fragmentManager =  ((FragmentActivity)getContext()).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_princi, fragment);
                        fragmentTransaction.addToBackStack(null);
                        // fragmentTransaction.commit();

                        fragmentTransaction.commit();*/

                    }
                });

            }
        });


        return view;
    }
}
