package com.example.fitnessapp.calendar;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;
import com.example.fitnessapp.util.CalendarUtils;

import java.time.Clock;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;

public class EventEditActivity extends AppCompatActivity {
    private EditText eventNameET;
    private TextView eventDateTV, eventTimeTV;
    private LocalTime time;
    private int hour;
    private int minute;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now(Clock.system(ZoneId.of("Europe/Budapest")));
        eventDateTV.setText(getResources().getString(R.string.date) + ": " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeTV.setText(getResources().getString(R.string.time) + ": " + CalendarUtils.formattedTime(time));
    }

    private void initWidgets() {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
    }

    public void changeTimeAction(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                eventTimeTV.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                time = LocalTime.of(hour, minute);
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, false);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void saveEventAction(View view) {
        String eventName = eventNameET.getText().toString();
        sharedPreferences = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "defvalue");
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, time, username);
        Event.eventsList.add(newEvent);
        finish();
    }
}