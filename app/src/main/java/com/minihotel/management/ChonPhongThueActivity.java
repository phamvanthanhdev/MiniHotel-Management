package com.minihotel.management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.minihotel.management.adapter.ChonPhongThueAdapter;
import com.minihotel.management.adapter.PhongThueAdapter;
import com.minihotel.management.managers.calls.CallPhieuDatById;
import com.minihotel.management.managers.interfaces.IPhieuDatById;
import com.minihotel.management.model.HangPhongDat;
import com.minihotel.management.model.PhieuDat;
import com.minihotel.management.model.PhongTrong;
import com.minihotel.management.utils.Common;
import com.minihotel.management.utils.Utils;

import java.time.LocalDate;

public class ChonPhongThueActivity extends AppCompatActivity {
    private RecyclerView rcHangPhongThue;
    private TextView txtTongTien;
    private PhieuDat phieuDat;
    private int idPhieuDat;
    private Button btnTiepTuc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_phong_thue);

        idPhieuDat = getIntent().getIntExtra("idPhieuDat", 0);
        initViews();
        setEvents();
        setBtnBack();
    }

    private void setBtnBack(){
        ImageButton btnBack = findViewById(R.id.imageButton);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private int soLuongHangPhongDaDat(){
        int tongSoLuongPhong = 0;
        for (HangPhongDat hangPhongDat: Utils.chitietHangPhongsThue) {
            tongSoLuongPhong += hangPhongDat.getSoLuong();
        }
        return tongSoLuongPhong;
    }

    private void setEvents() {
        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soLuongHangPhongDaDat() > Utils.phongChons.size()){
                    Common.onCreateMessageDialog(ChonPhongThueActivity.this,
                            "Vui lòng chọn đủ số lượng phòng").show();
                    Toast.makeText(ChonPhongThueActivity.this,
                            "phong dat " + soLuongHangPhongDaDat() + " phong chon " + Utils.phongChons.size(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(ChonPhongThueActivity.this, KhachHangDaiDienActivity.class);
                intent.putExtra("idPhieuDat", idPhieuDat);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPhieuDatById(idPhieuDat);
        //
        Utils.phongChons.clear();
    }

    private void initViews() {
        rcHangPhongThue = findViewById(R.id.rcChonPhongThue);
        txtTongTien = findViewById(R.id.txtTongTien);
        btnTiepTuc = findViewById(R.id.btnTiepTuc);
    }

    private void getPhieuDatById(int idPhieuDat){
        CallPhieuDatById.getPhieuDatById(idPhieuDat, new IPhieuDatById() {
            @Override
            public void onSuccess(PhieuDat phieuDatResponse) {
                phieuDat = phieuDatResponse;
                setHangPhongThueRecycler(rcHangPhongThue);
            }

            @Override
            public void onError(Throwable t) {
                Toast.makeText(ChonPhongThueActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("TAG-ERR", t.getMessage());
            }
        });
    }

    private void showDataTongTien(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate ngayDen = LocalDate.parse(phieuDat.getNgayBatDau());
            LocalDate ngayDi = LocalDate.parse(phieuDat.getNgayTraPhong());
            txtTongTien.setText(Common.convertCurrencyVietnamese(
                    Utils.getTongTienHangPhongsThue(ngayDen, ngayDi)) + " VNĐ");
        }
    }

    private void setHangPhongThueRecycler(RecyclerView rcChonPhongThue){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ChonPhongThueActivity.this,LinearLayoutManager.VERTICAL, false);
        rcHangPhongThue.setLayoutManager(layoutManager);
        ChonPhongThueAdapter adapter = new ChonPhongThueAdapter(ChonPhongThueActivity.this, Utils.chitietHangPhongsThue, phieuDat.getNgayBatDau(), phieuDat.getNgayTraPhong());
        rcChonPhongThue.setAdapter(adapter);
        showDataTongTien();
    }


}