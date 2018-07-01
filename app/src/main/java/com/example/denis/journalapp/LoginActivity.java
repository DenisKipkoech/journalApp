package com.example.denis.journalapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.denis.journalapp.Data.JournalPreferences;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private SignInButton signInButton;
    private TextView skipReg;
    private GoogleApiClient apiClient;
    private static final int REQ_CODE = 200;
    private boolean isSignedIn;


    public static final String INSTANCE_ID = "instance_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signInButton = findViewById(R.id.btn_login);
        skipReg = findViewById(R.id.tv_skip);


        isSignedIn = JournalPreferences.checkIfSignedIn(this);
        if (!isSignedIn) {

            GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(
                    GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

            apiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                    .build();

            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = Auth.GoogleSignInApi.getSignInIntent(apiClient);
                    startActivityForResult(intent, REQ_CODE);
                }
            });
        }

           Intent intent = new Intent(LoginActivity.this, MainActivity.class);
           startActivity(intent);
           skipReg.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(LoginActivity.this,
                           MainActivity.class);
                   startActivity(intent);
               }
           });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE){
            GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(signInResult);
        }

    }

    public void handleResult(GoogleSignInResult signInResult){
        GoogleSignInAccount signInAccount = signInResult.getSignInAccount();

        String name = signInAccount.getDisplayName();
        String email = signInAccount.getEmail();

        JournalPreferences.setUserDetails(this, name, email, true);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }


}
