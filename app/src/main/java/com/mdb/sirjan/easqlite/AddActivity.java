package com.mdb.sirjan.easqlite;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mdb.easqlitelib.EaSQLite;

import java.io.IOException;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    private EditText nameField;
    private EditText ageField;
    private EditText heightField;
    private EditText teamField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nameField = (EditText) findViewById(R.id.name_field);
        ageField = (EditText) findViewById(R.id.age_field);
        heightField = (EditText) findViewById(R.id.height_field);
        teamField = (EditText) findViewById(R.id.team_field);

        final String league = getIntent().getStringExtra(ViewEntriesActivity.LEAGUE_KEY);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = nameField.getText().toString();
                    int age = Integer.parseInt(ageField.getText().toString());
                    String height = heightField.getText().toString();
                    String team = teamField.getText().toString();
                    Object[] entries = {name, age, height, team};

                    List<String> colNames = EaSQLite.getColumnNames(league);
                    Pair<String, Object>[] row = new Pair[colNames.size()];

                    int i = 0;
                    for (String column : colNames) {
                        row[i] = new Pair<String, Object>(column, entries[i]);
                        i++;
                    }

                    try {
                        int succ = EaSQLite.addRow(league, row);
                        if (succ == -1) throw new IOException();
                    } catch (IOException e) {
                        Toast.makeText(AddActivity.this, "Addition to DB failed", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(AddActivity.this, "Addition to DB success", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
