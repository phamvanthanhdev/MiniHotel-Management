package com.minihotel.management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.minihotel.management.adapter.KhachThueAdapter;
import com.minihotel.management.adapter.PhieuDatKhachHangAdapter;
import com.minihotel.management.dto.KhachHangRequest;
import com.minihotel.management.dto.KhachThueRequest;
import com.minihotel.management.dto.ResultResponse;
import com.minihotel.management.managers.calls.CallKhachThueHienTai;
import com.minihotel.management.managers.calls.CallPhongsHienTai;
import com.minihotel.management.managers.calls.CallThemKhachThueToChiTietPhieuThue;
import com.minihotel.management.managers.calls.CallTimKiemKhachHang;
import com.minihotel.management.managers.calls.CallXoaKhachThueTrongChiTietPhieuThue;
import com.minihotel.management.managers.interfaces.IKhachThueHienTai;
import com.minihotel.management.managers.interfaces.IPhongsHienTai;
import com.minihotel.management.managers.interfaces.IThemKhachThueToChiTietPhieuThue;
import com.minihotel.management.managers.interfaces.ITimKiemKhacHang;
import com.minihotel.management.managers.interfaces.IXoaKhachThueTrongChiTietPhieuThue;
import com.minihotel.management.model.KhachHang;
import com.minihotel.management.model.KhachThue;
import com.minihotel.management.model.PhongHienTai;
import com.minihotel.management.utils.Common;

import java.util.ArrayList;
import java.util.List;

