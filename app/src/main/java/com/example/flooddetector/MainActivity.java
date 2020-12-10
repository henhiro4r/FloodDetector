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
        DatabaseReference reference = database.getReference("test");

        rvIndicators.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        initIndicators();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                tvLiveIndicators.setText(value);
                Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onCancelled: ", error.toException());
            }
        });
    }

    private void initIndicators() {
        adapter = new IndicatorAdapter(this);
        adapter.setListData();
        adapter.notifyDataSetChanged();
        rvIndicators.setAdapter(adapter);
    }
}