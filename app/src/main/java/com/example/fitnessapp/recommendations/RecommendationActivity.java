package com.example.fitnessapp.recommendations;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;

public class RecommendationActivity extends AppCompatActivity {
    private ImageView image;
    private Uri imgUri;
    private String imgUriText;
    private SharedPreferences sharedPreferences;
    final int[] adsImgArray = {
            R.drawable.virtualrace,
            R.drawable.beacdanceprogram,
            R.drawable.kekrun,
            R.drawable.eltesportsweek

    };
    final int[] exImgArray = {
            R.drawable.reverseabcrunch,
            R.drawable.squatjumps,
            R.drawable.bentkneepushup,
            R.drawable.glutebridge
    };
    final int[] recipeImgArray = {
            R.drawable.cheesecake,
            R.drawable.lentilsalad,
            R.drawable.supershake,
            R.drawable.tunawraps,
            R.drawable.oats
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_reco);
        loadData();
    }
    private void loadData() {
        sharedPreferences = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("title", "Not found");
        TextView title = findViewById(R.id.recoName);
        title.setText(name);
        ImageView img = findViewById(R.id.image);
        int priority = sharedPreferences.getInt("priority", 1);
        String type = sharedPreferences.getString("type", "");
        if (type.equals("ad")) img.setImageDrawable(getDrawable(adsImgArray[priority-1]));
        if (type.equals("exercise")) img.setImageDrawable(getDrawable(exImgArray[priority-1]));
        if (type.equals("recipe")) img.setImageDrawable(getDrawable(recipeImgArray[priority-1]));
        String instruction = sharedPreferences.getString("instruction", "sth");
        TextView description = findViewById(R.id.description);
        description.setText(instruction);
    }

}
