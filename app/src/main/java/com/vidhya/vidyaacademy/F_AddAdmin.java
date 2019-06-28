package com.vidhya.vidyaacademy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class F_AddAdmin extends Fragment implements AdapterView.OnItemSelectedListener {

    EditText edt_addadmin_name, edt_addadmin_address, edt_addadmin_email, edt_addadmin_phno, edt_addadmin_password, edt_addadmin_userid;
    Spinner spinner_addadmin;
    Button btn_addadmin_upload, btn_addadmin_add;
    public AddAdmin_adapter addAdminPojo;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String name, email, phno, password, address;
    private AwesomeValidation awesomeValidation;
    String regexPassword = ".{8,}";



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate( R.layout.fragment_addadmin,container,false );

        edt_addadmin_name = view.findViewById( R.id.edt_addadmin_name );
        edt_addadmin_address = view.findViewById( R.id.edt_addadmin_address );
        edt_addadmin_email = view.findViewById( R.id.edt_addadmin_email );
        edt_addadmin_phno = view.findViewById( R.id.edt_addadmin_phno );
        edt_addadmin_password = view.findViewById( R.id.edt_addadmin_password );
        edt_addadmin_userid = view.findViewById( R.id.edt_addadmin_userid );
        //spinner_addadmin=(Spinner)view.findViewById(R.id.spinner_addadmin);
      //  btn_addadmin_upload=(Button)view.findViewById(R.id.btn_addadmin_upload);
        btn_addadmin_add = (Button) view.findViewById( R.id.btn_addadmin_add );

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);






      /*  spinner_addadmin.setOnItemSelectedListener(F_AddAdmin.this);
        List<String> classes= new ArrayList<>();
        classes.add("classA");
        classes.add("classB");
        classes.add("classC");
        classes.add("classD");
        classes.add("classE");
        classes.add("classF");
        classes.add("classG");

        dataAdapter = new ArrayAdapter<String>(F_AddAdmin.this, android.R.layout.simple_spinner_item, classes);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_addadmin.setAdapter(dataAdapter);*/


        String [] classes = {"classA","classB","classC","classD","classE","classF","classG"};
      //  Spinner spinner = (Spinner) view.findViewById(R.id.spinner_addadmin);
        ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, classes);
        LTRadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        //spinner.setAdapter(LTRadapter);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference( "users/admin/" );

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        awesomeValidation.addValidation(getActivity(), R.id.edt_addadmin_name, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(getActivity(), R.id.edt_addadmin_email, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.addValidation(getActivity(), R.id.edt_addadmin_phno, "^[+]?[0-9]{10,13}$", R.string.invalid_phone);
        awesomeValidation.addValidation(getActivity(), R.id.edt_addadmin_password, regexPassword, R.string.invalid_password);
        awesomeValidation.addValidation(getActivity(), R.id.edt_addadmin_address, RegexTemplate.NOT_EMPTY, R.string.invalid_address);




        btn_addadmin_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {

                    final String uid = edt_addadmin_userid.getText().toString();
                    name = edt_addadmin_name.getText().toString();
                    email = edt_addadmin_email.getText().toString();
                    phno = edt_addadmin_phno.getText().toString();
                    password = edt_addadmin_password.getText().toString();
                    address = edt_addadmin_address.getText().toString();




                /*Log.e( "NAME",name );
                addAdminPojo.setName( name );
                addAdminPojo.setAddress( address );
                addAdminPojo.setEmail( email );
                addAdminPojo.setPhno( phno );
                addAdminPojo.setPswd( password );*/

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.hasChild(uid)) {
                                Toast.makeText(getActivity(), "This UserId already exist....Try another UserId", Toast.LENGTH_LONG).show();


                            } else {
                                addAdminPojo = new AddAdmin_adapter(name, email, address, phno, password);
                                databaseReference.child(uid).setValue(addAdminPojo);
                                Toast.makeText(getActivity(), "New Admin Created =" + uid, Toast.LENGTH_LONG).show();
                            /*Intent i=new Intent( getApplicationContext(),AdminList.class );
                            startActivity( i );*/

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

            }

        });



    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
