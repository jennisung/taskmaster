package com.jennisung.taskmaster.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.cognito.result.AWSCognitoAuthSignOutResult;
import com.amplifyframework.auth.options.AuthSignOutOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;
import com.jennisung.taskmaster.MainActivity;
import com.jennisung.taskmaster.R;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    public static final String TEAM_TAG = "teamName";

    public static final String TAG = "UserProfileActivity";
    public static final String USERNAME_TAG = "username";
    SharedPreferences preferences;

    Button signUpButton;
    Button loginButton;

    Button logoutButton;

//    String authUser = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        signUpButton = findViewById(R.id.SettingsActivitySignUpButton);
        loginButton = findViewById(R.id.SettingsActivityLoginButton);
        logoutButton = findViewById(R.id.SettingsActivityLogoutButton);


        setupUserNameEditText();
        fetchTeamsFromDynamoDB();
        setupSaveButton();
        setupSignUpButton();
        setupLoginButton();
        setupLogoutButton();
//        displayButtons();


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

    void setupSignUpButton() {
        signUpButton.setOnClickListener(v -> {
            Intent goToSignUpActivityIntent = new Intent(SettingsActivity.this, SignUpActivity.class);
            startActivity(goToSignUpActivityIntent);
        });
    }

    void setupLoginButton() {
        loginButton.setOnClickListener(v -> {
            Intent goToLoginActivityIntent = new Intent(SettingsActivity.this, LoginActivity.class);
            startActivity(goToLoginActivityIntent);
        });
    }

    void setupLogoutButton() {
        logoutButton.setOnClickListener(v -> {
            AuthSignOutOptions signOutOptions = AuthSignOutOptions.builder()
                    .globalSignOut(true)
                    .build();

            Amplify.Auth.signOut(signOutOptions,
                    signOutResult -> {
                        if(signOutResult instanceof AWSCognitoAuthSignOutResult.CompleteSignOut) {
                            Log.i(TAG, "Global sign out successful!");
                            Intent goToMainActivity = new Intent(SettingsActivity.this, MainActivity.class);
                            startActivity(goToMainActivity);
                        } else if (signOutResult instanceof AWSCognitoAuthSignOutResult.PartialSignOut) {
                            Log.i(TAG, "Partial sign out successful!");
                        } else if (signOutResult instanceof AWSCognitoAuthSignOutResult.FailedSignOut) {
                            Log.i(TAG, "Logout failed: " + signOutResult.toString());
                        }
                    }
            );
        });
    }

}



