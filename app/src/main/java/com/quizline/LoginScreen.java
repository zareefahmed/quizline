package com.quizline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginScreen extends AppCompatActivity {
private EditText et_number;
private Button btn_login;
private String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
       initView();

    }
    public void initView(){
        et_number=findViewById(R.id.et_number_login);
        btn_login=findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = et_number.getText().toString().trim();
                Pattern p = Pattern.compile("^(\\+91[\\-\\s]?)?[0]?(91)?[6789]\\d{9}$");
                Matcher matcher = p.matcher(number);
                if (number==null || !matcher.matches()){
                    Toast.makeText(LoginScreen.this, "Invalid Number", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(LoginScreen.this, "Welcome", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
