package com.clagroup.cowhandlerv2;

import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.MenuItem;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
        //btn5 = findViewById(R.id.overviewBtn);

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
                Intent intent = new Intent(MainActivity.this,SearchCow.class);
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
                // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">
                //                                  AlertDialog.Builder</a></code> with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage("Are you sure you want to continue?")
                        .setTitle("Confirm Deletion");
                // Add the buttons
                builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(MainActivity.this,"Confirming...",Toast.LENGTH_SHORT).show();
                        //Link activities
                        Intent intent = new Intent(MainActivity.this,DeleteCow.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(MainActivity.this,"Cancelling...",Toast.LENGTH_SHORT).show();
                    }
                });
                // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code>
                // from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
/*
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Link activities
                Intent intent = new Intent(MainActivity.this, OverviewPage.class);
                startActivity(intent);
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

/*
Don't currently know if needed or not

<com.google.android.material.button.MaterialButton
        style="@style/Widget.MaterialComponents.Button.OutlinedButton"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_weight=".05"
        app:backgroundTint="@color/green_light"
        android:id="@+id/overviewBtn"
        app:rippleColor="@color/sail_color"
        android:textColor="@color/sail_color"
        android:text="@string/View_All_Cows"
        app:strokeColor="@color/sail_color"/>
 */