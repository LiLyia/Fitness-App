package com.example.fitnessapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scottyab.aescrypt.AESCrypt;


import java.security.GeneralSecurityException;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private EditText username;
    private EditText password;
    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_login);
        find();
    }

    public void find() {
        username = findViewById(R.id.input_username);
        password = findViewById(R.id.input_password);
    }

    public void onLoginClick(View view) throws GeneralSecurityException {
        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();
        if (!usernameText.isEmpty() && !passwordText.isEmpty()) {
            String encryptedPasswordText = AESCrypt.encrypt("key", passwordText);
            db = FirebaseDatabase.getInstance();
            reference = db.getReference("users");
            reference.child(usernameText).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            String correctPasswordText = String.valueOf(dataSnapshot.child("password").getValue());
                            String type = String.valueOf(dataSnapshot.child("type").getValue());
                            if (encryptedPasswordText.equals(correctPasswordText)) {
                                sharedPreferences = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("username", usernameText);
                                editor.putString("type", type);
                                editor.commit();
                                if (type.equals("user")) {
                                    Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                    startActivity(intent);
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, R.string.wrond_password, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.user_does_not_exist, Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(LoginActivity.this, R.string.failed_to_read, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(LoginActivity.this, R.string.both_fields_cannot_empty, Toast.LENGTH_SHORT).show();
        }
    }

    public void onRegisterClick(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void onRestoreClick(View view) {
        Intent intent = new Intent(LoginActivity.this, RestorePasswordActivity.class);
        startActivity(intent);
    }

    public void onLanguagesClick(View view) {
        showChangeLanguageDialog();
    }

    private void showChangeLanguageDialog() {
        final String[] listItems = { "中文", "Русский", "عربي", "English" };
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle(R.string.choose_language);
        builder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    setLocale("zh");
                    recreate();
                } else if (which == 1) {
                    setLocale("ru");
                    recreate();
                } else if (which == 2) {
                    setLocale("ar");
                    recreate();
                } else if (which == 3) {
                    setLocale("en");
                    recreate();
                }

                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setLocale(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", language);
        editor.apply();
    }

    private void loadLocale() {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = sharedPreferences.getString("My_Lang", "");
        setLocale(language);
        ActionBar actionBar = getSupportActionBar();
    }

}