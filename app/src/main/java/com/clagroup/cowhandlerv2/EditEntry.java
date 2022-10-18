package com.clagroup.cowhandlerv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
/*
This class will handle editing entries when the user clicks the+
"Edit Entry" button on the landing page
 */
public class EditEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);
    }
}