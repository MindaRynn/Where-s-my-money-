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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TimeLine extends AppCompatActivity {
    Button timelineBut, memoBut, settingBut, addProjectBut;
    RelativeLayout shadow;
    RecyclerView recyclerView;
    private String devideID;
    Typeface font;
    DatabaseReference projectRef;
    DatabaseReference mRoofRef = FirebaseDatabase.getInstance().getReference();
    private FirebaseRecyclerAdapter<ProjectSingleItem, ShowDataViewHolder> mFirebaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");

        devideID = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
        projectRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://finalproject-4c0c6.firebaseio.com/"+devideID+"/project");
        timelineBut = (Button)findViewById(R.id.timelineBut);
        memoBut = (Button)findViewById(R.id.memoBut);
        settingBut = (Button)findViewById(R.id.settingBut);
        recyclerView = (RecyclerView)findViewById(R.id.project_list);
        addProjectBut = (Button)findViewById(R.id.addProjectBut);

        shadow = (RelativeLayout)findViewById(R.id.timelineShadow);

        recyclerView.setLayoutManager(new LinearLayoutManager(TimeLine.this));

        timelineBut.setTypeface(font);
        memoBut.setTypeface(font);
        settingBut.setTypeface(font);
        timelineBut.setText("\uf0ca");
        settingBut.setText("\uf013");
        memoBut.setText("\uf15c");

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
//        mFirebaseAdapter = new FirebaseRecyclerAdapter<ProjectSingleItem, ShowDataViewHolder>(ProjectSingleItem.class,
//                R.layout.project_card_item, ShowDataViewHolder.class, projectRef) {
//            @Override
//            protected void populateViewHolder(ShowDataViewHolder viewHolder, ProjectSingleItem model, final int position) {
//                viewHolder.name(model.getName());
//                viewHolder.goal(model.getGoal());
//                viewHolder.current(model.getCurrent());
//            }
//        };
//       recyclerView.setAdapter(mFirebaseAdapter);

    }

    @Override
    public void onStart(){
        super.onStart();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<ProjectSingleItem, ShowDataViewHolder>(ProjectSingleItem.class,
                R.layout.project_card_item, ShowDataViewHolder.class, projectRef) {
            @Override
            protected void populateViewHolder(ShowDataViewHolder viewHolder, ProjectSingleItem model, final int position) {
                viewHolder.name(model.getName());
                viewHolder.goal(model.getGoal());
                viewHolder.current(model.getCurrent());
            }
        };
        recyclerView.setAdapter(mFirebaseAdapter);
    }

    public static class ShowDataViewHolder extends RecyclerView.ViewHolder {
        private final TextView projectTitle, projectCurrent, projectGoal;

        public ShowDataViewHolder(final View itemView){
            super(itemView);
            projectTitle = (TextView) itemView.findViewById(R.id.projectCardName);
            projectCurrent = (TextView) itemView.findViewById(R.id.projectCardCurrent);
            projectGoal = (TextView) itemView.findViewById(R.id.projectCardGoal);

        }

        private void name(String title){
            projectTitle.setText(title);
        }
        private void current(String current){
            projectCurrent.setText(current);
        }
        private void goal(String goal){
            projectGoal.setText(goal);
        }
    }

    @Override
    protected void onPostResume(){
        shadow.setVisibility(View.INVISIBLE);
        super.onPostResume();
    }
}
