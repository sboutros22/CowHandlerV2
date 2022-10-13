package com.clagroup.cowhandlerv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class CowEntry extends AppCompatActivity {
    private EditText CowId, Species, Descr, BirthDt, Mother, Father, HerdNum, Weight, Age;
    private RadioGroup Vac1,Vac2, Gender;
    private Button submitButton;
    private FirebaseFirestore db;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cow_entry);

        initializeViews();
        db = FirebaseFirestore.getInstance();
        //Map<String, Object> Cow = new HashMap<>();


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cowId = CowId.getText().toString();
                String species = Species.getText().toString();
                int genders = Gender.getCheckedRadioButtonId();
                RadioButton gender = findViewById(genders);
                String birthdayDt = BirthDt.getText().toString();
                int weight = Integer.parseInt(Weight.getText().toString());
                int age = Integer.parseInt(Age.getText().toString());
                String mother = Mother.getText().toString();
                String father = Father.getText().toString();
                String Description = Descr.getText().toString();
                int herdNumber = Integer.parseInt(HerdNum.getText().toString());
                int vaccination1 = Vac1.getCheckedRadioButtonId();
                int vaccination2 = Vac2.getCheckedRadioButtonId();
                RadioButton vac1 = findViewById(vaccination1);
                RadioButton vac2 = findViewById(vaccination2);
                Log.d("Submit button check", "did this work at all");
/*
                Cow.put("CowID", cowId);
                Cow.put("birthDate", birthdayDt);
                Cow.put("Age", age);
                Cow.put("Species", species);
                Cow.put("Cow Weight", weight);
                Cow.put("Gender", gender);
                Cow.put("Description", Description);
                Cow.put("Vaccination Dose 1", vaccination1);
                Cow.put("Vaccination Dose 2", vaccination2);
                Cow.put("Mother", mother);
                Cow.put("Father", father);
                Cow.put("Herd Number", herdNumber);
*/
                NewCow Cow = new NewCow(cowId, birthdayDt, age, species, weight, gender, Description, vac1, vac2, mother, father, herdNumber);
                uploadCow(Cow);
            }

            public void uploadCow(NewCow Cow) {

                db.collection("Cow")
                        .add(Cow)
                        .addOnSuccessListener(documentReference -> Log.d("Successfully added", "DocumentSnapshot added with ID: " + documentReference.getId()))
                        .addOnFailureListener(e -> Log.w("Failed to add to database", "Error adding document", e));
                       // .addOnSuccessListener((OnSuccessListener) (documentReference) -> {
                        //    Toast.makeText(CowEntry.this, "Added successfully", Toast.LENGTH_SHORT).show();
                           // startActivity(new Intent(CowEntry.this, CowEntry.class));
                        //    finish();
                        }
                      //  .addOnFailureListener((e) -> {
                       //     Toast.makeText(CowEntry.this, "Failure", Toast.LENGTH_SHORT).show();
                     //   });

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
        submitButton = findViewById(R.id.submitButton);
    }

}
