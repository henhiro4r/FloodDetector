package com.example.flooddetector.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flooddetector.R;
import com.example.flooddetector.model.Indicator;

import java.util.ArrayList;
import java.util.List;

public class IndicatorAdapter extends RecyclerView.Adapter<IndicatorAdapter.ViewHolder> {

    private Context context;
    private List<Indicator> listData = new ArrayList<>();
    private String[] indicatorType, indicatorMin, indicatorMax;

    public IndicatorAdapter(Context context) {
        this.context = context;
        indicatorType = context.getResources().getStringArray(R.array.indicator_type);
        indicatorMin = context.getResources().getStringArray(R.array.indicator_min);
        indicatorMax = context.getResources().getStringArray(R.array.indicator_max);
    }

    public void setListData() {
        for (int i = 0; i < indicatorType.length; i++) {
            Indicator indicator = new Indicator(indicatorType[i], indicatorMin[i], indicatorMax[i]);
            listData.add(indicator);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.indicator_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Indicator indicator = listData.get(position);
        holder.tvIndicatorType.setText(indicator.getType());
        holder.tvIndicatorMin.setText(indicator.getMin());
        holder.tvIndicatorMax.setText(indicator.getMax());
        if (indicator.getType().equalsIgnoreCase("safe")) {
            holder.cvIndicatorColor.setCardBackgroundColor(context.getResources().getColor(R.color.green));
        } else if (indicator.getType().equalsIgnoreCase("alert")) {
            holder.cvIndicatorColor.setCardBackgroundColor(context.getResources().getColor(R.color.yellow));
        } else if (indicator.getType().equalsIgnoreCase("danger")) {
            holder.cvIndicatorColor.setCardBackgroundColor(context.getResources().getColor(R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cvIndicatorColor;
        TextView tvIndicatorType, tvIndicatorMin, tvIndicatorMax;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvIndicatorColor = itemView.findViewById(R.id.cvIndicatorColor);
            tvIndicatorType = itemView.findViewById(R.id.tvIndicatorType);
            tvIndicatorMin = itemView.findViewById(R.id.tvIndicatorMin);
            tvIndicatorMax = itemView.findViewById(R.id.tvIndicatorMax);
        }
    }
}
