package com.clagroup.cowhandlerv2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OverviewPage extends Activity {
    private FirebaseFirestore db;
    public FirebaseUser currentUser;
    Button IdButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview_page);

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        viewAllCows();
    }

    private void viewAllCows() {
        ArrayList<String> ids = new ArrayList<String>();
        db.collection(currentUser.getDisplayName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // TextView allCowId = (TextView) findViewById(R.id.allCows);
                            String results = "";
                            String values = "";
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Successfully pulled all cowIds", document.getId() + " => " + document.getId());
                                values = document.getId();
                                ids.add(values);
                                results += values + "\n";
                            }
                            //   allCowId.setText(results);
                        } else {
                            Log.d("Failed at pulling all CowIds", "Error getting documents: ", task.getException());
                        }
                        createCowBtns(ids);
                    }
                });
    }
        public void createCowBtns(ArrayList<String> ids){
        LinearLayout layout = (LinearLayout) findViewById(R.id.linear_layout_tags);
        for(int i = 0; i < ids.size(); i++) {
            //set the properties for button
            Button btnTag = new Button(this);
            btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            btnTag.setText(ids.get(i));
            btnTag.setId(i);
            //add button to the layout
            layout.addView(btnTag);
            btnTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Link activities
                    Intent intent = new Intent(OverviewPage.this,ViewCow.class);
                    String cowBtn = btnTag.getText().toString();
                    intent.putExtra("cowBtnId",cowBtn);

                    startActivity(intent);
                }
            });
        }
    }

}

