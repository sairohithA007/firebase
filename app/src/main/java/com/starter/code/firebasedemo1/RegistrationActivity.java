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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    // Declaring text boxes
    private EditText email, password;

    // Declaring FirebaseAuth variable
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initializing the FirebaseAuth variable
        firebaseAuth = FirebaseAuth.getInstance();

        // Identifying UI components
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

    }

    //******* Method to register user *******//
    public void registerUser(View view) {
        // Feting the text from UI
        String emailContent, passwordContent;
        emailContent = email.getText().toString();
        passwordContent = password.getText().toString();

        // Creating the user in Firebase using the method 'createUserWithEmailAndPassword'
        firebaseAuth.createUserWithEmailAndPassword(emailContent, passwordContent)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // If successfully created the user this block will execute
                            Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();

                            // Once registered, navigate to login screen
                            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        else {
                            // If any error while creating the user this block will execute
                            Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    // Method to navigate to login screen
    public void goToLogin(View view){
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
