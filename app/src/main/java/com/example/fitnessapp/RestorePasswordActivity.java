package com.example.fitnessapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RestorePasswordActivity extends AppCompatActivity {

    DatabaseReference reference;
    private SharedPreferences sharedPreferences;
    private EditText username;
    private EditText email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_password);
        ActionBar actionBar = getSupportActionBar();

        find();
    }

    private void find() {
        username = findViewById(R.id.input_username);
        email = findViewById(R.id.input_email);
    }

    public void onRestoreClick(View view) {
        String usernameText = username.getText().toString();
        String emailText = email.getText().toString();

        if (!usernameText.isEmpty() && !emailText.isEmpty()) {
            reference = FirebaseDatabase.getInstance().getReference("users");
            reference.child(usernameText).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            Toast.makeText(RestorePasswordActivity.this, R.string.read_data_success, Toast.LENGTH_SHORT).show();
                            DataSnapshot dataSnapshot = task.getResult();
                            String correctEmailText = String.valueOf(dataSnapshot.child("email").getValue());
                            if (emailText.equals(correctEmailText)) {
                                sharedPreferences = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("username", usernameText);
                                editor.commit();
                                Intent intent = new Intent(RestorePasswordActivity.this, ResetPasswordActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(RestorePasswordActivity.this, R.string.wrong_info, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RestorePasswordActivity.this, R.string.user_does_not_exist, Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(RestorePasswordActivity.this, R.string.failed_to_read, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

    public void onClearClick(View view) {
        username.setText("");
        email.setText("");
    }

}
