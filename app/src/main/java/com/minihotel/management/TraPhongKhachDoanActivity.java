package com.minihotel.management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.minihotel.management.adapter.ChiTietPhieuThueAdapter;
import com.minihotel.management.adapter.ChonDichVuAdapter;
import com.minihotel.management.adapter.PhieuThueAdapter;
import com.minihotel.management.dto.ChiTietSuDungDichVuRequest;
import com.minihotel.management.managers.calls.CallChiTietPhieuThueById;
import com.minihotel.management.managers.calls.CallChiTietPhieuThueByPhieuThue;
import com.minihotel.management.managers.calls.CallPhieuThueByCmnd;
import com.minihotel.management.managers.calls.CallPhieuThueById;
import com.minihotel.management.managers.interfaces.IChiTietPhieuThueById;
import com.minihotel.management.managers.interfaces.IChiTietPhieuThueByPhieuThue;
import com.minihotel.management.managers.interfaces.IPhieuThueByCmnd;
import com.minihotel.management.managers.interfaces.IPhieuThueById;
import com.minihotel.management.model.ChiTietPhieuThue;
import com.minihotel.management.model.PhieuThue;
import com.minihotel.management.utils.Common;

import java.util.ArrayList;
import java.util.List;

public class TraPhongKhachDoanActivity extends AppCompatActivity {
    private TextInputEditText edtCmnd;
    private Button btnTimKiem;
    private RecyclerView rcPhieuThue;
    private List<PhieuThue> phieuThues;
    private List<ChiTietPhieuThue> chiTietPhieuThues;
    private ArrayList<Integer> idChiTietPhieuThuesDaChon = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tra_phong_khach_doan);

        initViews();
        setEvents();
        setBtnBack();
    }

    @Override
    protected void onResume() {
        super.onResume();
        idChiTietPhieuThuesDaChon.clear();
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

    /*public void getPhieuThueById(int id){
        CallPhieuThueById.getPhieuThueById(id, new IPhieuThueById() {
            @Override
            public void onSuccess(PhieuThue response) {
                phieuThue = response;
                getChiTietPhieuThueByPhieuThue(phieuThue.getIdPhieuThue());
            }

            @Override
            public void onError(Throwable t) {
                Log.d("TAG-ERR", t.getMessage());
                Common.onCreateMessageDialog(TraPhongKhachDoanActivity.this,
                        "Không tìm thấy phiếu thuê!").show();
            }
        });
    }*/

    public void getPhieuThueByCmnd(String cmnd){
        CallPhieuThueByCmnd.getPhieuThueByCmnd(cmnd, new IPhieuThueByCmnd() {
            @Override
            public void onSuccess(List<PhieuThue> responses) {
                if(responses.size() <= 0){
                    Common.onCreateMessageDialog(TraPhongKhachDoanActivity.this,
                            "Không tìm thấy phiếu thuê nào!").show();
                }else {
                    phieuThues = responses;
                    setPhieuThueRecycler();
                }
            }

            @Override
            public void onError(Throwable t) {
                Log.d("TAG-ERR", t.getMessage());
                Common.onCreateMessageDialog(TraPhongKhachDoanActivity.this,
                        "Lỗi tìm phiếu thuê theo CMND!").show();
            }
        });
    }

    private void setPhieuThueRecycler(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        rcPhieuThue.setLayoutManager(layoutManager);
        PhieuThueAdapter adapter = new PhieuThueAdapter(this, phieuThues);
        rcPhieuThue.setAdapter(adapter);
        adapter.setOnItemClickListener(new PhieuThueAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, int idPhieuThue) {
                openDialogChonChiTietPhieuThue(idPhieuThue);
            }
        });
    }

    private void openDialogChonChiTietPhieuThue(int idPhieuThue){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_chon_chitiet_phieuthue);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windownAttributes);

        dialog.setCancelable(false);
        RecyclerView rcChiTietPhieuThue = dialog.findViewById(R.id.rcChiTietPhieuThue);
        getChiTietPhieuThueByPhieuThue(idPhieuThue, rcChiTietPhieuThue);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idChiTietPhieuThuesDaChon.clear();
                dialog.dismiss();
                Toast.makeText(TraPhongKhachDoanActivity.this, "size ptdc " + idChiTietPhieuThuesDaChon.size(), Toast.LENGTH_SHORT).show();
            }
        });
        Button btnXacNhan = dialog.findViewById(R.id.btnXacNhan);
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idChiTietPhieuThuesDaChon.size() <= 0){
                    Common.onCreateMessageDialog(TraPhongKhachDoanActivity.this,
                            "Chưa chọn chi tiết phiếu thuê nào!").show();
                }else{
                    Intent intent = new Intent(TraPhongKhachDoanActivity.this, TraPhongKhachDoanThanhToanActivity.class);
                    intent.putIntegerArrayListExtra("idChiTietPhieuThuesDaChon", idChiTietPhieuThuesDaChon);
                    intent.putExtra("idphieuThue", idPhieuThue);
                    startActivity(intent);
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    public void getChiTietPhieuThueByPhieuThue(int id, RecyclerView rcPhieuThue){
        CallChiTietPhieuThueByPhieuThue.getChiTietPhieuThueByPhieuThue(id, new IChiTietPhieuThueByPhieuThue() {
            @Override
            public void onSuccess(List<ChiTietPhieuThue> responses) {
                chiTietPhieuThues = responses;
                setChiTietPhieuThueRecycler(rcPhieuThue);
            }

            @Override
            public void onError(Throwable t) {
                Common.onCreateMessageDialog(TraPhongKhachDoanActivity.this,
                        "Không tìm thấy chi tiết phiếu thuê nào!").show();
            }
        });
    }

    private void setChiTietPhieuThueRecycler(RecyclerView rcChiTietPhieuThue){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        rcChiTietPhieuThue.setLayoutManager(layoutManager);
        ChiTietPhieuThueAdapter adapter = new ChiTietPhieuThueAdapter(this, chiTietPhieuThues);
        rcChiTietPhieuThue.setAdapter(adapter);
        adapter.setOnCheckBoxClickListener(new ChiTietPhieuThueAdapter.OnCheckBoxClickListener() {
            @Override
            public void onChecked(int position, boolean isChecked, int idChiTietPhieuThue) {
                if(isChecked == true)
                    idChiTietPhieuThuesDaChon.add(idChiTietPhieuThue);
                if(isChecked == false) {
                    for (int i = 0; i < idChiTietPhieuThuesDaChon.size(); i++) {
                        if(idChiTietPhieuThuesDaChon.get(i) == idChiTietPhieuThue){
                            idChiTietPhieuThuesDaChon.remove(i);
                            break;
                        }
                    }
                }

                Toast.makeText(TraPhongKhachDoanActivity.this, "size ptdc " + idChiTietPhieuThuesDaChon.size(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void setEvents() {
        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmnd = edtCmnd.getText().toString();
                getPhieuThueByCmnd(cmnd);
            }
        });

    }

    private void initViews() {
        edtCmnd = findViewById(R.id.edtCmnd);
        rcPhieuThue = findViewById(R.id.rcPhieuThue);
        btnTimKiem = findViewById(R.id.btnTimKiem);
    }
}