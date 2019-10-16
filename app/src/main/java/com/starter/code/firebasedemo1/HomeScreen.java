package com.starter.code.firebasedemo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.starter.code.firebasedemo1.model.User;

import java.util.HashMap;
import java.util.Map;

public class HomeScreen extends AppCompatActivity {

    // Declaring FireBaseAuth Variable
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    // Declaring TextView variable
    private TextView greetings;
    private EditText phone;
    private EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // Intializing firebase
        firebaseAuth = FirebaseAuth.getInstance();

        // Identifying greeting textview
        greetings = findViewById(R.id.greetings);

        // Identifying Address and Phone number textboxes
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);

        // Fetching the email from firebase instance and setting it to the text view
        greetings.setText("Welcome "+ firebaseAuth.getCurrentUser().getEmail());
    }

    public void updateUserDetails(View view){

        final String uuid = firebaseAuth.getCurrentUser().getUid();
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users/" + uuid);
        // Fetching the data from UI
        final String addressContent, phoneContent;
        addressContent = address.getText().toString();
        phoneContent = phone.getText().toString();


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) dataSnapshot.getValue();

 //                   final ObjectMapper mapper = new ObjectMapper();


//                    for (String key : dataMap.keySet()){
//                        System.out.println(key);
//                        Object data = dataMap.get(key);
//
//                        try{
//                            HashMap<String, Object> userData = (HashMap<String, Object>) data;
//
//                            User user = new User();
//                            user.setEmail((String)userData.get("email"));
//                            user.setPassword((String)userData.get("password"));
//                            user.setAddress(addressContent);
//                            user.setPhone(phoneContent);
//
//                            HashMap<String, Object> userObject = new HashMap<>();
//                            userObject.put(uuid, user);
//
//                            mDatabase.updateChildren(userObject);
//
//
//                        }catch (ClassCastException cce){
//
//// If the object can’t be casted into HashMap, it means that it is of type String.
//
//                            try{
//
//                                String mString = String.valueOf(dataMap.get(key));
//
//                            }catch (ClassCastException cce2){
//
//                            }
//                        }

//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        User user = mDatabase.getRepo();
//        user.setEmail();
//        user.setPassword(passwordContent);
//
//        Map<String, Object> userDocument = new HashMap<>();
//        userDocument.put(task.getResult().getUser().getUid(), user);
//
//        mDatabase.updateChildren(userDocument);
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
