package com.jennisung.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.auth.exceptions.NotAuthorizedException;
import com.amplifyframework.core.Amplify;
import com.jennisung.taskmaster.MainActivity;
import com.jennisung.taskmaster.R;

public class LoginActivity extends AppCompatActivity {
    public static String TAG = "LoginActivity";

    Button submitButton;
    EditText emailEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        submitButton = findViewById(R.id.LoginActivityButton);
        emailEditText = findViewById(R.id.LoginActivityEmailEditText);
        passwordEditText = findViewById(R.id.LoginActivityPasswordEditText);

        setupSubmitButton();
    }

    void setupSubmitButton(){
        submitButton.setOnClickListener(view -> {
            Amplify.Auth.signIn(emailEditText.getText().toString(),passwordEditText.getText().toString(),
                    success -> {
                        Log.i(TAG, "Sign in succeeded:");
                        Intent goToMainActivity = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(goToMainActivity);
                    },
                    failure -> {
                        if (failure instanceof NotAuthorizedException) {
                            // Inform user their account is disabled
                            Log.e(TAG, "Account is disabled.");
                        } else {
                            Log.e(TAG, "Sign in failed: " + failure.toString());
                        }
                    });

        });

    }
}

//        Amplify.Auth.signIn("",
//                "password",
//
//                    success -> {
//                    Log.i(TAG, "login success: " + success.toString());
//                 },
//                    failure -> {
//                    Log.i(TAG, "login failed: " + failure.toString());
////                 });