package com.minihotel.management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.minihotel.management.managers.calls.CallNhanVienDangNhap;
import com.minihotel.management.managers.interfaces.INhanVienDangNhap;
import com.minihotel.management.model.NhanVien;
import com.minihotel.management.utils.Common;
import com.minihotel.management.utils.Utils;

public class DangNhapActivity extends AppCompatActivity {
    private TextInputEditText edtTenDangNhap, edtMatKhau;
    private Button btnDangNhap;
    private NhanVien nhanVien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        initViews();
        setEvents();
    }

    private void setEvents() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDangNhap = edtTenDangNhap.getText().toString().trim();
                String matKhau = edtMatKhau.getText().toString().trim();
                if(tenDangNhap.equals("") || matKhau.equals("")){
                    Common.onCreateMessageDialog(DangNhapActivity.this,
                            "Vui lòng nhập đầy đủ thông tin.").show();
                    return;
                }
                getNhanVienDangNhap(tenDangNhap, matKhau);
            }
        });
    }

    private void initViews() {
        edtTenDangNhap = findViewById(R.id.edtTenDangNhap);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
    }

    private void getNhanVienDangNhap(String tenDangNhap, String matKhau){
        CallNhanVienDangNhap.getNhanVienDangNhap(tenDangNhap, matKhau, new INhanVienDangNhap() {
            @Override
            public void onSuccess(NhanVien response) {
                nhanVien = response;
                if(response != null) {
                    Utils.idNhanVien = nhanVien.getIdNhanVien();
                    startActivity(new Intent(DangNhapActivity.this, MenuActivity.class));
                }else{
                    Common.onCreateMessageDialog(DangNhapActivity.this,
                            "Tên đăng nhập hoặc mật khẩu chưa chính xác").show();
                }
            }

            @Override
            public void onError(Throwable t) {
                Common.onCreateMessageDialog(DangNhapActivity.this,
                        "Tên đăng nhập hoặc mật khẩu chưa chính xác").show();
            }
        });
    }
}