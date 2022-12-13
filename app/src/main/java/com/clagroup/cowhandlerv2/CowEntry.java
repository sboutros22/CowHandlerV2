package com.clagroup.cowhandlerv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

/*
This class handles the process of getting manual user input
for a cow within the Activity

Main -> AddCow -> CowEntry
 */
public class CowEntry extends AppCompatActivity {
/*
These are the constants and variable that will be used
 */

    private Button btn, picBtn;
    private FirebaseFirestore db;
    public FirebaseUser currentUser;
    public TextView GenderText, Vac1Text, Vac2Text, BreedableText;
    private EditText CowId, Species, Descr, BirthDt, Mother, Father, Weight, Age, TimeInHerd, NumOfBirths, HerdNum;
    private RadioGroup Vac1,Vac2, Gender,breeder;
    private ImageView cowPic;
    StorageReference storageRef;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String pathToCowPicture, sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cow_entry);

        initializeViews();

        // Create a storage reference from our app
        storageRef = storage.getReference();

        picBtn = findViewById(R.id.pictureButton);
        picBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent picIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(picIntent,REQUEST_IMAGE_CAPTURE);
            }
        });
        Spinner dropdown =findViewById(R.id.updateField);
        //create a list of items for the spinner.
        String[] items = new String[]{"Calf", "Cow", "Bull"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        // Creates the drop down box
        dropdown.setAdapter(adapter);
        // There are three options in the dropdown box
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (dropdown.getSelectedItem().toString()) {
                    case "Calf":
                        Log.d("Testing Calf case", "Is the Calf case being selected when chosen?");
                        // All the variables are currently hidden and will be set to visible once the case is selected
                        // Revealing the inputs a user can add for that case

                        CowId.setVisibility(View.VISIBLE);
                        Species.setVisibility(View.VISIBLE);
                        Descr.setVisibility(View.VISIBLE);
                        BirthDt.setVisibility(View.VISIBLE);
                        Mother.setVisibility(View.VISIBLE);
                        Father.setVisibility(View.VISIBLE);
                        Weight.setVisibility(View.VISIBLE);
                        Age.setVisibility(View.VISIBLE);
                        Vac1.setVisibility(View.VISIBLE);
                        Vac2.setVisibility(View.VISIBLE);
                        Gender.setVisibility(View.VISIBLE);
                        cowPic.setVisibility(View.VISIBLE);
                        breeder.setVisibility(View.VISIBLE);
                        TimeInHerd.setVisibility(View.VISIBLE);
                        HerdNum.setVisibility(View.VISIBLE);
                        NumOfBirths.setVisibility(View.VISIBLE);
                        GenderText.setVisibility(View.VISIBLE);
                        Vac1Text.setVisibility(View.VISIBLE);
                        Vac2Text.setVisibility(View.VISIBLE);
                        BreedableText.setVisibility(View.VISIBLE);
                        break;
                    case "Cow":
                        Log.d("Testing Cow case", "Is the Cow case being selected when chosen?");
                        CowId.setVisibility(View.VISIBLE);
                        Species.setVisibility(View.VISIBLE);
                        Descr.setVisibility(View.VISIBLE);
                        BirthDt.setVisibility(View.VISIBLE);
                        Mother.setVisibility(View.VISIBLE);
                        Father.setVisibility(View.VISIBLE);
                        Weight.setVisibility(View.VISIBLE);
                        Age.setVisibility(View.VISIBLE);
                        Vac1.setVisibility(View.VISIBLE);
                        Vac2.setVisibility(View.VISIBLE);
                        Gender.setVisibility(View.GONE);
                        cowPic.setVisibility(View.VISIBLE);
                        breeder.setVisibility(View.GONE);
                        TimeInHerd.setVisibility(View.VISIBLE);
                        HerdNum.setVisibility(View.VISIBLE);
                        NumOfBirths.setVisibility(View.VISIBLE);
                        GenderText.setVisibility(View.GONE);
                        Vac1Text.setVisibility(View.VISIBLE);
                        Vac2Text.setVisibility(View.VISIBLE);
                        BreedableText.setVisibility(View.GONE);
                        sex = "Female";
                        break;
                    case "Bull":
                        Log.d("Testing Bull case", "Is the Bull case being selected when chosen?");
                        CowId.setVisibility(View.VISIBLE);
                        Species.setVisibility(View.VISIBLE);
                        Descr.setVisibility(View.VISIBLE);
                        BirthDt.setVisibility(View.VISIBLE);
                        Mother.setVisibility(View.VISIBLE);
                        Father.setVisibility(View.VISIBLE);
                        Weight.setVisibility(View.VISIBLE);
                        Age.setVisibility(View.VISIBLE);
                        Vac1.setVisibility(View.VISIBLE);
                        Vac2.setVisibility(View.VISIBLE);
                        Gender.setVisibility(View.GONE);
                        cowPic.setVisibility(View.VISIBLE);
                        TimeInHerd.setVisibility(View.VISIBLE);
                        HerdNum.setVisibility(View.VISIBLE);
                        NumOfBirths.setVisibility(View.GONE);
                        GenderText.setVisibility(View.GONE);
                        Vac1Text.setVisibility(View.VISIBLE);
                        Vac2Text.setVisibility(View.VISIBLE);
                        BreedableText.setVisibility(View.GONE);
                        sex = "Male";
                        break;
                }
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

                btn = findViewById(R.id.submitButton);
//Create button click event
        btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        db = FirebaseFirestore.getInstance();
                        // Each of these correspond with an input the user puts into the app

                        String cowId = CowId.getText().toString();
                        String species = Species.getText().toString();
                        int genders = Gender.getCheckedRadioButtonId();
                        RadioButton gender = findViewById(genders);
                        sex = gender.getText().toString();
                        int Breeder = breeder.getCheckedRadioButtonId();
                        RadioButton breedable = findViewById(Breeder);
                        String Breedable = breedable.getText().toString();
                        String birthdayDt = BirthDt.getText().toString();
                        int weight = Integer.parseInt(Weight.getText().toString());
                        int age = Integer.parseInt(Age.getText().toString());
                        int numOffspring = Integer.parseInt(NumOfBirths.getText().toString());
                        int timeInHerd = Integer.parseInt(TimeInHerd.getText().toString());
                        String mother = Mother.getText().toString();
                        String father = Father.getText().toString();
                        String Description = Descr.getText().toString();
                        int vaccination1 = Vac1.getCheckedRadioButtonId();
                        int vaccination2 = Vac2.getCheckedRadioButtonId();
                        RadioButton vacCheck1 = findViewById(vaccination1);
                        String vac1 = vacCheck1.getText().toString();
                        RadioButton vacCheck2 = findViewById(vaccination2);
                        String vac2 = vacCheck2.getText().toString();
                        int herdNumber = Integer.parseInt(HerdNum.getText().toString());
                        pathToCowPicture = "images/" + currentUser.getUid() + "/" + cowId + "/picture.jpg";
                        Log.d("Submit button check", "did this work at all");

                        // Sends all the information that was inputted to NewCow Cow to create a new entry
                        // Cow is now a string of information
                        NewCow Cow = new NewCow(cowId, birthdayDt, age, species, weight, sex, Description, vac1, vac2, Breedable, numOffspring, mother, father, herdNumber, timeInHerd, pathToCowPicture);
                        // Sends all the information to the uploadCow function that will send that string to Firebase Firestore
                        uploadCow(Cow, cowId);

                    }

                });
    }
