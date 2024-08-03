package com.minihotel.management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.minihotel.management.adapter.ChiTietPhuThuAdapter;
import com.minihotel.management.adapter.ChiTietPhuThuTraPhongAdapter;
import com.minihotel.management.adapter.ChiTietSuDungDichVuAdapter;
import com.minihotel.management.adapter.ChiTietSuDungDichVuTraPhongAdapter;
import com.minihotel.management.dto.ChiTietPhuThuRequest;
import com.minihotel.management.dto.ChiTietSuDungDichVuRequest;
import com.minihotel.management.managers.calls.CallChiTietPhieuThueById;
import com.minihotel.management.managers.calls.CallChiTietPhuThuByChiTietPhieuThue;
import com.minihotel.management.managers.calls.CallChiTietSuDungDichVuByChiTietPhieuThue;
import com.minihotel.management.managers.calls.CallPhieuDatById;
import com.minihotel.management.managers.interfaces.IChiTietPhieuThueById;
import com.minihotel.management.managers.interfaces.IChiTietPhuThuByChiTietPhieuThue;
import com.minihotel.management.managers.interfaces.IChiTietSuDungDichVuByChiTietPhieuThue;
import com.minihotel.management.managers.interfaces.IPhieuDatById;
import com.minihotel.management.model.ChiTietPhieuThue;
import com.minihotel.management.model.ChiTietPhuThu;
import com.minihotel.management.model.ChiTietSuDungDichVu;
import com.minihotel.management.model.DichVu;
import com.minihotel.management.model.PhieuDat;
import com.minihotel.management.model.PhuThu;
import com.minihotel.management.utils.Common;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.List;

