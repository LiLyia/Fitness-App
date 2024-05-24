package com.example.fitnessapp.recommendations;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.fitnessapp.R;
import com.example.fitnessapp.account.UserActivity;
import com.example.fitnessapp.entity.Ad;
import com.example.fitnessapp.entity.Exercise;
import com.example.fitnessapp.entity.Recipe;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListRecosActivity extends AppCompatActivity {
    DatabaseReference adsReference;
    DatabaseReference exerciseReference;
    DatabaseReference recipesReference;
    FirebaseDatabase db;
    DatabaseReference reference;
    final int[] adsImgArray = {
            R.drawable.virtualrace,
            R.drawable.beacdanceprogram,
            R.drawable.kekrun,
            R.drawable.eltesportsweek
    };
    final int[] exImgArray = {
            R.drawable.reverseabcrunch,
            R.drawable.squatjumps,
            R.drawable.bentkneepushup,
            R.drawable.glutebridge
    };
    final int[] recipeImgArray = {
            R.drawable.cheesecake,
            R.drawable.lentilsalad,
            R.drawable.supershake,
            R.drawable.tunawraps,
            R.drawable.oats
    };
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
        recipesReference.orderByChild("priority").addChildEventListener(new ChildEventListener() {
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
                ImageView recipeImg = recipeView.findViewById(R.id.image);
                recipeImg.setImageDrawable(getDrawable(recipeImgArray[recipe.getPriority()-1]));
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
        adsReference.orderByChild("priority").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Ad ad = snapshot.getValue(Ad.class);
                assert ad != null;
                if (!ad.getType().equals("ad")) return;
                adsList.add(ad);
                View adsView = getLayoutInflater().inflate(R.layout.recomendation, null);
                TextView adName = adsView.findViewById(R.id.cardName);
                adName.setText(ad.getName());
                ImageView adImg = adsView.findViewById(R.id.image);
                adImg.setImageDrawable(getDrawable(adsImgArray[ad.getPriority()-1]));
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
        exerciseReference.orderByChild("priority").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Exercise exercise = snapshot.getValue(Exercise.class);
                assert exercise != null;
                if (!exercise.getType().equals("exercise")) return;
                exerciseList.add(exercise);
                View exerciseView = getLayoutInflater().inflate(R.layout.recomendation, null);
                TextView exName = exerciseView.findViewById(R.id.cardName);
                exName.setText(exercise.getName());
                ImageView exImg = exerciseView.findViewById(R.id.image);
                exImg.setImageDrawable(getDrawable(exImgArray[exercise.getPriority()-1]));
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
    public void onCardClick(View view) {
        ConstraintLayout parent = (ConstraintLayout) view.getParent();
        SharedPreferences sharedPref = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        TextView card = view.findViewById(R.id.cardName);
         //getString(R.string.titleReco)
        for (int i = 0; i < adsList.size(); i++) {
            if (adsList.get(i).getName().equals(card.getText().toString())) {
                //Toast.makeText(ListRecosActivity.this, adsList.get(i).getType(), Toast.LENGTH_SHORT).show();
                editor.putInt("priority", adsList.get(i).getPriority());
                editor.apply();
                editor.putString("instruction", adsList.get(i).getInstruction());
                editor.apply();
                editor.putString("type", adsList.get(i).getType());
                editor.apply();
            }
        }
        for (int i = 0; i < recipeList.size(); i++) {
            if (recipeList.get(i).getName().equals(card.getText().toString())) {
                editor.putInt("priority", recipeList.get(i).getPriority());
                editor.apply();
                editor.putString("instruction", recipeList.get(i).getInstruction());
                editor.apply();
                editor.putString("type", recipeList.get(i).getType());
                editor.apply();
            }
        }
        for (int i = 0; i < exerciseList.size(); i++) {
            if (exerciseList.get(i).getName().equals(card.getText().toString())) {
                editor.putInt("priority", exerciseList.get(i).getPriority());
                editor.apply();
                editor.putString("instruction", exerciseList.get(i).getInstruction());
                editor.apply();
                editor.putString("type", exerciseList.get(i).getType());
                editor.apply();
            }
        }
        //SharedPreferences sharedPref = getActivity(this).getPreferences(Context.MODE_PRIVATE);
        editor.putString("title", card.getText().toString());
        editor.apply();
        Intent intent = new Intent(ListRecosActivity.this, RecommendationActivity.class);
        startActivity(intent);
    }

}
