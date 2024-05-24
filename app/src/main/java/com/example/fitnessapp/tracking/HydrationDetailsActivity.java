package com.example.fitnessapp.tracking;

import android.app.AutomaticZenRule;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fitnessapp.R;
import com.example.fitnessapp.entity.Exercise;
import com.example.fitnessapp.entity.Hydration;
import com.example.fitnessapp.entity.Meal;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HydrationDetailsActivity extends AppCompatActivity {
    DatabaseReference reference;
    private LinearLayout mainLayout;
    EditText hydrationTypeView ,hydrationDateView,hydrationAmountView ;

    Button  editButton;
    boolean editMode = false ;
    private View mealTypeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hydration_details);
        getContent();
    }

    private  void find(){
        mainLayout = findViewById(R.id.hydration_details_main_layout);

        hydrationTypeView = mainLayout.findViewById(R.id.hydrationType_dt);
        hydrationAmountView = mainLayout.findViewById(R.id.hydrationAmount_dt);
        hydrationDateView = mainLayout.findViewById(R.id.hydrationDate_dt);

        editButton = findViewById(R.id.button_update_hyd);

    }
    private void getContent(){
        find();

        Intent i = getIntent();
        Hydration hydration = (Hydration) i.getSerializableExtra("hydration");

        hydrationTypeView.setText(hydration.getHydrationType());
        hydrationAmountView.setText(hydration.getAmount());
        hydrationDateView.setText(hydration.getHydrationDate());

    }

    private  void editMode(){
        editMode = !editMode;
        hydrationAmountView.setEnabled(editMode);
        hydrationDateView.setEnabled(editMode);

    }
    private void update(View view){
        editButton.setText("Edit");
        reference = FirebaseDatabase.getInstance().getReference("hydration");
        reference.orderByChild("hydrationType").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Hydration hydration = snapshot.getValue(Hydration.class);
                String mealTypeTxt =  hydrationTypeView.getText().toString();

                Map<String, Object> mealMap = new HashMap<>();

                mealMap.put("hydrationType",hydrationTypeView.getText().toString());
                mealMap.put("amount", hydrationAmountView.getText().toString() );
                mealMap.put("hydrationDate",   hydrationDateView.getText().toString());

                if (hydration.getHydrationType().equals(mealTypeTxt)) {
                    reference.child(mealTypeTxt).updateChildren(mealMap);

                    Toast.makeText(HydrationDetailsActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(HydrationDetailsActivity.this, HydrationActivity.class);
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
    private void edit(){
        System.out.println("edit mode" + editMode);
        editButton.setText("Update");
    }
    public void OnEdithydrationClick(View view){

        editMode();

        if(editMode)
            edit();
        else
            update(view);

    }
}