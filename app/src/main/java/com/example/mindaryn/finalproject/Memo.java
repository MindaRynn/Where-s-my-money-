package com.example.mindaryn.finalproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Memo extends AppCompatActivity {
    Button timelineBut, memoBut, settingBut, incomeBut, expenseBut;
    TextView total, monthly, project, balance, income, expense;
    RelativeLayout shadow;
    Typeface font;
    private String devideID;
    DatabaseReference mRoofRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");


        devideID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        timelineBut = (Button)findViewById(R.id.timelineBut);
        memoBut = (Button)findViewById(R.id.memoBut);
        settingBut = (Button)findViewById(R.id.settingBut);

        incomeBut = (Button)findViewById(R.id.incomeBUt);
        expenseBut = (Button)findViewById(R.id.expenseBut);

        shadow = (RelativeLayout)findViewById(R.id.shadow);
        shadow.setVisibility(View.INVISIBLE);

        total = (TextView)findViewById(R.id.total_in_memo);
        monthly = (TextView)findViewById(R.id.monthly_in_memo);
        project = (TextView)findViewById(R.id.project_in_memo);
        balance = (TextView)findViewById(R.id.balance_in_memo);

        income = (TextView)findViewById(R.id.memo_income);
        expense = (TextView)findViewById(R.id.memo_expense);

        timelineBut.setTypeface(font);
        memoBut.setTypeface(font);
        settingBut.setTypeface(font);

        timelineBut.setText("\uf0ca");
        settingBut.setText("\uf013");
        memoBut.setText("\uf15c");


        timelineBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Memo.this,TimeLine.class);
                finish();
                startActivity(intent);
            }
        });

        incomeBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Memo.this,IncomePopup.class);
                shadow.setVisibility(View.VISIBLE);
                startActivity(intent);

            }
        });
        expenseBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Memo.this,ExpensePopup.class);
                shadow.setVisibility(View.VISIBLE);
                startActivity(intent);

            }
        });
        mRoofRef.child(devideID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int totalMoney = Integer.parseInt(dataSnapshot.child("total_money").getValue().toString());
                int currentProj = Integer.parseInt(dataSnapshot.child("current_project").getValue().toString());
                int saving = Integer.parseInt(dataSnapshot.child("monthly_saving").getValue().toString());
                int diary = Integer.parseInt(dataSnapshot.child("current_diary").getValue().toString());

                total.setText((totalMoney-diary)+"");
                project.setText(currentProj+"");
                monthly.setText(saving+"");
                balance.setText((totalMoney-diary-saving-currentProj)+"");

                if(dataSnapshot.hasChild("diary")){
                    int incomeM = 0;
                    int expenseM = 0;
                    for (DataSnapshot messageSnapshot: dataSnapshot.child("diary").getChildren()) {
                        if(messageSnapshot.child("type").getValue().toString().equals("expense")){
                            expenseM += Integer.parseInt(messageSnapshot.child("price").getValue().toString());
                        } else {
                            incomeM += Integer.parseInt(messageSnapshot.child("price").getValue().toString());
                        }
                    }
                    income.setText(incomeM+"");
                    expense.setText(expenseM+"");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onPostResume(){
        shadow.setVisibility(View.INVISIBLE);
        super.onPostResume();
    }
}
