package com.bhukkad.eatit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    private EditText name_signup,mail_signup,phone_signup,user_signup,password_signup,confirm_signup ;
    private Button signup_signup ;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String name,mail,phone,pass,confirm,error_message,user ;
    private DatabaseReference databaseReference,getvalues ;
    private boolean present ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        event_signup();
    }

    public void event_signup()
    {
        name_signup = (EditText) findViewById(R.id.editText4);
        mail_signup = (EditText) findViewById(R.id.editText10);
        phone_signup = (EditText) findViewById(R.id.editText9);
        user_signup = (EditText) findViewById(R.id.editText11);
        password_signup = (EditText) findViewById(R.id.editText7);
        confirm_signup = (EditText) findViewById(R.id.editText8);
        signup_signup = (Button) findViewById(R.id.button4);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);

        signup_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(radioGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(SignupActivity.this,"Select from User/Delivery Boy/Hotel option",Toast.LENGTH_SHORT).show();
                }
                else{

                    boolean _cont = true;
                    databaseReference = FirebaseDatabase.getInstance().getReference("Eatit") ;

                    name = name_signup.getText().toString() ;
                    mail = mail_signup.getText().toString() ;
                    phone = phone_signup.getText().toString() ;
                    user = user_signup.getText().toString() ;
                    pass = password_signup.getText().toString() ;
                    confirm = confirm_signup.getText().toString() ;

                    _cont = _Cont(name,mail,phone,pass,confirm,user) ;

                    if(_cont){
                        if (!pass.equals(confirm)) {
                            Toast.makeText(SignupActivity.this, "Password and Confirm Password fields do not match", Toast.LENGTH_SHORT).show();
                            _cont = false;
                            password_signup.setText("");
                            confirm_signup.setText("");
                        }

                        if (!mail.contains("@gmail.com")) {
                            Toast.makeText(SignupActivity.this, "Invalid mail address", Toast.LENGTH_SHORT).show();
                            _cont = false;
                        }

                        if (10 != phone.length()) {
                            Toast.makeText(SignupActivity.this, "Invalid Phone", Toast.LENGTH_SHORT).show();
                            _cont = false;
                        }

                        if (pass.length() < 8) {
                            Toast.makeText(SignupActivity.this, "Password should be atleast 8 characters in length", Toast.LENGTH_SHORT).show();
                            password_signup.setText("");
                            confirm_signup.setText("");
                            _cont = false;
                        }
                    }

                    present = false ;

                    if(_cont) {
                        radioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId()) ;
                        getvalues = FirebaseDatabase.getInstance().getReference("Eatit");

                        getvalues.child("Users").child("User_"+user).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()) {
                                    Toast.makeText(SignupActivity.this, "Username already taken", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    getvalues.child("Deliver Boys").child("Delivery Boy_"+user).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.exists()){
                                                Toast.makeText(SignupActivity.this, "Username already taken", Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                getvalues.child("Hotels").child("Hotel_"+user).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        radioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId()) ;
                                                        if(dataSnapshot.exists()){
                                                            Toast.makeText(SignupActivity.this, "Username already taken", Toast.LENGTH_SHORT).show();
                                                        }
                                                        else{
                                                            Toast.makeText(SignupActivity.this,"SignUp Successful",Toast.LENGTH_LONG).show();
                                                            databaseReference.child(radioButton.getText().toString() + "s").child(radioButton.getText().toString()+"_"+user) ;
                                                            databaseReference.child(radioButton.getText().toString() + "s").child(radioButton.getText().toString()+"_"+user).child("Name").setValue(name);
                                                            databaseReference.child(radioButton.getText().toString() + "s").child(radioButton.getText().toString()+"_"+user).child("Username").setValue(user);
                                                            databaseReference.child(radioButton.getText().toString() + "s").child(radioButton.getText().toString()+"_"+user).child("Password").setValue(Integer.toString(Hash(pass)));
                                                            databaseReference.child(radioButton.getText().toString() + "s").child(radioButton.getText().toString()+"_"+user).child("Phone").setValue(phone);
                                                            databaseReference.child(radioButton.getText().toString() + "s").child(radioButton.getText().toString()+"_"+user).child("Mail").setValue(mail);

                                                            if(radioButton.getText().toString().equals("User")) {
                                                                Intent intent = new Intent("com.bhukkad.eatit.LoginActivity");
                                                                intent.putExtra("user", "User_"+user);
                                                                startActivity(intent);
                                                            }
                                                            else if(radioButton.getText().toString().equals("Delivery Boy")){
                                                                Intent intent = new Intent("com.bhukkad.eatit.RiderActivity");
                                                                intent.putExtra("user", "Delivery Boy_"+user);
                                                                startActivity(intent);
                                                            }
                                                            else{
                                                                Intent intent = new Intent("com.bhukkad.eatit.HotelActivity");
                                                                intent.putExtra("user","Hotel_"+user);
                                                                startActivity(intent);
                                                            }
                                                            name_signup.setText("");
                                                            phone_signup.setText("");
                                                            mail_signup.setText("");
                                                            user_signup.setText("");
                                                            password_signup.setText("");
                                                            confirm_signup.setText("");
                                                            finish();
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

                            }
                        });
                    }
                }

            }
        });
    }

    public boolean _Cont(String name,String mail,String phone,String pass,String confirm,String user)
    {
        String error_message = "" ;
        boolean _cont = true ;

        if(name.equals("") || name.equals(null)) {
            error_message = error_message + "/First Name";
        }
        if (mail.equals("") || mail.equals(null)) {
            error_message = error_message + "/EmailID";
        }
        if (phone.equals("") || phone.equals(null)) {
            error_message = error_message + "/Phone Number";
        }
        if(user.equals("") || user.equals(null)) {
            error_message = error_message + "/Username";
        }
        if (pass.equals("") || pass.equals(null)) {
            error_message = error_message + "/Password";
        }
        if (confirm.equals("") || confirm.equals(null)) {
            error_message = error_message + "/Confirm Password";
        }
        if(!error_message.equals("")) {
            error_message = error_message + " fields cannot be empty";
            _cont = false ;
            Toast.makeText(SignupActivity.this, error_message, Toast.LENGTH_SHORT).show();
        }

        return _cont ;
    }

    public int Hash(String msg)
    {
        int code ;
        String str = "" ;


        str = str.concat(phone.substring(0,6) + msg.substring(0,5) + user + msg.substring(5,msg.length()) + phone.substring(6,phone.length())) ;

        code = str.hashCode() ;

        return code ;
    }
}