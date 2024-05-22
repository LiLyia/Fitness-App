package com.example.fitnessapp.tracking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;
import com.example.fitnessapp.entity.Hydration;
import com.example.fitnessapp.entity.Meal;
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
    TextView hydrationTxtView , hydrationDateView;
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
                hydrationDateView = hydrationView.findViewById(R.id.date);

                hydrationTxtView.setText(hydration.getHydrationType());
                hydrationDateView.setText(hydration.getHydrationDate());

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

    public void OnHydrationClick(View view) {
        Intent intent = new Intent(HydrationActivity.this, AddHydrationActivity.class);
        startActivity(intent);
    }

    public void onDeleteClick(View view) {
        LinearLayout parent = (LinearLayout) view.getParent().getParent();
        TextView category = parent.findViewById(R.id.category);
        String categoryText = category.getText().toString();

        reference = FirebaseDatabase.getInstance().getReference("hydration");
        reference.orderByChild("hydrationType").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Hydration hydration = snapshot.getValue(Hydration.class);
                if (hydration.getHydrationType().equals(categoryText)) {
                    reference.child(categoryText).removeValue();
                    Toast.makeText(HydrationActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                }
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

    public void OnViewDetailsClick(View view) {
        LinearLayout parent = (LinearLayout) view.getParent().getParent();
        TextView category = parent.findViewById(R.id.category);
        String categoryText = category.getText().toString();

        reference = FirebaseDatabase.getInstance().getReference("hydration");
        reference.orderByChild("hydrationType").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Hydration hydration = snapshot.getValue(Hydration.class);

                if (hydration.getHydrationType().equals(categoryText)) {
                    Intent intent = new Intent(HydrationActivity.this,
                            HydrationDetailsActivity.class);
                    intent.putExtra("hydration",hydration);
                    startActivity(intent);
                }
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

}