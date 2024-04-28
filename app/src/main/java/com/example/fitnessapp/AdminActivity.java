package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ActionBar actionBar = getSupportActionBar();
    }

    public void onUserManagementClick(View view) {
        Intent intent = new Intent(AdminActivity.this, UserManagementActivity.class);
        startActivity(intent);
    }

    public void onChangePasswordClick(View view) {
        Intent intent = new Intent(AdminActivity.this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    public void onLogOutClick(View view) {
        Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
        finishAffinity();
        startActivity(intent);
    }

}
