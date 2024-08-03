package com.minihotel.management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.minihotel.management.adapter.PhongTrongAdapter;
import com.minihotel.management.model.PhongTrong;

import java.util.List;

public class PhongTrongActivity extends AppCompatActivity {
    private RecyclerView rcPhongTrong;
    private List<PhongTrong> phongTrongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_trong);

        initViews();
    }

    private void initViews() {
        rcPhongTrong = findViewById(R.id.rcPhongTrong);
    }

    private void setHangPhongRecycler(){
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rcPhongTrong.setLayoutManager(layoutManager);
        PhongTrongAdapter adapter = new PhongTrongAdapter(this, phongTrongs);
        rcPhongTrong.setAdapter(adapter);
        rcPhongTrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}