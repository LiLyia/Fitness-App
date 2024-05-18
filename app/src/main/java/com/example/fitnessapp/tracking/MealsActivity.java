package com.example.fitnessapp.tracking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;
import com.example.fitnessapp.entity.Meal;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MealsActivity extends AppCompatActivity {

    DatabaseReference reference;
    private SharedPreferences sharedPreferences;
    private LinearLayout mainLayout;
    List<Meal> mealList = new ArrayList<>();
    String mealName;
    TextView mealTxtView;
    View mealView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);
        fetchCategoriesFromDatabase();
//        categoryListInit();
    }


    private void fetchCategoriesFromDatabase() {
        mainLayout = findViewById(R.id.main_layout);
        reference = FirebaseDatabase.getInstance().getReference("meal");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Meal meal = snapshot.getValue(Meal.class);
                assert meal != null;
                mealList.add(meal);

                mealView = getLayoutInflater().inflate(R.layout.tracking_category_element, null);

                mealTxtView = mealView.findViewById(R.id.category);
                mealName = meal.getMealType();
                mealTxtView.setText(mealName);

                System.out.println("Cat txt:" + mealTxtView.getText());

                mealTxtView.setOnClickListener(header -> {
                    System.out.println("on click clicked");
                    System.out.println(mealTxtView.getText().toString());


                    switch (mealTxtView.getText().toString()) {
                        case "activity":
                            System.out.println(mealTxtView.getText().toString());
//                            Intent intent = new Intent(TrackingActivity.this, ActivitiesActivity.class);
//                            startActivity(intent);
                            break;
//                                        case "":
//                                        break;
//                                        case "activity":
//                                        break;
                    }});


                mainLayout.addView(mealView);

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

    public void OnAddMealClick(View view) {
        System.out.println("On Add Meal Click");
        Intent intent = new Intent(MealsActivity.this, AddMealActivity.class);
        finishAffinity();
        startActivity(intent);
    }
}