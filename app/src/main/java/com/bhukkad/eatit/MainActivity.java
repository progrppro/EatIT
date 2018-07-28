package com.bhukkad.eatit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText user_main,pass_main ;
    Button login_main ;
    TextView signup_main ;

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
                Intent intent = new Intent("com.bhukkad.eatit.SignupActivity") ;
                startActivity(intent);
            }
        });

        login_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user,pass ;

                user = user_main.getText().toString() ;
                pass = pass_main.getText().toString() ;

                Intent intent = new Intent("com.bhukkad.eatit.LoginActivity") ;
                startActivity(intent);
                pass_main.setText("");
            }
        });
    }

}
