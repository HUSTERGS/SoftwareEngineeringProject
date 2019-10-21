package com.example.sudoku;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sudoku.dlx.GenerateBoard;
import com.google.android.material.card.MaterialCardView;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryItemAdapter.ViewHolder> {
    private List<GameRecord> mHistory;

    private static String formatInterval(final long millis) {
        final long hr = TimeUnit.MILLISECONDS.toHours(millis);
        final long min = TimeUnit.MILLISECONDS.toMinutes(millis - TimeUnit.HOURS.toMillis(hr));
        final long sec = TimeUnit.MILLISECONDS.toSeconds(millis - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min));
//        final long ms = TimeUnit.MILLISECONDS.toMillis(millis - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min) - TimeUnit.SECONDS.toMillis(sec));
        return String.format("%02d:%02d:%02d", hr, min, sec);
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        public MaterialCardView materialCardView;
        public TextView userText;
        public TextView timeText;
        public TextView typeText;
        public TextView levelText;
        public TextView resultText;
        public TextView progressText;
        public ViewHolder(MaterialCardView v) {
            super(v);
            materialCardView = v;
            LinearLayout linearLayout = (LinearLayout) materialCardView.getChildAt(0);
            ConstraintLayout icons = (ConstraintLayout) linearLayout.getChildAt(0);
            ConstraintLayout result = (ConstraintLayout) linearLayout.getChildAt(1);

            LinearLayout temp = (LinearLayout) icons.getChildAt(0);
            userText = (TextView) temp.getChildAt(1);
            temp = (LinearLayout) icons.getChildAt(1);
            timeText = (TextView) temp.getChildAt(1);
            temp = (LinearLayout) icons.getChildAt(2);
            typeText = (TextView) temp.getChildAt(1);
            temp = (LinearLayout) icons.getChildAt(3);
            levelText = (TextView) temp.getChildAt(1);


            resultText = (TextView) result.getChildAt(0);
            progressText = (TextView) result.getChildAt(1);
        }
    }

    public HistoryItemAdapter(List<GameRecord> gameRecords) {
        mHistory = gameRecords;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MaterialCardView v= (MaterialCardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GameRecord gameRecord= mHistory.get(position);
        holder.userText.setText(gameRecord.getUser());
        holder.timeText.setText(formatInterval(-gameRecord.getusedTime()));
        holder.typeText.setText(gameRecord.getType() == 9 ? "九宫" : "四宫");
        int level = gameRecord.getLevel();
        String[] texts = new String[] {"简单", "中等", "困难", "极难"};
        if (gameRecord.getType() == 9) {
            holder.levelText.setText(texts[(gameRecord.getLevel() - 20) / 10]);
        } else {
            holder.levelText.setText(texts[(gameRecord.getLevel() - 3) / 3]);
        }

        int correctCount = GenerateBoard.correctCount(
                gameRecord.getOrigin(), gameRecord.getAnwser(), gameRecord.getCurrent()
        );
        holder.progressText.setText(correctCount + " / " + gameRecord.getLevel());

        holder.resultText.setText(correctCount == level ? "成功" : "失败");
        holder.resultText.setTextColor(correctCount == level ?
                Color.parseColor("#66ff33") :
                Color.parseColor("#ff0000")
        );

        holder.materialCardView.setStrokeColor(correctCount == level ?
                Color.parseColor("#66ff33") :
                Color.parseColor("#ff4d4d"));
    }

    @Override
    public int getItemCount() {
        return mHistory.size();
    }
}
