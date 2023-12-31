package com.jennisung.taskmaster;

import android.app.Application;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;

public class TaskMasterAmplifyApplication extends Application {
    public static final String TAG = "taskmasterApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
        } catch (AmplifyException e) {
            Log.e(TAG, "Error initializing Amplify: " + e.getMessage(), e);
        }
    }
}
