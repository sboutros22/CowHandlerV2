package com.clagroup.cowhandlerv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
/*
The user will be navigated to this activity for deleting an
entry upon clicking the "Delete Entry" button on the landing page
 */
public class DeleteCow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_cow);
    }
}