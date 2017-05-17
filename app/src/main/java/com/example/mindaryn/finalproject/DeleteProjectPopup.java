package com.example.mindaryn.finalproject;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteProjectPopup extends AppCompatActivity {
    private String devideID;
    Button confirm, cancel;
    DatabaseReference projectRef;
    String selectedItem;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_project_popup);
        devideID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        projectRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://finalproject-4c0c6.firebaseio.com/"+devideID+"/project");

        selectedItem = getIntent().getStringExtra("position");
        confirm = (Button)findViewById(R.id.confirm_delete);
        cancel = (Button)findViewById(R.id.cancel_delete);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        key = getIntent().getStringExtra("key");
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.35));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("key","delete : "+key);
                projectRef.child(key).removeValue();
                finish();
            }
        });
    }
}
