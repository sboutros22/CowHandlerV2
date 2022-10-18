package com.clagroup.cowhandlerv2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//This page will serve as the
public class OverviewPage extends Activity {

    public FirebaseUser currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview_page);
//MORE PLACEHOLDER CODE FOR PLAYING AROUND WITH GETTING LOGIN INFO
        //currentUser = FirebaseAuth.getInstance().getCurrentUser();
        //String usrName = currentUser.getDisplayName();
//Log.d("Secret Value", secretValue.toString());
    }
}
