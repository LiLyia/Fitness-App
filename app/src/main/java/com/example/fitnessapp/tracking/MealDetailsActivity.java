package com.example.fitnessapp.tracking;

import android.app.AutomaticZenRule;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fitnessapp.R;
import com.example.fitnessapp.entity.Exercise;
import com.example.fitnessapp.entity.Meal;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MealDetailsActivity extends AppCompatActivity {
    DatabaseReference reference;
    private SharedPreferences sharedPreferences;
    private LinearLayout mainLayout;
    EditText mealTypeView , caloriesConsumedView, totalCarbsView,proteinView,dateView;

    Button  editButton;
    boolean editMode = false ;
//    private EditText excDateView , excDurationView, excDistanceView,excEnergyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);
        getContent();
    }

    private  void find(){
        mainLayout = findViewById(R.id.meal_details_main_layout);

        mealTypeView = mainLayout.findViewById(R.id.mealType_dt);
        caloriesConsumedView = mainLayout.findViewById(R.id.calories_consumed_dt);
        totalCarbsView = mainLayout.findViewById(R.id.total_Carbs_dt);
        proteinView = mainLayout.findViewById(R.id.protein_dt);
        dateView = mainLayout.findViewById(R.id.meal_date_dt);
        editButton = findViewById(R.id.button_update_meal);

    }
    private void getContent(){
        find();

        Intent i = getIntent();
        Meal meal = (Meal)i.getSerializableExtra("Meal");

        mealTypeView.setText(meal.getMealType());
        caloriesConsumedView.setText(meal.getCaloriesConsumed());
        totalCarbsView.setText(meal.getCarbs());
        proteinView.setText(meal.getProtein());
        dateView.setText(meal.getDate());
    }

    private  void editMode(){
        editMode = !editMode;
        caloriesConsumedView.setEnabled(editMode);
        totalCarbsView.setEnabled(editMode);
        proteinView.setEnabled(editMode);
        dateView.setEnabled(editMode);
    }
    private void update(View view){
        editButton.setText("Edit");
        reference = FirebaseDatabase.getInstance().getReference("meal");
        reference.orderByChild("mealType").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Meal meal = snapshot.getValue(Meal.class);
                String mealTypeTxt =  mealTypeView.getText().toString();

                Map<String, Object> mealMap = new HashMap<>();
                //   exMap.put("activityType" , excTxtView.getText().toString());
                mealMap.put("caloriesConsumed",caloriesConsumedView.getText().toString());
                mealMap.put("carbs", totalCarbsView.getText().toString() );
                mealMap.put("date",   dateView.getText().toString());
                mealMap.put("protein", proteinView.getText().toString());



                if (meal.getMealType().equals(mealTypeTxt)) {
                    reference.child(mealTypeTxt).updateChildren(mealMap);

                    Toast.makeText(MealDetailsActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
//                    finish();
                    Intent intent = new Intent(MealDetailsActivity.this, MealsActivity.class);
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
    public void OnEditMealClick(View view){

        editMode();

        if(editMode)
            edit();
        else
            update(view);

    }
}