package com.example.oscarmedinaendevinaelnmero;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.graphics.Typeface;
import android.graphics.Color;
import android.view.Gravity;

public class RecordsView extends Activity {

    public ArrayList<Record> records;
    public TableLayout tableLayout;
    public Button BackButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        tableLayout = findViewById(R.id.tableLayout);
        BackButton = findViewById(R.id.BackButton);
        Intent intent = getIntent();
        records = (ArrayList<Record>) intent.getSerializableExtra("records");
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecordsView.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        if (records.size() == 0) {
            TextView noRecords = new TextView(this);
            noRecords.setText("No records found");
            tableLayout.addView(noRecords);
            return;
        }
        //Add Headers
        TableRow headerRow = new TableRow(this);
        headerRow.setGravity(Gravity.CENTER);

        TextView nameHeader = new TextView(this);
        nameHeader.setText("Name");
        nameHeader.setPadding(10, 10, 10, 10);
        nameHeader.setBackgroundResource(R.drawable.cell_header_background);
        nameHeader.setTypeface(null, Typeface.BOLD);
        nameHeader.setTextColor(Color.WHITE);
        nameHeader.setGravity(Gravity.CENTER);

        TextView attemptsHeader = new TextView(this);
        attemptsHeader.setText("Attempts");
        attemptsHeader.setPadding(10, 10, 10, 10);
        attemptsHeader.setBackgroundResource(R.drawable.cell_header_background);
        attemptsHeader.setTypeface(null, Typeface.BOLD);
        attemptsHeader.setTextColor(Color.WHITE);
        attemptsHeader.setGravity(Gravity.CENTER);

        TextView timeHeader = new TextView(this);
        timeHeader.setText("Time");
        timeHeader.setPadding(10, 10, 10, 10);
        timeHeader.setBackgroundResource(R.drawable.cell_header_background);
        timeHeader.setTypeface(null, Typeface.BOLD);
        timeHeader.setTextColor(Color.WHITE);
        timeHeader.setGravity(Gravity.CENTER);
        headerRow.addView(nameHeader);
        headerRow.addView(attemptsHeader);
        headerRow.addView(timeHeader);
        tableLayout.addView(headerRow);

        for (Record record : records) {
            TableRow row = new TableRow(this);
            row.setGravity(Gravity.CENTER);

            TextView name = new TextView(this);
            name.setText(record.name);
            name.setPadding(10, 10, 10, 10);
            name.setBackgroundResource(R.drawable.cell_background);
            name.setGravity(Gravity.CENTER);

            TextView attempts = new TextView(this);
            attempts.setText(String.valueOf(record.attempts));
            attempts.setPadding(10, 10, 10, 10);
            attempts.setBackgroundResource(R.drawable.cell_background);
            attempts.setGravity(Gravity.CENTER);

            TextView time = new TextView(this);
            time.setText(record.time);
            time.setPadding(10, 10, 10, 10);
            time.setBackgroundResource(R.drawable.cell_background);
            time.setGravity(Gravity.CENTER);

            row.addView(name);
            row.addView(attempts);
            row.addView(time);
            tableLayout.addView(row);
        }
    }
}
