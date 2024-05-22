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
import com.example.fitnessapp.entity.Meal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.GeneralSecurityException;

public class AddMealActivity extends AppCompatActivity {
    EditText mealType, caloriesConsumed, carbs,protein ,sugar , date;
    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        ActionBar actionBar = getSupportActionBar();
        find();
    }

    public void find() {
        mealType = findViewById(R.id.input_meal_type);
        caloriesConsumed = findViewById(R.id.input_calories);
        carbs = findViewById(R.id.input_carbs);
        protein = findViewById(R.id.input_proteins);
        sugar = findViewById(R.id.input_sugar);
        date = findViewById(R.id.meal_date_input);
    }

    public void onAddClick(View view) throws GeneralSecurityException {
        String mealTypeText = mealType.getText().toString();
        String caloriesConsumedText = caloriesConsumed.getText().toString();
        String carbsText = carbs.getText().toString();
        String proteinText = protein.getText().toString();
        String sugarText = sugar.getText().toString();
        String dateText = date.getText().toString();


        if (!mealTypeText.isEmpty() && !caloriesConsumedText.isEmpty() ) {
//            String encryptedPasswordText = AESCrypt.encrypt("key", passwordText);

            Meal meal = new Meal(mealTypeText, caloriesConsumedText,carbsText, proteinText ,sugarText , dateText);
            db = FirebaseDatabase.getInstance();
            reference = db.getReference("meal");
            System.out.println("add meal");
            reference.child(mealTypeText).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (!task.getResult().exists()) {
                            reference.child(mealTypeText).setValue(meal).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    new AlertDialog.Builder(AddMealActivity.this)
                                            .setTitle(R.string.success)
                                            .setMessage(R.string.meal_success)
                                            .setPositiveButton(R.string.continue_now, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(AddMealActivity.this, MealsActivity.class);
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
                        Toast.makeText(AddMealActivity.this, R.string.failed_to_read, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(AddMealActivity.this, R.string.cannot_be_empty, Toast.LENGTH_SHORT).show();
        }

    }

}