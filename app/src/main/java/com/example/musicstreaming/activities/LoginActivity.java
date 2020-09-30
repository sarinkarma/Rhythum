package com.example.musicstreaming.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.musicstreaming.R;
import com.example.musicstreaming.controllers.LoginController;
import com.example.musicstreaming.utils.Validator;
import com.example.musicstreaming.views.Authentication;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity implements Authentication {

    TextInputEditText email, password;
    Button login;
    ImageButton action_bar_back;
    SharedPreferences sharedPreferences;
    LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("userPref", MODE_PRIVATE);
        loginController = new LoginController(this, sharedPreferences, this);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login_btn);
        action_bar_back = findViewById(R.id.action_bar_back);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
        action_bar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    public void goBack(){
        this.onBackPressed();
    }

    private void validate(){
        int err = 0;
        String email_txt = email.getText().toString();
        String password_txt = password.getText().toString();

        if(!Validator.validateEmail(email_txt) ){
            err++;
            email.requestFocus();
            email.setError("Enter email correctly!");
            email.setText(null);
        }

        if(!Validator.validateFields(password_txt)){
            err++;
            password.requestFocus();
            password.setError("Password Cannot Be Empty!");
            password.setText(null);
        }

        if(err == 0){
            loginController.login(email_txt, password_txt);
        }
    }

    private void setError(){
        email.setText(null);
        password.setText(null);
        email.requestFocus();
        email.setError("Incorrect email or password!");
    }

    @Override
    public void isAuthenticated(boolean isAuthenticated) {
        if(isAuthenticated){
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }else{
            setError();
        }
    }
}