package com.jennisung.taskmaster.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;
import com.jennisung.taskmaster.R;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    public static final String TEAM_TAG = "teamName";

//    public static final String TEAM_ID_TAG = "teamId";
    public static final String USERNAME_TAG = "username";
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        setupUserNameEditText();
        fetchTeamsFromDynamoDB();
        setupSaveButton();
    }

    private void fetchTeamsFromDynamoDB() {
        Amplify.API.query(
                ModelQuery.list(Team.class),
                response -> {
                    List<Team> teams = new ArrayList<>();
                    for (Team team : response.getData()) {
                        teams.add(team);
                    }
                    runOnUiThread(() -> setupTeamSpinner(teams));
                },
                error -> {
                    Log.e(TEAM_TAG, "Error fetching teams from DynamoDB: " + error.getMessage());
                }
        );
    }


    public void setupUserNameEditText() {
        String username = preferences.getString(USERNAME_TAG, "");
        ((EditText) findViewById(R.id.SettingsUsernameInputForm)).setText(username);
    }

    public void setupTeamSpinner(List<Team> teams) {
        Spinner teamSpinner = findViewById(R.id.SettingsTeamSpinner);
        List<String> teamNames = new ArrayList<>();
        for (Team team : teams) {
            teamNames.add(team.getName());
        }
        ArrayAdapter<String> teamAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, teamNames);
        teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamSpinner.setAdapter(teamAdapter);
    }

    public void setupSaveButton() {
        Button saveButton = findViewById(R.id.SettingsAddButton);
        saveButton.setOnClickListener(v -> {
            SharedPreferences.Editor preferenceEditor = preferences.edit();

            EditText usernameEditText = findViewById(R.id.SettingsUsernameInputForm);
            String usernameString = usernameEditText.getText().toString();

            Spinner teamSpinner = findViewById(R.id.SettingsTeamSpinner);
            String selectedTeamName = teamSpinner.getSelectedItem().toString();

            preferenceEditor.putString(USERNAME_TAG, usernameString);
            preferenceEditor.putString(TEAM_TAG, selectedTeamName); // Save selected team name
            preferenceEditor.apply();

            Log.d("SettingsActivity", "Save button clicked");
            Log.d("SettingsActivity", "Username saved: " + usernameString);
            Log.d("SettingsActivity", "Selected team: " + selectedTeamName);

            Toast.makeText(getApplicationContext(), "Settings saved!", Toast.LENGTH_SHORT).show();
        });
    }
}



//package com.jennisung.taskmaster.activities;
//
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.preference.PreferenceManager;
//
//import com.amplifyframework.api.graphql.model.ModelQuery;
//import com.amplifyframework.core.Amplify;
//import com.amplifyframework.datastore.generated.model.Team;
//import com.jennisung.taskmaster.R;
//
//import java.sql.Array;
//import java.util.ArrayList;
//import java.util.List;
//
//public class SettingsActivity extends AppCompatActivity {
//
//    public static final String TEAM_TAG = "teamName";
//
//    public static final String USERNAME_TAG = "username";
//    SharedPreferences preferences;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_settings);
//
//        preferences = PreferenceManager.getDefaultSharedPreferences(this);
//
//        setupUserNameEditText();
//        setupSaveButton();
//        setupTeamSpinner();
//    }
//
//    public void setupUserNameEditText() {
//        String username = preferences.getString(USERNAME_TAG, "");
//        ((EditText) findViewById(R.id.SettingsUsernameInputForm)).setText(username);
//    }
//
//
//    public void setupTeamSpinner() {
//        Spinner teamSpinner = findViewById(R.id.SettingsTeamSpinner);
//
//        List<String> teamNames = new ArrayList<>();
//        teamNames.add("Team 1");
//        teamNames.add("Team 2");
//        teamNames.add("Team 3");
//
//        ArrayAdapter<String> teamAdapter = new ArrayAdapter<>(
//                this,
//                android.R.layout.simple_spinner_item,
//                teamNames
//        );
//        teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        teamSpinner.setAdapter(teamAdapter);
//    }
//
//
//
//
//
//    public void setupSaveButton() {
//        Button saveButton = findViewById(R.id.SettingsAddButton);
//        saveButton.setOnClickListener(v -> {
//            SharedPreferences.Editor preferenceEditor = preferences.edit();
//
//            EditText usernameEditText = findViewById(R.id.SettingsUsernameInputForm);
//            String usernameString = usernameEditText.getText().toString();
//
//            Spinner teamSpinner = findViewById(R.id.SettingsTeamSpinner);
//            String selectedTeam = teamSpinner.getSelectedItem().toString();
//
//            preferenceEditor.putString(USERNAME_TAG, usernameString);
//            preferenceEditor.putString(TEAM_TAG, selectedTeam);
//            preferenceEditor.apply();
//
//            Log.d("SettingsActivity", "Save button clicked");
//            Log.d("SettingsActivity", "Username saved: " + usernameString);
//            Log.d("SettingsActivity", "Selected Team: " + selectedTeam);
//
//            Toast.makeText(getApplicationContext(), "Settings saved!", Toast.LENGTH_SHORT).show();
//        });
//    }
//
//}


//    public void setupSaveButton() {
//        Button saveButton = findViewById(R.id.SettingsAddButton);
//        saveButton.setOnClickListener(v -> {
//            SharedPreferences.Editor preferenceEditor = preferences.edit();
//
//            EditText usernameEditText = findViewById(R.id.SettingsUsernameInputForm);
//            String usernameString = usernameEditText.getText().toString();
//
//            preferenceEditor.putString(USERNAME_TAG, usernameString);
//            preferenceEditor.apply();
//
//            Log.d("SettingsActivity", "Save button clicked");
//            Log.d("SettingsActivity", "Username saved: " + usernameString);
//
//            Toast.makeText(getApplicationContext(), "Settings saved!", Toast.LENGTH_SHORT).show();
//        });
//    }