// send the information of the cow and saves it in the database
    // uses the cowID as the name of that line of data
    // cowId will be used as a way to pull or push new information from the app
    public void uploadCow(NewCow Cow, String cowId) {
        db.collection(currentUser.getDisplayName()).document(cowId)
                .set(Cow)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // calls the uploadPhoto function to input an image into the database
                        uploadPhoto();
                        Log.d("Cow added Successfully", "DocumentSnapshot successfully written!");;

                        startActivity(new Intent(CowEntry.this, MainActivity.class));
                    }
                })
                // Is a check to see if the connection to firebase failed or if failed to send
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Failed to add to database", "Error adding document", e);
                    }
                });

    }
    // The initialized values for the variables
    // Necessary to set up the values for late input of information from users
    private void initializeViews() {
        CowId = findViewById(R.id.cowId);
        Species = findViewById(R.id.Species);
        Descr = findViewById(R.id.Descr);
        BirthDt = findViewById(R.id.BirthDt);
        Mother = findViewById(R.id.Mother);
        Father = findViewById(R.id.Father);
        Weight = findViewById(R.id.Weight);
        Age = findViewById(R.id.Age);
        Vac1 = findViewById(R.id.Vac1);
        Vac2 = findViewById(R.id.Vac2);
        Gender = findViewById(R.id.Gender);
        cowPic = findViewById(R.id.cowImage);
        breeder = findViewById(R.id.breeder);
        TimeInHerd = findViewById(R.id.TimeInHerd);
        HerdNum = findViewById(R.id.HerdNum);
        NumOfBirths = findViewById(R.id.NumOfBirths);
        GenderText = findViewById(R.id.GenderText);
        Vac1Text = findViewById(R.id.Vac1Text);
        Vac2Text = findViewById(R.id.Vac2Text);
        BreedableText = findViewById(R.id.BreedableText);

    }
    // Sets up the image inputting option
    // Used for taking pictures
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            cowPic.setImageBitmap(imageBitmap);
        }
    }
// Used for pushing photo into Firestore database
    private void uploadPhoto(){
        Bitmap bitmap = ((BitmapDrawable) cowPic.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        StorageReference pictureRef = storageRef.child("images/" + currentUser.getUid() + "/" + CowId.getText().toString() + "/picture.jpg");

        UploadTask uploadTask = pictureRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.w("Failed to add photo to storage", "Error adding document");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                Log.d("Cow picture stored Successfully", "Stored Cow Picture!");;
            }
        });
    }
}
