package com.example.fitnessapp.tracking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;
import com.example.fitnessapp.entity.Exercise;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ExerciseDetailsActivity extends AppCompatActivity {

    private LinearLayout mainLayout;
    EditText excTxtView , excDateView, excDurationView,excDistanceView, excEnergyView;
    boolean editMode = false ;
    Button editButton;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);
        getContent();

    }
        private  void find(){
        mainLayout = findViewById(R.id.details_main_layout);
        excTxtView = mainLayout.findViewById(R.id.exerciseType_dt);
        excDateView = mainLayout.findViewById(R.id.exerciseDate_dt);
        excDurationView = mainLayout.findViewById(R.id.exerciseDuration_dt);
        excEnergyView = mainLayout.findViewById(R.id.energyExpended_dt);
        excDistanceView = mainLayout.findViewById(R.id.distance_dt);
        editButton = findViewById(R.id.button_update_exe);

    }
        private void getContent() {
            find();

            Intent i = getIntent();
            Exercise exercise = (Exercise) i.getSerializableExtra("Exercise");

            excTxtView.setText(exercise.getActivityType());
            excDateView.setText(exercise.getDate());
            excDurationView.setText(exercise.getDuration());
            excEnergyView.setText(exercise.getEnergyExpended());
            excDistanceView.setText(exercise.getDistance());
        }
        private  void editMode(){
            editMode = !editMode;
            excDateView.setEnabled(editMode);
            excDurationView.setEnabled(editMode);
            excDistanceView.setEnabled(editMode);
            excEnergyView.setEnabled(editMode);
        }
    private void update(View view){
        editButton.setText("Edit");
        System.out.println("update");

        reference = FirebaseDatabase.getInstance().getReference("exercise");
        reference.orderByChild("activityType").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Exercise exercise = snapshot.getValue(Exercise.class);
                String activityTypeTxt =  excTxtView.getText().toString();

                Map<String, Object> exMap = new HashMap<>();
             //   exMap.put("activityType" , excTxtView.getText().toString());
                exMap.put("duration",excDurationView.getText().toString());
                exMap.put("distance", excDistanceView.getText().toString() );
                exMap.put("energyExpended", excEnergyView.getText().toString());
                exMap.put("date",   excDateView.getText().toString());


                if (exercise.getActivityType().equals(activityTypeTxt)) {
                    reference.child(activityTypeTxt).updateChildren(exMap);

                    Toast.makeText(ExerciseDetailsActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
//                    finish();
                    Intent intent = new Intent(ExerciseDetailsActivity.this, ExercisesActivity.class);

                    startActivity(intent);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void edit(){
        System.out.println("edit mode" + editMode);
        editButton.setText("Update");
    }
    public void OnEditExeClick(View view){

            editMode();

            if(editMode)
                edit();
            else
                update(view);

        }
}