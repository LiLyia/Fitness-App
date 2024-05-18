package com.example.fitnessapp.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;
import com.example.fitnessapp.calendar.CalendarActivity;
import com.example.fitnessapp.tracking.TrackingActivity;

public class UserActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ActionBar actionBar = getSupportActionBar();
    }

    public void onCalendarClick(View view) {
        Intent intent = new Intent(UserActivity.this, CalendarActivity.class);
        startActivity(intent);
    }

    public void onChangePasswordClick(View view) {
        Intent intent = new Intent(UserActivity.this, ChangePasswordActivity.class);
        String usernameText = "";
        sharedPreferences = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        usernameText = sharedPreferences.getString("username", "defvalue");
        startActivity(intent);
    }

    public void onLogOutClick(View view) {
        Intent intent = new Intent(UserActivity.this, LoginActivity.class);
        finishAffinity();
        startActivity(intent);
    }
    public void onBrowseClick(View view) {
        System.out.println("Brwose click");

        Intent intent = new Intent(UserActivity.this, TrackingActivity.class);
        startActivity(intent);
    }

//    public void onAddExClick(View view) {
//        System.out.println("Activities click");
//        Intent intent = new Intent(UserActivity.this, AddExerciseActivity.class);
//        finishAffinity();
//        startActivity(intent);
//    }
//    public void onAddMealClick(View view) {
//        System.out.println("Meal click");
//        Intent intent = new Intent(UserActivity.this, MealsActivity.class);
//        finishAffinity();
//        startActivity(intent);
//    }
//    public void onAddHydrationClick(View view) {
//        System.out.println("Hydration click");
//        Intent intent = new Intent(UserActivity.this, AddHydrationActivity.class);
//        finishAffinity();
//        startActivity(intent);
//    }
}
