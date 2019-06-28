package com.vidhya.vidyaacademy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;

public class RecyclerViewForStudentInfo_admin extends RecyclerView.Adapter<RecyclerViewForStudentInfo_admin.ViewHolder> {


    TextView Student_Name, Register_No, Total_Grade,Adddress;
    CircleImageView iv_student_photo;
    ArrayList<Admin_Stud_CardDetails> list;
    Context context;
    String uids;


    public RecyclerViewForStudentInfo_admin(Context context, ArrayList<Admin_Stud_CardDetails> arrayList, String uidw) {
        this.list = arrayList;
        this.context = context;
        this.uids = uidw;

    }


    @NonNull
    @Override
    public RecyclerViewForStudentInfo_admin.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView view = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_admin_card_stud, viewGroup, false);
        return new RecyclerViewForStudentInfo_admin.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewForStudentInfo_admin.ViewHolder viewHolder, final int i) {
        final int j;
        final CardView cardView = viewHolder.cardView;
        final Context context = cardView.getContext();
        iv_student_photo = cardView.findViewById(R.id.iv_student_photo);
        Student_Name = (TextView) cardView.findViewById(R.id.tv_card_name);
        Register_No = (TextView) cardView.findViewById(R.id.tv_card_reg_no);
        Total_Grade = (TextView) cardView.findViewById(R.id.tv_card__grade);
       // Adddress=(TextView) cardView.findViewById(R.id.tv_card_address);

        Student_Name.setText(list.get(i).getName().toString());
        Register_No.setText(list.get(i).getRegno().toString());
        Total_Grade.setText(list.get(i).getParent_name().toString());
        //Adddress.setText(list.get(i).getAddress().toString());

        Glide.with(context).load(list.get(i).getImage()).into(iv_student_photo);


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("nadeem",uids);


                Intent intent = new Intent(v.getContext(), MainActivty_Tab.class);
                intent.putExtra("Student Name",list.get(i).getRegno());
                intent.putExtra("UID",uids);
                context.startActivity(intent);

               /* Bundle bundle3=new Bundle();
                bundle3.putString("UID",uids);
                bundle3.putString("Student Name",list.get(i).getRegno());

               Fragment fragment = new MyAdapter_Tab();
               // FragmentManager fragmentManager = getFragmentManager();;
                fragment.setArguments( bundle3 );
                FragmentManager fragmentManager =  ((FragmentActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
              fragmentTransaction.replace(R.id.frame_princi, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
            }
        });



    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        public ViewHolder(View view) {
            super(view);

            cardView = (CardView) view.findViewById(R.id.cardview_forStudent);

        }
    }

    @Override
    public int getItemCount() {
        int len = list.size();
        return len;
    }
}
