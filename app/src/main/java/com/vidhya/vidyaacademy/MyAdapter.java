package com.vidhya.vidyaacademy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter {
    Context context;
    ArrayList<String> arrayList=new ArrayList<>();
    public MyAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource,objects);
        this.context = context;
        arrayList=objects;

    }
    @Override
    public int getCount() {
        return super.getCount();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v=convertView;
        LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v=inflater.inflate(R.layout.activity_layout_for_listview,null);
        TextView tv_classlist_class=(TextView)v.findViewById(R.id.tv_classlist_class);
        tv_classlist_class.setText(arrayList.get(position).toString());



        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle3=new Bundle();
                bundle3.putString("student_list",arrayList.get(position));

                Log.e("student_list",arrayList.get(position));

                Fragment fragment = new F_Admin_StudentList();
                //FragmentManager fragmentManager = getFragmentManager();;
                fragment.setArguments( bundle3 );
                FragmentManager fragmentManager =  ((FragmentActivity)getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_admin, fragment);
                fragmentTransaction.addToBackStack(null);
                // fragmentTransaction.commit();

                fragmentTransaction.commit();


            }
        });

        return v;

    }


}
