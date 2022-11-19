package com.clagroup.cowhandlerv2;

import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.MenuItem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

/*
This class controls the landing page that the user arrives to after signing in.
 */
public class MainActivity extends AppCompatActivity {

    private EditText CowId, Species, Gender, Descr, BirthDt, Mother, Father, HerdNum, Weight, Age;
    private RadioGroup Vac1, Vac2;
    private Button submitButton;
    private FirebaseFirestore db;
    public FirebaseUser currentUser;

    DatabaseReference databaseReference;

//Create buttons
    Button btn, btn2,btn3,btn4;
    TextView credDisplay;

    // Initialize nav drawer in action bar
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get firebase user email and display
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String user_cred = currentUser.getEmail().toString();

        credDisplay = findViewById(R.id.credView);
        credDisplay.setText(user_cred);
        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//Assign button to Create Entry view
        btn = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.displayEntryBtn);
        btn3 = findViewById(R.id.editEntryBtn);
        btn4 = findViewById(R.id.deleteEntryBtn);

//Create button click event
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Link activities
                Intent intent = new Intent(MainActivity.this,AddCow.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Link activities
                Intent intent = new Intent(MainActivity.this,ViewCow.class);
                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Link activities
                Intent intent = new Intent(MainActivity.this,EditEntry.class);
                startActivity(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Link activities
                Intent intent = new Intent(MainActivity.this,DeleteCow.class);
                startActivity(intent);
            }
        });




                //Cow uploading shifted to enter cow page.


            // This is the current form of the database I just need to alter it and add the new value to make it larger
            //NewCow cow = new NewCow("11/9","Longhorn",119,"small black spots",1,0);
           // uploadcow(cow);

/*
            db.collection("Cow")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("successful pull", document.getId() + " => " + document.getData());
                                }
                            } else {
                                Log.w("failed pull", "Error getting documents.", task.getException());
                            }
                        }
                    });
                    */
    }

    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

