package com.vidhya.vidyaacademy;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    SmsManager smsManager;

    ImageView img_login_logo;
    EditText edt_login_username, edt_login_password, studname, class_name;
    Button btn_login_signin;
    TextView tv_login_register, tv_login_forgot;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference( "users" );
    DatabaseReference databaseReference = database.getReference( "users/admin/" );
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    static final String REMINDER = "Reminder";
    static final String userid = "userid";
    static final String password = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login_new );


        btn_login_signin = (Button) findViewById( R.id.btn_login_signin );
        edt_login_username = (EditText) findViewById( R.id.edt_login_username );
        edt_login_password = (EditText) findViewById( R.id.edt_login_password );
        tv_login_register = (TextView) findViewById( R.id.tv_login_register );
        tv_login_forgot = (TextView) findViewById( R.id.tv_login_forgot );
        img_login_logo = (ImageView) findViewById( R.id.img_login_logo );


        btn_login_signin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = edt_login_username.getText().toString();

                final String pass = edt_login_password.getText().toString();

                signin( user, pass );

                overridePendingTransition( 0, 0 );
                View relativeLayout = findViewById( R.id.login_container );
                Animation animation = AnimationUtils.loadAnimation( Login.this, android.R.anim.fade_in );
                relativeLayout.startAnimation( animation );


            }
        } );
        tv_login_forgot.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate( R.layout.fragment_forget_password, null );
                studname = alertLayout.findViewById( R.id.studname );
                class_name = alertLayout.findViewById( R.id.class_name );
                AlertDialog.Builder alert = new AlertDialog.Builder( Login.this );
                alert.setTitle( "Forgot Password?" );
                alert.setMessage( "Do You want to send Message?" );
                // this is set the view from XML inside AlertDialog
                alert.setView( alertLayout );
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable( false );
                alert.setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText( getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT ).show();


                    }
                } );

                alert.setPositiveButton( "Done", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (studname.getText().toString().isEmpty() || class_name.getText().toString().isEmpty()) {

                            Toast.makeText( Login.this, "student name and class name cannot be empty", Toast.LENGTH_LONG ).show();

                        } else {
                            try {
                                final String stud = studname.getText().toString();
                                final String cls = class_name.getText().toString();


                                databaseReference.addValueEventListener( new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                            Log.e( "message1",dataSnapshot1.getKey().toString() );

                                            for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                                Log.e( "message2",dataSnapshot2.getKey().toString() );
                                                if(dataSnapshot2.getKey().toString().equals( cls )){
                                                    final String msg = stud + " " + cls;
                                                   // String ph=dataSnapshot2.child( "phno" ).getValue().toString();
                                                    Log.e( "insideloop",dataSnapshot1.child( "phno" ).getValue().toString() );
                                                    String phoneNo =  "+91"+ dataSnapshot1.child( "phno" ).getValue().toString();
                                                    SmsManager smsManager = SmsManager.getDefault();
                                                    ArrayList<String> messageParts = smsManager.divideMessage( msg );
                                                    smsManager.sendMultipartTextMessage( phoneNo, null, messageParts, null, null );
                                                    Toast.makeText( getApplicationContext(), "Message Sent", Toast.LENGTH_LONG ).show();
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                } );


                            } catch (Exception ex) {
                                Toast.makeText( getApplicationContext(), ex.getMessage().toString(), Toast.LENGTH_LONG ).show();
                                ex.printStackTrace();
                            }
                        }
                    }


                } );
                AlertDialog dialog = alert.create();
                dialog.show();


            }
        } );

    }


    public void signin(final String user, final String pass) {

        if (user.isEmpty() && pass.isEmpty()) {

            Toast.makeText( Login.this, "userid and password cannot be empty", Toast.LENGTH_LONG ).show();
            return;

        } else if (user.isEmpty()) {
            edt_login_username.setError( "Please enter username" );
            return;
        } else if (pass.isEmpty()) {
            edt_login_password.setError( "Please enter password" );
            return;
        } else {
            Toast.makeText( getApplicationContext(), "Please enter valid details", Toast.LENGTH_LONG ).show();
        }


        myRef.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                        // Log.e(TAG,dataSnapshot1.getChildren().toString());

                        if (user.equals( dataSnapshot2.getKey() ) && pass.equals( dataSnapshot2.child( "password" ).getValue() )) {

                            sharedPreferences = getApplicationContext().getSharedPreferences( "MyShared", Context.MODE_PRIVATE );
                            editor = sharedPreferences.edit();
                            editor.putString( REMINDER, "yes" );
                            editor.putString( userid, dataSnapshot2.getKey().toString() );
                            editor.putString( password, dataSnapshot2.child( "password" ).getValue().toString() );
                            editor.commit();

                            switch (dataSnapshot1.getKey().toString()) {

                                case "admin":
                                    Toast.makeText( getApplicationContext(), "admin", Toast.LENGTH_LONG ).show();
                                    sharedToSave( dataSnapshot2 );

                                    editor.putString( "key", "admin" );
                                    editor.commit();

                                    Intent intent = new Intent( Login.this, Admin.class );
                                    startActivity( intent );
                                    Toast.makeText( getApplicationContext(), "adminl", Toast.LENGTH_LONG ).show();

                                    break;
                                case "parent":
                                  /*  Toast.makeText( getApplicationContext(), "activity_parent", Toast.LENGTH_LONG ).show();
                                    sharedToSave( dataSnapshot2 );
                                    editor.putString( "key", "activity_parent" );
                                    editor.commit();
                                    Intent intent1 = new Intent( Login.this, Parent.class );
                                    startActivity( intent1 );
                                    Toast.makeText( getApplicationContext(), "parentl", Toast.LENGTH_LONG ).show();

                                    break;*/
                                    continue;
                                case "principal":
                                    Toast.makeText( getApplicationContext(), "Principal", Toast.LENGTH_LONG ).show();
                                    sharedToSave( dataSnapshot2 );

                                    editor.putString( "key", "principal" );
                                    editor.commit();

                                    Intent intent2 = new Intent( Login.this, Principal.class );
                                    startActivity( intent2 );
                                    Toast.makeText( getApplicationContext(), "activity_principal", Toast.LENGTH_LONG ).show();

                                    break;
                            }


                        } else if (user.equals( dataSnapshot2.getKey() ) && pass.equals( dataSnapshot2.child( "reg_No" ).getValue() )) {

                            sharedPreferences = getApplicationContext().getSharedPreferences( "MyShared", Context.MODE_PRIVATE );
                            editor = sharedPreferences.edit();
                            editor.putString( REMINDER, "yes" );
                            editor.putString( userid, dataSnapshot2.getKey().toString() );
                            editor.putString( password, dataSnapshot2.child( "reg_No" ).getValue().toString() );
                            editor.commit();

                            switch (dataSnapshot1.getKey().toString()) {


                                case "admin":
                                    continue;
                                case "parent":
                                    Toast.makeText( getApplicationContext(), "activity_parent", Toast.LENGTH_LONG ).show();
                                    sharedToSave2( dataSnapshot2 );
                                    editor.putString( "key", "activity_parent" );
                                    editor.commit();
                                    Intent intent1 = new Intent( Login.this, Parent.class );
                                    startActivity( intent1 );
                                    Toast.makeText( getApplicationContext(), "parentl", Toast.LENGTH_LONG ).show();

                                    break;
                                case "principal":
                                    continue;


                            }

                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        } );
    }

    public void sharedToSave(DataSnapshot dataSnapshot2) {
        sharedPreferences = getApplicationContext().getSharedPreferences( "MyShared", Context.MODE_PRIVATE );
        editor = sharedPreferences.edit();
        editor.putString( "username", dataSnapshot2.getKey().toString() );
        editor.putString( "password", dataSnapshot2.child( "password" ).getValue().toString() );
        editor.commit();


    }

    public void sharedToSave2(DataSnapshot dataSnapshot2) {
        sharedPreferences = getApplicationContext().getSharedPreferences( "MyShared", Context.MODE_PRIVATE );
        editor = sharedPreferences.edit();
        editor.putString( "username", dataSnapshot2.getKey().toString() );
        editor.putString( "password", dataSnapshot2.child( "reg_No" ).getValue().toString() );
        editor.commit();


    }


}