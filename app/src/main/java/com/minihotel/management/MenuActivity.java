package com.minihotel.management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.minihotel.management.utils.Utils;

public class MenuActivity extends AppCompatActivity {
    private ImageButton btnSoDo, btnCheckIn, btnPhieuDatTrongNgay, btnTraPhongKhachDoan, btnHoaDonNgay;
    private ImageButton btnDoanhThu, btnKhachLuuTru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initViews();
        setEvents();
    }


    private void setEvents() {
        btnSoDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, SoDoActivity.class));
            }
        });

        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, LuaChonThueActivity.class));
            }
        });

        btnPhieuDatTrongNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, PhieuDatTheoNgayActivity.class));
            }
        });

        btnTraPhongKhachDoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, TraPhongKhachDoanActivity.class));
            }
        });

        btnHoaDonNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, HoaDonNgayActivity.class));
            }
        });

        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, DoanhThuTheoNgayActivity.class));
            }
        });

        btnKhachLuuTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, KhachThueActivity.class));
            }
        });
    }

    private void initViews() {
        btnSoDo = findViewById(R.id.btnSoDo);
        btnCheckIn = findViewById(R.id.btnCheckin);
        btnPhieuDatTrongNgay = findViewById(R.id.btnPhieuDatTrongNgay);
        btnTraPhongKhachDoan = findViewById(R.id.btnTraPhongKhachDoan);
        btnHoaDonNgay = findViewById(R.id.btnHoaDonNgay);
        btnDoanhThu = findViewById(R.id.btnDoanhThu);
        btnKhachLuuTru = findViewById(R.id.btnKhachLuuTru);
    }

}