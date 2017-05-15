package com.example.mindaryn.finalproject;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Step1 extends AppCompatActivity {
    private String devideID;
    DatabaseReference mRoofRef = FirebaseDatabase.getInstance().getReference();

    Switch monthlySavingSwitch, projectSwitch;
    LinearLayout step1, step2, step3;
    FrameLayout title;
    Button next1, next2, finish, addFixedCostBut;
    EditText totalMoneyText, monthlySavingText, projectNameText, projectGoalText, fixedCostText;

    private String totalMoney, monthlySaving;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step1);

        devideID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        initialView();

        mRoofRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(!snapshot.hasChild(devideID)){
                    mRoofRef.child(devideID).child("fixed_cost").setValue("0");
                    setUpApp();
                }else {
                    if(!snapshot.child(devideID).hasChild("set_up")){
                        setUpApp();
                    } else {
                        goToTimeLine();
                    }
                }
            }
            @Override public void onCancelled(DatabaseError error) { }
        });

        setEventButton();
    }

    public void initialView(){
        monthlySavingSwitch = (Switch)findViewById(R.id.monthlySwitch);
        projectSwitch = (Switch)findViewById(R.id.projectSwitch);

        totalMoneyText = (EditText)findViewById(R.id.totalMoney);
        monthlySavingText = (EditText)findViewById(R.id.monthlySaving);
        projectNameText = (EditText)findViewById(R.id.projectName);
        projectGoalText = (EditText)findViewById(R.id.projectGoal);
        fixedCostText = (EditText)findViewById(R.id.fixedField);

        step1 = (LinearLayout)findViewById(R.id.step1);
        step2 = (LinearLayout)findViewById(R.id.step2);
        step3 = (LinearLayout)findViewById(R.id.step3);

        title = (FrameLayout)findViewById(R.id.setup_title);

        next1 = (Button)findViewById(R.id.nextBut1);
        next2 = (Button)findViewById(R.id.nextBut2);
        finish = (Button)findViewById(R.id.finishBut);
        addFixedCostBut = (Button)findViewById(R.id.addFixedCostBut);
    }

    public void goToTimeLine(){
        Intent intent = new Intent(Step1.this,TimeLine.class);
        startActivity(intent);
    }

    public void setUpApp(){
        step1.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);

        monthlySavingText.setFocusable(false);
        monthlySavingText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        monthlySavingText.setClickable(false); // user

        projectNameText.setFocusable(false);
        projectNameText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        projectNameText.setClickable(false); // user

        projectGoalText.setFocusable(false);
        projectGoalText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        projectGoalText.setClickable(false); // user

        monthlySavingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    monthlySavingText.setFocusableInTouchMode(true);
                    monthlySavingText.setClickable(true);
                    monthlySavingText.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_white));
                } else {
                    monthlySavingText.setFocusable(false);
                    monthlySavingText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                    monthlySavingText.setClickable(false); // user
                    monthlySavingText.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_gray));
                }

            }
        });

        projectSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    projectNameText.setFocusableInTouchMode(true);
                    projectNameText.setClickable(true);
                    projectNameText.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_white));

                    projectGoalText.setFocusableInTouchMode(true);
                    projectGoalText.setClickable(true);
                    projectGoalText.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_white));
                } else {
                    projectNameText.setFocusable(false);
                    projectNameText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                    projectNameText.setClickable(false); // user
                    projectNameText.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_gray));

                    projectGoalText.setFocusable(false);
                    projectGoalText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                    projectGoalText.setClickable(false); // user
                    projectGoalText.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_gray));
                }
            }
        });

    }

    public void setEventButton(){
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String money = totalMoneyText.getText().toString();
                if(!money.matches("")){
                    if(monthlySavingSwitch.isChecked()){
                        String save = monthlySavingText.getText().toString();
                        if(!save.matches("")){
                            step1.setVisibility(View.INVISIBLE);
                            step2.setVisibility(View.VISIBLE);
                            totalMoney = money;
                            monthlySaving = save;
                            storeStepOne();
                        } else {
                            Toast.makeText(Step1.this, "Please fill in your monthly saving", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        totalMoney = money;
                        step1.setVisibility(View.INVISIBLE);
                        step2.setVisibility(View.VISIBLE);
                        storeStepOne();
                    }
                } else {
                    Toast.makeText(Step1.this, "Please fill in your total money", Toast.LENGTH_SHORT).show();
                }
            }
        });

        addFixedCostBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRoofRef.child(devideID)
                        .child("fixed_cost")
                        .push()
                        .child("name").setValue(fixedCostText.getText().toString());
                fixedCostText.setText("");
            }
        });

        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step2.setVisibility(View.INVISIBLE);
                step3.setVisibility(View.VISIBLE);
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRoofRef.child(devideID).child("set_up").setValue("1");
                mRoofRef.child(devideID).child("project").setValue("1");
                goToTimeLine();
            }
        });

    }

    public void storeStepOne(){
        mRoofRef.child(devideID).child("total_money").setValue(totalMoney);
        mRoofRef.child(devideID).child("monthly_saving").setValue(monthlySaving);
    }


}
