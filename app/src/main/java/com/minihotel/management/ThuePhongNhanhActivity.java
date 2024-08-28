package com.minihotel.management;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.minihotel.management.dto.ChiTietRequest;
import com.minihotel.management.dto.KhachHangRequest;
import com.minihotel.management.dto.PhieuThuePhongRequest;
import com.minihotel.management.dto.ResultResponse;
import com.minihotel.management.managers.calls.CallPhieuDatById;
import com.minihotel.management.managers.calls.CallThemKhachHang;
import com.minihotel.management.managers.calls.CallThuePhongKhachSan;
import com.minihotel.management.managers.calls.CallTimKiemKhachHang;
import com.minihotel.management.managers.interfaces.IPhieuDatById;
import com.minihotel.management.managers.interfaces.IThemKhachHang;
import com.minihotel.management.managers.interfaces.IThuePhongKhachSan;
import com.minihotel.management.managers.interfaces.ITimKiemKhacHang;
import com.minihotel.management.model.KhachHang;
import com.minihotel.management.model.PhieuDat;
import com.minihotel.management.utils.Common;
import com.minihotel.management.utils.Utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ThuePhongNhanhActivity extends AppCompatActivity {
    private TextInputEditText edtTimKiem;
    private TextInputEditText edtCccd, edtHoTen, edtSdt, edtDiaChi, edtEmail;
    private Button btnTimKiem, btnThemMoi, btnXacNhan;
    private KhachHang khachHang;
    private TextView txtTongTien, txtDonGia, txtSoNgayThue;
    private PhieuThuePhongRequest phieuThuePhongRequest = new PhieuThuePhongRequest();
    private LocalDate ngayDi;
    private LocalDate ngayDen = Common.getCurrentDate();
    private long tongTien = 0;
    private long donGia = 0;
    private int idHangPhong = 0;
    private String maPhong, ngayDiString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thue_phong_nhanh);

        maPhong = getIntent().getStringExtra("maPhong");
        idHangPhong = getIntent().getIntExtra("idHangPhong", 0);
        donGia = getIntent().getLongExtra("donGia", 0);
        ngayDiString = getIntent().getStringExtra("ngayDi");

        Log.d("AAA", maPhong + " " + idHangPhong+ " " + donGia + " " + ngayDiString);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ngayDi = LocalDate.parse(ngayDiString);
        }
        initViews();
        setEvents();
        showData();
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

    private void showData() {
        tongTien = Common.calculateBetweenDate(ngayDen, ngayDi) * donGia;
        txtTongTien.setText(Common.convertCurrencyVietnamese(tongTien));
        txtDonGia.setText(Common.convertCurrencyVietnamese(donGia));
        txtSoNgayThue.setText(Common.calculateBetweenDate(ngayDen, ngayDi) + " ngày");
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
                Common.onCreateMessageDialog(ThuePhongNhanhActivity.this,
                        "Không tìm thấy khách hàng nào").show();
            }
        });
    }

    public void thuePhongKhachSan(PhieuThuePhongRequest request){
        CallThuePhongKhachSan.thuePhongKhachSan(request, new IThuePhongKhachSan() {
            @Override
            public void onSuccess(ResultResponse resultResponse) {
                if(resultResponse.getCode() == 200){
                    Common.onCreateMessageDialog(ThuePhongNhanhActivity.this,
                            resultResponse.getMessage()).show();
//                    startActivity(new Intent(KhachHangDaiDienActivity.this, SoDoActivity.class));
                }
            }

            @Override
            public void onError(Throwable t) {
                Log.d("TAG-ERR", t.getMessage());
                Common.onCreateMessageDialog(ThuePhongNhanhActivity.this,
                        "Lỗi thuê phòng khách sạn. Vui lòng thử lại sau!").show();
            }
        });
    }

    private void setEvents() {
        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtTimKiem.getText().toString().trim().equals("")){
                    Common.onCreateMessageDialog(ThuePhongNhanhActivity.this,
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
                    Common.onCreateMessageDialog(ThuePhongNhanhActivity.this,
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
                            Common.onCreateMessageDialog(ThuePhongNhanhActivity.this,
                                    "Thêm khách hàng thành công!").show();
                        }

                        @Override
                        public void onError(Throwable t) {
                            Log.d("TAG-ERR", t.getMessage());
                            Common.onCreateMessageDialog(ThuePhongNhanhActivity.this,
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
                    Common.onCreateMessageDialog(ThuePhongNhanhActivity.this,
                            "Vui lòng chọn khách hàng.").show();
                    return;
                }else{
                    phieuThuePhongRequest.setIdKhachHang(khachHang.getIdKhachHang());
                }
                phieuThuePhongRequest.setIdPhieuDat(null);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    phieuThuePhongRequest.setNgayDen(Common.fommatDateRequest(ngayDen));
                    phieuThuePhongRequest.setNgayDi(Common.fommatDateRequest(ngayDi));
                }
                phieuThuePhongRequest.setNgayTao(Common.fommatDateRequest(Common.getCurrentDate()));
                phieuThuePhongRequest.setIdNhanVien(Utils.idNhanVien);

                List<ChiTietRequest> chiTietRequestList = new ArrayList<>();
                ChiTietRequest chiTietRequest = new ChiTietRequest(maPhong, idHangPhong, donGia, Common.fommatDateRequest(ngayDen), ngayDiString);
                chiTietRequestList.add(chiTietRequest);
                phieuThuePhongRequest.setChiTietRequests(chiTietRequestList);

                Log.d("AAA", phieuThuePhongRequest.toString());
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
    }

    private void initViews() {
        edtTimKiem = findViewById(R.id.edtSdtTimKiem);
        btnTimKiem = findViewById(R.id.btnTimKiem);//099876543
        edtCccd = findViewById(R.id.edtCccd);
        edtHoTen = findViewById(R.id.edtHoTen);
        edtSdt = findViewById(R.id.edtSdt);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtEmail = findViewById(R.id.edtEmail);
        txtTongTien = findViewById(R.id.txtTongTien);
        txtDonGia = findViewById(R.id.txtDonGia);
        txtSoNgayThue = findViewById(R.id.txtSoNgayThue);
        btnThemMoi = findViewById(R.id.btnThemMoi);
        btnXacNhan = findViewById(R.id.btnXacNhan);
    }
}