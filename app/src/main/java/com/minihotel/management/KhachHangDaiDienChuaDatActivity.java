package com.minihotel.management;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.minihotel.management.dto.ChiTietRequest;
import com.minihotel.management.dto.KhachHangRequest;
import com.minihotel.management.dto.PhieuThuePhongRequest;
import com.minihotel.management.dto.ResultResponse;
import com.minihotel.management.managers.calls.CallThemKhachHang;
import com.minihotel.management.managers.calls.CallThuePhongKhachSan;
import com.minihotel.management.managers.calls.CallTimKiemKhachHang;
import com.minihotel.management.managers.interfaces.IThemKhachHang;
import com.minihotel.management.managers.interfaces.IThuePhongKhachSan;
import com.minihotel.management.managers.interfaces.ITimKiemKhacHang;
import com.minihotel.management.model.KhachHang;
import com.minihotel.management.model.PhieuDat;
import com.minihotel.management.utils.Common;
import com.minihotel.management.utils.Utils;

import java.time.LocalDate;

public class KhachHangDaiDienChuaDatActivity extends AppCompatActivity {
    private TextInputEditText edtTimKiem;
    private TextInputEditText edtCccd, edtHoTen, edtSdt, edtDiaChi, edtEmail;
    private Button btnTimKiem, btnThemMoi, btnXacNhan;
    private KhachHang khachHang;
    private TextView txtDaiDien, txtTongTien;
    private PhieuThuePhongRequest phieuThuePhongRequest = new PhieuThuePhongRequest();
    private LocalDate ngayDi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang_dai_dien_chua_dat);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ngayDi = LocalDate.parse(getIntent().getStringExtra("ngayDi"));
        }
        initViews();
        setEvents();
        showDataTongTien();
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

    private void showDataTongTien(){
        long tongTien = 0;
        for (ChiTietRequest chiTietRequest: Utils.chiTietRequests) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                long soNgayThue = Common.calculateBetweenDate(LocalDate.parse(chiTietRequest.getNgayDen()),
                        LocalDate.parse(chiTietRequest.getNgayDi()));
                tongTien += chiTietRequest.getDonGia() * soNgayThue;
            }
        }
        txtTongTien.setText(Common.convertCurrencyVietnamese(tongTien));
    }

    public void timKiemKhachHangTheoSdt(String sdt){
        CallTimKiemKhachHang.timKiemKhachHangBySdt(sdt, new ITimKiemKhacHang() {
            @Override
            public void onSuccess(KhachHang khachHangResponse) {
                khachHang = khachHangResponse;
                showDataKhachHang();
            }

            @Override
            public void onError(Throwable t) {
                Common.onCreateMessageDialog(KhachHangDaiDienChuaDatActivity.this,
                        "Không tìm thấy khách hàng nào").show();
            }
        });
    }

    public void thuePhongKhachSan(PhieuThuePhongRequest request){
        CallThuePhongKhachSan.thuePhongKhachSan(request, new IThuePhongKhachSan() {
            @Override
            public void onSuccess(ResultResponse resultResponse) {
                if(resultResponse.getCode() == 200){
                    Common.onCreateMessageDialog(KhachHangDaiDienChuaDatActivity.this,
                            resultResponse.getMessage()).show();
                    Utils.chiTietRequests.clear();
//                    startActivity(new Intent(KhachHangDaiDienActivity.this, SoDoActivity.class));
                }
            }

            @Override
            public void onError(Throwable t) {
                Log.d("TAG-ERR", t.getMessage());
                Common.onCreateMessageDialog(KhachHangDaiDienChuaDatActivity.this,
                        "Lỗi thuê phòng khách sạn. Vui lòng thử lại sau!").show();
            }
        });
    }

    private void setEvents() {
        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtTimKiem.getText().toString().trim().equals("")){
                    Common.onCreateMessageDialog(KhachHangDaiDienChuaDatActivity.this,
                            "Vui lòng nhập số điện thoại.").show();
                }
                String sdt = edtTimKiem.getText().toString().trim();
                timKiemKhachHangTheoSdt(sdt);
            }
        });

        btnThemMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtCccd.getText().toString().trim().equals("") ||
                        edtHoTen.getText().toString().trim().equals("") ||
                        edtEmail.getText().toString().trim().equals("") ||
                        edtSdt.getText().toString().trim().equals("")){
                    Common.onCreateMessageDialog(KhachHangDaiDienChuaDatActivity.this,
                            "Vui lòng nhập đầy đủ thông tin!").show();
                }else {
                    KhachHangRequest khachHangRequest = new KhachHangRequest();
                    khachHangRequest.setCmnd(edtCccd.getText().toString().trim());
                    khachHangRequest.setHoTen(edtHoTen.getText().toString().trim());
                    khachHangRequest.setEmail(edtEmail.getText().toString().trim());
                    khachHangRequest.setSdt(edtSdt.getText().toString().trim());
                    if(!edtDiaChi.getText().toString().trim().equals("")) {
                        khachHangRequest.setDiaChi(edtDiaChi.getText().toString().trim());
                    }
                    CallThemKhachHang.themKhachHang(khachHangRequest, new IThemKhachHang() {
                        @Override
                        public void onSuccess(KhachHang response) {
                            khachHang = response;
                            Common.onCreateMessageDialog(KhachHangDaiDienChuaDatActivity.this,
                                    "Thêm khách hàng thành công!").show();
                        }

                        @Override
                        public void onError(Throwable t) {
                            Log.d("TAG-ERR", t.getMessage());
                            Common.onCreateMessageDialog(KhachHangDaiDienChuaDatActivity.this,
                                    "Lỗi thêm khách hàng, vui lòng thử lại!").show();
                        }
                    });
                }
            }
        });

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(khachHang == null){
                    Common.onCreateMessageDialog(KhachHangDaiDienChuaDatActivity.this,
                            "Vui lòng chọn khách hàng.").show();
                    return;
                }else{
                    phieuThuePhongRequest.setIdKhachHang(khachHang.getIdKhachHang());
                }
                phieuThuePhongRequest.setIdPhieuDat(null);
                phieuThuePhongRequest.setNgayDen(Common.fommatDateRequest(Common.getCurrentDate()));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    phieuThuePhongRequest.setNgayDi(Common.fommatDateRequest(ngayDi));
                }
                phieuThuePhongRequest.setNgayTao(Common.fommatDateRequest(Common.getCurrentDate()));
                phieuThuePhongRequest.setIdNhanVien(Utils.idNhanVien);
                phieuThuePhongRequest.setChiTietRequests(Utils.chiTietRequests);

                thuePhongKhachSan(phieuThuePhongRequest);
            }
        });
    }

    private void showDataKhachHang(){
        edtCccd.setText(khachHang.getCmnd());
        edtHoTen.setText(khachHang.getHoTen());
        edtSdt.setText(khachHang.getSdt());
        edtDiaChi.setText(khachHang.getDiaChi());
        edtEmail.setText(khachHang.getEmail());

        txtDaiDien.setText(khachHang.getHoTen());
    }

    private void initViews() {
        edtTimKiem = findViewById(R.id.edtSdtTimKiem);
        btnTimKiem = findViewById(R.id.btnTimKiem);//099876543
        edtCccd = findViewById(R.id.edtCccd);
        edtHoTen = findViewById(R.id.edtHoTen);
        edtSdt = findViewById(R.id.edtSdt);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtEmail = findViewById(R.id.edtEmail);
        txtDaiDien = findViewById(R.id.txtDaiDien);
        txtTongTien = findViewById(R.id.txtTongTien);
        btnThemMoi = findViewById(R.id.btnThemMoi);
        btnXacNhan = findViewById(R.id.btnXacNhan);
    }
}