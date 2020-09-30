package com.example.musicstreaming.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.musicstreaming.R;
import com.example.musicstreaming.activities.DashboardActivity;
import com.example.musicstreaming.activities.EditProfileActivity;
import com.example.musicstreaming.activities.ImageUpdateActivity;
import com.example.musicstreaming.activities.WelcomeActivity;
import com.example.musicstreaming.controllers.LogoutController;
import com.example.musicstreaming.models.User;
import com.example.musicstreaming.utils.Constants;
import com.example.musicstreaming.views.LogoutView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsFragment extends Fragment implements LogoutView {

    View root;
    CircleImageView user_image;
    ImageView update_image;
    TextView username, email, logout_text;
    LinearLayout edit_profile, logout, logout_google;
    User user;
    String token;
    SharedPreferences sharedPreferences;
    LogoutController logoutController;
    GoogleSignInClient mGoogleSignInClient;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_settings, container, false);

        user_image = root.findViewById(R.id.circleImageView);
        update_image = root.findViewById(R.id.update_image);
        username = root.findViewById(R.id.username);
        email = root.findViewById(R.id.email);
        logout_text = root.findViewById(R.id.logout_text);
        edit_profile = root.findViewById(R.id.edit_profile);
        logout = root.findViewById(R.id.logout);
        logout_google = root.findViewById(R.id.logout_google);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);


        sharedPreferences = this.getActivity().getSharedPreferences("userPref", Context.MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.TOKEN, null);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(Constants.USER, "");
        user = gson.fromJson(json, User.class);

        logoutController = new LogoutController(getContext(), sharedPreferences, this);

        username.setText(user.getUsername());
        email.setText(user.getEmail());
        logout_text.setText("You are logged in as " + user.getUsername());
        if(user.getImage() != null){
            Picasso.get()
                    .load(Constants.IMAGE_URL + user.getImage())
                    .into(user_image);
        }

        if(user.getAccount().equals("Google")){
            logout.setVisibility(View.GONE);
            logout_google.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signOut();
                }
            });
        }
        if(user.getAccount().equals("Account")){
            logout_google.setVisibility(View.GONE);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logoutController.logout(token);
                }
            });
        }

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                startActivityForResult(intent, 2);
            }
        });

        update_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageUpdateActivity.class);
                startActivityForResult(intent, 2);
            }
        });

        return root;
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        logoutController.logout(token);
                    }
                });
    }

    @Override
    public void isLoggedOut(boolean isLoggedOut) {
        if(isLoggedOut){
            Intent intent = new Intent(root.getContext(), WelcomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        switch(requestCode) {
//            case (2) : {
//                Fragment frg = null;
//                frg = getActivity().getSupportFragmentManager().findFragmentByTag("Your_Fragment_TAG");
//                final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                ft.detach(frg);
//                ft.attach(frg);
//                ft.commit();
//                break;
//            }
//        }
    }
}