package com.example.mindaryn.finalproject;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManangeProjectPopup extends AppCompatActivity {
    private String devideID;
    EditText addCurrentField;
    TextView current, goal;
    Button finish, delete, add, remove;
    String key;
    int currentMoney;
    DatabaseReference projectRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manange_project_popup);

        devideID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        key = getIntent().getStringExtra("key");
        projectRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://finalproject-4c0c6.firebaseio.com/"+devideID+"/project/"+key);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.53));
        Log.d("key","position in manage : "+key);

        finish = (Button)findViewById(R.id.finishManageProject);
        delete = (Button)findViewById(R.id.deleteProject);

        add = (Button)findViewById(R.id.addCurrentBut);
        remove = (Button)findViewById(R.id.removeCurrentBut);

        addCurrentField = (EditText)findViewById(R.id.addCurrentField);

        current = (TextView)findViewById(R.id.current_in_manage);
        goal = (TextView)findViewById(R.id.goal_in_manage);

        projectRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("key","onChildAdded");
                if(dataSnapshot.getKey().equals("current")){
                    String cur = dataSnapshot.getValue().toString();
                    currentMoney = Integer.parseInt(cur);
                    current.setText(cur+" THB");

                }
                if(dataSnapshot.getKey().equals("goal")){
                    String myGoal = dataSnapshot.getValue().toString();
                    goal.setText(myGoal+" THB");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("key","onChildChanged");
                if(dataSnapshot.getKey().equals("current")){
                    String myCurrent = dataSnapshot.getValue().toString();
                    current.setText(myCurrent+" THB");
                    currentMoney = Integer.parseInt(myCurrent);
                }
                Log.d("key","Test child key : "+dataSnapshot.getKey()+", Value : "+dataSnapshot.getValue()+", s : "+s);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adding = Integer.parseInt(addCurrentField.getText().toString());
                projectRef.child("current").setValue((currentMoney+adding)+"");
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int removing = Integer.parseInt(addCurrentField.getText().toString());
                projectRef.child("current").setValue((currentMoney-removing)+"");
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManangeProjectPopup.this, DeleteProjectPopup.class);
                intent.putExtra("key", key );
                finish();
                startActivity(intent);
            }
        });
    }
}
