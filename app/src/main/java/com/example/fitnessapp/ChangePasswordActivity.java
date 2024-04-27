package com.example.fitnessapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText originalPassword;
    private EditText password;
    private EditText repeatPassword;
    private FirebaseDatabase db;
    private DatabaseReference reference;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ActionBar actionBar = getSupportActionBar();
        find();
    }

    private void find() {
        originalPassword = findViewById(R.id.input_original_password);
        password = findViewById(R.id.input_password);
        repeatPassword = findViewById(R.id.input_repeat_password);
    }

    public void onSubmitClick(View view) throws GeneralSecurityException {
        db = FirebaseDatabase.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");

        sharedPreferences = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        String usernameText = sharedPreferences.getString("username", "defvalue");

        String originalPasswordText = originalPassword.getText().toString();
        String encryptedOriginalPasswordText = AESCrypt.encrypt("key", originalPasswordText);
        String passwordText = password.getText().toString();
        String repeatPasswordText = repeatPassword.getText().toString();

        if (!originalPasswordText.isEmpty() && !passwordText.isEmpty() && !repeatPasswordText.isEmpty()) {

            reference = db.getReference("users");
            reference.child(usernameText).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {

                        if (task.getResult().exists()) {
//                            Toast.makeText(LoginActivity.this, "Successfully read data!", Toast.LENGTH_SHORT).show();
                            DataSnapshot dataSnapshot = task.getResult();
                            String correctPasswordText = String.valueOf(dataSnapshot.child("password").getValue());
                            if (correctPasswordText.equals(encryptedOriginalPasswordText)) {
                                try {
                                    updatePasswordInDatabase(passwordText, repeatPasswordText, usernameText);
                                } catch (GeneralSecurityException e) {
                                    throw new RuntimeException(e);
                                }

                            } else {
                                Toast.makeText(ChangePasswordActivity.this, R.string.wrond_password, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ChangePasswordActivity.this, R.string.user_does_not_exist, Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(ChangePasswordActivity.this, R.string.failed_to_read, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(ChangePasswordActivity.this, R.string.cannot_be_empty, Toast.LENGTH_SHORT).show();
        }

    }

    private void updatePasswordInDatabase(String passwordText, String repeatPasswordText, String usernameText) throws GeneralSecurityException  {
        if (!passwordText.equals(repeatPasswordText)) {
            Toast.makeText(ChangePasswordActivity.this, R.string.different_passwords, Toast.LENGTH_SHORT).show();
        } else {
            String encryptedPasswordText = AESCrypt.encrypt("key", passwordText);
            reference.child(usernameText).child("password").setValue(encryptedPasswordText);
            new AlertDialog.Builder(this)
                    .setTitle(R.string.success)
                    .setMessage(R.string.password_reset_complete)
                    .setPositiveButton(R.string.continue_now, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                            finishAffinity();
                            startActivity(intent);
                        }
                    })
                    .show();
        }
    }

    public void onClearClick(View view) {
        originalPassword.setText("");
        password.setText("");
        repeatPassword.setText("");
    }
}
