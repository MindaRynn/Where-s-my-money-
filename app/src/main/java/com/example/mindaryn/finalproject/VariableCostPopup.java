package com.example.mindaryn.finalproject;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class VariableCostPopup extends AppCompatActivity {
    private String devideID;
    EditText name, price;
    Button add;
    DatabaseReference mRoofRef = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variable_cost_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        devideID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        name = (EditText)findViewById(R.id.varExpenseName);
        price = (EditText)findViewById(R.id.varExpensePrice);

        add = (Button)findViewById(R.id.addVarExpenseBut);

        getWindow().setLayout((int)(width*.8),(int)(height*.45));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> data= new HashMap<String, String>();
                data.put("name",name.getText().toString());
                data.put("price",price.getText().toString());
                data.put("type","expense");
                mRoofRef.child(devideID)
                        .child("diary")
                        .push().setValue(data);
                finish();
            }
        });
    }
}
