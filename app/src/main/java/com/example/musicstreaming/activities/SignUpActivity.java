package com.example.musicstreaming.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.musicstreaming.R;
import com.example.musicstreaming.controllers.SignUpController;
import com.example.musicstreaming.models.request.EmailCheckRequest;
import com.example.musicstreaming.models.response.EmailCheckResponse;
import com.example.musicstreaming.network.RetrofitClient;
import com.example.musicstreaming.network.api.AuthInterface;
import com.example.musicstreaming.utils.Validator;
import com.example.musicstreaming.views.Authentication;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, Authentication {

    SignUpController signUpController;
    LinearLayout emailLayout, passwordLayout, dobLayout, genderLayout, nameLayout;
    Button emailBtn, passwordBtn, dobBtn, genderBtn, signUpBtn;
    TextInputEditText email, password, username;
    DatePicker datePicker;
    RadioGroup rgGender;
    RadioButton rbMale, rbFemale, rbOther;
    ImageButton action_bar_back;
    String eemail, epassword, udob, ugender, uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        SharedPreferences sharedPreferences = getSharedPreferences("userPref", MODE_PRIVATE);
        signUpController = new SignUpController(this, sharedPreferences, this);

        ViewsInit();
    }

    private void ViewsInit(){
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        datePicker = findViewById(R.id.datePicker);
        rgGender = findViewById(R.id.rgGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        rbOther = findViewById(R.id.rbOther);
        action_bar_back = findViewById(R.id.action_bar_back);

        emailBtn = findViewById(R.id.email_btn);
        passwordBtn = findViewById(R.id.password_btn);
        dobBtn = findViewById(R.id.dob_btn);
        genderBtn = findViewById(R.id.gender_btn);
        signUpBtn = findViewById(R.id.signup_btn);

        emailLayout = findViewById(R.id.email_layout);
        passwordLayout = findViewById(R.id.password_layout);
        dobLayout = findViewById(R.id.dob_layout);
        genderLayout = findViewById(R.id.gender_layout);
        nameLayout = findViewById(R.id.name_layout);

        emailBtn.setOnClickListener(this);
        passwordBtn.setOnClickListener(this);
        dobBtn.setOnClickListener(this);
        genderBtn.setOnClickListener(this);
        signUpBtn.setOnClickListener(this);
        rgGender.setOnCheckedChangeListener(this);
        action_bar_back.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        if(passwordLayout.getVisibility() == View.VISIBLE ){
            passwordLayout.setVisibility(View.GONE);
            emailLayout.setVisibility(View.VISIBLE);
        } else if (dobLayout.getVisibility() == View.VISIBLE ){
            passwordLayout.setVisibility(View.VISIBLE);
            dobLayout.setVisibility(View.GONE);
        } else if (genderLayout.getVisibility() == View.VISIBLE ){
            dobLayout.setVisibility(View.VISIBLE);
            genderLayout.setVisibility(View.GONE);
        } else if (nameLayout.getVisibility() == View.VISIBLE ){
            genderLayout.setVisibility(View.VISIBLE);
            nameLayout.setVisibility(View.GONE);
        }else {
            Log.d("backpressed", "onBackPressed: hello");
            super.onBackPressed();
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.email_btn:
                eemail = email.getText().toString();
                if (!Validator.validateEmail(eemail)) {
                    email.setError("Enter correct email");
                } else {
                    AuthInterface api = RetrofitClient.getRetrofit().create(AuthInterface.class);
                    Call<EmailCheckResponse> emailCheckResponseCall = api.email_check(new EmailCheckRequest(eemail));

                    emailCheckResponseCall.enqueue(new Callback<EmailCheckResponse>() {
                        @Override
                        public void onResponse(Call<EmailCheckResponse> call, Response<EmailCheckResponse> response) {
                            if(response.body().getStatus() == 200){
                                emailLayout.setVisibility(View.GONE);
                                passwordLayout.setVisibility(View.VISIBLE);
                            }else{
                                email.setError("Email already exists");
                            }
                        }

                        @Override
                        public void onFailure(Call<EmailCheckResponse> call, Throwable t) {
                            Log.d("email", "onFailure: "+ t.getMessage());
                            Toast.makeText(SignUpActivity.this, "Problem connecting to server.", Toast.LENGTH_SHORT).show();
                            email.setError("Problem connecting to server.");
                        }
                    });

                }
                break;

            case R.id.password_btn:
                epassword = password.getText().toString();
                if (TextUtils.isEmpty(epassword)) {
                    password.setError("Enter Your Password");
                } else {
                    passwordLayout.setVisibility(View.GONE);
                    dobLayout.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.dob_btn:
                dobLayout.setVisibility(View.GONE);
                genderLayout.setVisibility(View.VISIBLE);
                udob = datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth();
                break;

            case R.id.gender_btn:
                if (TextUtils.isEmpty(ugender)) {
                    Toast.makeText(this, "Select Your Gender", Toast.LENGTH_SHORT).show();
                } else {
                    genderLayout.setVisibility(View.GONE);
                    nameLayout.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.signup_btn:
                uname = username.getText().toString();
                if (TextUtils.isEmpty(uname)) {
                    username.requestFocus();
                    username.setError("Enter Username");
                }else{
                    String account = "Account";
                    signUpController.signUp(eemail, epassword, uname, ugender , udob, account);
                    break;
                }
            case R.id.action_bar_back:
                this.onBackPressed();

        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int i) {
        if (i == R.id.rbMale) {
            ugender = "Male";
            Toast.makeText(this, "Male", Toast.LENGTH_SHORT).show();
        }
        if (i == R.id.rbFemale) {
            ugender = "Female";
            Toast.makeText(this, "Female", Toast.LENGTH_SHORT).show();
        }
        if (i == R.id.rbOther) {
            ugender = "Other";
            Toast.makeText(this, "Others", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void isAuthenticated(boolean isAuthenticated) {
        if (isAuthenticated){
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "Failed to Register the user", Toast.LENGTH_SHORT).show();
        }
    }
}