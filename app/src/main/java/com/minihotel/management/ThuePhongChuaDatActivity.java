package com.minihotel.management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.minihotel.management.adapter.PhongHienTaiAdapter;
import com.minihotel.management.dto.ChiTietRequest;
import com.minihotel.management.dto.ResultResponse;
import com.minihotel.management.managers.calls.CallKiemTraPhongThue;
import com.minihotel.management.managers.calls.CallPhongsHienTai;
import com.minihotel.management.managers.calls.CallThongTinHangPhongTheoThoiGian;
import com.minihotel.management.managers.interfaces.IKiemTraPhongThue;
import com.minihotel.management.managers.interfaces.IPhongsHienTai;
import com.minihotel.management.managers.interfaces.IThongTinHangPhongTheoThoiGian;
import com.minihotel.management.model.PhongChon;
import com.minihotel.management.model.PhongHienTai;
import com.minihotel.management.model.ThongTinHangPhong;
import com.minihotel.management.utils.Common;
import com.minihotel.management.utils.Utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ThuePhongChuaDatActivity extends AppCompatActivity {
    private List<PhongHienTai> phongHienTais;
    private RecyclerView rcPhongHienTai;
    private LocalDate ngayDen = Common.getCurrentDate();
    private LocalDate ngayDi;
    private Button btnKiemTra;
    private TextView txtHangPhong;
    private List<ThongTinHangPhong> thongTinHangPhongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thue_phong_chua_dat);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ngayDi = LocalDate.parse(getIntent().getStringExtra("ngayDi"));
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        Utils.chiTietRequests.clear();
        getPhongsHienTai();
        getThongTinHangPhongThoiGian();
    }
    private void initViews() {
        rcPhongHienTai = findViewById(R.id.rcPhongHienTai);
        btnKiemTra = findViewById(R.id.btnKiemTra);
        txtHangPhong = findViewById(R.id.txtHangPhong);
    }

    private void setEvents() {
        btnKiemTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThuePhongChuaDatActivity.this, KhachHangDaiDienChuaDatActivity.class);
                intent.putExtra("ngayDi", Common.fommatDateRequest(ngayDi));
                startActivity(intent);
            }
        });
    }

    private void getThongTinHangPhongThoiGian(){
        CallThongTinHangPhongTheoThoiGian.getThongTinHangPhongTheoThoiGian(Common.fommatDateRequest(ngayDen),
                Common.fommatDateRequest(ngayDi), new IThongTinHangPhongTheoThoiGian() {
            @Override
            public void onSuccess(List<ThongTinHangPhong> responses) {
                thongTinHangPhongs = responses;
                showThongTinHangPhong();
            }

            @Override
            public void onError(Throwable t) {
                Log.d("TAG-ERR", t.getMessage());
            }
        });
    }

    private void showThongTinHangPhong() {
        txtHangPhong.setText("");
        for (ThongTinHangPhong hangPhong:thongTinHangPhongs) {
            txtHangPhong.append(hangPhong.getTenHangPhong() + ": còn trống " + hangPhong.getSoLuongTrong() + "\n");
        }
    }

    private void giamSoLuongPhongTrong(int idHangPhong){
        for (ThongTinHangPhong hangPhong: thongTinHangPhongs) {
            if(hangPhong.getIdHangPhong() == idHangPhong){
                hangPhong.setSoLuongTrong(hangPhong.getSoLuongTrong() - 1);
            }
        }
    }

    private void tangSoLuongPhongTrong(int idHangPhong){
        for (ThongTinHangPhong hangPhong: thongTinHangPhongs) {
            if(hangPhong.getIdHangPhong() == idHangPhong){
                hangPhong.setSoLuongTrong(hangPhong.getSoLuongTrong() + 1);
            }
        }
    }

    private boolean kiemTraSoLuongPhongTrong(int idHangPhong){
        for (ThongTinHangPhong hangPhong: thongTinHangPhongs) {
            if(hangPhong.getIdHangPhong() == idHangPhong){
                return hangPhong.getSoLuongTrong() > 0 ? true : false;
            }
        }
        return false;
    }

    public void getPhongsHienTai(){
        CallPhongsHienTai.getPhongsHienTai(new IPhongsHienTai() {
            @Override
            public void onSuccess(List<PhongHienTai> response) {
                phongHienTais = response;
                setPhongHienTaisRecycler();
            }

            @Override
            public void onError(Throwable t) {
                Toast.makeText(ThuePhongChuaDatActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("TAG-ERR", t.getMessage());
            }
        });
    }

    public boolean kiemTraChiTieRequest(String maPhong){
        for (ChiTietRequest chiTietRequest: Utils.chiTietRequests
             ) {
            if(chiTietRequest.getMaPhong().equals(maPhong)){
                return false;
            }
        }
        return true;
    };

    public void removeChiTietRequest(String maPhong){
        for (ChiTietRequest chiTietRequest: Utils.chiTietRequests
        ) {
            if(chiTietRequest.getMaPhong().equals(maPhong)){
                Utils.chiTietRequests.remove(chiTietRequest);
                break;
            }
        }
    }

    private void setPhongHienTaisRecycler(){
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        rcPhongHienTai.setLayoutManager(layoutManager);
        PhongHienTaiAdapter adapter = new PhongHienTaiAdapter(this, phongHienTais);
        rcPhongHienTai.setAdapter(adapter);
        adapter.setOnItemClickListener(new PhongHienTaiAdapter.OnItemClickListener() {
            @Override
            public void onClick(String maPhong, int idHangPhong, Long giaPhong, Integer idChiTietPhieuThue, String trangThai, View view, LinearLayout layout) {
                if(!trangThai.equals("Sạch sẽ")){
                    Common.onCreateMessageDialog(ThuePhongChuaDatActivity.this,
                            "Trạng thái phòng không sẵn sàng để thuê!").show();
                    return;
                }

                if(idChiTietPhieuThue == null) {
                    ChiTietRequest chiTietRequest = new ChiTietRequest(maPhong, idHangPhong, giaPhong, Common.fommatDateRequest(ngayDen),
                            Common.fommatDateRequest(ngayDi));
                    if(kiemTraChiTieRequest(maPhong)){
                        if(!kiemTraSoLuongPhongTrong(idHangPhong)){
                            Common.onCreateMessageDialog(ThuePhongChuaDatActivity.this,
                                    "Số lượng hạng phòng không đủ!").show();
                            return;
                        }
                        Utils.chiTietRequests.add(chiTietRequest);
                        //Giảm số phòng trống xuống
                        giamSoLuongPhongTrong(idHangPhong);
                        layout.setBackgroundResource(R.color.bg_green);
                    }else{
                        removeChiTietRequest(maPhong);
                        //Tăng số lượng phòng trống
                        tangSoLuongPhongTrong(idHangPhong);
                        layout.setBackgroundResource(R.color.white);
                    }
                    showThongTinHangPhong();
                    Toast.makeText(ThuePhongChuaDatActivity.this, "size " + Utils.chiTietRequests.size(), Toast.LENGTH_SHORT).show();
                }else{
                    Common.onCreateMessageDialog(ThuePhongChuaDatActivity.this,
                            "Phòng này đang được thuê").show();
                }
            }
        });
    }


}