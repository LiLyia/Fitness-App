package com.example.fitnessapp.tracking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;
import com.example.fitnessapp.account.UserManagementActivity;
import com.example.fitnessapp.entity.Exercise;
import com.example.fitnessapp.entity.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ExercisesActivity extends AppCompatActivity {
    DatabaseReference reference;
    private SharedPreferences sharedPreferences;
    private LinearLayout mainLayout;
    List<Exercise> excList = new ArrayList<>();
    String excName;
    TextView excTxtView , excDateView;
    View excView;
    Button viewDetailsBtn;

    Button deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        fetchExcFromDatabase();
    }

    private void fetchExcFromDatabase() {
        mainLayout = findViewById(R.id.main_layout);
        reference = FirebaseDatabase.getInstance().getReference("exercise");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Exercise exercise = snapshot.getValue(Exercise.class);
                assert exercise != null;
                excList.add(exercise);
                System.out.println(exercise);
                excView = getLayoutInflater().inflate(R.layout.tracking_category_element, null);

                excTxtView = excView.findViewById(R.id.category);
                excDateView = excView.findViewById(R.id.date);
                viewDetailsBtn = excView.findViewById(R.id.viewDetailsBtn);
                deleteBtn = excView.findViewById(R.id.delete_button);

                System.out.println("date " +exercise.getDate());

                excTxtView.setText(exercise.getActivityType());
                excDateView.setText(exercise.getDate());

                System.out.println("Cat txt:" + excTxtView.getText());
                System.out.println("Cat date:" + excDateView.getText());

                mainLayout.addView(excView);

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

    public void OnAddExcClick(View view) {
        System.out.println("On Add exc Click");
        Intent intent = new Intent(ExercisesActivity.this, AddExerciseActivity.class);
        startActivity(intent);
    }

    public void onDeleteClick(View view) {
        LinearLayout parent = (LinearLayout) view.getParent().getParent();
        TextView category = parent.findViewById(R.id.category);
        String categoryText = category.getText().toString();

        reference = FirebaseDatabase.getInstance().getReference("exercise");
        reference.orderByChild("activityType").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Exercise exercise = snapshot.getValue(Exercise.class);
                if (exercise.getActivityType().equals(categoryText)) {
                    reference.child(categoryText).removeValue();
                    Toast.makeText(ExercisesActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
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

    public void OnViewDetailsClick(View view) {
//        LinearLayout parent = (LinearLayout) view.getParent().getParent();
//        Intent intent = new Intent(ExercisesActivity.this, AddExerciseActivity.class);
//        intent.putExtra()
//        startActivity(intent);
    }



}