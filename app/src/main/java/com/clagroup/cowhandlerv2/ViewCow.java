package com.clagroup.cowhandlerv2;

import static android.Manifest.permission.MANAGE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;

/*
This class should handle displaying individual entries
Or the whole db? undecided
 */

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ViewCow extends AppCompatActivity {
    private FirebaseFirestore db;
    public FirebaseUser currentUser;

    private String cowId;

    // variables for our buttons.
    Button generatePDFbtn;

    // declaring width and height
    // for our PDF file.
    int pageHeight = 1120;
    int pagewidth = 792;

    // creating a bitmap variable
    // for storing our images
    Bitmap bmp, scaledbmp;

    // constant code for runtime permissions
    private static final int PERMISSION_REQUEST_CODE = 200;

    private Button btn, Ebtn, Dbtn;
    private String cowID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cow);

        //GET FIREBASE INFORMATION
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // initializing our variables for pdf generation.
        generatePDFbtn = findViewById(R.id.pdfButton);

        //BELOW USED FOR IMAGE DRAWING IN PDF.
        //bmp = BitmapFactory.decodeResource(getResources(), R.drawable.imageName);
        //scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false);

        btn = findViewById(R.id.submitButton);
        Ebtn = findViewById(R.id.Ebtn);
        Dbtn = findViewById(R.id.Dbtn);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            if(extras.getBoolean("pdfRequest")) {
                currentUser = FirebaseAuth.getInstance().getCurrentUser();
                db = FirebaseFirestore.getInstance();
                String value = extras.getString("cowBtnId");
                cowId = value;
                ViewCow(value, true);
                generatePDF(value);
                Log.d("bundle pdf generation", "PDF generated via bundle call");
                Intent intent = new Intent(ViewCow.this,OverviewPage.class);
                startActivity(intent);
            }else{
                currentUser = FirebaseAuth.getInstance().getCurrentUser();
                db = FirebaseFirestore.getInstance();
                String value = extras.getString("cowBtnId");
                cowId = value;
                ViewCow(value, false);
                Log.d("Does viewCow run via extras bundle", "ViewCow via bundle successful");
            }

        }

        generatePDFbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewCow(cowId, true);
            }
        });
        Ebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Link activities
                Intent intent = new Intent(ViewCow.this, EditEntry.class);
                Log.d("what is cowid","cowId string: " + cowID);
                intent.putExtra("cowBtnId",cowID);
                Log.d("Did this send cowId to edit?", "This is to see if the putExtra worked");
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

    @SuppressLint("NotConstructor")
    public void ViewCow(String cowId, Boolean pdfRequested) {
        Log.d("cowId check for viewCow", "view cowId: " + cowId);
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
                       if(pdfRequested){
                           // calling method to
                           // generate our PDF file.
                           generatePDF(cowId);
                           Log.d("pdf generation", "PDF requested successfully");
                       }
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

    private void generatePDF(String cowId) {
        // below code is used for
        // checking our permissions.
        if (checkPermission()) {
            Toast.makeText(ViewCow.this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }

        Log.d("cowId check for pdf name", "pdf cowId: " + cowId);
        // creating an object variable
        // for our PDF document.
        PdfDocument pdfDocument = new PdfDocument();

        // two variables for paint "paint" is used
        // for drawing shapes and we will use "title"
        // for adding text in our PDF file.
        Paint paint = new Paint();
        Paint title = new Paint();

        // we are adding page info to our PDF file
        // in which we will be passing our pageWidth,
        // pageHeight and number of pages and after that
        // we are calling it to create our PDF.
        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();

        // below line is used for setting
        // start page for our PDF file.
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        // creating a variable for canvas
        // from our page of PDF.
        Canvas pageCanvas = myPage.getCanvas();
        View contentView = findViewById(R.id.results);

        pageCanvas.scale(1f, 1f);
        int pageWidth = pageCanvas.getWidth();
        int pageHeight = pageCanvas.getHeight();
        int measureWidth = View.MeasureSpec.makeMeasureSpec(pageWidth, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(pageHeight, View.MeasureSpec.EXACTLY);
        contentView.measure(measureWidth, measuredHeight);
        contentView.layout(0, 0, pageWidth, pageHeight);
        contentView.draw(pageCanvas);

        // after adding all attributes to our
        // PDF file we will be finishing our page.
        pdfDocument.finishPage(myPage);

        // below line is used to set the name of
        // our PDF file and its path.
        Log.d("FilePath check", "Filepath: " + getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString());
        Log.d("cowId check for pdf name", "pdf cowId: " + cowId);
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "Cow_" + cowId + ".pdf");

        try {
            // after creating a file name we will
            // write our PDF file to that location.
            pdfDocument.writeTo(new FileOutputStream(file));

            // below line is to print toast message
            // on completion of PDF generation.
            Toast.makeText(ViewCow.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // below line is used
            // to handle error
            e.printStackTrace();
        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        pdfDocument.close();
    }

    private boolean checkPermission() {
        // checking of permissions.
        //will need to update permission 3 prior to app store launch, as that will be hard to justify.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
}