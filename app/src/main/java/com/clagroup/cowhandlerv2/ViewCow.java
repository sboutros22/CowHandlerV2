package com.clagroup.cowhandlerv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
/*
This class should handle displaying individual entries
Or the whole db? undecided
 */
public class ViewCow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cow);
    }
}