package com.example.fitnessapp.tracking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;
import com.example.fitnessapp.entity.TrackingCategory;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class TrackingActivity extends AppCompatActivity {

    DatabaseReference reference;
    private SharedPreferences sharedPreferences;
    private LinearLayout mainLayout;
    List<TrackingCategory> categoryList = new ArrayList<>();
    String catName;
    TextView categoryTxtView;
    View categoryView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
//        fetchCategoriesFromDatabase();
    }
    public void onAddExClick(View view) {
        System.out.println("Activities click");
        Intent intent = new Intent(TrackingActivity.this, ExercisesActivity.class);
        startActivity(intent);
    }
    public void onAddMealClick(View view) {
        System.out.println("Meal click");
        Intent intent = new Intent(TrackingActivity.this, MealsActivity.class);
        startActivity(intent);
    }
    public void onAddHydrationClick(View view) {
        System.out.println("Hydration click");
        Intent intent = new Intent(TrackingActivity.this, HydrationActivity.class);
        startActivity(intent);
    }

//    private void fetchCategoriesFromDatabase() {
//        mainLayout = findViewById(R.id.main_layout);
//        reference = FirebaseDatabase.getInstance().getReference("tracking_categories");
//        reference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                TrackingCategory category = snapshot.getValue(TrackingCategory.class);
//                assert category != null;
//                categoryList.add(category);
//
//                categoryView = getLayoutInflater().inflate(R.layout.tracking_category_element, null);
//
//                categoryTxtView = categoryView.findViewById(R.id.category);
//                catName = category.getName();
//                categoryTxtView.setText(catName);
//
//                System.out.println("Cat txt:" + categoryTxtView.getText());
//
//                categoryTxtView.setOnClickListener(header -> {
//                    System.out.println("on click clicked");
//                    System.out.println(categoryTxtView.getText().toString());
//
//
//                    switch (categoryTxtView.getText().toString()) {
//                        case "activity":
//                            System.out.println(categoryTxtView.getText().toString());
////                            Intent intent = new Intent(TrackingActivity.this, ActivitiesActivity.class);
////                            startActivity(intent);
//                            break;
////                                        case "":
////                                        break;
////                                        case "activity":
////                                        break;
//                    }});
//
//
//                mainLayout.addView(categoryView);
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//
//        });
//
//
//    }
//

}
