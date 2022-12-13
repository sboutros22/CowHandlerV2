package com.clagroup.cowhandlerv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/*
The user will be navigated to this activity for deleting an
entry upon clicking the "Delete Entry" button on the landing page
 */
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class DeleteCow extends AppCompatActivity {

    private Button btn;
    private FirebaseFirestore db;
    public FirebaseUser currentUser;
    private EditText CowId;
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_cow);

        initializeViews();

        btn = findViewById(R.id.deleteButton);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUser = FirebaseAuth.getInstance().getCurrentUser();
            db = FirebaseFirestore.getInstance();
            String value = extras.getString("cowBtnId");
            Log.d("will this work", "why wont this work?");
            deleteCow(value);
            Log.d("Does deleteCow run", "why wont this work?");

        }

//Create button click event
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                currentUser = FirebaseAuth.getInstance().getCurrentUser();
                db = FirebaseFirestore.getInstance();

                String cowId = CowId.getText().toString();
                deleteCow(cowId);
            }
        });


    }
    // gets all the information from the database that is associated with the cowId
    // It also gets the image string as it also needs to be removed
    public void deleteCow(String cowId) {
        DocumentReference docRef = db.collection(currentUser.getDisplayName()).document(cowId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String picPath = (String) document.get("pathToCowPicture");
                        StorageReference storageRef = storage.getReference();
                        storageRef.child(picPath).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            // If the cowId is real and the ID is found with all the corresponding information it calls another
                            // function called deleteCowIn for that will actually delete the cow
                            public void onSuccess(Void aVoid) {
                                Log.d("Photo deletion", "successfully deleted picture.", task.getException());
                                deleteCowInfo(cowId);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                deleteCowInfo(cowId);
                                Log.w("Photo deletion", "Error deleting picture.", task.getException());
                            }
                        });

                    } else {
                        Log.d("Photo deletion", "No documents with id");
                    }
                }
                else {
                    Log.w("Photo deletion", "Error getting document.", task.getException());
                }
            }
        });
    }
// this removes the actual cowId and all the information that is with it
    public void deleteCowInfo(String cowId) {
        db.collection(currentUser.getDisplayName()).document(cowId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Cow " + cowId + " deleted", "DocumentSnapshot successfully deleted!");
                        startActivity(new Intent(DeleteCow.this, MainActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Failed to delete cow", "Error deleting document", e);
                    }
                });
    }



    private void initializeViews() {
        CowId = findViewById(R.id.Delete);
    }
}