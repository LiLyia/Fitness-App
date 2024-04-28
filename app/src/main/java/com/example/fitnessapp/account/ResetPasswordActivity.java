package com.example.fitnessapp.account;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

public class ResetPasswordActivity extends AppCompatActivity {

    private DatabaseReference reference;
    private SharedPreferences sharedPreferences;

    private EditText password;
    private EditText repeatPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ActionBar actionBar = getSupportActionBar();

        find();
    }

    private void find() {
        password = findViewById(R.id.input_password);
        repeatPassword = findViewById(R.id.input_repeat_password);
    }

    public void onSubmitClick(View view) throws GeneralSecurityException {
        reference = FirebaseDatabase.getInstance().getReference("users");
        sharedPreferences = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        String usernameText = sharedPreferences.getString("username", "defvalue");


        String passwordText = password.getText().toString();
        String repeatPasswordText = repeatPassword.getText().toString();

        if (!passwordText.isEmpty() && !repeatPasswordText.isEmpty()) {
            if (!passwordText.equals(repeatPasswordText)) {
                Toast.makeText(ResetPasswordActivity.this,
                        R.string.different_passwords, Toast.LENGTH_SHORT).show();
            } else {
                String encryptedPasswordText = AESCrypt.encrypt("key", passwordText);
                reference.child(usernameText).child("password").setValue(encryptedPasswordText);
                new AlertDialog.Builder(this)
                        .setTitle(R.string.success)
                        .setMessage(R.string.password_reset_complete)
                        .setPositiveButton(R.string.continue_now, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                                finishAffinity();
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        } else {
            Toast.makeText(ResetPasswordActivity.this, R.string.both_fields_cannot_empty, Toast.LENGTH_SHORT).show();
        }

    }

    public void onClearClick(View view) {
        password.setText("");
        repeatPassword.setText("");
    }
}
