package com.clagroup.cowhandlerv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CowEntry extends AppCompatActivity {

    private Button btn;
    private FirebaseFirestore db;
    public FirebaseUser currentUser;
    private EditText CowId, Species, Descr, BirthDt, Mother, Father, HerdNum, Weight, Age;
    private RadioGroup Vac1,Vac2, Gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cow_entry);

        initializeViews();

        btn = findViewById(R.id.submitButton);
//Create button click event
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentUser = FirebaseAuth.getInstance().getCurrentUser();
                db = FirebaseFirestore.getInstance();

                String cowId = CowId.getText().toString();
                String species = Species.getText().toString();
                int genders = Gender.getCheckedRadioButtonId();
                RadioButton gender = findViewById(genders);
                String sex = gender.getText().toString();
                String birthdayDt = BirthDt.getText().toString();
                int weight = Integer.parseInt(Weight.getText().toString());
                int age = Integer.parseInt(Age.getText().toString());
                String mother = Mother.getText().toString();
                String father = Father.getText().toString();
                String Description = Descr.getText().toString();
                int vaccination1 = Vac1.getCheckedRadioButtonId();
                int vaccination2 = Vac2.getCheckedRadioButtonId();
                RadioButton vacCheck1 = findViewById(vaccination1);
                String vac1 = vacCheck1.getText().toString();
                RadioButton vacCheck2 = findViewById(vaccination2);
                String vac2 = vacCheck2.getText().toString();
                Log.d("Submit button check", "did this work at all");

                NewCow Cow = new NewCow(cowId, birthdayDt, age, species, weight, sex, Description, vac1, vac2, mother, father);
                uploadCow(Cow, cowId);
            }

        });

    }

    public void uploadCow(NewCow Cow, String cowId) {
        db.collection(currentUser.getDisplayName()).document(cowId)
                .set(Cow)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Cow added Successfully", "DocumentSnapshot successfully written!");;
                        startActivity(new Intent(CowEntry.this, MainActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Failed to add to database", "Error adding document", e);
                    }
                });

    }
    private void initializeViews() {
        CowId = findViewById(R.id.cowId);
        Species = findViewById(R.id.Species);
        Descr = findViewById(R.id.Descr);
        BirthDt = findViewById(R.id.BirthDt);
        Mother = findViewById(R.id.Mother);
        Father = findViewById(R.id.Father);
        HerdNum = findViewById(R.id.HerdNum);
        Weight = findViewById(R.id.Weight);
        Age = findViewById(R.id.Age);
        Vac1 = findViewById(R.id.Vac1);
        Vac2 = findViewById(R.id.Vac2);
        Gender = findViewById(R.id.Gender);
    }
}
