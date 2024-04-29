package com.example.fitnessapp.account;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;
import com.example.fitnessapp.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

public class AddUserActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText email;
    private FirebaseDatabase db;
    private DatabaseReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        ActionBar actionBar = getSupportActionBar();
        find();
    }

    public void find() {
        username = findViewById(R.id.input_username);
        password = findViewById(R.id.input_password);
        email = findViewById(R.id.input_email);
    }

    public void onAddClick(View view) throws GeneralSecurityException {
        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();
        String emailText = email.getText().toString();

        if (!usernameText.isEmpty() && usernameText.equals("AVAILABLE")) {
            Toast.makeText(AddUserActivity.this, R.string.invalid_username, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!usernameText.isEmpty() && !passwordText.isEmpty() && !emailText.isEmpty()) {
            String encryptedPasswordText = AESCrypt.encrypt("key", passwordText);
            User user = new User(usernameText, encryptedPasswordText, emailText);
            db = FirebaseDatabase.getInstance();
            reference = db.getReference("users");
            reference.child(usernameText).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (!task.getResult().exists()) {
                            reference.child(usernameText).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(AddUserActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
                                    clear();
                                }
                            });
                        } else {
                            Toast.makeText(AddUserActivity.this, R.string.username_taken, Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(AddUserActivity.this, R.string.failed_to_read, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(AddUserActivity.this, R.string.cannot_be_empty, Toast.LENGTH_SHORT).show();
        }
    }

    public void onClearClick(View view) {
        clear();
    }

    private void clear() {
        username.setText("");
        password.setText("");
        email.setText("");
    }

}
