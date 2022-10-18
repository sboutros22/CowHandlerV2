package com.clagroup.cowhandlerv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
/*
After the user clicks the "add" button on the landing page, this
activity will prompt the user available options.

Currently only the cow functionality is in this class
 */
public class AddCow extends AppCompatActivity {

    Button cowBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cow);

        cowBtn = findViewById(R.id.selectCowBtn);

        cowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Link activities
                Intent intent = new Intent(AddCow.this,CowEntry.class);
                startActivity(intent);
            }
        });
    }
}