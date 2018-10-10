package com.bhukkad.eatit;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    private EditText user_main,pass_main ;
    private Button login_main ;
    private TextView signup_main ;
    private DatabaseReference databaseReference ;
    private String database_user,database_pass ;
    private String user,pass ;
    private boolean valid = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        event_main();
    }

    public void event_main()
    {
        signup_main = (TextView) findViewById(R.id.textView4);
        user_main = (EditText) findViewById(R.id.editText) ;
        pass_main = (EditText) findViewById(R.id.editText2);
        login_main = (Button) findViewById(R.id.button2) ;

        signup_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent("com.bhukkad.eatit.SignupActivity");
                    startActivity(intent);
                    user_main.setText("");
                    pass_main.setText("");
            }
        });

        login_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user = user_main.getText().toString() ;
                pass = pass_main.getText().toString() ;

                if(NetConnection()) {
                    try {
                        if((!user.equals("") && !user.equals(null)) && (!pass.equals("")) && !pass.equals(null)) {
                                databaseReference = FirebaseDatabase.getInstance().getReference("Eatit");
                                databaseReference.child("Users").child("User_"+user).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {

                                                    database_pass = dataSnapshot.child("Password").getValue(String.class);
                                                    String phone = dataSnapshot.child("Phone").getValue(String.class);

                                                    if (Integer.toString(Hash(pass, phone)).equals(database_pass)) {
                                                            Intent intent = new Intent("com.bhukkad.eatit.LoginActivity");
                                                            intent.putExtra("user", "User_"+user);
                                                            startActivity(intent);
                                                            user_main.setText("");
                                                            pass_main.setText("");
                                                            System.exit(0);
                                                    } else {
                                                        Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                                    }
                                        } else {
                                            databaseReference.child("Delivery Boys").child("Delivery Boy_"+user).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    if(dataSnapshot.exists()){
                                                        database_pass = dataSnapshot.child("Password").getValue(String.class);
                                                        String phone = dataSnapshot.child("Phone").getValue(String.class);

                                                        if (Integer.toString(Hash(pass, phone)).equals(database_pass)) {
                                                            Intent intent = new Intent("com.bhukkad.eatit.RiderActivity");
                                                            intent.putExtra("user","Delivery Boy_"+user);
                                                            startActivity(intent);
                                                            user_main.setText("");
                                                            pass_main.setText("");
                                                            System.exit(0);
                                                        } else {
                                                            Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                    else{
                                                        databaseReference.child("Hotels").child("Hotel_"+user).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                if(dataSnapshot.exists()){
                                                                    database_pass = dataSnapshot.child("Password").getValue(String.class);
                                                                    String phone = dataSnapshot.child("Phone").getValue(String.class);

                                                                    if (Integer.toString(Hash(pass, phone)).equals(database_pass)) {
                                                                        Intent intent = new Intent("com.bhukkad.eatit.HotelActivity");
                                                                        intent.putExtra("user","Hotel_"+user);
                                                                        startActivity(intent);
                                                                        user_main.setText("");
                                                                        pass_main.setText("");
                                                                        System.exit(0);
                                                                    } else {
                                                                        Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                                else{
                                                                    Toast.makeText(MainActivity.this,"Invalid Username/Password",Toast.LENGTH_SHORT).show();
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(MainActivity.this, "Something went wrong\nPlease try again", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        }
                        else {
                                Toast.makeText(MainActivity.this, "Invalid Username/Password", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e("Error", e.getMessage());
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public int Hash(String msg,String phone)
    {
        int code ;
        String str = "" ;

        str = str.concat(phone.substring(0,6) + msg.substring(0,5) + user + msg.substring(5,msg.length()) + phone.substring(6,phone.length())) ;

        code = str.hashCode() ;

        return code ;
    }

    public boolean NetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE) ;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo() ;

        if(null == networkInfo || !networkInfo.isConnected() || !networkInfo.isAvailable()){
            Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
            return false ;
        }
        return true ;
    }

}
