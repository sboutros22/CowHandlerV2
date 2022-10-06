package com.clagroup.cowhandlerv2;

import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.text.TextUtils;
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
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText CowId, Species, Gender, Descr, BirthDt, Mother, Father, HerdNum, Weight, Age;
    private RadioGroup Vac1, Vac2;
    private Button submitButton;
    private FirebaseFirestore db;

    DatabaseReference databaseReference;

//Create button

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//Assign button to Create Entry view
        btn = findViewById(R.id.btn1);
//Create button click event
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Link activities
                Intent intent = new Intent(MainActivity.this,OverviewPage.class);
                startActivity(intent);
            }
        });

        db = FirebaseFirestore.getInstance();

        Map<String, String> Cow = new HashMap<>();
       // Map<String, NewCow> cowMap = new HashMap<>();

/*
// This will create the database from the inputs the user fills in
        submitButton.setOnClickListener((V) -> {
            String cowId = CowId.getText().toString();
            String species = Species.getText().toString();
            int birthdaydDt = Integer.parseInt(BirthDt.getText().toString());
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
*/
        //makes the first cow
            Cow.put("cowId", "1011");
            Cow.put("Species", "LongHorn");
            Cow.put("birthDate", "10/11");
            Cow.put("Description", "small black spots around face");
            Cow.put("Vax1", "True");
            Cow.put("Vax2", "False");
            //makes a second cow
        Cow.put("cowId", "1023");
        Cow.put("Species", "LongHorn");
        Cow.put("birthDate", "10/23");
        Cow.put("Description", "small black spots around butt");
        Cow.put("Vax1", "False");
        Cow.put("Vax2", "False");

            // This is the current form of the database I just need to alter it and add the new value to make it larger
            NewCow cow = new NewCow("11/9","Longhorn",119,"small black spots",1,0);
           // uploadcow(cow);


            db.collection("Cow")
                    .add(Cow)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("Successfully added", "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("Failed to add to database", "Error adding document", e);
                        }
                    });

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
   /*
            // trying to get the database to show up on phone
        DocumentReference documentReference = db.collection("Cow").document(String.valueOf(CowId));
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

            }
        });

    */

/*
// This will call the second page on the java that will check to see if it created or not
    public void uploadcow(NewCow cow){
        db.collection("Cow")
                .add(cow)
                .addOnSuccessListener((OnSuccessListener) (documentReference) -> {
                    Toast.makeText(MainActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    finish();
                })
                .addOnFailureListener((e) ->{
                    Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
                });
    }
//This will inilize the values and what they should be, essentially it makes a spot for them
    public void initializeViews() {

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

 */
    }
}



