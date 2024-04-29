package com.example.fitnessapp.account;

import android.content.Context;
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
import com.example.fitnessapp.entity.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserManagementActivity extends AppCompatActivity {

    DatabaseReference reference;
    private SharedPreferences sharedPreferences;
    private LinearLayout mainLayout;
    List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        fetchUsersFromDatabase();
    }

    private void fetchUsersFromDatabase() {
        mainLayout = findViewById(R.id.main_layout);
        reference = FirebaseDatabase.getInstance().getReference("users");
        reference.orderByChild("username").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                assert user != null;
                if (!user.getType().equals("user")) {
                    return;
                }
                userList.add(user);

                View userView = getLayoutInflater().inflate(R.layout.user, null);
                TextView username = userView.findViewById(R.id.username);
                TextView email = userView.findViewById(R.id.email);

                username.setText(user.getUsername());
                email.setText(user.getEmail());
                mainLayout.addView(userView);
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

    public void onDeleteClick(View view) {
        LinearLayout parent = (LinearLayout) view.getParent();
        TextView username = parent.findViewById(R.id.username);
        String usernameText = username.getText().toString();

        reference = FirebaseDatabase.getInstance().getReference("users");
        reference.orderByChild("username").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                if (user.getUsername().equals(usernameText)) {
                    reference.child(usernameText).removeValue();
                    Toast.makeText(UserManagementActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
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

    public void onAddAccountClick(View view) {
        Intent intent = new Intent(UserManagementActivity.this, AddUserActivity.class);
        startActivity(intent);
    }

    public void onBackClick(View view) {
        sharedPreferences = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        String type = sharedPreferences.getString("type", "defvalue");
        Intent intent = new Intent(UserManagementActivity.this, AdminActivity.class);
        startActivity(intent);

    }

}
