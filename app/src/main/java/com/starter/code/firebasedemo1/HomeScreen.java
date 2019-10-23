package com.starter.code.firebasedemo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.starter.code.firebasedemo1.model.User;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity {

    // Declaring FireBaseAuth Variable
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    // Declaring TextView variable
    private TextView greetings;
    private EditText phone;
    private EditText address;

    private List<User> users;
    private ListView lv;
    private ArrayAdapter<String> adapter;

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

        // identifying listview from UI
        lv = (ListView) findViewById(R.id.dataList);

        // Fetching the email from firebase instance and setting it to the text view
        greetings.setText("Welcome "+ firebaseAuth.getCurrentUser().getEmail());

        // Creating a Firebase DB reference object
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        users = new ArrayList<>();

        //*********************** Start: Reading data from database  ***********************//
        // Listener which gets triggered whenever there is a change in data present in database
        database.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users = new ArrayList<>();
                String[] emails = new String[(int)dataSnapshot.getChildrenCount()];
                int index = 0;
                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                    User user = userDataSnapshot.getValue(User.class);
                    user.setuUid(userDataSnapshot.getKey());
                   // System.out.println(userDataSnapshot);
                    users.add(user);
                    emails[index++] = user.getEmail();
                }
                // Rendering the data in listview
                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, emails);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError dberror){
                // Not implemented
            }
        });
        //*********************** End: Reading data from database  ***********************//


        //*********************** Start: Deleting data from database  ***********************//
        // On click of any item in the list view, it will be deleted
        // Deleting data from database
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(),adapter.getItem(position),Toast.LENGTH_SHORT).show();
                String uuid = users.get(position).getuUid();
                String itemContent = adapter.getItem(position);
                if(!uuid.equals(firebaseAuth.getUid())){
                    // Snippet to remove a record from database
                    database.child("users").child(uuid).removeValue();
                    Toast.makeText(getBaseContext(), "Successfully deleted: " + itemContent , Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getBaseContext(), "You cannot delete yourself", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //*********************** End: Deleting data from database  ***********************//
    }

    //***** Method to update a particular record(document) on firebase database *****//
    public void updateUserDetails(View view){

        final String uuid = firebaseAuth.getCurrentUser().getUid();
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        // Fetching the data from UI
        final String addressContent, phoneContent;
        addressContent = address.getText().toString();
        phoneContent = phone.getText().toString();

        // Building the object and updating to firebase database
        try{
            // Building the object
            User user = new User();
            user.setEmail(firebaseAuth.getCurrentUser().getEmail());
            user.setAddress(addressContent);
            user.setPhone(phoneContent);

            // Updating on firebase
            database.child("users").child(uuid).setValue(user);
        }catch (Exception ex){
            Log.d("Update Error: ", ex.getStackTrace().toString());
        }
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
