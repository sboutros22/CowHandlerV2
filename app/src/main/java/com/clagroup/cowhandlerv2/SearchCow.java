package com.clagroup.cowhandlerv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SearchCow extends AppCompatActivity {
    private FirebaseFirestore db;
    public FirebaseUser currentUser;
    private EditText CowId;
    private Button btn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cow);

        //GET FIREBASE INFORMATION
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        initializeViews();
        btn = findViewById(R.id.submitButton);

        //Create button click event
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Link activities
                Intent intent = new Intent(SearchCow.this, ViewCow.class);
                String cowId = CowId.getText().toString();
                intent.putExtra("cowBtnId", cowId);
                startActivity(intent);
            }
        });
    }

    private void initializeViews() {
        CowId = findViewById(R.id.cowId);
    }
}
