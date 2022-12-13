package com.clagroup.cowhandlerv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

/*
This class will handle editing entries when the user clicks the+
"Edit Entry" button on the landing page
 */
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditEntry extends AppCompatActivity {

    private Button btn;
    private FirebaseFirestore db;
    public FirebaseUser currentUser;
    private RadioGroup vaccineValue;
    private EditText newValue;
    private TextView cowIdDisplay, currentValueDisplay;
    private String cowId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);

        initializeViews();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String value = extras.getString("cowBtnId");
            cowId = value;
            Log.d("Edit CowId Input", "Edit cow  reads cowId:" + cowId);
            cowIdDisplay.setText(cowId);
        }

        //get the spinner from the xml.
        Spinner dropdown =findViewById(R.id.updateField);
        //create a list of items for the spinner.
        String[] items = new String[]{"Weight", "Age", "Vaccination Dose 1", "Vaccination Dose 2", "Description"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //need to adjust cases to limit input type
                switch(dropdown.getSelectedItem().toString().toLowerCase()) {
                    case "weight":
                    case "age":
                        newValue.setVisibility(View.VISIBLE);
                        newValue.setInputType(2);//sets number input
                        currentValueDisplay.setVisibility((View.VISIBLE));
                        vaccineValue.setVisibility(View.GONE);
                        getCurrentValue(cowId, dropdown.getSelectedItem().toString().toLowerCase());
                        break;
                    case "description":
                        newValue.setVisibility(View.VISIBLE);
                        newValue.setInputType(1);//sets text input
                        currentValueDisplay.setVisibility((View.VISIBLE));
                        vaccineValue.setVisibility(View.GONE);
                        getCurrentValue(cowId, dropdown.getSelectedItem().toString().toLowerCase());
                        break;
                    case "vaccination dose 1":
                        vaccineValue.setVisibility(View.VISIBLE);
                        newValue.setVisibility(View.GONE);
                        currentValueDisplay.setVisibility((View.GONE));
                        getCurrentValue(cowId, "vac1");
                        selectCurrentButton();
                        break;
                    case "vaccination dose 2":
                        vaccineValue.setVisibility(View.VISIBLE);
                        newValue.setVisibility(View.GONE);
                        currentValueDisplay.setVisibility((View.GONE));
                        getCurrentValue(cowId, "vac2");
                        selectCurrentButton();
                        break;
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        btn = findViewById(R.id.editButton);
        //Create button click event
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

// this sets up the drop down box for the edit page
                // each case has its own respected values that are updated based on the type of inputs they are
                String updateValue;
                String updateField = dropdown.getSelectedItem().toString().toLowerCase();
                switch(updateField){
                    case "weight":
                    case "age":
                    case "description":
                        updateValue = newValue.getText().toString();
                        break;
                    case "vaccination dose 1":
                        updateField = "vac1";
                        int vaccination1 = vaccineValue.getCheckedRadioButtonId();
                        RadioButton vacCheck1 = findViewById(vaccination1);
                        updateValue = vacCheck1.getText().toString();
                        break;
                    case "vaccination dose 2":
                        updateField = "vac2";
                        int vaccination2 = vaccineValue.getCheckedRadioButtonId();
                        RadioButton vacCheck2 = findViewById(vaccination2);
                        updateValue = vacCheck2.getText().toString();
                        break;
                    default:
                        updateValue = null;
                        break;
                }

                if(updateValue != null){
                    Log.d("Update Value", "update value: " + updateValue);
                    updateEntry(cowId, updateValue, updateField);
                }
                else{
                    Log.d("Update Value", "There was an error finding the value to update");
                }



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
// gets the current value and displays it
    // pulls from the database
    public void getCurrentValue(String cowId, String key){
        DocumentReference docRef = db.collection(currentUser.getDisplayName()).document(cowId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> mapSetUp = new HashMap<String, Object>();
                        mapSetUp = document.getData();
                        for(Map.Entry<String, Object> entry : mapSetUp.entrySet()) {
                            //checks to ensure we don't show a filepath.
                            if(entry.getKey().equals(key)) {
                                String key = entry.getKey();
                                String currentValue = document.get(key).toString();
                                Log.d("Getting Value", "Successfully got value: " + currentValue);
                                currentValueDisplay.setText(currentValue);
                                Log.d("Getting Value", "Display value setText ran.");
                            }

                        }
                    }
                    else {
                        Log.d("Getting Value", "No documents with id");
                    }
                }
                else {
                    Log.w("Getting Value", "Error getting document.", task.getException());
                }
            }
        });
    }
// used for changing radio options from yes to no or vis-versa
    private void selectCurrentButton(){
        if(currentValueDisplay.getText() == "Yes"){
            RadioButton b = (RadioButton) findViewById(R.id.Yes);
            b.setChecked(true);
        }
        else{
            RadioButton b = (RadioButton) findViewById(R.id.No);
            b.setChecked(true);
        }
    }
// Sets the values before user inputs
    private void initializeViews() {
        vaccineValue = findViewById(R.id.Vaccine);
        newValue = findViewById(R.id.updateValue);
        cowIdDisplay = findViewById(R.id.cowIdDisplay);
        currentValueDisplay = findViewById(R.id.currentValueDisplay);
    }
}