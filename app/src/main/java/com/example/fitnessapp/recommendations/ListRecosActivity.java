package com.example.fitnessapp.recommendations;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;
import com.example.fitnessapp.account.LoginActivity;
import com.example.fitnessapp.account.UserActivity;
import com.example.fitnessapp.calendar.CalendarActivity;
import com.example.fitnessapp.entity.Ad;
import com.example.fitnessapp.entity.Exercise;
import com.example.fitnessapp.entity.Recipe;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListRecosActivity extends AppCompatActivity {
    DatabaseReference adsReference;
    DatabaseReference exerciseReference;
    DatabaseReference recipesReference;
    FirebaseDatabase db;
    DatabaseReference reference;

    private SharedPreferences sharedPreferences;
    private LinearLayout adsLayout;
    private LinearLayout exerciseLayout;
    private LinearLayout recipesLayout;
    List<Recipe> recipeList = new ArrayList<>();
    List<Ad> adsList = new ArrayList<>();
    List<Exercise> exerciseList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recos);
        fetchFromDatabase();
    }


    private void fetchFromDatabase() {
        adsLayout = findViewById(R.id.adds_layout);
        exerciseLayout = findViewById(R.id.exercises_layout);
        recipesLayout = findViewById(R.id.recipes_layout);
        adsReference = FirebaseDatabase.getInstance().getReference("ads");
        exerciseReference = FirebaseDatabase.getInstance().getReference("reco_exercises");
        recipesReference = FirebaseDatabase.getInstance().getReference("recipes");
        recipesReference.orderByChild("name").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Recipe recipe = snapshot.getValue(Recipe.class);
                assert recipe != null;
                if (!recipe.getType().equals("recipe")) {
                    Toast.makeText(ListRecosActivity.this, "empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                recipeList.add(recipe);

                View recipeView = getLayoutInflater().inflate(R.layout.recomendation, null);
                TextView recipeName = recipeView.findViewById(R.id.cardName);
                //Toast.makeText(ListRecosActivity.this, recipe.getName(), Toast.LENGTH_SHORT).show();
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
        adsReference.orderByChild("name").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Ad ad = snapshot.getValue(Ad.class);
                assert ad != null;
                if (!ad.getType().equals("ad")) return;
                adsList.add(ad);
                View adsView = getLayoutInflater().inflate(R.layout.recomendation, null);
                TextView adName = adsView.findViewById(R.id.cardName);
                adName.setText(ad.getName());
                adsLayout.addView(adsView);
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
        exerciseReference.orderByChild("name").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Exercise exercise = snapshot.getValue(Exercise.class);
                assert exercise != null;
                if (!exercise.getType().equals("exercise")) return;
                exerciseList.add(exercise);
                View exerciseView = getLayoutInflater().inflate(R.layout.recomendation, null);
                TextView exName = exerciseView.findViewById(R.id.cardName);
                exName.setText(exercise.getName());
                exerciseLayout.addView(exerciseView);
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
