package com.example.fitnessapp.tracking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;
import com.example.fitnessapp.entity.Hydration;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HydrationActivity extends AppCompatActivity {

    DatabaseReference reference;
    private SharedPreferences sharedPreferences;
    private LinearLayout mainLayout;
    List<Hydration> hydrationList = new ArrayList<>();
    String amount;
    TextView hydrationTxtView;
    View hydrationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hydration);
        fetchExcFromDatabase();
    }

    private void fetchExcFromDatabase() {
        mainLayout = findViewById(R.id.main_layout);
        reference = FirebaseDatabase.getInstance().getReference("hydration");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Hydration hydration = snapshot.getValue(Hydration.class);
                assert hydration != null;
                hydrationList.add(hydration);

                hydrationView= getLayoutInflater().inflate(R.layout.tracking_category_element, null);

                hydrationTxtView = hydrationView.findViewById(R.id.category);
                hydrationTxtView.setText(hydration.getAmount());

                System.out.println("Cat txt:" + hydrationTxtView.getText());

                hydrationTxtView.setOnClickListener(header -> {
                    System.out.println("on click clicked");
                    System.out.println(hydrationTxtView.getText().toString());
                });


                mainLayout.addView(hydrationView);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }

    public void OnAddExcClick(View view) {
        System.out.println("On Add exc Click");
        Intent intent = new Intent(HydrationActivity.this, AddHydrationActivity.class);
        finishAffinity();
        startActivity(intent);
    }
}