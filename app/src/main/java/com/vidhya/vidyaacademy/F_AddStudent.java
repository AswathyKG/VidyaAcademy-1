package com.vidhya.vidyaacademy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class F_AddStudent extends Fragment implements AdapterView.OnItemSelectedListener {

    EditText edt_addstud_studid, edt_addstud_pname, edt_addstud_address, edt_addstud_name, edt_addstud_class, edt_addadmin_userid;
    Spinner spinner_addadmin;
    Button btn_addadmin_chooseimg, btn_addstud_add;
    public AddStudent_adapter addStudent_adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    // Create a storage reference from our app
    StorageReference storageRef = storage.getReference();
    String name, email, pname,address,userid,studregno;
    String classname;
    private AwesomeValidation awesomeValidation;
    String regexPassword = ".{8,}";
    SharedPreferences sharedPreferences;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    ImageView Addstudent_choosen_imageview;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate( R.layout.fragment_addstudent,container,false );

        edt_addstud_name = view.findViewById( R.id.edt_addstud_name );
        edt_addstud_address = view.findViewById( R.id.edt_addstud_address );
        edt_addstud_pname = view.findViewById( R.id.edt_addstud_pname );
        //edt_addadmin_phno = view.findViewById( R.id.edt_addadmin_phno );
        edt_addstud_class = view.findViewById( R.id.edt_addstud_class);
        edt_addstud_studid = view.findViewById( R.id.edt_addstud_studid );
        //spinner_addadmin=(Spinner)view.findViewById(R.id.spinner_addadmin);
        btn_addadmin_chooseimg=view.findViewById(R.id.btn_addadmin_chooseimg);
        btn_addstud_add = view.findViewById( R.id.btn_addstud_add );
        Addstudent_choosen_imageview=view.findViewById( R.id.addstudent_choosen_imageview );

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        sharedPreferences = getActivity().getSharedPreferences( "MyShared", Context.MODE_PRIVATE );
        userid = sharedPreferences.getString( "userid", "" );




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
        databaseReference = firebaseDatabase.getReference( "users/admin/" +userid );


        btn_addadmin_chooseimg.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }


        } );



        btn_addstud_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                studregno = edt_addstud_studid.getText().toString();
                name = edt_addstud_name.getText().toString();
                address = edt_addstud_address.getText().toString();
                pname = edt_addstud_pname.getText().toString();
                classname =edt_addstud_class.getText().toString();

                Bitmap image=((BitmapDrawable)Addstudent_choosen_imageview.getDrawable()).getBitmap();
               final StorageReference imagesRef = storageRef.child("students/"+"rajan"+".jpg");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

               UploadTask uploadTask = imagesRef.putBytes(data);

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return imagesRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            Log.e("MASTER",downloadUri.toString());
                        } else {
                            // Handle failures
                            // ...
                        }
                    }
                });

                /*Log.e( "NAME",name );
                addAdminPojo.setName( name );
                addAdminPojo.setAddress( address );
                addAdminPojo.setEmail( email );
                addAdminPojo.setPhno( phno );
                addAdminPojo.setPswd( password );*/

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild(classname)) {
                            if (dataSnapshot.hasChild( studregno )){

                            }else{

                            addStudent_adapter = new AddStudent_adapter( name, address, pname);
                            databaseReference.child( classname ).child(studregno).setValue(addStudent_adapter);
                            databaseReference.child( classname ).child( studregno ).child( "status" ).setValue( "request" );
                            Toast.makeText(getActivity(),  "New Student Added =" + studregno, Toast.LENGTH_LONG).show();
                                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                                progressDialog.setTitle("Uploading...");
                                progressDialog.show();



                            }


                        } else {

                            Toast.makeText(getActivity(),"mentioned class does not exist....Try another UserId", Toast.LENGTH_LONG).show();
                            /*Intent i=new Intent( getApplicationContext(),AdminList.class );
                            startActivity( i );*/

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }



    });

        return view;
    }

   /* @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        awesomeValidation.addValidation(getActivity(), R.id.edt_addadmin_name, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(getActivity(), R.id.edt_addadmin_email, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.addValidation(getActivity(), R.id.edt_addadmin_phno, "^[+]?[0-9]{10,13}$", R.string.invalid_phone);
        awesomeValidation.addValidation(getActivity(), R.id.edt_addadmin_password, regexPassword, R.string.invalid_password);
        awesomeValidation.addValidation(getActivity(), R.id.edt_addadmin_address, RegexTemplate.NOT_EMPTY, R.string.invalid_address);

*/









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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                Addstudent_choosen_imageview.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
