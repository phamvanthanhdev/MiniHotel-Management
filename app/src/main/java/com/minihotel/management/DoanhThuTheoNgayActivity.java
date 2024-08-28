package com.minihotel.management;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.minihotel.management.adapter.DoanhThuNgayAdapter;
import com.minihotel.management.adapter.HoaDonNgayAdapter;
import com.minihotel.management.managers.calls.CallDoanhThuTheoNgay;
import com.minihotel.management.managers.interfaces.IDoanhThuTheoNgay;
import com.minihotel.management.model.ChiTietPhuThu;
import com.minihotel.management.model.ChiTietSuDungDichVu;
import com.minihotel.management.model.DoanhThuTheoNgay;
import com.minihotel.management.utils.Common;
import com.minihotel.management.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DoanhThuTheoNgayActivity extends AppCompatActivity {
    private TextInputEditText edtNgayBatDau, edtNgayKetThuc;
    private TextView txtDoanhThu;
    private RecyclerView rcDoanhThuNgay;
    private List<DoanhThuTheoNgay> doanhThuTheoNgays;
    private LocalDate ngayBatDau = Common.getPlusDayCurrentDate();
    private LocalDate ngayKetThuc = Common.getCurrentDate();
    private Button btnInBaoCao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanh_thu_theo_ngay);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ngayBatDau = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
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
        getDoanhThuTheoNgay();
    }

    private void setEvents() {
        edtNgayBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDateNgayBatDau(edtNgayBatDau);
            }
        });

        edtNgayKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDateNgayKetThuc(edtNgayKetThuc);
            }
        });

        btnInBaoCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doanhThuTheoNgays.size() <= 0){
                    Common.onCreateMessageDialog(DoanhThuTheoNgayActivity.this,
                            "Không có doanh thu để in báo cáo!").show();
                }else{
                    inBaoCaoPdf();
                }
            }
        });
    }

    private void initViews() {
        edtNgayBatDau = findViewById(R.id.edtNgayBatDau);
        edtNgayKetThuc = findViewById(R.id.edtNgayKetThuc);
        rcDoanhThuNgay = findViewById(R.id.rcDoanhThuNgay);
        txtDoanhThu = findViewById(R.id.txtDoanhThu);
        edtNgayBatDau.setText(Common.fommatDateShow(ngayBatDau));
        edtNgayKetThuc.setText(Common.fommatDateShow(ngayKetThuc));
        btnInBaoCao = findViewById(R.id.btnInBaoCao);
    }

    private void showDoanhThu(){
        long tongDoanhThu = 0;
        for (DoanhThuTheoNgay doanhThuTheoNgay: doanhThuTheoNgays) {
            tongDoanhThu += doanhThuTheoNgay.getTongTien();
        }
        txtDoanhThu.setText("Tổng doanh thu: " + Common.convertCurrencyVietnamese(tongDoanhThu));
    }

    private void getDoanhThuTheoNgay(){
        String ngayBatDauStr = Common.fommatDateRequest(ngayBatDau);
        String ngayKetThucStr = Common.fommatDateRequest(ngayKetThuc);
        CallDoanhThuTheoNgay.getDoanhThuTheoNgay(ngayBatDauStr, ngayKetThucStr, new IDoanhThuTheoNgay() {
            @Override
            public void onSuccess(List<DoanhThuTheoNgay> responses) {
                doanhThuTheoNgays = responses;
                if(responses.size() <= 0){
                    Common.onCreateMessageDialog(DoanhThuTheoNgayActivity.this,
                            "Không có doanh thu nào." ).show();
                }
                setDoanhThuNgaysRecycler();
                showDoanhThu();
            }

            @Override
            public void onError(Throwable t) {
                Common.onCreateMessageDialog(DoanhThuTheoNgayActivity.this,
                        "Lỗi trong khi lấy doanh thu theo ngày. " + t.getMessage()).show();
            }
        });
    }

    private void setDoanhThuNgaysRecycler(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        rcDoanhThuNgay.setLayoutManager(layoutManager);
        DoanhThuNgayAdapter adapter = new DoanhThuNgayAdapter(this, doanhThuTheoNgays);
        rcDoanhThuNgay.setAdapter(adapter);
        adapter.setOnItemClickListener(new DoanhThuNgayAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, String ngayTao) {

            }
        });

    }

    @SuppressLint("NewApi")
    public void pickDateNgayBatDau(TextInputEditText edt){
        int ngay = ngayBatDau.getDayOfMonth();
        int thang = ngayBatDau.getMonthValue();
        int nam = ngayBatDau.getYear();
        DatePickerDialog datePickerDialog = new DatePickerDialog(DoanhThuTheoNgayActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalDate chooseDate = LocalDate.of(year, month, dayOfMonth);
                    edt.setText(Common.fommatDateShow(chooseDate));
                    ngayBatDau = chooseDate;
                    getDoanhThuTheoNgay();
                }
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    @SuppressLint("NewApi")
    public void pickDateNgayKetThuc(TextInputEditText edt){
        int ngay = ngayKetThuc.getDayOfMonth();
        int thang = ngayKetThuc.getMonthValue();
        int nam = ngayKetThuc.getYear();
        DatePickerDialog datePickerDialog = new DatePickerDialog(DoanhThuTheoNgayActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalDate chooseDate = LocalDate.of(year, month, dayOfMonth);
                    edt.setText(Common.fommatDateShow(chooseDate));
                    ngayKetThuc = chooseDate;
                    getDoanhThuTheoNgay();
                }
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }


    private void inBaoCaoPdf(){
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1080, 1920, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        paint.setColor(Color.BLACK);
        paint.setTextSize(50);

        String title = "BÁO CÁO DOANH THU THEO NGÀY";
        canvas.drawText(title, 180, 120, paint);

        paint.setTextSize(32);
        int startY = 200;
        int lineHeight = 60;


        // Thêm thông tin ngày tạo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas.drawText("Thời gian: từ " + Common.fommatDateShow(ngayBatDau) + " đến " + Common.fommatDateShow(ngayKetThuc),
                    210, startY, paint);
            startY += lineHeight;
        }

        // Tiêu đề bảng sản phẩm
        paint.setTextSize(36);
        startY += lineHeight;
        canvas.drawText("Ngày", 200, startY, paint);
        canvas.drawText("Tổng tiền", 720, startY, paint);

        // Dữ liệu sản phẩm
        paint.setTextSize(30);
        int yOffset = startY + lineHeight;


        for (DoanhThuTheoNgay doanhThuTheoNgay : doanhThuTheoNgays) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDate ngayTao = LocalDate.parse(doanhThuTheoNgay.getNgayTao());
                canvas.drawText(Common.fommatDateShow(ngayTao), 200, yOffset, paint);
                canvas.drawText(Common.convertCurrencyVietnamese(doanhThuTheoNgay.getTongTien()), 720, yOffset, paint);
                yOffset += lineHeight;
            }
        }

        paint.setTextSize(36);

        LocalDate ngayHienTai = Common.getCurrentDate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas.drawText("Ngày " + ngayHienTai.getDayOfMonth() + " tháng " + ngayHienTai.getMonthValue() + " năm " + ngayHienTai.getYear(),
                    630, yOffset + lineHeight, paint);
        }

        canvas.drawText("NHÂN VIÊN LẬP PHIẾU", 650, yOffset + 2 * lineHeight, paint);

        canvas.drawText("Nguyễn Viết Sĩ", 710, yOffset + 4 * lineHeight, paint);

        document.finishPage(page);

        File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String fileName = taoFilename("BC")+".pdf";
        File file = new File(downloadDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            document.writeTo(fos);
            document.close();
            fos.close();

            Common.onCreateMessageDialog(DoanhThuTheoNgayActivity.this,
                    "In báo cáo thành công!").show();
        } catch (FileNotFoundException e) {
            Log.d("TAG-ERR", "Error while writing " + e.toString());
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String taoFilename(String prefix) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String currentDateTime = sdf.format(new Date());
        return prefix + "_" + currentDateTime + ".pdf";
    }
}