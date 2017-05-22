package com.example.mindaryn.finalproject;

import android.graphics.Typeface;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncomePopup extends AppCompatActivity {
    Typeface font;
    private String devideID;
    DatabaseReference mRoofRef = FirebaseDatabase.getInstance().getReference();
    List<Button> logoButList;
    List<TextView> textList;
    Button borrowBut, otherBut, add;
    TextView borrowText, otherText;
    EditText borrowNote, otherNote, moneyText;
    int selected = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_popup);

        font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        devideID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        logoButList = new ArrayList<Button>();
        textList = new ArrayList<TextView>();

        int[] logoIds = {R.id.logo1, R.id.logo2, R.id.logo3, R.id.logo4, R.id.logo5, R.id.logo6 };
        int[] textIds = {R.id.text1, R.id.text2, R.id.text3, R.id.text4, R.id.text5, R.id.text6 };
        String[] logoUnis = {"\uf0d6", "\uf06b", "\uf19c", "\uf091", "\uf005","\uf29a"};
        for(int i = 0; i < 6; i++){
            textList.add((TextView) findViewById(textIds[i]));
            logoButList.add((Button)findViewById(logoIds[i]));
            logoButList.get(i).setTypeface(font);
            logoButList.get(i).setText(logoUnis[i]);
        }

        for(int j = 0; j < 6; j++){
            final int finalJ = j;
            logoButList.get(j).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logoButList.get(finalJ).setTextColor(getResources().getColor(R.color.darkBrown));
                    textList.get(finalJ).setTextColor(getResources().getColor(R.color.darkBrown));

                    if(selected>=0){
                        logoButList.get(selected).setTextColor(getResources().getColor(R.color.colorAccent));
                        textList.get(selected).setTextColor(getResources().getColor(R.color.colorAccent));
                    }else if(selected==-2){
                        otherBut.setTextColor(getResources().getColor(R.color.colorAccent));
                        otherText.setTextColor(getResources().getColor(R.color.colorAccent));
                        borrowBut.setTextColor(getResources().getColor(R.color.colorAccent));
                        borrowText.setTextColor(getResources().getColor(R.color.colorAccent));

                        borrowText.setFocusable(false);
                        borrowText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                        borrowText.setClickable(false); // user
                        borrowNote.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_gray));

                        otherText.setFocusable(false);
                        otherText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                        otherText.setClickable(false); // user
                        otherNote.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_gray));
                    }
                    selected = finalJ;
                }
            });
        }

        borrowBut = (Button)findViewById(R.id.borrowLogo);
        otherBut = (Button)findViewById(R.id.otherLogo);

        add = (Button)findViewById(R.id.addIncomeBut);

        borrowText = (TextView)findViewById(R.id.borrowText);
        otherText = (TextView)findViewById(R.id.otherText);

        borrowNote = (EditText)findViewById(R.id.noteBorrow);
        otherNote = (EditText)findViewById(R.id.noteOther);
        moneyText = (EditText)findViewById(R.id.incomeMoney);

        borrowBut.setTypeface(font);
        otherBut.setTypeface(font);

        borrowBut.setText("\uf2a3");
        otherBut.setText("\uf141");

        otherText.setFocusable(false);
        otherText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        otherText.setClickable(false); // user

        borrowText.setFocusable(false);
        borrowText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        borrowText.setClickable(false); // user

        borrowBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrowBut.setTextColor(getResources().getColor(R.color.darkBrown));
                borrowText.setTextColor(getResources().getColor(R.color.darkBrown));

                borrowText.setFocusableInTouchMode(true);
                borrowText.setClickable(true);
                borrowNote.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_white));

                if(selected<-1){
                    otherBut.setTextColor(getResources().getColor(R.color.colorAccent));
                    otherText.setTextColor(getResources().getColor(R.color.colorAccent));

                    otherText.setFocusable(false);
                    otherText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                    otherText.setClickable(false); // user
                    otherNote.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_gray));
                } else if((selected>=0)){
                    logoButList.get(selected).setTextColor(getResources().getColor(R.color.colorAccent));
                    textList.get(selected).setTextColor(getResources().getColor(R.color.colorAccent));
                }
                selected = -2;
            }
        });

        otherBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otherBut.setTextColor(getResources().getColor(R.color.darkBrown));
                otherText.setTextColor(getResources().getColor(R.color.darkBrown));

                otherText.setFocusableInTouchMode(true);
                otherText.setClickable(true);
                otherNote.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_white));


                if(selected<-1){
                    borrowBut.setTextColor(getResources().getColor(R.color.colorAccent));
                    borrowText.setTextColor(getResources().getColor(R.color.colorAccent));

                    borrowText.setFocusable(false);
                    borrowText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                    borrowText.setClickable(false); // user
                    borrowNote.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_gray));

                } else if((selected>=0)){
                    logoButList.get(selected).setTextColor(getResources().getColor(R.color.colorAccent));
                    textList.get(selected).setTextColor(getResources().getColor(R.color.colorAccent));
                }
                selected = -3;
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> data= new HashMap<String, String>();
                if(selected>=0) {
                    data.put("name",textList.get(selected).getText().toString());
                    data.put("price",moneyText.getText().toString());
                    data.put("type","income");

                } else if( selected ==-2 ) {
                    data.put("name","Borrowed from : "+ borrowNote.getText().toString());
                    data.put("price",moneyText.getText().toString());
                    data.put("type","income");
                } else if(selected == -3 ) {
                    data.put("name",otherNote.getText().toString());
                    data.put("price",moneyText.getText().toString());
                    data.put("type","income");
                }

                mRoofRef.child(devideID)
                        .child("diary")
                        .push().setValue(data);
                finish();
            }
        });

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.85));

    }
}
