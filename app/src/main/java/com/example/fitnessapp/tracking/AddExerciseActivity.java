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
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;
import com.example.fitnessapp.entity.Exercise;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.GeneralSecurityException;

public class AddExerciseActivity extends AppCompatActivity {
    EditText activityType, duration, distance,energyExpended ,date;
    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
//        ActionBar actionBar = getSupportActionBar();
        find();
    }

    public void find() {
        System.out.println("Add Exc");
         activityType = findViewById(R.id.input_activity_type);
         duration = findViewById(R.id.input_duration);
         distance = findViewById(R.id.input_distance);
         energyExpended = findViewById(R.id.input_energy_expended);
         date = findViewById(R.id.input_date);
    }

    public void OnAddExcClick(View view) throws GeneralSecurityException {
        String activityTypeText = activityType.getText().toString();
        String durationText = duration.getText().toString();
        String distanceText = distance.getText().toString();
        String energyExpendedText = energyExpended.getText().toString();
        String dateText = date.getText().toString();


        if (!activityTypeText.isEmpty() && !durationText.isEmpty() ) {
//            String encryptedPasswordText = AESCrypt.encrypt("key", passwordText);

            Exercise exercise = new Exercise(activityTypeText, durationText,distanceText, energyExpendedText , dateText);
            db = FirebaseDatabase.getInstance();
            reference = db.getReference("exercise");

            reference.child(activityTypeText).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (!task.getResult().exists()) {
                            reference.child(activityTypeText).setValue(exercise).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    new AlertDialog.Builder(AddExerciseActivity.this)
                                            .setTitle(R.string.success)
                                            .setMessage(R.string.exercise_success)
                                            .setPositiveButton(R.string.continue_now, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(AddExerciseActivity.this, ExercisesActivity.class);
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
                        Toast.makeText(AddExerciseActivity.this, R.string.failed_to_read, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(AddExerciseActivity.this, R.string.cannot_be_empty, Toast.LENGTH_SHORT).show();
        }

    }



}