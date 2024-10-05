package com.example.oscarmedinaendevinaelnmero;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import java.util.Date;
import java.util.Random;
import java.io.Serializable;
import java.util.ArrayList;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;

class Record implements Serializable {
    public String name;
    public int attempts;
    public String time;
}

public class MainActivity extends AppCompatActivity {

    int number = 0;
    int attempts = 0;
    TextView attemptsText;
    TextView historyText;
    EditText editText;
    boolean timerActive = false;
    long startTime = 0;
    static ArrayList<Record> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        number = new Random().nextInt(100)+1;
        if (records == null) {
            records = new ArrayList<Record>();
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        Button RecordsButton = findViewById(R.id.RecordsButton);
        editText = findViewById(R.id.inputText);
        editText.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // Trigger the button's click event
                button.performClick();
                return true; // Indicate that the event was handled
            }
            return false; // Pass other events
        });
        attemptsText = findViewById(R.id.attempts);
        attemptsText.setText("Attempts: " + 0);
        historyText = findViewById(R.id.history);
        ClearHistory();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckInput();
            }
        });

        RecordsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ChangeToRecords();
            }
        });
    }

    public void ChangeToRecords() {
        Intent intent = new Intent(this, RecordsView.class);
        intent.putExtra("records", records);
        startActivity(intent);
    }

    public void CheckInput() {
        if (!timerActive) {
            startTime = System.currentTimeMillis();
            timerActive = true;
        }
        SetAttempts(attempts+1);
        String input = editText.getText().toString();
        TestInput(input);
        editText.setText("");
    }

    public void TestInput(String input) {
        int num = 0;
        try {
            num = Integer.parseInt(input);
        }
        catch (Exception e) {
            Toast.makeText(this, "Put a number between 1 and 100", Toast.LENGTH_SHORT).show();
            return;
        }
        if (num < 1 || num > 100) {
            Toast.makeText(this, "Put a number between 1 and 100", Toast.LENGTH_SHORT).show();
        }
        else if (num < number) {
            Toast.makeText(this, "The number is greater than " + num, Toast.LENGTH_SHORT).show();
            AddHistory("The number is greater than " + num);
        }
        else if (num > number) {
            Toast.makeText(this, "The number is less than " + num, Toast.LENGTH_SHORT).show();
            AddHistory("The number is less than " + num);
        }
        else {
            WinDialog();
        }
    }

    public void WinDialog() {

        long endTime = System.currentTimeMillis();
        int time = (int) ((endTime - startTime) / 1000);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You win!");
        builder.setMessage("You won in " + attempts + " attempts and in " + time + " seconds");
        EditText name = new EditText(this);
        builder.setView(name);
        builder.setPositiveButton("Save Record", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (name.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "You need to write your name", Toast.LENGTH_SHORT).show();
                    return;
                }
                Record record = new Record();
                record.name = name.getText().toString();
                record.attempts = attempts;
                record.time = formatTime(time);
                records.add(record);
                number = new Random().nextInt(100)+1;
                SetAttempts(0);
                ClearHistory();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Continue Without Saving", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                number = new Random().nextInt(100)+1;
                SetAttempts(0);
                ClearHistory();
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void SetAttempts(int num) {
        attempts = num;
        attemptsText.setText("Attempts: " + attempts);
    }

    public void AddHistory(String text) {
        historyText.setText(historyText.getText() + text + "\n");
    }

    public void ClearHistory() {
        historyText.setText("history:\n");
    }

    public static String formatTime(int seconds) {
        int minutes = seconds / 60;
        seconds = seconds % 60;
        
        if (minutes > 0) {
            return minutes + "m " + seconds + "s";
        } else {
            return seconds + "s";
        }
    }
}