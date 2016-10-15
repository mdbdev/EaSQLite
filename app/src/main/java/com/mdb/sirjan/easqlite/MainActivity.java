package com.mdb.sirjan.easqlite;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mdb.easqlitelib.*;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textViewColumn;
    private EditText editTextColumn;
    private List<String> columnList;
    private String TABLE_NAME = "Test Table";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
}
