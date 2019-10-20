package com.example.sudoku;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class HistoryItemAdapter extends ArrayAdapter<MaterialCardView> {
    private int resourceId;

    public HistoryItemAdapter(Context context, int CardViewResourceId, List<MaterialCardView> objects) {
        super(context, CardViewResourceId, objects);
    }

    @Override
    public View getView(int position , View convertView, ViewGroup parent) {
        return null;
    }
}
