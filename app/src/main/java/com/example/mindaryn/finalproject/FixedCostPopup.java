package com.example.mindaryn.finalproject;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FixedCostPopup extends AppCompatActivity {
    private String devideID;
    private FirebaseRecyclerAdapter<FixedCostItem, ShowFixedCostExpenseDataViewHolder> mFirebaseAdapter;
    RecyclerView fixedList;
    DatabaseReference fixedcostRef;
    DatabaseReference mRoofRef = FirebaseDatabase.getInstance().getReference();
    Button addPrice;
    private static FixedCostPopup instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_cost_popup);

        instance = this;
        devideID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        fixedcostRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://finalproject-4c0c6.firebaseio.com/"+devideID+"/fixed_cost");

        fixedList = (RecyclerView)findViewById(R.id.fixedCostExpenseList);
        fixedList.setLayoutManager(new LinearLayoutManager(FixedCostPopup.this));

        addPrice = (Button)findViewById(R.id.addPriceFixedCost);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        addPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i<fixedList.getChildCount();i++){
                    View child = (View)fixedList.getChildAt(i);
                    CheckBox checkBox = (CheckBox)child.findViewById(R.id.fixedCostCheckBox);

                    if(checkBox.isChecked()){
                        EditText price = (EditText)child.findViewById(R.id.fixedExpensePrice);
                        Map<String, String> data= new HashMap<String, String>();
                        data.put("name",checkBox.getText().toString());
                        data.put("price",price.getText().toString());
                        data.put("type","expense");

                        mRoofRef.child(devideID)
                                .child("diary")
                                .push().setValue(data);
                    }
                }
                finish();
            }
        });

    }

    public static FixedCostPopup getInstance() {
        return instance;
    }

    @Override
    public void onStart(){
        super.onStart();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<FixedCostItem, ShowFixedCostExpenseDataViewHolder>(FixedCostItem.class,
                R.layout.expense_fixed_cost_card, ShowFixedCostExpenseDataViewHolder.class, fixedcostRef) {
            @Override
            protected void populateViewHolder(ShowFixedCostExpenseDataViewHolder viewHolder, FixedCostItem model, final int position) {
                viewHolder.setCard(model.getName());
            }
        };
        fixedList.setAdapter(mFirebaseAdapter);
    }

    public static class ShowFixedCostExpenseDataViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox fixedCost;
        private final EditText price;

        public ShowFixedCostExpenseDataViewHolder(final View itemView){
            super(itemView);
            fixedCost = (CheckBox) itemView.findViewById(R.id.fixedCostCheckBox);
            price = (EditText) itemView.findViewById(R.id.fixedExpensePrice);

            price.setFocusable(false);
            price.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            price.setClickable(false); // user

            fixedCost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        price.setFocusableInTouchMode(true);
                        price.setClickable(true);
                        price.setBackgroundDrawable(FixedCostPopup.getInstance().getResources().getDrawable(R.drawable.rounded_white));
                    }else {
                        price.setFocusable(false);
                        price.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                        price.setClickable(false); // user
                        price.setBackgroundDrawable(FixedCostPopup.getInstance().getResources().getDrawable(R.drawable.rounded_gray));
                    }
                }
            });
        }

        private void setCard(String name){
            fixedCost.setText(name);
        }
    }
}
