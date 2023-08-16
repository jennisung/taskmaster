package com.jennisung.taskmaster.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import com.jennisung.taskmaster.R;

public class SettingsActivity extends AppCompatActivity {

    public static final String USERNAME_TAG = "username";
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        setupUserNameEditText();
        setupSaveButton();
    }

    public void setupUserNameEditText() {
        String username = preferences.getString(USERNAME_TAG, "");
        ((EditText) findViewById(R.id.SettingsUsernameInputForm)).setText(username);
    }

    public void setupSaveButton() {
        Button saveButton = findViewById(R.id.SettingsAddButton);
        saveButton.setOnClickListener(v -> {
            SharedPreferences.Editor preferenceEditor = preferences.edit();

            EditText usernameEditText = findViewById(R.id.SettingsUsernameInputForm);
            String usernameString = usernameEditText.getText().toString();

            preferenceEditor.putString(USERNAME_TAG, usernameString);
            preferenceEditor.apply();

            Log.d("SettingsActivity", "Save button clicked");

            Toast.makeText(getApplicationContext(), "Settings saved!", Toast.LENGTH_SHORT).show();
        });
    }
}
