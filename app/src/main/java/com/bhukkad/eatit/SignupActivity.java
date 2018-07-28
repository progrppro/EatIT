package com.bhukkad.eatit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    EditText fname_signup,lname_signup,mail_signup,phone_signup,user_signup,password_signup,confirm_signup ;
    Button signup_signup ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        event_signup();
    }

    public void event_signup()
    {
        fname_signup = (EditText) findViewById(R.id.editText4);
        lname_signup = (EditText) findViewById(R.id.editText6);
        mail_signup = (EditText) findViewById(R.id.editText10);
        phone_signup = (EditText) findViewById(R.id.editText9);
        password_signup = (EditText) findViewById(R.id.editText7);
        confirm_signup = (EditText) findViewById(R.id.editText8);
        signup_signup = (Button) findViewById(R.id.button4);

        final String fname,lname,mail,phone,pass,confirm ;

        fname = fname_signup.getText().toString() ;
        lname = lname_signup.getText().toString() ;
        mail = mail_signup.getText().toString() ;
        phone = phone_signup.getText().toString() ;
        pass = password_signup.getText().toString() ;
        confirm = confirm_signup.getText().toString() ;

        signup_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fname.equals("") || fname == null)
                    Toast.makeText(SignupActivity.this,"First Name field cannot be Empty",Toast.LENGTH_SHORT).show();
                if (lname.equals("") || lname == null)
                    Toast.makeText(SignupActivity.this,"Last Name field cannot be Empty",Toast.LENGTH_SHORT).show();
                if (mail.equals("") || mail == null)
                    Toast.makeText(SignupActivity.this,"MailID field cannot be Empty",Toast.LENGTH_SHORT).show();
                if (phone.equals("") || phone == null)
                    Toast.makeText(SignupActivity.this,"Phone Number field cannot be Empty",Toast.LENGTH_SHORT).show();
                if (pass.equals("") || pass == null)
                    Toast.makeText(SignupActivity.this,"Password filed cannot be Empty",Toast.LENGTH_SHORT).show();
                if (confirm.equals("") || confirm == null)
                    Toast.makeText(SignupActivity.this,"Confirm Password field cannot be Empty",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
