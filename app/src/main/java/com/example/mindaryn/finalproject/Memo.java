package com.example.mindaryn.finalproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Memo extends AppCompatActivity {
    Button timelineBut, memoBut, settingBut, incomeBut, expenseBut;
    RelativeLayout shadow;
    Typeface font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");

        timelineBut = (Button)findViewById(R.id.timelineBut);
        memoBut = (Button)findViewById(R.id.memoBut);
        settingBut = (Button)findViewById(R.id.settingBut);

        incomeBut = (Button)findViewById(R.id.incomeBUt);
        expenseBut = (Button)findViewById(R.id.expenseBut);

        shadow = (RelativeLayout)findViewById(R.id.shadow);
        shadow.setVisibility(View.INVISIBLE);

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
    }

    @Override
    protected void onPostResume(){
        shadow.setVisibility(View.INVISIBLE);
        super.onPostResume();
    }
}
