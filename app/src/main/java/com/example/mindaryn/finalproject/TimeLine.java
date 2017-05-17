package com.example.mindaryn.finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Debug;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.provider.Settings.Secure;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TimeLine extends AppCompatActivity {
    Button timelineBut, memoBut, settingBut, addProjectBut;
    TextView totalMoney, savingMoney, balanceMoney;
    RelativeLayout shadow;
    RecyclerView recyclerView;
    private String devideID;
    Typeface font;
    DatabaseReference projectRef;
    DatabaseReference mRoofRef = FirebaseDatabase.getInstance().getReference();
    private FirebaseRecyclerAdapter<ProjectSingleItem, ShowProjectDataViewHolder> mFirebaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        init();

        initButtonEvent();
    }

    public void initButtonEvent(){
        memoBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeLine.this,Memo.class);
                startActivity(intent);
            }
        });

        addProjectBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeLine.this,AddProjectPopup.class);
                shadow.setVisibility(View.VISIBLE);
                startActivity(intent);
            }
        });
    }

    public void init(){
        font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");

        devideID = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
        projectRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://finalproject-4c0c6.firebaseio.com/"+devideID+"/project");
        timelineBut = (Button)findViewById(R.id.timelineBut);
        memoBut = (Button)findViewById(R.id.memoBut);
        settingBut = (Button)findViewById(R.id.settingBut);
        recyclerView = (RecyclerView)findViewById(R.id.project_list);
        addProjectBut = (Button)findViewById(R.id.addProjectBut);
        shadow = (RelativeLayout)findViewById(R.id.timelineShadow);

        totalMoney = (TextView)findViewById(R.id.total_in_timeline);
        savingMoney = (TextView)findViewById(R.id.monthly_saving_in_timeline);
        balanceMoney = (TextView)findViewById(R.id.balance_in_timeline);

        timelineBut.setTypeface(font);
        memoBut.setTypeface(font);
        settingBut.setTypeface(font);
        timelineBut.setText("\uf0ca");
        settingBut.setText("\uf013");
        memoBut.setText("\uf15c");

        recyclerView.setLayoutManager(new LinearLayoutManager(TimeLine.this));

        mRoofRef.child(devideID).child("project").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int projectCurrent = 0;
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    projectCurrent += Integer.parseInt(messageSnapshot.child("current").getValue().toString());
                }
                mRoofRef.child(devideID).child("current_project").setValue(projectCurrent+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRoofRef.child(devideID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int total = Integer.parseInt(dataSnapshot.child("total_money").getValue().toString());
                int currentProj = Integer.parseInt(dataSnapshot.child("current_project").getValue().toString());
                int saving = Integer.parseInt(dataSnapshot.child("monthly_saving").getValue().toString());

                totalMoney.setText(total+"");
                savingMoney.setText(saving+"");
                balanceMoney.setText((total-currentProj-saving)+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<ProjectSingleItem, ShowProjectDataViewHolder>(ProjectSingleItem.class,
                R.layout.project_card_item, ShowProjectDataViewHolder.class, projectRef) {
            @Override
            protected void populateViewHolder(ShowProjectDataViewHolder viewHolder, ProjectSingleItem model, final int position) {
                viewHolder.setCard(model.getName(), model.getCurrent(), model.getGoal());
                viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(final View v){
                        Intent intent = new Intent(TimeLine.this,ManangeProjectPopup.class);
                        intent.putExtra("key", mFirebaseAdapter.getRef(position).getKey());
                        shadow.setVisibility(View.VISIBLE);
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(mFirebaseAdapter);
    }

    public static class ShowProjectDataViewHolder extends RecyclerView.ViewHolder {
        private final TextView projectTitle, projectCurrent, projectGoal;

        public ShowProjectDataViewHolder(final View itemView){
            super(itemView);
            projectTitle = (TextView) itemView.findViewById(R.id.projectCardName);
            projectCurrent = (TextView) itemView.findViewById(R.id.projectCardCurrent);
            projectGoal = (TextView) itemView.findViewById(R.id.projectCardGoal);

        }

        private void setCard(String title, String current, String goal){
            projectTitle.setText(title);
            projectCurrent.setText(current);
            projectGoal.setText(goal);
        }
    }

    @Override
    protected void onPostResume(){
        shadow.setVisibility(View.INVISIBLE);
        super.onPostResume();
    }
}
