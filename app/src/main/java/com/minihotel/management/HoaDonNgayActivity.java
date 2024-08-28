package com.minihotel.management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.minihotel.management.adapter.HoaDonNgayAdapter;
import com.minihotel.management.adapter.PhongHienTaiAdapter;
import com.minihotel.management.managers.calls.CallHoaDonNgayHienTai;
import com.minihotel.management.managers.calls.CallHoaDonTheoNgay;
import com.minihotel.management.managers.interfaces.IHoaDonNgayHienTai;
import com.minihotel.management.managers.interfaces.IHoaDonTheoNgay;
import com.minihotel.management.model.HoaDonNgay;
import com.minihotel.management.utils.Common;
import com.minihotel.management.utils.Utils;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class HoaDonNgayActivity extends AppCompatActivity {
    private RecyclerView rcHoaDonNgay;
    private TextInputEditText edtThoiGian;
    private List<HoaDonNgay> hoaDonNgays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_ngay);
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
        getHoaDonNgayHienTai();
    }

    private void initViews() {
        rcHoaDonNgay = findViewById(R.id.rcHoaDonNgay);
        edtThoiGian = findViewById(R.id.edtThoiGian);
        edtThoiGian.setText(Common.fommatDateShow(Common.getCurrentDate()));
    }

    private void setEvents() {
        edtThoiGian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate(edtThoiGian);
            }
        });
    }

    private void getHoaDonNgayHienTai() {
        CallHoaDonNgayHienTai.getHoaDonNgayHienTai(new IHoaDonNgayHienTai() {
            @Override
            public void onSuccess(List<HoaDonNgay> responses) {
                hoaDonNgays = responses;
                setHoaDonNgaysRecycler();
                if(responses.size() <= 0) {
                    Common.onCreateMessageDialog(HoaDonNgayActivity.this,
                            "Không có hóa đơn nào").show();
                }
            }

            @Override
            public void onError(Throwable t) {
                Log.d("TAG-ERR", t.getMessage());
            }
        });
    }

    private void setHoaDonNgaysRecycler(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        rcHoaDonNgay.setLayoutManager(layoutManager);
        HoaDonNgayAdapter adapter = new HoaDonNgayAdapter(this, hoaDonNgays);
        rcHoaDonNgay.setAdapter(adapter);
        adapter.setOnItemClickListener(new HoaDonNgayAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, String soHoaDon) {
                Toast.makeText(HoaDonNgayActivity.this, soHoaDon, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @SuppressLint("NewApi")
    public void pickDate(TextInputEditText edt){
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DAY_OF_MONTH);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(HoaDonNgayActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month, dayOfMonth);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalDate chooseDate = LocalDate.of(year, month + 1, dayOfMonth);
                    edt.setText(Common.fommatDateShow(chooseDate));
                    getHoaDonTheoNgay(Common.fommatDateRequest(chooseDate));
                }
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private void getHoaDonTheoNgay(String ngay) {
        CallHoaDonTheoNgay.getHoaDonTheoNgay(ngay, new IHoaDonTheoNgay() {
            @Override
            public void onSuccess(List<HoaDonNgay> responses) {
                hoaDonNgays = responses;
                setHoaDonNgaysRecycler();
                if(responses.size() <= 0) {
                    Common.onCreateMessageDialog(HoaDonNgayActivity.this,
                            "Không có hóa đơn nào").show();
                }
            }

            @Override
            public void onError(Throwable t) {
                Log.d("TAG-ERR", t.getMessage());
            }
        });
    }
}