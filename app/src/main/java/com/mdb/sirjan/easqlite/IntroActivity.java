package com.mdb.sirjan.easqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mdb.easqlitelib.EaSQLite;
import com.mdb.easqlitelib.exceptions.InvalidInputException;
import com.mdb.easqlitelib.exceptions.InvalidTypeException;

import java.io.IOException;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        try {
            EaSQLite.initialize(getApplicationContext());
        } catch (InvalidTypeException|InvalidInputException|IOException|ClassNotFoundException e) {
            new AlertDialog.Builder(IntroActivity.this)
                    .setTitle("Database Corrupted")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
        }

        Button nflBtn = (Button) findViewById(R.id.btn_nfl);
        Button nbaBtn = (Button) findViewById(R.id.btn_nba);
        Button nhlBtn = (Button) findViewById(R.id.btn_nhl);

        setSpecificOnClickListener(nflBtn, "nfl");
        setSpecificOnClickListener(nbaBtn, "nba");
        setSpecificOnClickListener(nhlBtn, "nhl");
    }

    private void setSpecificOnClickListener(Button btn, final String league) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewEntriesActivity.class);
                intent.putExtra(ViewEntriesActivity.LEAGUE_KEY, league);
                startActivity(intent);
            }
        });
    }
}
