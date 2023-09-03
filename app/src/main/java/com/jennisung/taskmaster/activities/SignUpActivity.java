package com.jennisung.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.jennisung.taskmaster.R;

public class SignUpActivity extends AppCompatActivity {
    public static final String TAG = "SignUpActivity";

    Button submitButton;
    EditText emailEditText;
    EditText passwordEditText;

    EditText nicknameEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        submitButton = findViewById(R.id.SignUpActivityButton);
        emailEditText = findViewById(R.id.SignUpInputText);
        passwordEditText = findViewById(R.id.SignUpPasswordEditText);
        nicknameEditText = findViewById(R.id.SignUpActivityUsernameInput);

        setupSubmitButton();
    }

void setupSubmitButton() {
    submitButton.setOnClickListener(v -> {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String nickname = nicknameEditText.getText().toString();

        Amplify.Auth.signUp(
                email,
                password,
                AuthSignUpOptions.builder()
                        .userAttribute(AuthUserAttributeKey.email(), email)
                        .userAttribute(AuthUserAttributeKey.nickname(), nickname)
                        .build(),
                successResponse -> {
                    Log.i(TAG, "Signup succeeded: " + successResponse.toString());

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", nickname); // Save username
                    editor.apply();

                    Intent goToVerifyActivity = new Intent(SignUpActivity.this, VerificationActivity.class);
                    startActivity(goToVerifyActivity);
                },
                failureResponse -> Log.i(TAG, "Signup failed with username: " + " with this message: " + failureResponse.toString())
        );
    });
}


}

