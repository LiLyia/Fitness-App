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
        String name = "Protein cheesecake";
        int priority = 1;
        String ingredients = "Almond flour\n" + "Protein powder\n" + "Sweetener - 2 tablespoons\n" + "Salted butter - 4 tablespoons\n" + "Cottage cheese\n" + "Cream cheese\n" + "Eggs";
        /*ingredients.add("Almond flour");
        ingredients.add("Protein powder");
        ingredients.add("Sweetener - 2 tablespoons");
        ingredients.add("Salted butter - 4 tablespoons");
        ingredients.add("Cottage cheese");
        ingredients.add("Cream cheese");
        ingredients.add("Eggs"); */
        String type = "recipe";
        String instruction = "How to make almond crust?\n" + "Step One: In a large food processor pulse the almond flour, protein powder, 2 tablespoon sweetener, and 4 tablespoon butter until a coarse dough forms. It may seem dry at first but will come together in a ball. \n" + "Step Two: Press into the bottom of an 8-inch springform pan to form a crust.\n" + "Step Three: Put in the oven and bake for 8 minutes.\n" + "How to make the cheesecake?\n" + "Step One: Since you've just baked the crust at 350 degrees, reduce the oven temperature to 275 degrees. \n" +
                "Step Two: Combine the cottage cheese and the cream cheese in the food processor (you don’t need to wash the bowl). Pulse the cheeses until smooth. Add the protein powder, sweetener, and extract. Mix until combined.\n" + "Step Three: Add the eggs. Blend until smooth. You will need to scrape down the sides. Pour the cheesecake mixture over the crust. The cheesecake filling should spread out evenly in the pan once you pour it in but if you need to gently shake the pan or tap it, do so.\n" + "Step Four: Bake the protein cheesecake for 90 minutes until the center no longer jiggles when the pan is lightly shaken. Cool completely. Refrigerate for at least 4 hours before serving.";
        Recipe newr = new Recipe(name, priority, ingredients, instruction);
        reference.setValue(newr);
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