public class TraPhongActivity extends AppCompatActivity {
    private Integer idChiTietPhieuThue;
    private ChiTietPhieuThue chiTietPhieuThue;
    private TextView txtMaPhong, txtTenHangPhong, txtSoNgayThue, txtDonGia, txtTongTienPhong, txtTongTien, txtKhachCanTra;
    private TextInputEditText edtGiamGia;
    private TextView txtNgayDen, txtNgayDi;
    private RecyclerView rcChiTietSuDungDichVu, rcChiTietPhuThu;
    private Button btnHoanThanh;
    private List<ChiTietSuDungDichVu> chiTietSuDungDichVus;
    private List<ChiTietPhuThu> chiTietPhuThus;
    private long tienKhachTra = 0;
    private long tongTien = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tra_phong);

        idChiTietPhieuThue = getIntent().getIntExtra("idChiTietPhieuThue", 0);
        initViews();
        setEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getChiTietPhieuThueById();
        getChiTietSuDungDichVuByChiTietPhieuThue();
        getChiTietPhuThuByChiTietPhieuThue();
    }

    private void getChiTietPhieuThueById() {
        CallChiTietPhieuThueById.getChiTietPhieuThueById(idChiTietPhieuThue, new IChiTietPhieuThueById() {
            @Override
            public void onSuccess(ChiTietPhieuThue response) {
                chiTietPhieuThue = response;
                showData();
                showDataTongTien();

            }

            @Override
            public void onError(Throwable t) {
                Log.d("TAG-ERR", t.getMessage());
            }
        });
    }

    @SuppressLint("NewApi")
    private void showData() {
        txtMaPhong.setText("Mã phòng: " + chiTietPhieuThue.getMaPhong());
        txtTenHangPhong.setText(chiTietPhieuThue.getTenHangPhong());

        LocalDate ngayDen = LocalDate.parse(chiTietPhieuThue.getNgayDen());
        LocalDate ngayDi = LocalDate.parse(chiTietPhieuThue.getNgayDi());
        long soNgayThue = Common.calculateBetweenDate(ngayDen, ngayDi);
        txtSoNgayThue.setText("Số ngày thuê: " + soNgayThue);
        txtDonGia.setText("Đơn giá: " + Common.convertCurrencyVietnamese(chiTietPhieuThue.getDonGia()) + " VNĐ");
        long tongTien = chiTietPhieuThue.getDonGia() * soNgayThue;
        txtTongTienPhong.setText("Tổng tiền phòng: " + Common.convertCurrencyVietnamese(tongTien) + " VNĐ");

        txtNgayDen.setText("Ngày nhận phòng: " + Common.fommatDateShow(ngayDen));
        txtNgayDi.setText("Ngày trả phòng: "+Common.fommatDateShow(ngayDi));
    }

    @SuppressLint("NewApi")
    private long tinhTongTienPhong(){
        LocalDate ngayDen = LocalDate.parse(chiTietPhieuThue.getNgayDen());
        LocalDate ngayDi = LocalDate.parse(chiTietPhieuThue.getNgayDi());
        long soNgayThue = Common.calculateBetweenDate(ngayDen, ngayDi);
        return chiTietPhieuThue.getDonGia() * soNgayThue;
    }

    private void getChiTietSuDungDichVuByChiTietPhieuThue(){
        CallChiTietSuDungDichVuByChiTietPhieuThue.getChiTietSuDungDichVuByChiTietPhieuThue(idChiTietPhieuThue, new IChiTietSuDungDichVuByChiTietPhieuThue() {
            @Override
            public void onSuccess(List<ChiTietSuDungDichVu> responses) {
                chiTietSuDungDichVus = responses;
                setChiTietSuDungDichVuRecycler();
                showDataTongTien();
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


    private void getChiTietPhuThuByChiTietPhieuThue(){
        CallChiTietPhuThuByChiTietPhieuThue.getChiTietPhuThuByChiTietPhieuThue(idChiTietPhieuThue, new IChiTietPhuThuByChiTietPhieuThue() {
            @Override
            public void onSuccess(List<ChiTietPhuThu> responses) {
                chiTietPhuThus = responses;
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

    public void showDataTongTien(){
        long tongTienDichVu = 0;
        if(chiTietSuDungDichVus != null) {
            for (ChiTietSuDungDichVu chiTietSuDungDichVu : chiTietSuDungDichVus) {
                tongTienDichVu += (chiTietSuDungDichVu.getDonGia() * chiTietSuDungDichVu.getSoLuong());
            }
        }
        long tongTienPhuThu = 0;
        if(chiTietPhuThus != null) {
            for (ChiTietPhuThu chiTietPhuThu : chiTietPhuThus) {
                tongTienPhuThu += (chiTietPhuThu.getDonGia() * chiTietPhuThu.getSoLuong());
            }
        }
        if(chiTietPhieuThue != null) {
            tongTien = tinhTongTienPhong() + tongTienDichVu + tongTienPhuThu;
            txtTongTien.setText(Common.convertCurrencyVietnamese(tongTien));
            Log.d("AAA", "Tong tien: " + tongTien);
        }
        tienKhachTra = tongTien;
        txtKhachCanTra.setText(Common.convertCurrencyVietnamese(tienKhachTra));
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
                        Toast.makeText(TraPhongActivity.this, "Phần trăm giảm giá chưa chính xác", Toast.LENGTH_SHORT).show();
                        phanTramGiam = 0;
                    }
                    tienKhachTra = tongTien - ((tongTien * phanTramGiam) / 100);
                    txtKhachCanTra.setText(Common.convertCurrencyVietnamese(tienKhachTra));
                }catch (Exception ex){
                    Toast.makeText(TraPhongActivity.this, "Phần trăm giảm giá chưa chính xác", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initViews() {
        txtMaPhong = findViewById(R.id.txtMaPhong);
        txtTenHangPhong = findViewById(R.id.txtTenHangPhong);
        txtSoNgayThue = findViewById(R.id.txtSoNgayThue);
        txtDonGia = findViewById(R.id.txtDonGia);
        txtTongTienPhong = findViewById(R.id.txtTongTienPhong);
        txtNgayDen = findViewById(R.id.txtNgayDen);
        txtNgayDi = findViewById(R.id.txtNgayDi);
        rcChiTietSuDungDichVu = findViewById(R.id.rcChiTietSuDungDichVu);
        rcChiTietPhuThu = findViewById(R.id.rcChiTietPhuThu);
        txtTongTien = findViewById(R.id.txtTongTien);
        txtKhachCanTra = findViewById(R.id.txtKhachCanTra);
        btnHoanThanh = findViewById(R.id.btnHoanThanh);
        edtGiamGia = findViewById(R.id.edtGiamGia);
    }
}