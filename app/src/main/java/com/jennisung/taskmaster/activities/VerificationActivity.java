package com.jennisung.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;
import com.jennisung.taskmaster.R;

public class VerificationActivity extends AppCompatActivity {
    public static String TAG = "VerificationActivity";

    Button submitButton;
    EditText emailEditText;
    EditText verificationCodeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);


        submitButton = findViewById(R.id.VerificationActivitySubmitButton);
        emailEditText = findViewById(R.id.VerificationActivityEmailTextView);
        verificationCodeEditText = findViewById(R.id.VerifyActivityVerifyCodeTextView);


//        setupEmailTextInput();
        setupSubmitButton();

    }

    void setupSubmitButton() {
        submitButton.setOnClickListener(v -> {
            Amplify.Auth.confirmSignUp(
                    emailEditText.getText().toString(),
                    verificationCodeEditText.getText().toString(),
                    success -> {
                        Log.i(TAG, "Verify success: " + success.toString());
                        Intent goToLoginIntent = new Intent(VerificationActivity.this, LoginActivity.class);
                        goToLoginIntent.putExtra("email", emailEditText.getText().toString());
                        startActivity(goToLoginIntent);
                    },
                    failure -> {
                        Log.i(TAG, "Verify failed: " + failure.toString());
                    }
            );
        });
    }

}