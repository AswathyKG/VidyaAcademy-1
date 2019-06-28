package com.vidhya.vidyaacademy;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Enter_Student_MarkDetails extends Fragment {

    EditText edt_addstud_class_md,edt_addstud_regno_md;


    Context con;
    TableLayout table;
    TableRow tr[] = new TableRow[9];
    EditText txt[] = new EditText[9];
    EditText txt1[] = new EditText[9];
    //ImageView img[] = new ImageView[5];
    Button img[]=new Button[9];
    int count ;
    int lastDeletedIndex;
    int lastPopulatedIndex;
    boolean isDel;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;
    Button btn_enter_student_mark_details;

    String classname,regno;

    String admin;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate( R.layout.enter_student_details
                ,container,false );
        btn_enter_student_mark_details=view.findViewById( R.id.btn_enter_student_mark_details );
        sharedPreferences = getActivity().getSharedPreferences( "MyShared", Context.MODE_PRIVATE );
        admin = sharedPreferences.getString( "userid", "" );
        edt_addstud_regno_md=view.findViewById( R.id.edt_addstud_regno_md );
        edt_addstud_class_md=view.findViewById( R.id.edt_addstud_class_md );



        // Log.d("","On Create");
        con = this.getActivity();
        count = 1;

        table = (TableLayout)view.findViewById(R.id.imageLin);
        tr[count] = new TableRow(this.getActivity());
        tr[count].setId(count);
        tr[count].setLayoutParams(new TableRow.LayoutParams(    TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

        txt[count] = new EditText(this.getActivity());
        txt[count].setId(count);
        txt[count].setText("1");
        txt[count].setMaxLines(1);
        txt[count].setWidth(250);
        txt1[count] = new EditText(this.getActivity());
        txt1[count].setId(count);
        txt1[count].setText("1");
        txt1[count].setMaxLines(1);
        txt1[count].setWidth(250);

        img[count] = new Button(this.getActivity());
        img[count].setId(count);
        img[count].setText("+");
        //img[count].setBackgroundResource(R.drawable.ic_indeterminate_check_box_black_24dp);
        img[count].setPadding(5, 7, 20, 0);


        tr[count].addView(txt[count]);
        tr[count].addView(txt1[count]);
        tr[count].addView(img[count]);
        tr[count].setPadding(20, 20, 20, 20);

        img[count].setOnClickListener(new OnClick(img[count]));

        table.addView(tr[count]);


        btn_enter_student_mark_details.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                classname=edt_addstud_class_md.getText().toString();
                regno=edt_addstud_regno_md.getText().toString();


                databaseReference=firebaseDatabase.getReference("users/admin/" + admin + classname + regno );
                databaseReference.addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


/*
                        for(int i = 0;i < txt.length;i++) {
                            String name = txt[i].getText().toString();
                            databaseReference.child( name ).setVallue( name );
                        }*/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                } );
            }
        } );


        return view;

    }



    class OnClick implements View.OnClickListener {

        View view;

        Button addIcon;

        public OnClick(View view) {

            this.addIcon = (Button) view;

        }

        @Override
        public void onClick(View arg0) {

            int id=addIcon.getId();
            if(addIcon.getText().toString().equals("-"))
            {

                if ((id >0  )) {
                    isDel=true;
                    count=id;
                    lastDeletedIndex=id;
                    txt[count]=null;
                    txt1[count] = null;
                    img[count]=null;
                    table.removeView(tr[count]);
                    tr[count]=null;
                    Log.d("", "Cancel img Id" + addIcon.getId());
                }


            } else {
                if (count <9) {
                    if(table.getChildCount()!=9)
                    {
                        //addIcon.setBackgroundDrawable(getResources().getDrawable(R.drawable.delete));
                        addIcon.setText("-");


                        addIcon.setId(count);
                        if(!isDel) {
                            count++;
                        }else
                        {
                            count=lastDeletedIndex;
                        }


                        tr[count] = new TableRow(con);
                        tr[count].setId(count);


                        img[count] = new Button(con);
                        img[count].setId(count);
                        // img[count].setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_add_box_black_24dp));
                        img[count].setText("+");
                        img[count-1].setText("-");
                        img[count].setPadding(3, 7, 20, 0);
                        img[count].setOnClickListener(new OnClick(img[count]));

                        txt[count] = new EditText(con);
                        txt[count].setId( count);
                        txt[count].setText(""+count);
                        txt[count].setMaxLines(1);
                        txt[count].setWidth(250);

                        txt1[count] = new EditText(con);
                        txt1[count].setId( count);
                        txt1[count].setText(""+count);
                        txt1[count].setMaxLines(1);
                        txt1[count].setWidth(250);

                        tr[count].addView(txt[count]);
                        tr[count].addView(txt1[count]);

                        tr[count].addView(img[count]);

                        tr[count].setPadding(20, 20, 0, 0);
                        table.addView(tr[count]);

                        isDel=false;

                        lastPopulatedIndex=count;

                        Log.d("", "Cancel img Id" + addIcon.getId());
                    }
                }

            }

        }


    }

}
