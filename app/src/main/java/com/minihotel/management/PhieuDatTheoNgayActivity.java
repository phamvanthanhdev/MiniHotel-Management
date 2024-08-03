package com.minihotel.management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.minihotel.management.adapter.ChiTietSuDungDichVuAdapter;
import com.minihotel.management.adapter.PhieuDatTheoNgayAdapter;
import com.minihotel.management.dto.ChiTietSuDungDichVuRequest;
import com.minihotel.management.managers.calls.CallPhieuDatTheoNgay;
import com.minihotel.management.managers.interfaces.IPhieuDatTheoNgay;
import com.minihotel.management.model.PhieuDatTheoNgay;
import com.minihotel.management.utils.Common;
import com.minihotel.management.utils.Utils;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PhieuDatTheoNgayActivity extends AppCompatActivity {
    private TextInputEditText edtNgay;
    private RecyclerView rcPhieuDatTheoNgay;
    private Button btnTimKiem;
    private List<PhieuDatTheoNgay> phieuDatTheoNgays;
    private LocalDate ngay = Common.getCurrentDate();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phieu_dat_theo_ngay);

        initViews();
        edtNgay.setText(Common.fommatDateShow(ngay));
        setEvents();
    }

    private void setEvents() {
        edtNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate(edtNgay);
            }
        });

        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhieuDatTheoNgay();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPhieuDatTheoNgay();
    }

    private void initViews() {
        rcPhieuDatTheoNgay = findViewById(R.id.rcPhieuDatTheoNgay);
        edtNgay = findViewById(R.id.edtNgay);
        btnTimKiem = findViewById(R.id.btnTimKiem);
    }

    private void getPhieuDatTheoNgay(){
        CallPhieuDatTheoNgay.getPhieuDatTheoNgay(Common.fommatDateRequest(ngay), new IPhieuDatTheoNgay() {
            @Override
            public void onSuccess(List<PhieuDatTheoNgay> responses) {
                phieuDatTheoNgays = responses;
                setPhieuDatTheoNgayRecycler();
            }

            @Override
            public void onError(Throwable t) {
                Log.d("TAG-ERR", t.getMessage());
            }
        });
    }

    private void setPhieuDatTheoNgayRecycler(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        rcPhieuDatTheoNgay.setLayoutManager(layoutManager);
        PhieuDatTheoNgayAdapter adapter = new PhieuDatTheoNgayAdapter(this, phieuDatTheoNgays);
        rcPhieuDatTheoNgay.setAdapter(adapter);
        adapter.setOnItemClickListener(new PhieuDatTheoNgayAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, int idPhieuDat) {
                Intent intent = new Intent(PhieuDatTheoNgayActivity.this, ChonHangPhongThueActivity.class);
                intent.putExtra("idPhieuDat", idPhieuDat);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("NewApi")
    public void pickDate(TextInputEditText edt){
        Calendar calendar = Calendar.getInstance();
        int ngayHienTai = calendar.get(Calendar.DAY_OF_MONTH);
        int thangHienTai = calendar.get(Calendar.MONTH);
        int namHienTai = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(PhieuDatTheoNgayActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month, dayOfMonth);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    ngay = LocalDate.of(year, month + 1, dayOfMonth);
                    edtNgay.setText(Common.fommatDateShow(ngay));
                }
            }
        }, namHienTai, thangHienTai, ngayHienTai);
        datePickerDialog.show();
    }
}