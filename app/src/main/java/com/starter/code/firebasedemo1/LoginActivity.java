package com.starter.code.firebasedemo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.starter.code.firebasedemo1.model.User;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    // Declaring variables for Text boxes
    private EditText email, password;

    // Declaring variable for FirebaseAuth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initializing FirebaseAuth variable
        firebaseAuth = FirebaseAuth.getInstance();

        // Identifying UI Components
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        email.setText("achantasairohith@gmail.com");
        password.setText("achantasai");

    }

    // ****** Method to validate the user and login using email and password ******//
    public void loginUser(View view){

        // Fetching the data from UI
        final String emailContent, passwordContent;
        emailContent = email.getText().toString();
        passwordContent = password.getText().toString();

        // Firebase login using the method signInWithEmailAndPassword
        firebaseAuth.signInWithEmailAndPassword(emailContent, passwordContent)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If successfully created the user this block will execute
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();

                            // Once sign in success, navigate to HomeScreen
                            Intent intent = new Intent(LoginActivity.this, HomeScreen.class);
                            startActivity(intent);
                        }
                        else {
                            // If any error while creating the user this block will execute
                            Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

}
