package com.clagroup.cowhandlerv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
/*
This class handles the process of getting manual user input
for a cow within the Activity

Main -> AddCow -> CowEntry
 */
public class CowEntry extends AppCompatActivity {

    private Button btn, picBtn;
    private FirebaseFirestore db;
    public FirebaseUser currentUser;
    private EditText CowId, Species, Descr, BirthDt, Mother, Father, Weight, Age;
    private RadioGroup Vac1,Vac2, Gender;
    private ImageView cowPic;
    StorageReference storageRef;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String pathToCowPicture;

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
                pathToCowPicture = "images/" + currentUser.getUid() + "/" + cowId + "/picture.jpg";
                Log.d("Submit button check", "did this work at all");

                NewCow Cow = new NewCow(cowId, birthdayDt, age, species, weight, sex, Description, vac1, vac2, mother, father, pathToCowPicture);
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
                        uploadPhoto();
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
        Weight = findViewById(R.id.Weight);
        Age = findViewById(R.id.Age);
        Vac1 = findViewById(R.id.Vac1);
        Vac2 = findViewById(R.id.Vac2);
        Gender = findViewById(R.id.Gender);
        cowPic = findViewById(R.id.cowImage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            cowPic.setImageBitmap(imageBitmap);
        }
    }

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
