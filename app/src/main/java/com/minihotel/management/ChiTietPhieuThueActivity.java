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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.minihotel.management.adapter.ChiTietPhuThuAdapter;
import com.minihotel.management.adapter.ChonDichVuAdapter;
import com.minihotel.management.adapter.ChiTietSuDungDichVuAdapter;
import com.minihotel.management.adapter.ChonPhuThuAdapter;
import com.minihotel.management.dto.ChiTietPhuThuRequest;
import com.minihotel.management.dto.ChiTietSuDungDichVuRequest;
import com.minihotel.management.dto.ResultResponse;
import com.minihotel.management.managers.calls.CallChiTietPhieuThueById;
import com.minihotel.management.managers.calls.CallChiTietPhuThuByChiTietPhieuThue;
import com.minihotel.management.managers.calls.CallChiTietSuDungDichVuByChiTietPhieuThue;
import com.minihotel.management.managers.calls.CallDichVus;
import com.minihotel.management.managers.calls.CallKiemTraPhongThue;
import com.minihotel.management.managers.calls.CallPhuThus;
import com.minihotel.management.managers.calls.CallThemChiTietPhuThu;
import com.minihotel.management.managers.calls.CallThemChiTietSuDungDichVu;
import com.minihotel.management.managers.calls.CallUpdateNgayDiChiTiet;
import com.minihotel.management.managers.interfaces.IChiTietPhieuThueById;
import com.minihotel.management.managers.interfaces.IChiTietPhuThuByChiTietPhieuThue;
import com.minihotel.management.managers.interfaces.IChiTietSuDungDichVuByChiTietPhieuThue;
import com.minihotel.management.managers.interfaces.IGetAllDichVu;
import com.minihotel.management.managers.interfaces.IGetAllPhuThu;
import com.minihotel.management.managers.interfaces.IKiemTraPhongThue;
import com.minihotel.management.managers.interfaces.IThemChiTietPhuThu;
import com.minihotel.management.managers.interfaces.IThemChiTietSuDungDichVu;
import com.minihotel.management.managers.interfaces.IUpdateNgayDiChiTiet;
import com.minihotel.management.model.ChiTietPhieuThue;
import com.minihotel.management.model.ChiTietPhuThu;
import com.minihotel.management.model.ChiTietSuDungDichVu;
import com.minihotel.management.model.DichVu;
import com.minihotel.management.model.PhuThu;
import com.minihotel.management.utils.Common;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class ChiTietPhieuThueActivity extends AppCompatActivity {
    private Integer idChiTietPhieuThue;
    private ChiTietPhieuThue chiTietPhieuThue;
    private TextView txtMaPhong, txtTenHangPhong, txtSoNgayThue, txtDonGia, txtTongTien;
    private TextInputEditText edtNgayDen, edtNgayDi;
    private RecyclerView rcDichVu, rcPhuThu;
    private Button btnThemDichVu, btnThemPhuThu, btnTraPhong;
    private List<DichVu> dichVus;
    private List<PhuThu> phuThus;
    private List<ChiTietSuDungDichVu> chiTietSuDungDichVus;
    private List<ChiTietPhuThu> chiTietPhuThus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_phieu_thue);

        idChiTietPhieuThue = getIntent().getIntExtra("idChiTietPhieuThue", 0);
        initViews();
        setEvents();
    }

    private void setEvents() {
        edtNgayDi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate(edtNgayDi);
            }
        });

        btnThemDichVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogChonDichVu();
            }
        });

        btnThemPhuThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogChonPhuThu();
            }
        });

        btnTraPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTietPhieuThueActivity.this, TraPhongActivity.class);
                intent.putExtra("idChiTietPhieuThue", idChiTietPhieuThue);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        txtMaPhong = findViewById(R.id.txtMaPhong);
        txtTenHangPhong = findViewById(R.id.txtTenHangPhong);
        txtSoNgayThue = findViewById(R.id.txtSoNgayThue);
        txtDonGia = findViewById(R.id.txtDonGia);
        txtTongTien = findViewById(R.id.txtTongTien);
        edtNgayDen = findViewById(R.id.edtNgayDen);
        edtNgayDi = findViewById(R.id.edtNgayDi);
        rcDichVu = findViewById(R.id.rcDichVu);
        rcPhuThu = findViewById(R.id.rcPhuThu);
        btnThemDichVu = findViewById(R.id.btnThemDichVu);
        btnThemPhuThu = findViewById(R.id.btnThemPhuThu);
        btnTraPhong = findViewById(R.id.btnTraPhong);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getChiTietPhieuThueById();
        getAllDichVu();
        getChiTietSuDungDichVuByChiTietPhieuThue();
        getAllPhuThu();
        getChiTietPhuThuByChiTietPhieuThue();
    }

    private void getChiTietPhieuThueById() {
        CallChiTietPhieuThueById.getChiTietPhieuThueById(idChiTietPhieuThue, new IChiTietPhieuThueById() {
            @Override
            public void onSuccess(ChiTietPhieuThue response) {
                chiTietPhieuThue = response;
                showData();
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
        txtTongTien.setText("Tổng tiền: " + Common.convertCurrencyVietnamese(tongTien) + " VNĐ");

        edtNgayDen.setText(Common.fommatDateShow(ngayDen));
        edtNgayDi.setText(Common.fommatDateShow(ngayDi));
    }

    @SuppressLint("NewApi")
    public void kiemTraHangPhongConTrongDeThue(LocalDate newDate){
        LocalDate ngayDi = LocalDate.parse(chiTietPhieuThue.getNgayDi());
        //Nếu thời gian thuê lớn hơn thời gian trước đó thì phải kiểm tra
        if(newDate.isAfter(ngayDi)) {
            LocalDate ngayBatDauThueThem = Common.getPlusDay(ngayDi);
            LocalDate ngayKetThucThueThem = newDate;
            int idHangPhong = chiTietPhieuThue.getIdHangPhong();
            kiemTraHangPhongThue(idHangPhong, ngayBatDauThueThem, ngayKetThucThueThem);
        }else{
            chiTietPhieuThue.setNgayDi(Common.fommatDateRequest(newDate));
            loadData();
            updatNgayDiChiTiet(newDate);
        }
    }

    @SuppressLint("NewApi")
    private void loadData() {
        LocalDate ngayDen = LocalDate.parse(chiTietPhieuThue.getNgayDen());
        LocalDate ngayDi = LocalDate.parse(chiTietPhieuThue.getNgayDi());
        long soNgayThue = Common.calculateBetweenDate(ngayDen, ngayDi);
        txtSoNgayThue.setText("Số ngày thuê: " + soNgayThue);
        txtDonGia.setText("Đơn giá: " + Common.convertCurrencyVietnamese(chiTietPhieuThue.getDonGia()) + " VNĐ");
        long tongTien = chiTietPhieuThue.getDonGia() * soNgayThue;
        txtTongTien.setText("Tổng tiền: " + Common.convertCurrencyVietnamese(tongTien) + " VNĐ");

        edtNgayDi.setText(Common.fommatDateShow(ngayDi));
    }

    public void kiemTraHangPhongThue(int idHangPhong, LocalDate ngayBatDauThueThem, LocalDate ngayKetThucThueThem){
        CallKiemTraPhongThue.kiemTraPhongThue(idHangPhong,
                Common.fommatDateRequest(ngayBatDauThueThem),
                Common.fommatDateRequest(ngayKetThucThueThem),
                new IKiemTraPhongThue() {
                    @Override
                    public void onSuccess(ResultResponse resultResponse) {
                        if (resultResponse.getCode() == 200) {
                            chiTietPhieuThue.setNgayDi(Common.fommatDateRequest(ngayKetThucThueThem));
                            updatNgayDiChiTiet(ngayKetThucThueThem);
                            loadData();
                        } else {
                            Common.onCreateMessageDialog(ChiTietPhieuThueActivity.this,
                                    "Số lượng phòng không đủ để thuê thêm!").show();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d("TAG-ERR", t.getMessage());
                    }
                });
    }

    public void updatNgayDiChiTiet(LocalDate ngayDiMoi){
        CallUpdateNgayDiChiTiet.updateNgayDiChiTiet(chiTietPhieuThue.getIdChiTietPhieuThue(),
                Common.fommatDateRequest(ngayDiMoi), new IUpdateNgayDiChiTiet() {
                    @Override
                    public void onSuccess(ChiTietPhieuThue chiTietPhieuThue) {
                        Toast.makeText(ChiTietPhieuThueActivity.this, "Cập nhật thời gian thành công!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d("TAG-ERR", t.getMessage());
                    }
                });
    }

    @SuppressLint("NewApi")
    public void pickDate(TextInputEditText edt){
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DAY_OF_MONTH);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(ChiTietPhieuThueActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month, dayOfMonth);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalDate chooseDate = LocalDate.of(year, month + 1, dayOfMonth);
                    kiemTraHangPhongConTrongDeThue(chooseDate);
                }
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    public void getAllDichVu(){
        CallDichVus.getAllDichVu(new IGetAllDichVu() {
            @Override
            public void onSuccess(List<DichVu> responses) {
                dichVus = responses;
                Log.d("AAA", dichVus.size() + "size");
            }

            @Override
            public void onError(Throwable t) {
                Log.d("TAG-ERR", t.getMessage());
            }
        });
    }

    public void getAllPhuThu(){
        CallPhuThus.getAllPhuThu(new IGetAllPhuThu() {
            @Override
            public void onSuccess(List<PhuThu> responses) {
                phuThus = responses;
                Log.d("AAA", phuThus.size() + "size");
            }

            @Override
            public void onError(Throwable t) {
                Log.d("TAG-ERR", t.getMessage());
            }
        });
    }

    private void openDialogChonDichVu(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_chon_dich_vu);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windownAttributes);

        dialog.setCancelable(true);

        RecyclerView rcDichVu = dialog.findViewById(R.id.rcChonDichVu);
        setChonDichVuRecycler(rcDichVu);


        dialog.show();
    }

    private void setChonDichVuRecycler(RecyclerView recycler){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
        ChonDichVuAdapter adapter = new ChonDichVuAdapter(this, dichVus);
        recycler.setAdapter(adapter);
        adapter.setOnButtonThemClickListener(new ChonDichVuAdapter.OnButtonThemClickListener() {
            @Override
            public void onClick(int position, int idDichVu, long donGia) {
                ChiTietSuDungDichVuRequest chiTietSuDungDichVuRequest = 
                        new ChiTietSuDungDichVuRequest(idDichVu, idChiTietPhieuThue, 1, 
                                Common.fommatDateRequest(Common.getCurrentDate()), donGia);
                themChiTietSuDungDichVu(chiTietSuDungDichVuRequest);
            }
        });
    }
    
    private void themChiTietSuDungDichVu(ChiTietSuDungDichVuRequest chiTietSuDungDichVuRequest){
        CallThemChiTietSuDungDichVu.themChiTietSuDungDichVu(chiTietSuDungDichVuRequest, new IThemChiTietSuDungDichVu() {
            @Override
            public void onSuccess(List<ChiTietSuDungDichVu> responses) {
                chiTietSuDungDichVus = responses;
                Toast.makeText(ChiTietPhieuThueActivity.this, "Thêm dịch vụ thành công!", Toast.LENGTH_SHORT).show();
                setChiTietSuDungDichVuRecycler();

            }

            @Override
            public void onError(Throwable t) {
                Log.e("TAG-ERR", t.getMessage());
            }
        });
    }

    private void getChiTietSuDungDichVuByChiTietPhieuThue(){
        CallChiTietSuDungDichVuByChiTietPhieuThue.getChiTietSuDungDichVuByChiTietPhieuThue(idChiTietPhieuThue, new IChiTietSuDungDichVuByChiTietPhieuThue() {
            @Override
            public void onSuccess(List<ChiTietSuDungDichVu> responses) {
                chiTietSuDungDichVus = responses;
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
        rcDichVu.setLayoutManager(layoutManager);
        ChiTietSuDungDichVuAdapter adapter = new ChiTietSuDungDichVuAdapter(this, chiTietSuDungDichVus);
        rcDichVu.setAdapter(adapter);
        adapter.setOnButtonTangClickListener(new ChiTietSuDungDichVuAdapter.OnButtonTangClickListener() {
            @Override
            public void onClick(int position, int idDichVu, long donGia) {
                ChiTietSuDungDichVuRequest chiTietSuDungDichVuRequest =
                        new ChiTietSuDungDichVuRequest(idDichVu, idChiTietPhieuThue, 1,
                                Common.fommatDateRequest(Common.getCurrentDate()), donGia);
                themChiTietSuDungDichVu(chiTietSuDungDichVuRequest);
                adapter.notifyDataSetChanged();
            }
        });

        adapter.setOnButtonGiamClickListener(new ChiTietSuDungDichVuAdapter.OnButtonGiamClickListener() {
            @Override
            public void onClick(int position, int idDichVu, long donGia) {
                ChiTietSuDungDichVuRequest chiTietSuDungDichVuRequest =
                        new ChiTietSuDungDichVuRequest(idDichVu, idChiTietPhieuThue, -1,
                                Common.fommatDateRequest(Common.getCurrentDate()), donGia);
                themChiTietSuDungDichVu(chiTietSuDungDichVuRequest);
                adapter.notifyDataSetChanged();
            }
        });
    }

    // ==================== PHỤ THU =====================
    private void getChiTietPhuThuByChiTietPhieuThue(){
        CallChiTietPhuThuByChiTietPhieuThue.getChiTietPhuThuByChiTietPhieuThue(idChiTietPhieuThue, new IChiTietPhuThuByChiTietPhieuThue() {
            @Override
            public void onSuccess(List<ChiTietPhuThu> responses) {
                chiTietPhuThus = responses;
                setChiTietPhuThuRecycler();
            }

            @Override
            public void onError(Throwable t) {
                Log.e("TAG-ERR", t.getMessage());
            }
        });
    }

    private void openDialogChonPhuThu(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_chon_phu_thu);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windownAttributes);

        dialog.setCancelable(true);

        RecyclerView rcPhuThu = dialog.findViewById(R.id.rcChonPhuThu);
        setChonPhuThuRecycler(rcPhuThu);


        dialog.show();
    }

    private void setChonPhuThuRecycler(RecyclerView recycler){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
        ChonPhuThuAdapter adapter = new ChonPhuThuAdapter(this, phuThus);
        recycler.setAdapter(adapter);
        adapter.setOnButtonThemClickListener(new ChonPhuThuAdapter.OnButtonThemClickListener() {
            @Override
            public void onClick(int position, int idPhuThu, long donGia) {
                ChiTietPhuThuRequest chiTietPhuThuRequest =
                        new ChiTietPhuThuRequest(idPhuThu, idChiTietPhieuThue, 1,
                                Common.fommatDateRequest(Common.getCurrentDate()), donGia);
                themChiTietPhuThu(chiTietPhuThuRequest);
            }
        });
    }

    private void themChiTietPhuThu(ChiTietPhuThuRequest chiTietPhuThuRequest){
        CallThemChiTietPhuThu.themChiTietPhuThu(chiTietPhuThuRequest, new IThemChiTietPhuThu() {
            @Override
            public void onSuccess(List<ChiTietPhuThu> responses) {
                chiTietPhuThus = responses;
                Toast.makeText(ChiTietPhieuThueActivity.this, "Thêm phiếu thuê thành công!", Toast.LENGTH_SHORT).show();
                setChiTietPhuThuRecycler();
            }

            @Override
            public void onError(Throwable t) {
                Log.e("TAG-ERR", t.getMessage());
            }
        });
    }

    private void setChiTietPhuThuRecycler(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        rcPhuThu.setLayoutManager(layoutManager);
        ChiTietPhuThuAdapter adapter = new ChiTietPhuThuAdapter(this, chiTietPhuThus);
        rcPhuThu.setAdapter(adapter);
        adapter.setOnButtonTangClickListener(new ChiTietPhuThuAdapter.OnButtonTangClickListener() {
            @Override
            public void onClick(int position, int idPhuThu, long donGia) {
                ChiTietPhuThuRequest chiTietPhuThuRequest =
                        new ChiTietPhuThuRequest(idPhuThu, idChiTietPhieuThue, 1,
                                Common.fommatDateRequest(Common.getCurrentDate()), donGia);
                themChiTietPhuThu(chiTietPhuThuRequest);
                adapter.notifyDataSetChanged();
            }
        });

        adapter.setOnButtonGiamClickListener(new ChiTietPhuThuAdapter.OnButtonGiamClickListener() {
            @Override
            public void onClick(int position, int idPhuThu, long donGia) {
                ChiTietPhuThuRequest chiTietPhuThuRequest =
                        new ChiTietPhuThuRequest(idPhuThu, idChiTietPhieuThue, -1,
                                Common.fommatDateRequest(Common.getCurrentDate()), donGia);
                themChiTietPhuThu(chiTietPhuThuRequest);
                adapter.notifyDataSetChanged();
            }
        });
    }
}