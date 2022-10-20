package com.clagroup.cowhandlerv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class DeleteCow extends AppCompatActivity {

    private Button btn;
    private FirebaseFirestore db;
    public FirebaseUser currentUser;
    private EditText CowId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_cow);

        initializeViews();

        btn = findViewById(R.id.deleteButton);
//Create button click event
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                currentUser = FirebaseAuth.getInstance().getCurrentUser();
                db = FirebaseFirestore.getInstance();

                String cowId = CowId.getText().toString();

                //deleteCow(cowId);


            }
        });


    }
/*
    public void deleteCow(String cowId) {
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

 */

    private void initializeViews() {
        CowId = findViewById(R.id.cowId);
    }
}