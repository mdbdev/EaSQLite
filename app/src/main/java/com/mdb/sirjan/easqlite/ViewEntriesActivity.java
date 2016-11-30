package com.mdb.sirjan.easqlite;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mdb.easqlitelib.EaSQLite;

import java.util.List;

public class ViewEntriesActivity extends AppCompatActivity {
    public static final String LEAGUE_KEY = "league_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entries);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        final String tableName = intent.getStringExtra(LEAGUE_KEY);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                    intent.putExtra(ViewEntriesActivity.LEAGUE_KEY, tableName);
                    startActivity(intent);
                }
            });
        }

        setTitle("League: " + tableName);
        displayTableContents(tableName);
    }

    private void displayTableContents(String tableName) {
        List<Object> playerNames = EaSQLite.getColumn(tableName, "name");
        List<Object> playerAges = EaSQLite.getColumn(tableName, "age");
        List<Object> playerHeights = EaSQLite.getColumn(tableName, "height");
        List<Object> playerTeams = EaSQLite.getColumn(tableName, "team");

        ListView names = (ListView) findViewById(R.id.name);
        ListView ages = (ListView) findViewById(R.id.age);
        ListView heights = (ListView) findViewById(R.id.height);
        ListView teams = (ListView) findViewById(R.id.team);

        names.addHeaderView(getHeaderView("Names"));
        ages.addHeaderView(getHeaderView("Ages"));
        heights.addHeaderView(getHeaderView("Heights"));
        teams.addHeaderView(getHeaderView("Teams"));

        names.setAdapter(getAdapter(playerNames, names));
        ages.setAdapter(getAdapter(playerAges, ages));
        heights.setAdapter(getAdapter(playerHeights, heights));
        teams.setAdapter(getAdapter(playerTeams, teams));
    }

    private TextView getHeaderView(String leagueName) {
        TextView view = new TextView(getApplicationContext());
        view.setText(leagueName);
        view.setTextColor(Color.BLACK);
        return view;
    }

    private ListAdapter getAdapter(final List<Object> entries, final ListView view) {
        return new ListAdapter() {
            @Override
            public boolean areAllItemsEnabled() {
                return true;
            }

            @Override
            public boolean isEnabled(int position) {
                return true;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getCount() {
                return entries.size();
            }

            @Override
            public Object getItem(int position) {
                return entries.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return view;
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public boolean isEmpty() {
                return entries.size() == 0;
            }
        };
    }
}
