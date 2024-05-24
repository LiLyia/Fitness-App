package com.example.fitnessapp.tracking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;
import com.example.fitnessapp.account.UserActivity;
import com.example.fitnessapp.entity.Hydration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.GeneralSecurityException;

public class AddHydrationActivity extends AppCompatActivity {
    EditText hydration;
    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hydration);
        ActionBar actionBar = getSupportActionBar();
        find();
    }

    public void find() {
        hydration = findViewById(R.id.input_hydration);
    }

    public void onAddClick(View view) throws GeneralSecurityException {
        String hydrationText = hydration.getText().toString();


        if (!hydrationText.isEmpty()) {
//            String encryptedPasswordText = AESCrypt.encrypt("key", passwordText);

            Hydration hydration = new Hydration(hydrationText);
            db = FirebaseDatabase.getInstance();
            reference = db.getReference("hydration");

            reference.child(hydrationText).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (!task.getResult().exists()) {
                            reference.child(hydrationText).setValue(hydration).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    new AlertDialog.Builder(AddHydrationActivity.this)
                                            .setTitle(R.string.success)
                                            .setMessage(R.string.hydration_success)
                                            .setPositiveButton(R.string.continue_now, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(AddHydrationActivity.this, HydrationActivity.class);
                                                    finishAffinity();
                                                    startActivity(intent);
                                                }
                                            })
                                            .show();
                                }
                            });
                        } else {
//                            Toast.makeText(RegisterActivity.this, R.string.username_taken, Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(AddHydrationActivity.this, R.string.failed_to_read, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(AddHydrationActivity.this, R.string.cannot_be_empty, Toast.LENGTH_SHORT).show();
        }

    }


}