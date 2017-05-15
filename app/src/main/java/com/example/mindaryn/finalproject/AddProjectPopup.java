package com.example.mindaryn.finalproject;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AddProjectPopup extends AppCompatActivity {
    private String devideID;
    Button create;
    EditText nameText, goalText;
    DatabaseReference mRoofRef = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project_popup);
        devideID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.53));

        create = (Button)findViewById(R.id.createBut);
        nameText = (EditText)findViewById(R.id.newProjectName);
        goalText = (EditText)findViewById(R.id.newProjectGoal);

        setEventButton();
    }

    public void setEventButton(){
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> data= new HashMap<String, String>();
                data.put("name",nameText.getText().toString());
                data.put("goal",goalText.getText().toString());
                data.put("current","0");

                mRoofRef.child(devideID)
                        .child("project")
                        .push().setValue(data);
              AddProjectPopup.this.finish();
            }
        });
    }
}
