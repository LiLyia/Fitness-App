package com.example.fitnessapp.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.calendar.CalendarActivity;
import com.example.fitnessapp.R;
import com.example.fitnessapp.recommendations.ListRecosActivity;

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
    public void onRecoClick(View view) {
        System.out.println("Trying to open recos");
        Intent intent = new Intent(UserActivity.this, ListRecosActivity.class);

        startActivity(intent);
    }
}
