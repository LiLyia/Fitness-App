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
    DatabaseReference addsReference;
    DatabaseReference exerciseReference;
    DatabaseReference recipesReference;
    FirebaseDatabase db;
    DatabaseReference reference;

    private SharedPreferences sharedPreferences;
    private LinearLayout addsLayout;
    private LinearLayout exerciseLayout;
    private LinearLayout recipesLayout;
    List<Recipe> recipeList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recos);
        //putToDatabase();
        fetchFromDatabase();
    }

    private void putToDatabase() {
        recipesLayout = findViewById(R.id.recipes_layout);
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("recipes");
        String name = "";
        int priority = 4;
        String ingredients = "";
        String instruction = "";
        Recipe newr = new Recipe(ingredients, instruction, name, priority, "recipe");
        reference.setValue(newr);
        /*name = "";
        priority = 2;
        ingredients = "";
        instruction = "";
        Recipe newr2 = new Recipe(name, priority, ingredients, instruction);
        reference.setValue(newr2);
        name = "";
        priority = 3;
        ingredients = "";
        instruction = "";
        Recipe newr3 = new Recipe(name, priority, ingredients, instruction);
        reference.setValue(newr3);
        name = "";
        priority = 5;
        ingredients = "";
        instruction = "";
        Recipe newr4 = new Recipe(name, priority, ingredients, instruction);
        reference.setValue(newr4);
        reference = db.getReference("exercises");
        name = "";
        priority = 2;
        List<String> instructions = new ArrayList<>();
        List<String> images = new ArrayList<>();
        Exercise ex1 = new Exercise(name, priority, instructions, images);
        reference.setValue(ex1);
        name = "";
        priority = 1;
        instructions = new ArrayList<>();
        images = new ArrayList<>();
        Exercise ex2 = new Exercise(name, priority, instructions, images);
        reference.setValue(ex2);
        name = "";
        priority = 4;
        instructions = new ArrayList<>();
        images = new ArrayList<>();
        Exercise ex3 = new Exercise(name, priority, instructions, images);
        reference.setValue(ex3);
        name = "";
        priority = 5;
        instructions = new ArrayList<>();
        images = new ArrayList<>();
        Exercise ex4 = new Exercise(name, priority, instructions, images);
        reference.setValue(ex4);
        name = "";
        priority = 3;
        instructions = new ArrayList<>();
        images = new ArrayList<>();
        Exercise ex5 = new Exercise(name, priority, instructions, images);
        reference.setValue(ex5);

        name = "";
        priority = 1;
        String description = "";
        reference = db.getReference("ads");
        Ad ad1 = new Ad(name, priority, description);
        reference.setValue(ad1);
        name = "";
        priority = 2;
        description = "";
        Ad ad2 = new Ad(name, priority, description);
        reference.setValue(ad2);
        name = "";
        priority = 3;
        description = "";
        Ad ad3 = new Ad(name, priority, description);
        reference.setValue(ad3);
        name = "";
        priority = 4;
        description = "";
        Ad ad4 = new Ad(name, priority, description);
        reference.setValue(ad4);
        name = "";
        priority = 5;
        description = "";
        Ad ad5 = new Ad(name, priority, description);
        reference.setValue(ad5); */

    }

    private void fetchFromDatabase() {
        addsLayout = findViewById(R.id.adds_layout);
        exerciseLayout = findViewById(R.id.exercises_layout);
        recipesLayout = findViewById(R.id.recipes_layout);
        //addsReference = FirebaseDatabase.getInstance().getReference("adds");
        //exerciseReference = FirebaseDatabase.getInstance().getReference("exercises");
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

    }

}
