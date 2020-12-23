package com.example.flooddetector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flooddetector.adapter.IndicatorAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rvIndicators)
    RecyclerView rvIndicators;
    @BindView(R.id.tvLiveIndicators)
    TextView tvLiveIndicators;
    @BindView(R.id.tvDescription)
    TextView tvLiveDesc;
    @BindView(R.id.tvWaterLevel)
    TextView tvCurrentWaterLevel;
    @BindView(R.id.tvHeightLeft)
    TextView tvWaterHeightLeft;


    private IndicatorAdapter adapter;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference currLevel = database.getReference("currlevel");

        rvIndicators.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        initIndicators();

        currLevel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = Objects.requireNonNull(snapshot.getValue()).toString() + " cm";
                tvCurrentWaterLevel.setText(value);
                setIndicatorType(Integer.parseInt(snapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onCancelled: ", error.toException());
            }
        });
    }

    private void setIndicatorType(Integer level) {
        if (level <= 4) {
            tvLiveIndicators.setText(getString(R.string.safe));
            tvLiveDesc.setText(getString(R.string.safe_description));
            tvLiveIndicators.setTextColor(getResources().getColor(R.color.green));
        } else if (level <= 10) {
            tvLiveIndicators.setText(getString(R.string.alert));
            tvLiveDesc.setText(getString(R.string.alert_description));
            tvLiveIndicators.setTextColor(getResources().getColor(R.color.yellow));
        } else {
            tvLiveIndicators.setText(getString(R.string.danger));
            tvLiveDesc.setText(getString(R.string.danger_description));
            tvLiveIndicators.setTextColor(getResources().getColor(R.color.red));
        }
    }

    private void initIndicators() {
        adapter = new IndicatorAdapter(this);
        adapter.setListData();
        adapter.notifyDataSetChanged();
        rvIndicators.setAdapter(adapter);
    }
}