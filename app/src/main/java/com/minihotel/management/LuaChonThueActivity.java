package com.minihotel.management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.minihotel.management.adapter.PhieuDatKhachHangAdapter;
import com.minihotel.management.adapter.PhieuThueAdapter;
import com.minihotel.management.managers.calls.CallPhieuDatById;
import com.minihotel.management.managers.calls.CallPhieuDatKhachHang;
import com.minihotel.management.managers.interfaces.IPhieuDatById;
import com.minihotel.management.managers.interfaces.IPhieuDatKhachHang;
import com.minihotel.management.model.PhieuDat;
import com.minihotel.management.utils.Common;
import com.minihotel.management.utils.Utils;

import java.time.LocalDate;
import java.util.List;

public class LuaChonThueActivity extends AppCompatActivity {
    private TextInputEditText edtCmnd;
    private Button btnChuaDatPhong, btnXacNhan;
    private List<PhieuDat> phieuDats;
    private RecyclerView rcPhieuDat;
    private LocalDate ngayDi = Common.getPlusDayCurrentDate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lua_chon_thue);

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

    private void setEvents() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtCmnd.getText().toString().trim().equals("")){
                    Common.onCreateMessageDialog(LuaChonThueActivity.this,
                            "Vui lòng nhập đầy đủ thông tin").show();
                }else{
                    String cmnd = edtCmnd.getText().toString();
                    getPhieuDatTheoCMND(cmnd);
                }
            }
        });

        btnChuaDatPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogChonThoiGian();
            }
        });
    }

    private void setViews() {
        edtCmnd = findViewById(R.id.edtCmnd);
        btnChuaDatPhong = findViewById(R.id.btnChuaDatPhong);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        rcPhieuDat = findViewById(R.id.rcPhieuDat);
    }

    /*private void getPhieuDatById(int idPhieuDat){
        CallPhieuDatById.getPhieuDatById(idPhieuDat, new IPhieuDatById() {
            @Override
            public void onSuccess(PhieuDat phieuDatResponse) {
                if(phieuDatResponse != null){
                    Intent intent = new Intent(LuaChonThueActivity.this, ChonHangPhongThueActivity.class);
                    intent.putExtra("idPhieuDat", idPhieuDat);
                    startActivity(intent);
                }else{
                    Common.onCreateMessageDialog(LuaChonThueActivity.this,
                            "Mã đặt phòng không chính xác. Vui lòng kiểm tra lại!").show();
                }
            }

            @Override
            public void onError(Throwable t) {
                Toast.makeText(LuaChonThueActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Common.onCreateMessageDialog(LuaChonThueActivity.this,
                        "Mã đặt phòng không chính xác. Vui lòng kiểm tra lại!").show();
            }
        });
    }*/

    public void getPhieuDatTheoCMND(String cmnd){
        CallPhieuDatKhachHang.getPhieuDatTheoCMND(cmnd, new IPhieuDatKhachHang() {
            @Override
            public void onSuccess(List<PhieuDat> responses) {
                phieuDats = responses;
                if(responses.size() <= 0){
                    Common.onCreateMessageDialog(LuaChonThueActivity.this,
                            "Không có phiếu đặt nào trong hôm nay!").show();
                }
                setPhieuDatRecycler();
            }

            @Override
            public void onError(Throwable t) {
                Log.d("TAG-ERR", t.getMessage());
                Common.onCreateMessageDialog(LuaChonThueActivity.this,
                        "Lỗi tìm phiếu đặt.Vui lòng kiểm tra lại!").show();
            }
        });
    }

    private void setPhieuDatRecycler(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        rcPhieuDat.setLayoutManager(layoutManager);
        PhieuDatKhachHangAdapter adapter = new PhieuDatKhachHangAdapter(this, phieuDats);
        rcPhieuDat.setAdapter(adapter);
        adapter.setOnItemClickListener(new PhieuDatKhachHangAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, int idPhieuDat) {
                Intent intent = new Intent(LuaChonThueActivity.this, ChonHangPhongThueActivity.class);
                intent.putExtra("idPhieuDat", idPhieuDat);
                startActivity(intent);
            }
        });
    }

    private void openDialogChonThoiGian(/*String maPhong, int idHangPhong, Long giaPhong*/){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_chon_thoi_gian);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windownAttributes);

        dialog.setCancelable(true);

        TextInputEditText edtNgayDen = dialog.findViewById(R.id.edtNgayDen);
        TextInputEditText edtNgayDi = dialog.findViewById(R.id.edtNgayDi);
        LocalDate ngayDen = Common.getCurrentDate();
        edtNgayDen.setText(Common.fommatDateShow(ngayDen));
        edtNgayDi.setText(Common.fommatDateShow(ngayDi));
        Button btnKiemTra = dialog.findViewById(R.id.btnKiemTra);
        edtNgayDi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate(edtNgayDi);
            }
        });
        btnKiemTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                kiemTraHangPhongThue(idHangPhong, maPhong, giaPhong, ngayDen, ngayDi);
                Intent intent = new Intent(LuaChonThueActivity.this, ThuePhongChuaDatActivity.class);
                intent.putExtra("ngayDi", Common.fommatDateRequest(ngayDi));
                startActivity(intent);
            }
        });

        dialog.show();
    }

    @SuppressLint("NewApi")
    public void pickDate(TextInputEditText edt){
        int ngay = ngayDi.getDayOfMonth();
        int thang = ngayDi.getMonthValue();
        int nam = ngayDi.getYear();
        DatePickerDialog datePickerDialog = new DatePickerDialog(LuaChonThueActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalDate chooseDate = LocalDate.of(year, month, dayOfMonth);
                    ngayDi = chooseDate;
                    edt.setText(Common.fommatDateShow(chooseDate));
                }
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }
}