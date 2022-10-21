package com.clagroup.cowhandlerv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditEntry extends AppCompatActivity {

    private Button btn;
    Spinner cowUpdateFields;
    private FirebaseFirestore db;
    public FirebaseUser currentUser;
    private EditText newValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);

        //get the spinner from the xml.
        Spinner dropdown =findViewById(R.id.updateField);
        //create a list of items for the spinner.
        String[] items = new String[]{"Weight", "Age", "Vaccination Dose 1", "Vaccination Dose 2", "Description"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);


        initializeViews();

        btn = findViewById(R.id.editButton);
        //Create button click event
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                currentUser = FirebaseAuth.getInstance().getCurrentUser();
                db = FirebaseFirestore.getInstance();

                String updateValue = newValue.getText().toString();
                String updateField = dropdown.getSelectedItem().toString().toLowerCase();

                updateEntry("0307", updateValue, updateField);


            }
        });


    }

    public void updateEntry(String cowId, String updateValue, String updateField) {
        db.collection(currentUser.getDisplayName()).document(cowId)
                .update(updateField, updateValue)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Cow " + cowId + " updated", "Field successfully updated!");
                        startActivity(new Intent(EditEntry.this, MainActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Failed to update cow", "Error updating field", e);
                    }
                });
    }

    private void initializeViews() {
        newValue = findViewById(R.id.updateValue);
    }
}