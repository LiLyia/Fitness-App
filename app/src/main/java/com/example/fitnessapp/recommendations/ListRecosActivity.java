package com.example.fitnessapp.recommendations;

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
import com.example.fitnessapp.account.UserActivity;
import com.example.fitnessapp.calendar.CalendarActivity;
import com.example.fitnessapp.entity.Recipe;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListRecosActivity extends AppCompatActivity {
    DatabaseReference addsReference;
    DatabaseReference exerciseReference;
    DatabaseReference recipesReference;
    private SharedPreferences sharedPreferences;
    private LinearLayout addsLayout;
    private LinearLayout exerciseLayout;
    private LinearLayout recipesLayout;
    List<Recipe> recipeList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recos);
        fetchFromDatabase();
    }
    private void fetchFromDatabase() {
        addsLayout = findViewById(R.id.adds_layout);
        exerciseLayout = findViewById(R.id.exercises_layout);
        recipesLayout = findViewById(R.id.recipes_layout);
        addsReference = FirebaseDatabase.getInstance().getReference("adds");
        exerciseReference = FirebaseDatabase.getInstance().getReference("exercises");
        recipesReference = FirebaseDatabase.getInstance().getReference("recipes");
        recipesReference.orderByChild("name").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Recipe recipe = snapshot.getValue(Recipe.class);
                assert recipe != null;
                if (!recipe.getType().equals("recipe")) {
                    return;
                }
                recipeList.add(recipe);

                View recipeView = getLayoutInflater().inflate(R.layout.recomendation, null);
                TextView recipeName = recipeView.findViewById(R.id.cardName);

                recipeName.setText(recipe.getName());
                recipesLayout.addView(recipeView);
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

}
