package com.minihotel.management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.minihotel.management.adapter.ChiTietPhieuThueTraPhongAdapter;
import com.minihotel.management.adapter.ChiTietPhuThuTraPhongAdapter;
import com.minihotel.management.adapter.ChiTietSuDungDichVuTraPhongAdapter;
import com.minihotel.management.managers.calls.CallChiTietPhieuThueById;
import com.minihotel.management.managers.calls.CallChiTietPhieuThueByPhieuThue;
import com.minihotel.management.managers.calls.CallChiTietPhuThuByChiTietPhieuThue;
import com.minihotel.management.managers.calls.CallChiTietSuDungDichVuByChiTietPhieuThue;
import com.minihotel.management.managers.calls.CallPhieuThueById;
import com.minihotel.management.managers.interfaces.IChiTietPhieuThueById;
import com.minihotel.management.managers.interfaces.IChiTietPhieuThueByPhieuThue;
import com.minihotel.management.managers.interfaces.IChiTietPhuThuByChiTietPhieuThue;
import com.minihotel.management.managers.interfaces.IChiTietSuDungDichVuByChiTietPhieuThue;
import com.minihotel.management.managers.interfaces.IPhieuThueById;
import com.minihotel.management.model.ChiTietPhieuThue;
import com.minihotel.management.model.ChiTietPhuThu;
import com.minihotel.management.model.ChiTietSuDungDichVu;
import com.minihotel.management.model.PhieuThue;
import com.minihotel.management.utils.Common;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TraPhongKhachDoanThanhToanActivity extends AppCompatActivity {
    private ArrayList<Integer> idChiTietPhieuThuesDaChon;
    private int idphieuThue;
    private PhieuThue phieuThue;
    private List<ChiTietPhieuThue> chiTietPhieuThues = new ArrayList<>();
    private List<ChiTietPhuThu> chiTietPhuThus = new ArrayList<>();
    private List<ChiTietSuDungDichVu> chiTietSuDungDichVus = new ArrayList<>();
    private RecyclerView rcChiTietSuDungDichVu, rcChiTietPhuThu, rcChiTietPhieuThue;
    private long tienKhachTra = 0, tongTien = 0, tienTamUng = 0;
    private TextView txtTongTien, txtKhachCanTra, txtTamUng, txtMaPhieuThue, txtNgayDen, txtNgayDi, txtKhachHang, txtCmnd;
    private TextInputEditText edtGiamGia;
    private Button btnHoanThanh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tra_phong_khach_doan_thanh_toan);

        idChiTietPhieuThuesDaChon = getIntent().getIntegerArrayListExtra("idChiTietPhieuThuesDaChon");
        idphieuThue = getIntent().getIntExtra("idphieuThue", 0);

        initViews();
        setEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPhieuThueById(idphieuThue);
        getChiTietPhieuThueDaChon();
    }

    private void initViews() {
        rcChiTietPhieuThue = findViewById(R.id.rcChiTietPhieuThue);
        rcChiTietSuDungDichVu = findViewById(R.id.rcChiTietSuDungDichVu);
        rcChiTietPhuThu = findViewById(R.id.rcChiTietPhuThu);

        txtTongTien = findViewById(R.id.txtTongTien);
        txtKhachCanTra = findViewById(R.id.txtKhachCanTra);
        txtTamUng = findViewById(R.id.txtTamUng);
        edtGiamGia = findViewById(R.id.edtGiamGia);

        txtMaPhieuThue = findViewById(R.id.txtMaPhieuThue);
        txtNgayDen = findViewById(R.id.txtNgayDen);
        txtNgayDi = findViewById(R.id.txtNgayDi);
        txtKhachHang = findViewById(R.id.txtKhachHang);
        txtCmnd = findViewById(R.id.txtCmnd);

        btnHoanThanh = findViewById(R.id.btnHoanThanh);
    }

    private void setEvents() {
        btnHoanThanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        edtGiamGia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    s = "0";
                }
                try {
                    int phanTramGiam = Integer.parseInt(s.toString());
                    if(phanTramGiam < 0 || phanTramGiam > 100){
                        Toast.makeText(TraPhongKhachDoanThanhToanActivity.this, "Phần trăm giảm giá chưa chính xác", Toast.LENGTH_SHORT).show();
                        phanTramGiam = 0;
                    }
                    tienKhachTra = (tongTien - tienTamUng) - ((tongTien * phanTramGiam) / 100);
                    txtKhachCanTra.setText(Common.convertCurrencyVietnamese(tienKhachTra));
                }catch (Exception ex){
                    Toast.makeText(TraPhongKhachDoanThanhToanActivity.this, "Phần trăm giảm giá chưa chính xác", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void getPhieuThueById(int id){
        CallPhieuThueById.getPhieuThueById(id, new IPhieuThueById() {
            @Override
            public void onSuccess(PhieuThue response) {
                phieuThue = response;
                showDataPhieuThue();
                showDataTongTien();
            }

            @Override
            public void onError(Throwable t) {
                Common.onCreateMessageDialog(TraPhongKhachDoanThanhToanActivity.this,
                        "Không tìm thấy phiếu thuê!").show();
            }
        });
    }

    public void getChiTietPhieuThueDaChon(){
        for (int idChiTietPhieuThue:idChiTietPhieuThuesDaChon) {
            CallChiTietPhieuThueById.getChiTietPhieuThueById(idChiTietPhieuThue, new IChiTietPhieuThueById() {
                @Override
                public void onSuccess(ChiTietPhieuThue chiTietPhieuThue) {
                    chiTietPhieuThues.add(chiTietPhieuThue);
                    setChiTietPhieuThueTraPhongRecycler();
                    showDataTongTien();
                    getChiTietSuDungDichVuByChiTietPhieuThue(idChiTietPhieuThue);
                    getChiTietPhuThuByChiTietPhieuThue(idChiTietPhieuThue);
                }

                @Override
                public void onError(Throwable t) {
                    Log.e("TAG-ERR", t.getMessage());
                }
            });
        }
    }

    private void setChiTietPhieuThueTraPhongRecycler(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        rcChiTietPhieuThue.setLayoutManager(layoutManager);
        ChiTietPhieuThueTraPhongAdapter adapter = new ChiTietPhieuThueTraPhongAdapter(this, chiTietPhieuThues);
        rcChiTietPhieuThue.setAdapter(adapter);
    }

    private void getChiTietSuDungDichVuByChiTietPhieuThue(int idChiTietPhieuThue){
        CallChiTietSuDungDichVuByChiTietPhieuThue.getChiTietSuDungDichVuByChiTietPhieuThue(idChiTietPhieuThue, new IChiTietSuDungDichVuByChiTietPhieuThue() {
            @Override
            public void onSuccess(List<ChiTietSuDungDichVu> responses) {
                chiTietSuDungDichVus.addAll(responses);
                showDataTongTien();
                setChiTietSuDungDichVuRecycler();
            }

            @Override
            public void onError(Throwable t) {
                Log.e("TAG-ERR", t.getMessage());
            }
        });
    }

    private void setChiTietSuDungDichVuRecycler(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        rcChiTietSuDungDichVu.setLayoutManager(layoutManager);
        ChiTietSuDungDichVuTraPhongAdapter adapter = new ChiTietSuDungDichVuTraPhongAdapter(this, chiTietSuDungDichVus);
        rcChiTietSuDungDichVu.setAdapter(adapter);
    }

    private void getChiTietPhuThuByChiTietPhieuThue(int idChiTietPhieuThue){
        CallChiTietPhuThuByChiTietPhieuThue.getChiTietPhuThuByChiTietPhieuThue(idChiTietPhieuThue, new IChiTietPhuThuByChiTietPhieuThue() {
            @Override
            public void onSuccess(List<ChiTietPhuThu> responses) {
                chiTietPhuThus.addAll(responses);
                setChiTietPhuThuRecycler();
                showDataTongTien();
            }

            @Override
            public void onError(Throwable t) {
                Log.e("TAG-ERR", t.getMessage());
            }
        });
    }

    private void setChiTietPhuThuRecycler(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        rcChiTietPhuThu.setLayoutManager(layoutManager);
        ChiTietPhuThuTraPhongAdapter adapter = new ChiTietPhuThuTraPhongAdapter(this, chiTietPhuThus);
        rcChiTietPhuThu.setAdapter(adapter);
    }

    @SuppressLint("NewApi")
    private long tinhTongTienPhong(ChiTietPhieuThue chiTietPhieuThue){
        LocalDate ngayDen = LocalDate.parse(chiTietPhieuThue.getNgayDen());
        LocalDate ngayDi = LocalDate.parse(chiTietPhieuThue.getNgayDi());
        long soNgayThue = Common.calculateBetweenDate(ngayDen, ngayDi);
        return chiTietPhieuThue.getDonGia() * soNgayThue;
    }

    public void showDataTongTien(){
        long tongTienDichVu = 0;
        if(chiTietSuDungDichVus != null) {
            for (ChiTietSuDungDichVu chiTietSuDungDichVu : chiTietSuDungDichVus) {
                tongTienDichVu += (chiTietSuDungDichVu.getDonGia() * chiTietSuDungDichVu.getSoLuong());
                Log.d("AAA", "tien dich vu " + tongTienDichVu);
            }
        }
        long tongTienPhuThu = 0;
        if(chiTietPhuThus != null) {
            for (ChiTietPhuThu chiTietPhuThu : chiTietPhuThus) {
                tongTienPhuThu += (chiTietPhuThu.getDonGia() * chiTietPhuThu.getSoLuong());
                Log.d("AAA", "tien phu thu " + tongTienPhuThu);
            }
        }
        long tongTienThuePhong = 0;
        if(chiTietPhieuThues != null) {
            for(ChiTietPhieuThue chiTietPhieuThue: chiTietPhieuThues) {
                tongTienThuePhong += tinhTongTienPhong(chiTietPhieuThue);
                Log.d("AAA", "tien phong " + tongTienThuePhong);
            }
        }
        if(phieuThue != null){
            tienTamUng = phieuThue.getTienTamUng();
        }
        tongTien = tongTienThuePhong + tongTienDichVu + tongTienPhuThu;
        tienKhachTra = tongTien - tienTamUng;
        txtTongTien.setText(Common.convertCurrencyVietnamese(tienKhachTra));

        txtTongTien.setText(Common.convertCurrencyVietnamese(tongTien));
        txtKhachCanTra.setText(Common.convertCurrencyVietnamese(tienKhachTra));
        txtTamUng.setText(Common.convertCurrencyVietnamese(tienTamUng));
    }

    private void showDataPhieuThue() {
        txtMaPhieuThue.setText("Mã phiếu thuê: " + phieuThue.getIdPhieuThue());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate ngayDen = LocalDate.parse(phieuThue.getNgayDen());
            LocalDate ngayDi = LocalDate.parse(phieuThue.getNgayDi());
            txtNgayDen.setText("Ngày nhận phòng: " + Common.fommatDateShow(ngayDen));
            txtNgayDi.setText("Ngày trả phòng: " + Common.fommatDateShow(ngayDi));
        }
        txtKhachHang.setText("Khách hàng đại diện: " + phieuThue.getHoTenKhach());
        txtCmnd.setText("CMND/CCCD: " + phieuThue.getCmnd());
    }
}