package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.litepal.LitePal;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class History extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = (RecyclerView) findViewById(R.id.history_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<GameRecord> gameRecords = LitePal.findAll(GameRecord.class);
        Collections.reverse(gameRecords);
        mAdapter = new HistoryItemAdapter(gameRecords);
        recyclerView.setAdapter(mAdapter);
    }
}
