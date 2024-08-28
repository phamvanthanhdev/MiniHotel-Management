package com.minihotel.management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.minihotel.management.adapter.ChiTietSuDungDichVuAdapter;
import com.minihotel.management.adapter.PhieuDatTheoNgayAdapter;
import com.minihotel.management.dto.ChiTietSuDungDichVuRequest;
import com.minihotel.management.managers.calls.CallPhieuDatTheoNgay;
import com.minihotel.management.managers.calls.CallPhieuDatTheoThoiGian;
import com.minihotel.management.managers.interfaces.IPhieuDatTheoNgay;
import com.minihotel.management.managers.interfaces.IPhieuDatTheoThoiGian;
import com.minihotel.management.model.PhieuDatTheoNgay;
import com.minihotel.management.utils.Common;
import com.minihotel.management.utils.Utils;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PhieuDatTheoNgayActivity extends AppCompatActivity {
    private TextInputEditText edtNgayBatDau, edtNgayKetThuc;
    private RecyclerView rcPhieuDatTheoNgay;
    private List<PhieuDatTheoNgay> phieuDatTheoNgays;
    private LocalDate ngayBatDauTim = Common.getCurrentDate();
    private LocalDate ngayKetThucTim = Common.getPlusDayCurrentDate();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phieu_dat_theo_ngay);

        initViews();
        edtNgayBatDau.setText(Common.fommatDateShow(ngayBatDauTim));
        edtNgayKetThuc.setText(Common.fommatDateShow(ngayKetThucTim));
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
    private void setEvents() {
        edtNgayBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDateBatDau();
            }
        });

        edtNgayKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDateKetThuc();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPhieuDatTheoThoiGian(ngayBatDauTim, ngayKetThucTim);
    }

    private void initViews() {
        rcPhieuDatTheoNgay = findViewById(R.id.rcPhieuDatTheoNgay);
        edtNgayBatDau = findViewById(R.id.edtNgayBatDau);
        edtNgayKetThuc = findViewById(R.id.edtNgayKetThuc);
    }

    /*private void getPhieuDatTheoNgay(){
        CallPhieuDatTheoNgay.getPhieuDatTheoNgay(Common.fommatDateRequest(ngay), new IPhieuDatTheoNgay() {
            @Override
            public void onSuccess(List<PhieuDatTheoNgay> responses) {
                phieuDatTheoNgays = responses;
                setPhieuDatTheoNgayRecycler();
            }

            @Override
            public void onError(Throwable t) {
                Log.d("TAG-ERR", t.getMessage());
            }
        });
    }*/

    private void getPhieuDatTheoThoiGian(LocalDate ngayBatDauTim, LocalDate ngayKetThucTim){
        CallPhieuDatTheoThoiGian.getPhieuDatTheoThoiGian(Common.fommatDateRequest(ngayBatDauTim),
                Common.fommatDateRequest(ngayKetThucTim), new IPhieuDatTheoThoiGian() {
            @Override
            public void onSuccess(List<PhieuDatTheoNgay> responses) {
                phieuDatTheoNgays = responses;
                setPhieuDatTheoNgayRecycler();
            }

            @Override
            public void onError(Throwable t) {
                Log.d("TAG-ERR", t.getMessage());
            }
        });
    }

    private void setPhieuDatTheoNgayRecycler(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        rcPhieuDatTheoNgay.setLayoutManager(layoutManager);
        PhieuDatTheoNgayAdapter adapter = new PhieuDatTheoNgayAdapter(this, phieuDatTheoNgays);
        rcPhieuDatTheoNgay.setAdapter(adapter);
        adapter.setOnItemClickListener(new PhieuDatTheoNgayAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, int idPhieuDat) {
                Intent intent = new Intent(PhieuDatTheoNgayActivity.this, ChonHangPhongThueActivity.class);
                intent.putExtra("idPhieuDat", idPhieuDat);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("NewApi")
    public void pickDateBatDau(){
        int ngayHienTai = ngayBatDauTim.getDayOfMonth();
        int thangHienTai = ngayBatDauTim.getMonthValue();
        int namHienTai = ngayBatDauTim.getYear();

        DatePickerDialog datePickerDialog = new DatePickerDialog(PhieuDatTheoNgayActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    ngayBatDauTim = LocalDate.of(year, month, dayOfMonth);
                    edtNgayBatDau.setText(Common.fommatDateShow(ngayBatDauTim));
                    getPhieuDatTheoThoiGian(ngayBatDauTim, ngayKetThucTim);
                }
            }
        }, namHienTai, thangHienTai, ngayHienTai);
        datePickerDialog.show();
    }

    @SuppressLint("NewApi")
    public void pickDateKetThuc(){
        int ngayHienTai = ngayKetThucTim.getDayOfMonth();
        int thangHienTai = ngayKetThucTim.getMonthValue();
        int namHienTai = ngayKetThucTim.getYear();

        DatePickerDialog datePickerDialog = new DatePickerDialog(PhieuDatTheoNgayActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    ngayKetThucTim = LocalDate.of(year, month, dayOfMonth);
                    edtNgayKetThuc.setText(Common.fommatDateShow(ngayKetThucTim));
                    getPhieuDatTheoThoiGian(ngayBatDauTim, ngayKetThucTim);
                }
            }
        }, namHienTai, thangHienTai, ngayHienTai);
        datePickerDialog.show();
    }
}