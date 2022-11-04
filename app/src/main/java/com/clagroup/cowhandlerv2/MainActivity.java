package com.clagroup.cowhandlerv2;

import static android.app.ProgressDialog.show;

import androidx.appcompat.app.AppCompatActivity;

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

//Create button

    Button btn, btn2,btn3,btn4,btn5;
    TextView credDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get firebase user email and display
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String user_cred = currentUser.getEmail().toString();

        credDisplay = findViewById(R.id.credView);
        credDisplay.setText(user_cred);

//Assign button to Create Entry view
        btn = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.displayEntryBtn);
        btn3 = findViewById(R.id.editEntryBtn);
        btn4 = findViewById(R.id.deleteEntryBtn);
        btn5 = findViewById(R.id.overviewBtn);

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
                //Link activities
                Intent intent = new Intent(MainActivity.this,DeleteCow.class);
                startActivity(intent);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Link activities
                Intent intent = new Intent(MainActivity.this,OverviewPage.class);
                startActivity(intent);
            }
        });

    }

}

