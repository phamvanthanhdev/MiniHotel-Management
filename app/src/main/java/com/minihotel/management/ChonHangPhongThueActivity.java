package com.minihotel.management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.minihotel.management.adapter.HangPhongDatAdapter;
import com.minihotel.management.managers.calls.CallChiTiet2sByIdPhieuDat;
import com.minihotel.management.managers.calls.CallPhieuDatById;
import com.minihotel.management.managers.interfaces.IChiTiet2sByIdPhieuDat;
import com.minihotel.management.managers.interfaces.IPhieuDatById;
import com.minihotel.management.model.HangPhongDat;
import com.minihotel.management.model.PhieuDat;
import com.minihotel.management.utils.Common;
import com.minihotel.management.utils.Utils;

import java.time.LocalDate;
import java.util.List;

public class ChonHangPhongThueActivity extends AppCompatActivity {
    private PhieuDat phieuDat;
    private int idPhieuDat;
    private RecyclerView rcHangPhongThue;
    private TextInputEditText edtNgayNhanPhong, edtNgayTraPhong;
    private Button btnTiepTuc;
    private TextView txtTongTien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_hang_phong_thue);

        idPhieuDat = getIntent().getIntExtra("idPhieuDat", 0);
        initViews();
        setEvents();
    }

    private void setEvents() {
        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                if(LocalDate.parse(phieuDat.getNgayBatDau()).isAfter(Common.getCurrentDate())) {
                    Common.onCreateMessageDialog(ChonHangPhongThueActivity.this,
                            "Chưa đến thời gian nhận phòng!").show();
                    return;
                }
                if(LocalDate.parse(phieuDat.getNgayTraPhong()).isBefore(Common.getCurrentDate())) {
                    Common.onCreateMessageDialog(ChonHangPhongThueActivity.this,
                            "Đã quá thời gian nhận phòng!").show();
                    return;
                }
                Intent intent = new Intent(ChonHangPhongThueActivity.this, ChonPhongThueActivity.class);
                intent.putExtra("idPhieuDat", idPhieuDat);
                startActivity(intent);
            }
        });
    }

    private void showData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            edtNgayNhanPhong.setText(Common.fommatDateShow(LocalDate.parse(phieuDat.getNgayBatDau())));
            edtNgayTraPhong.setText(Common.fommatDateShow(LocalDate.parse(phieuDat.getNgayTraPhong())));
        }
    }

    private void initViews() {
        rcHangPhongThue = findViewById(R.id.rcHangPhongThue);
        edtNgayNhanPhong = findViewById(R.id.edtNgayNhanPhong);
        edtNgayTraPhong = findViewById(R.id.edtNgayTraPhong);
        btnTiepTuc = findViewById(R.id.btnTiepTuc);
        txtTongTien = findViewById(R.id.txtTongTien);
    }

    private void showDataTongTien(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate ngayDen = LocalDate.parse(phieuDat.getNgayBatDau());
            LocalDate ngayDi = LocalDate.parse(phieuDat.getNgayTraPhong());
            txtTongTien.setText(Common.convertCurrencyVietnamese(
                    Utils.getTongTienHangPhongsThue(ngayDen, ngayDi)
            ) + " VNĐ");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getChiTiet2sByIdPhieuDat();
        getPhieuDatById(idPhieuDat);
    }

    private void getPhieuDatById(int idPhieuDat){
        CallPhieuDatById.getPhieuDatById(idPhieuDat, new IPhieuDatById() {
            @Override
            public void onSuccess(PhieuDat phieuDatResponse) {
                phieuDat = phieuDatResponse;
                showData();
            }

            @Override
            public void onError(Throwable t) {
                Toast.makeText(ChonHangPhongThueActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("TAG-ERR", t.getMessage());
            }
        });
    }

    private void getChiTiet2sByIdPhieuDat(){
        CallChiTiet2sByIdPhieuDat.getChiTietsByIdPhieuDat(idPhieuDat, new IChiTiet2sByIdPhieuDat() {
            @Override
            public void onSuccess(List<HangPhongDat> responses) {
                Utils.chitietHangPhongsThue = responses;
                setHangPhongRecycler();
                showDataTongTien();
            }

            @Override
            public void onError(Throwable t) {
                Log.d("TAG-ERR", t.getMessage());
            }
        });
    }

    private void setHangPhongRecycler(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        rcHangPhongThue.setLayoutManager(layoutManager);
        HangPhongDatAdapter adapter = new HangPhongDatAdapter(this, Utils.chitietHangPhongsThue);
        rcHangPhongThue.setAdapter(adapter);
        adapter.setOnButtonGiamClickListener(new HangPhongDatAdapter.OnButtonGiamClickListener() {
            @Override
            public void onClick(int position, int idHangPhong, int soLuong) {
                if(soLuong <= 1){
                    Utils.xoaHangPhong(idHangPhong);
                    adapter.notifyItemRemoved(position);
                }else{
                    Utils.giamSoLuongHangPhong(idHangPhong);
                    adapter.notifyItemChanged(position);
                }
                showDataTongTien();
            }
        });
        adapter.setOnButtonTangClickListener(new HangPhongDatAdapter.OnButtonTangClickListener() {
            @Override
            public void onClick(int position, int idHangPhong, int soLuong, int soLuongTrong) {
                if((soLuong + 1) > soLuongTrong){
                    Common.onCreateMessageDialog(ChonHangPhongThueActivity.this, "Số lượng phòng hiện tại không đủ!").show();
                }else {
                    Utils.tangSoLuongHangPhong(idHangPhong);
                    adapter.notifyItemChanged(position);
                    showDataTongTien();
                }
            }
        });

    }
}