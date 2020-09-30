package com.example.musicstreaming.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.musicstreaming.R;
import com.example.musicstreaming.controllers.ProfileUpdateController;
import com.example.musicstreaming.models.User;
import com.example.musicstreaming.utils.Constants;
import com.example.musicstreaming.utils.Validator;
import com.example.musicstreaming.views.ProfileUpdateView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.Calendar;

public class EditProfileActivity extends AppCompatActivity implements ProfileUpdateView {

    TextInputEditText username, dob;
    TextInputLayout dob_layout;
    RadioGroup rgGender;
    RadioButton rbMale, rbFemale, rbOther;
    Button update;
    SharedPreferences sharedPreferences;
    String token;
    User user;
    ProfileUpdateController profileUpdateController;
    String ugender;
    int year, month, day;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    ImageButton action_bar_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        username = findViewById(R.id.username);
        dob = findViewById(R.id.dob);
        dob_layout = findViewById(R.id.dob_layout);
        rgGender = findViewById(R.id.rgGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        rbOther = findViewById(R.id.rbOther);
        update = findViewById(R.id.update);
        action_bar_back = findViewById(R.id.action_bar_back);

        Gson gson = new Gson();
        sharedPreferences = getSharedPreferences("userPref", MODE_PRIVATE);
        profileUpdateController = new ProfileUpdateController(this, this, sharedPreferences);

        token = sharedPreferences.getString(Constants.TOKEN, null);
        String json = sharedPreferences.getString(Constants.USER, "");
        user = gson.fromJson(json, User.class);

        username.setText(user.getUsername());
        dob.setText(user.getDob());

        if(user.getGender().equals("Male")){
            rbMale.setChecked(true);
            ugender = "Male";
        }else if(user.getGender().equals("Female")){
            rbFemale.setChecked(true);
            ugender = "Female";
        }else if(user.getGender().equals("Other")){
            rbOther.setChecked(true);
            ugender = "Other";
        }

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbMale) {
                    ugender = "Male";
                }
                if (checkedId == R.id.rbFemale) {
                    ugender = "Female";
                }
                if (checkedId == R.id.rbOther) {
                    ugender = "Other";
                }
            }
        });

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(EditProfileActivity.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                dob.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
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
        String fname = username.getText().toString();

        if(!Validator.validateFields(fname)){
            err++;
            username.requestFocus();
            username.setError("Username cannot be empty");
            username.setText(null);
        }

        if(err == 0){
            profileUpdateController.updateProfile(token, user.get_id(), fname, dob.getText().toString(), ugender);
        }
    }

    @Override
    public void isUpdate(boolean isUpdated) {
        if(isUpdated){
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            Toast.makeText(this, "Profile Update Successfully", Toast.LENGTH_SHORT).show();
        }
    }
}