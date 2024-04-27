package com.example.fitnessapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ActionBar actionBar = getSupportActionBar();
    }

    public void onBrowseClick(View view) {
        Intent intent = new Intent(UserActivity.this, EmptyActivity.class);
        startActivity(intent);
    }

    public void onRentedBookClick(View view) {
        Intent intent = new Intent(UserActivity.this, EmptyActivity.class);
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
}