public class KhachThueActivity extends AppCompatActivity {
    private List<String> phongDangThue = new ArrayList<>();
    private List<PhongHienTai> phongHienTais;
    private AutoCompleteTextView autoTextMaPhong;
    private Button btnXacNhan;
    private TextInputEditText edtSdt;
    private KhachHang khachHang;
    private List<KhachThue> khachThues;
    private RecyclerView rcKhachThue;
    int idChiTietPhieuThue = 0;
    int idKhachThue = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_thue);

        setViews();
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

    @Override
    protected void onResume() {
        super.onResume();
        getPhongsHienTai();
        getKhachThueHienTai();
    }

    private void setEvents() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtSdt.getText().toString().trim().equals("")){
                    Common.onCreateMessageDialog(KhachThueActivity.this,
                            "Vui lòng nhập số điện thoại.").show();
                }
                String sdt = edtSdt.getText().toString().trim();
                timKiemKhachHangTheoSdt(sdt);
            }
        });
    }

    public void timKiemKhachHangTheoSdt(String sdt){
        CallTimKiemKhachHang.timKiemKhachHangBySdt(sdt, new ITimKiemKhacHang() {
            @Override
            public void onSuccess(KhachHang khachHangResponse) {
                khachHang = khachHangResponse;
                KhachThueRequest khachThueRequest = new KhachThueRequest();
                List<Integer> idKhachThues = new ArrayList<>();
                idKhachThues.add(khachHangResponse.getIdKhachHang());
                khachThueRequest.setIdKhachThues(idKhachThues);
                String maPhong = autoTextMaPhong.getText().toString();
                for (PhongHienTai phongHienTai: phongHienTais) {
                    if(maPhong.trim().equals(phongHienTai.getMaPhong().trim())){
                        khachThueRequest.setIdChiTietPhieuThue(phongHienTai.getIdChiTietPhieuThue());
                    }
                }
                if(khachThueRequest.getIdChiTietPhieuThue() == null){
                    Common.onCreateMessageDialog(KhachThueActivity.this,
                            "Vui lòng chọn phòng muốn thêm").show();
                    return;
                }
                themKhachHangToChiTietPhieuThue(khachThueRequest);
            }

            @Override
            public void onError(Throwable t) {
                Common.onCreateMessageDialog(KhachThueActivity.this,
                        "Không tìm thấy khách hàng nào").show();
            }
        });
    }

    private void setViews() {
        autoTextMaPhong = findViewById(R.id.autoTextMaPhong);
        edtSdt = findViewById(R.id.edtSdt);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        rcKhachThue = findViewById(R.id.rcKhachThue);
    }

    public void getKhachThueHienTai(){
        CallKhachThueHienTai.getKhachThueHienTai(new IKhachThueHienTai() {
            @Override
            public void onSuccess(List<KhachThue> responses) {
                khachThues = responses;
                setKhachThueRecycler();
            }

            @Override
            public void onError(Throwable t) {
                Log.e("TAG-ERR", t.getMessage());
            }
        });
    }

    public void getPhongsHienTai(){
        CallPhongsHienTai.getPhongsHienTai(new IPhongsHienTai() {
            @Override
            public void onSuccess(List<PhongHienTai> response) {
                phongHienTais = response;
                for (PhongHienTai phong: response) {
                    if(phong.getIdChiTietPhieuThue() != null)
                        phongDangThue.add(phong.getMaPhong());
                }
                setDataMaPhong();
            }

            @Override
            public void onError(Throwable t) {
                Toast.makeText(KhachThueActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("TAG-ERR", t.getMessage());
            }
        });
    }

    private void setDataMaPhong(){
        ArrayAdapter<String> mucGiaAdapter = new ArrayAdapter<>(KhachThueActivity.this, R.layout.list_item_dropdown, phongDangThue);
        autoTextMaPhong.setAdapter(mucGiaAdapter);
    }

    private void setKhachThueRecycler(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        rcKhachThue.setLayoutManager(layoutManager);
        KhachThueAdapter adapter = new KhachThueAdapter(this, khachThues);
        rcKhachThue.setAdapter(adapter);

        adapter.setOnButtonXoaClickListener(new KhachThueAdapter.OnButtonXoaClickListener() {
            @Override
            public void onClick(int position, String maPhong, String sdt) {

                CallTimKiemKhachHang.timKiemKhachHangBySdt(sdt, new ITimKiemKhacHang() {
                    @Override
                    public void onSuccess(KhachHang khachHangResponse) {
                        for (PhongHienTai phongHienTai: phongHienTais) {
                            if(maPhong.trim().equals(phongHienTai.getMaPhong().trim())){
                                idChiTietPhieuThue = phongHienTai.getIdChiTietPhieuThue();
                            }
                        }
                        idKhachThue = khachHangResponse.getIdKhachHang();
                        xoaKhachThueInChiTietPhieuThue();
                    }

                    @Override
                    public void onError(Throwable t) {
                        Common.onCreateMessageDialog(KhachThueActivity.this,
                                "Không tìm thấy khách hàng nào").show();
                    }
                });

            }
        });
        adapter.setOnItemClickListener(new KhachThueAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, String maPhong) {

            }
        });
    }

    private void themKhachHangToChiTietPhieuThue(KhachThueRequest khachThueRequest){
        CallThemKhachThueToChiTietPhieuThue.themKhachThueToChiTietPhieuThue(khachThueRequest, new IThemKhachThueToChiTietPhieuThue() {
            @Override
            public void onSuccess(ResultResponse response) {
                if(response.getCode() == 200){
                    Common.onCreateMessageDialog(KhachThueActivity.this,
                            response.getMessage()).show();
                }else{
                    Common.onCreateMessageDialog(KhachThueActivity.this,
                            response.getMessage()).show();
                }
                getKhachThueHienTai();
            }

            @Override
            public void onError(Throwable t) {
                Log.e("TAG-ERR", t.getMessage());
                Common.onCreateMessageDialog(KhachThueActivity.this,
                        "Lỗi thêm khách lưu trú").show();
            }
        });
    }

    private void xoaKhachThueInChiTietPhieuThue(){
        CallXoaKhachThueTrongChiTietPhieuThue.xoaKhachThueTrongChiTietPhieuThue(idChiTietPhieuThue, idKhachThue, new IXoaKhachThueTrongChiTietPhieuThue() {
            @Override
            public void onSuccess(ResultResponse response) {
                if(response.getCode() == 200){
                    Common.onCreateMessageDialog(KhachThueActivity.this,
                            response.getMessage()).show();
                }else{
                    Common.onCreateMessageDialog(KhachThueActivity.this,
                            response.getMessage()).show();
                }
                getKhachThueHienTai();
            }

            @Override
            public void onError(Throwable t) {
                Common.onCreateMessageDialog(KhachThueActivity.this,
                        "Lỗi thêm khách lưu trú").show();
            }
        });
    }
}