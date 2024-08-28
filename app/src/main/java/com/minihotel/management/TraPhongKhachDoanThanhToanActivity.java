package com.minihotel.management;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.minihotel.management.adapter.ChiTietPhieuThueTraPhongAdapter;
import com.minihotel.management.adapter.ChiTietPhuThuTraPhongAdapter;
import com.minihotel.management.adapter.ChiTietSuDungDichVuTraPhongAdapter;
import com.minihotel.management.dto.HoaDonResponse;
import com.minihotel.management.dto.ResultResponse;
import com.minihotel.management.managers.calls.CallCapNhatTrangThaiPhong;
import com.minihotel.management.managers.calls.CallChiTietPhieuThueById;
import com.minihotel.management.managers.calls.CallChiTietPhieuThueByPhieuThue;
import com.minihotel.management.managers.calls.CallChiTietPhuThuByChiTietPhieuThue;
import com.minihotel.management.managers.calls.CallChiTietSuDungDichVuByChiTietPhieuThue;
import com.minihotel.management.managers.calls.CallKiemTraSoLuongPhongTra;
import com.minihotel.management.managers.calls.CallPhieuThueById;
import com.minihotel.management.managers.calls.CallTraPhongKhachDoan;
import com.minihotel.management.managers.calls.CallTraPhongKhachLe;
import com.minihotel.management.managers.interfaces.ICapNhatTrangThaiPhong;
import com.minihotel.management.managers.interfaces.IChiTietPhieuThueById;
import com.minihotel.management.managers.interfaces.IChiTietPhieuThueByPhieuThue;
import com.minihotel.management.managers.interfaces.IChiTietPhuThuByChiTietPhieuThue;
import com.minihotel.management.managers.interfaces.IChiTietSuDungDichVuByChiTietPhieuThue;
import com.minihotel.management.managers.interfaces.IKiemTraSoLuongPhongTra;
import com.minihotel.management.managers.interfaces.IPhieuThueById;
import com.minihotel.management.managers.interfaces.ITraPhongKhachDoan;
import com.minihotel.management.managers.interfaces.ITraPhongKhachLe;
import com.minihotel.management.model.ChiTietPhieuThue;
import com.minihotel.management.model.ChiTietPhuThu;
import com.minihotel.management.model.ChiTietSuDungDichVu;
import com.minihotel.management.model.PhieuThue;
import com.minihotel.management.utils.Common;
import com.minihotel.management.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private long tienKhachTra = 0, tongTien = 0, tienTamUng = 0, tienGiamGia = 0;
    private  int phanTramGiam = 0;
    private TextView txtTongTien, txtKhachCanTra, txtTamUng, txtMaPhieuThue, txtNgayDen, txtNgayDi, txtKhachHang, txtCmnd;
    private TextInputEditText edtGiamGia;
    private Button btnHoanThanh;
    private HoaDonResponse hoaDonResponse;
    private Boolean kiemTraSoLuong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tra_phong_khach_doan_thanh_toan);

        idChiTietPhieuThuesDaChon = getIntent().getIntegerArrayListExtra("idChiTietPhieuThuesDaChon");
        idphieuThue = getIntent().getIntExtra("idphieuThue", 0);

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
        getPhieuThueById(idphieuThue);
        getChiTietPhieuThueDaChon();
        kiemTraSoLuongPhongTra(idChiTietPhieuThuesDaChon.size(), idphieuThue);
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
                traPhongKhachDoan();
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
                    phanTramGiam = Integer.parseInt(s.toString());
                    if(phanTramGiam < 0 || phanTramGiam > 100){
                        Toast.makeText(TraPhongKhachDoanThanhToanActivity.this, "Phần trăm giảm giá chưa chính xác", Toast.LENGTH_SHORT).show();
                        phanTramGiam = 0;
                    }
                    tienKhachTra = (tongTien - tienTamUng) - ((tongTien * phanTramGiam) / 100);
                    tienGiamGia = tongTien - tienTamUng - tienKhachTra;
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
        if(phieuThue != null && kiemTraSoLuong != null){
            if(kiemTraSoLuong) // Nếu chọn trả hết phòng thì mới cộng tạm ứng
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

    private void traPhongKhachDoan(){
        CallTraPhongKhachDoan.traPhongKhachDoan(Utils.idNhanVien, tienKhachTra,
                Common.fommatDateRequest(Common.getCurrentDate()),
                idChiTietPhieuThuesDaChon,
                new ITraPhongKhachDoan() {
                    @Override
                    public void onSuccess(HoaDonResponse response) {
                        hoaDonResponse = response;
                        for (ChiTietPhieuThue chiTiet: chiTietPhieuThues) {
                            capNhatTrangThaiPhongSauThue(chiTiet.getMaPhong());
                        }
                        openDialogInHoaDon(TraPhongKhachDoanThanhToanActivity.this,
                                "Trả phòng thành công. Xác nhận in hóa đơn ?").show();
                    }

                    @Override
                    public void onError(Throwable t) {

                        Common.onCreateMessageDialog(TraPhongKhachDoanThanhToanActivity.this,
                                "Gặp lỗi khi trả phòng khách sạn!").show();
                    }
                });
    }

    public Dialog openDialogInHoaDon(Activity activity, String message) {
        // Use the Builder class for convenient dialog construction.
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        inHoaDonPdf();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancels the dialog.
                        dialog.dismiss();
                    }
                });
        // Create the AlertDialog object and return it.
        return builder.create();
    }

    private void inHoaDonPdf(){
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1080, 1920, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        paint.setColor(Color.BLACK);
        paint.setTextSize(80);

        // Tiêu đề hóa đơn
        String title = "HÓA ĐƠN";
        canvas.drawText(title, 330, 120, paint);


        // Thông tin hóa đơn
        paint.setTextSize(36);
        int startY = 200;
        int lineHeight = 60;

        //Thông tin khách hàng
        canvas.drawText("Tên khách hàng: " + phieuThue.getHoTenKhach(), 40, startY, paint);
        startY += lineHeight;

        // Thêm thông tin nhân viên lập phiếu
        canvas.drawText("Tên nhân viên: " + hoaDonResponse.getTenNhanVien(), 40, startY, paint);
        startY += lineHeight;

        // Thêm thông tin số hóa đơn
        canvas.drawText("Số hóa đơn: " + hoaDonResponse.getSoHoaDon(), 40, startY, paint);
        startY += lineHeight;

        // Thêm thông tin ngày tạo
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate ngayTao = LocalDate.parse(hoaDonResponse.getNgayTao());
            canvas.drawText("Ngày tạo: " + Common.fommatDateShow(ngayTao), 40, startY, paint);
            startY += lineHeight;
        }

        // Tiêu đề bảng sản phẩm
        paint.setTextSize(36);
        canvas.drawText("Tên dịch vụ", 40, startY, paint);
        canvas.drawText("Đơn giá", 450, startY, paint);
        canvas.drawText("Số lượng", 650, startY, paint);
        canvas.drawText("Tổng tiền", 850, startY, paint);

        // Dữ liệu sản phẩm
        paint.setTextSize(30);
        int yOffset = startY + lineHeight;

        for (ChiTietPhieuThue chiTietPhieuThue: chiTietPhieuThues
             ) {
            canvas.drawText(chiTietPhieuThue.getTenHangPhong() + "(" + chiTietPhieuThue.getMaPhong() + ")", 40, yOffset, paint);
            canvas.drawText(Common.convertCurrencyVietnamese(chiTietPhieuThue.getDonGia()), 450, yOffset, paint);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDate ngayDen = LocalDate.parse(chiTietPhieuThue.getNgayDen());
                LocalDate ngayDi = LocalDate.parse(chiTietPhieuThue.getNgayDi());
                long soNgayThue = Common.calculateBetweenDate(ngayDen, ngayDi);
                canvas.drawText(String.valueOf(soNgayThue), 650, yOffset, paint);
                long tongTien = chiTietPhieuThue.getDonGia() * soNgayThue;
                canvas.drawText(Common.convertCurrencyVietnamese(tongTien), 850, yOffset, paint);
            }
            yOffset += lineHeight;
        }

        for (ChiTietSuDungDichVu chiTietSuDungDichVu : chiTietSuDungDichVus) {
            canvas.drawText(chiTietSuDungDichVu.getTenDichVu(), 40, yOffset, paint);
            canvas.drawText(Common.convertCurrencyVietnamese(chiTietSuDungDichVu.getDonGia()), 450, yOffset, paint);
            canvas.drawText(String.valueOf(chiTietSuDungDichVu.getSoLuong()), 650, yOffset, paint);
            long tongTien = chiTietSuDungDichVu.getDonGia() * chiTietSuDungDichVu.getSoLuong();
            canvas.drawText(Common.convertCurrencyVietnamese(tongTien), 850, yOffset, paint);
            yOffset += lineHeight;
        }

        for (ChiTietPhuThu chiTietPhuThu : chiTietPhuThus) {
            canvas.drawText(chiTietPhuThu.getNoiDung(), 40, yOffset, paint);
            canvas.drawText(Common.convertCurrencyVietnamese(chiTietPhuThu.getDonGia()), 450, yOffset, paint);
            canvas.drawText(String.valueOf(chiTietPhuThu.getSoLuong()), 650, yOffset, paint);
            long tongTien = chiTietPhuThu.getDonGia() * chiTietPhuThu.getSoLuong();
            canvas.drawText(Common.convertCurrencyVietnamese(tongTien), 850, yOffset, paint);
            yOffset += lineHeight;
        }

        // Tổng tiền hàng
        paint.setTextSize(36);
        canvas.drawText("Tổng tiền hàng:", 40, yOffset + lineHeight, paint);
        canvas.drawText(Common.convertCurrencyVietnamese(tongTien), 850, yOffset + lineHeight, paint);

        //Tiền tạm ứng
        canvas.drawText("Tiền tạm ứng:", 40, yOffset + 2 * lineHeight, paint);
        canvas.drawText(Common.convertCurrencyVietnamese(tienTamUng), 850, yOffset + 2 * lineHeight, paint);

        // Giảm giá (giả định là 10%)
        canvas.drawText("Giảm giá ("+phanTramGiam+"%):", 40, yOffset + 3 * lineHeight, paint);
        canvas.drawText("-"+Common.convertCurrencyVietnamese(tienGiamGia), 850, yOffset + 3 * lineHeight, paint);

        // Tiền khách phải trả
        canvas.drawText("Tiền khách trả:", 40, yOffset + 4 * lineHeight, paint);
        canvas.drawText(Common.convertCurrencyVietnamese(tienKhachTra), 850, yOffset + 4 * lineHeight, paint);

        document.finishPage(page);

        File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String fileName = "HD" + hoaDonResponse.getSoHoaDon()+".pdf";
        File file = new File(downloadDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            document.writeTo(fos);
            document.close();
            fos.close();

            Common.onCreateMessageDialog(TraPhongKhachDoanThanhToanActivity.this,
                    "In hóa đơn thành công!").show();
        } catch (FileNotFoundException e) {
            Log.d("TAG-ERR", "Error while writing " + e.toString());
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void capNhatTrangThaiPhongSauThue(String maPhong){
        CallCapNhatTrangThaiPhong.capNhatTrangThaiPhong(2, maPhong, new ICapNhatTrangThaiPhong() {
            @Override
            public void onSuccess(ResultResponse resultResponse) {

            }

            @Override
            public void onError(Throwable t) {
                Log.d("TAG-ERR", t.getMessage());
            }
        });
    }

    //Kiểm tra số lượng phòng đã chọn có phải là trả hết phòng hay trả một ít
    // Nếu trả hết thì + tiền tạm ứng, nếu trả một phần số phòng đã thuê thì không cộng tiền tạm ứng
    //Chỉ phiếu trả phòng cuối cùng mới được tính tiền tạm ứng
    private void kiemTraSoLuongPhongTra(int soLuong, int idphieuThue){
        CallKiemTraSoLuongPhongTra.kiemTraSoLuongPhongTra(idphieuThue, soLuong, new IKiemTraSoLuongPhongTra() {
            @Override
            public void onSuccess(Boolean result) {
                kiemTraSoLuong = result;
                showDataTongTien();
            }

            @Override
            public void onError(Throwable t) {
                Log.d("TAG-ERR", t.getMessage());
            }
        });
    }
}