package com.starter.code.firebasedemo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class HomeScreen extends AppCompatActivity {

    // Declaring FireBaseAuth Variable
    private FirebaseAuth firebaseAuth;

    // Declaring TextView variable
    private TextView greetings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // Intializing firebase
        firebaseAuth = FirebaseAuth.getInstance();

        // Identifying greeting textview
        greetings = findViewById(R.id.greetings);

        // Fetching the email from firebase instance and setting it to the text view
        greetings.setText("Welcome "+ firebaseAuth.getCurrentUser().getEmail());

    }


    //***** method to signout the user *****//
    public void logout(View view){
        try{
            // Below line does the job of signout
            firebaseAuth.signOut();

            // Navigating to Registration screen
            Intent intent = new Intent(HomeScreen.this, RegistrationActivity.class);
            startActivity(intent);
        }catch (Exception ex){
            Log.d("Error while signout: ", ex.getMessage());
        }

    }
}
