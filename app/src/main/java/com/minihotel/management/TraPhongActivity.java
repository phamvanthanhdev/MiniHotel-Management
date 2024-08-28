package com.minihotel.management;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.minihotel.management.adapter.ChiTietPhuThuAdapter;
import com.minihotel.management.adapter.ChiTietPhuThuTraPhongAdapter;
import com.minihotel.management.adapter.ChiTietSuDungDichVuAdapter;
import com.minihotel.management.adapter.ChiTietSuDungDichVuTraPhongAdapter;
import com.minihotel.management.dto.ChiTietPhuThuRequest;
import com.minihotel.management.dto.ChiTietSuDungDichVuRequest;
import com.minihotel.management.dto.HoaDonResponse;
import com.minihotel.management.dto.ResultResponse;
import com.minihotel.management.managers.calls.CallCapNhatTrangThaiPhong;
import com.minihotel.management.managers.calls.CallChiTietPhieuThueById;
import com.minihotel.management.managers.calls.CallChiTietPhuThuByChiTietPhieuThue;
import com.minihotel.management.managers.calls.CallChiTietSuDungDichVuByChiTietPhieuThue;
import com.minihotel.management.managers.calls.CallKiemTraSoLuongPhongTra;
import com.minihotel.management.managers.calls.CallPhieuDatById;
import com.minihotel.management.managers.calls.CallPhieuThueById;
import com.minihotel.management.managers.calls.CallTraPhongKhachLe;
import com.minihotel.management.managers.interfaces.ICapNhatTrangThaiPhong;
import com.minihotel.management.managers.interfaces.IChiTietPhieuThueById;
import com.minihotel.management.managers.interfaces.IChiTietPhuThuByChiTietPhieuThue;
import com.minihotel.management.managers.interfaces.IChiTietSuDungDichVuByChiTietPhieuThue;
import com.minihotel.management.managers.interfaces.IKiemTraSoLuongPhongTra;
import com.minihotel.management.managers.interfaces.IPhieuDatById;
import com.minihotel.management.managers.interfaces.IPhieuThueById;
import com.minihotel.management.managers.interfaces.ITraPhongKhachLe;
import com.minihotel.management.model.ChiTietPhieuThue;
import com.minihotel.management.model.ChiTietPhuThu;
import com.minihotel.management.model.ChiTietSuDungDichVu;
import com.minihotel.management.model.DichVu;
import com.minihotel.management.model.PhieuDat;
import com.minihotel.management.model.PhieuThue;
import com.minihotel.management.model.PhuThu;
import com.minihotel.management.utils.Common;
import com.minihotel.management.utils.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

public class TraPhongActivity extends AppCompatActivity {
    private Integer idChiTietPhieuThue;
    private ChiTietPhieuThue chiTietPhieuThue;
    private TextView txtMaPhong, txtTenHangPhong, txtSoNgayThue, txtDonGia, txtTongTienPhong, txtTongTien, txtKhachCanTra;
    private TextInputEditText edtGiamGia;
    private TextView txtNgayDen, txtNgayDi, txtTamUng;
    private RecyclerView rcChiTietSuDungDichVu, rcChiTietPhuThu;
    private Button btnHoanThanh;
    private List<ChiTietSuDungDichVu> chiTietSuDungDichVus;
    private List<ChiTietPhuThu> chiTietPhuThus;
    private long tienKhachTra = 0;
    private long tongTien = 0;
    private long tienGiamGia = 0, tienTamUng = 0;
    private HoaDonResponse hoaDonResponse;
    private  int phanTramGiam = 0;
    private PhieuThue phieuThue;
    private Boolean kiemTraSoLuong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tra_phong);

        idChiTietPhieuThue = getIntent().getIntExtra("idChiTietPhieuThue", 0);
        initViews();
        setEvents();
        askPermissions();
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
        getChiTietPhieuThueById();
        getChiTietSuDungDichVuByChiTietPhieuThue();
        getChiTietPhuThuByChiTietPhieuThue();
    }

    private void getChiTietPhieuThueById() {
        CallChiTietPhieuThueById.getChiTietPhieuThueById(idChiTietPhieuThue, new IChiTietPhieuThueById() {
            @Override
            public void onSuccess(ChiTietPhieuThue response) {
                chiTietPhieuThue = response;
                getPhieuThueById(chiTietPhieuThue.getIdPhieuThue());
                kiemTraSoLuongPhongTra(chiTietPhieuThue.getIdPhieuThue(), 1);
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
        }
        if(phieuThue != null && kiemTraSoLuong != null){
            if(kiemTraSoLuong)
                tienTamUng = phieuThue.getTienTamUng();
        }
        tienKhachTra = tongTien - tienTamUng;
        txtTongTien.setText(Common.convertCurrencyVietnamese(tongTien));
        txtTamUng.setText(Common.convertCurrencyVietnamese(tienTamUng));
        txtKhachCanTra.setText(Common.convertCurrencyVietnamese(tienKhachTra));
    }


    private void setEvents() {
        btnHoanThanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                traPhongKhachLe();
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
                        Toast.makeText(TraPhongActivity.this, "Phần trăm giảm giá chưa chính xác", Toast.LENGTH_SHORT).show();
                        phanTramGiam = 0;
                    }
                    tienKhachTra = (tongTien - tienTamUng) - ((tongTien * phanTramGiam) / 100);
                    tienGiamGia = tongTien - tienTamUng - tienKhachTra;
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

    private void traPhongKhachLe(){
        CallTraPhongKhachLe.traPhongKhachLe(Utils.idNhanVien, tienKhachTra,
                Common.fommatDateRequest(Common.getCurrentDate()),
                idChiTietPhieuThue,
                new ITraPhongKhachLe() {
                    @Override
                    public void onSuccess(HoaDonResponse response) {
                        hoaDonResponse = response;
                        //Cập nhật trạng thái phòng "Chưa dọn dẹp"
                        capNhatTrangThaiPhongSauThue(chiTietPhieuThue.getMaPhong());
                        openDialogInHoaDon(TraPhongActivity.this,
                                "Trả phòng thành công. Xác nhận in hóa đơn ?").show();
                    }

                    @Override
                    public void onError(Throwable t) {

                        Common.onCreateMessageDialog(TraPhongActivity.this,
                                "Gặp lỗi khi trả phòng khách sạn!").show();
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
        txtTamUng = findViewById(R.id.txtTamUng);
    }

    final static int REQUEST_CODE = 1234;
    private void askPermissions(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
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

            Common.onCreateMessageDialog(TraPhongActivity.this,
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

    public void getPhieuThueById(int id){
        CallPhieuThueById.getPhieuThueById(id, new IPhieuThueById() {
            @Override
            public void onSuccess(PhieuThue response) {
                phieuThue = response;
                showDataTongTien();
            }

            @Override
            public void onError(Throwable t) {
                Common.onCreateMessageDialog(TraPhongActivity.this,
                        "Không tìm thấy phiếu thuê!").show();
            }
        });
    }

    private void kiemTraSoLuongPhongTra(int idphieuThue, int soLuong){
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