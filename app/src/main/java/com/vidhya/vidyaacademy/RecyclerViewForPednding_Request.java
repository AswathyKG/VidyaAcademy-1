package com.vidhya.vidyaacademy;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewForPednding_Request extends RecyclerView.Adapter<RecyclerViewForPednding_Request.ViewHolder> {
    TextView tv_card_name_admin_pr ;
    CircleImageView iv_card_photo_admin_pr;
    ArrayList<Peding_Request_Adapter> list;
    Context context;
    ImageButton ib_edit_AdminList,ib_delete_AdminList;

    public RecyclerViewForPednding_Request(Context context, ArrayList<Peding_Request_Adapter> arrayList) {
        this.list = arrayList;
        this.context = context;



    }

    @NonNull
    @Override
    public RecyclerViewForPednding_Request.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        CardView view = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_pending_request_card, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewForPednding_Request.ViewHolder viewHolder, final int i) {
        final int j;
        final CardView cardView = viewHolder.cardView;


        tv_card_name_admin_pr= cardView.findViewById(R.id.tv_card_name_admin_pr);
        iv_card_photo_admin_pr=cardView.findViewById( R.id.iv_card_photo_admin_pr );
        tv_card_name_admin_pr.setText(list.get(i).getName().toString());

        Glide.with( context ).load( list.get( i ).getImage().toString()).into(iv_card_photo_admin_pr  );



        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /*Intent intent = new Intent(context, Pending_Classlist.class);
                intent.putExtra("AdminID",list.get( i ).getUserid());
                Log.e("AdminID",list.get(i).getUserid());
                context.startActivity(intent);*/

                Bundle b3=new Bundle();
                b3.putString("AdminID",list.get( i ).getUserid());

                Log.e("AdminID",list.get( i ).getUserid());

                Fragment fragment = new F_Princi_Pending_Classlist();
                //FragmentManager fragmentManager = getFragmentManager();;
                fragment.setArguments( b3 );
                FragmentManager fragmentManager =  ((FragmentActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_princi, fragment);
                fragmentTransaction.addToBackStack(null);
                // fragmentTransaction.commit();*/

                fragmentTransaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        int len = list.size();
        return len;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public CardView cardView;
        public ViewHolder(View view) {
            super(view);

            cardView = (CardView) view.findViewById(R.id.cardview_forAdmin_pr);

        }
    }
}
