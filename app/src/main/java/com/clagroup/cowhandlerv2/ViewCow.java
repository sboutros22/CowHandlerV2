package com.clagroup.cowhandlerv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/*
This class should handle displaying individual entries
Or the whole db? undecided
 */

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class ViewCow extends AppCompatActivity {
    private FirebaseFirestore db;
    public FirebaseUser currentUser;
    private EditText CowId;
    private Button btn, Ebtn, Dbtn;
    private String cowID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cow);
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        initializeViews();
        btn = findViewById(R.id.submitButton);
        Ebtn = findViewById(R.id.Ebtn);
        Dbtn = findViewById(R.id.Dbtn);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            currentUser = FirebaseAuth.getInstance().getCurrentUser();
            db = FirebaseFirestore.getInstance();
            String value = extras.getString("cowBtnId");
            Log.d("will this work", "why wont this work?");
            ViewCow(value);
            Log.d("Does viewCow run", "why wont this work?");

        }
        //Create button click event
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentUser = FirebaseAuth.getInstance().getCurrentUser();
                db = FirebaseFirestore.getInstance();
                String cowId = CowId.getText().toString();
                ViewCow(cowId);
            }
        });
        Ebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Link activities
                Intent intent = new Intent(ViewCow.this, EditEntry.class);
                startActivity(intent);
            }
        });
        Dbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Link activities
                Intent intent = new Intent(ViewCow.this, DeleteCow.class);
                Log.d("what is cowid","cowId string: " + cowID);
                intent.putExtra("cowBtnId",cowID);
                Log.d("Did this send cowId to delete", "This is to see if the putExtra worked");
                startActivity(intent);
            }
        });

    }

    private void initializeViews() {
        CowId = findViewById(R.id.cowId);
    }

    @SuppressLint("NotConstructor")
    public void ViewCow(String cowId) {
        DocumentReference docRef = db.collection(currentUser.getDisplayName()).document(cowId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String results = "";
                       TextView cowInfo = (TextView) findViewById(R.id.results);
                       Map<String, Object> mapSetUp = new HashMap<String, Object>();
                       mapSetUp = document.getData();
                       for(Map.Entry<String, Object> entry : mapSetUp.entrySet()) {
                           //checks to ensure we don't show a filepath.
                           if(entry.getKey().equals("pathToCowPicture")) {
                               //gets the photo download url if one exists, then calls "showCowPicture" to display photo.
                               FirebaseStorage.getInstance().getReference().child(document.get(entry.getKey()).toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                   @Override
                                   public void onSuccess(Uri uri) {
                                       String url = uri.toString();
                                       showCowPicture(url);
                                       Log.d("successfully fetched cow pic", "Cow pic URL retrieved: " + url);
                                   }
                               }).addOnFailureListener(new OnFailureListener() {
                                   @Override
                                   public void onFailure(@NonNull Exception exception) {
                                       Log.d("Failed to retrieve photo from storage", "Error finding photo");
                                   }
                               });

                           }
                           //enters all the other data into a string to display
                           else {
                               String key = entry.getKey();
                               Log.d("What are the keys in else statement","Keys:  " + key);

                               String value = document.get(key).toString();
                               if(entry.getKey().equals("cowId")){
                                   cowID = value;
                                   Log.d("what is cowId in if statement","cowId string:  " + cowID);

                               }

                                   Log.d("successfully fetched cow", "Key: " + key + "  Value: " + value);
                               results += key + ": " + value + "\n";
                           }
                       }
                       cowInfo.setText(results);


                        Log.d("Successfully pull", "Successfully pulled data to phone:" + document.getData());
                    } else {
                        Log.d("No documents to pull", "No documents with id");
                    }
                } else {
                    Log.w("failed pull", "Error getting documents.", task.getException());
                }
            }
        });
    }
    private void showCowPicture(String picturePath){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        ImageView imageView = findViewById(R.id.cowImage);
        Glide.with(this)
                .load(picturePath)
                .into(imageView);
    }
}