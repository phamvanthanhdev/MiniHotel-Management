package com.minihotel.management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.minihotel.management.adapter.ChonDichVuAdapter;
import com.minihotel.management.adapter.ChonTrangThaiAdapter;
import com.minihotel.management.adapter.PhongHienTaiAdapter;
import com.minihotel.management.adapter.PhongTrongAdapter;
import com.minihotel.management.dto.ChiTietSuDungDichVuRequest;
import com.minihotel.management.dto.ResultResponse;
import com.minihotel.management.managers.calls.CallCapNhatTrangThaiPhong;
import com.minihotel.management.managers.calls.CallGetAllTrangThai;
import com.minihotel.management.managers.calls.CallPhongsHienTai;
import com.minihotel.management.managers.interfaces.ICapNhatTrangThaiPhong;
import com.minihotel.management.managers.interfaces.IGetAllTrangThai;
import com.minihotel.management.managers.interfaces.IPhongsHienTai;
import com.minihotel.management.model.PhongHienTai;
import com.minihotel.management.model.TrangThai;
import com.minihotel.management.utils.Common;

import java.util.List;

public class SoDoActivity extends AppCompatActivity {
    private List<PhongHienTai> phongHienTais;
    private RecyclerView rcPhongHienTai;
    private ImageButton btnThuePhong;
    private List<TrangThai> trangThais;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_so_do);

        initViews();
        setEvents();
    }

    private void setEvents() {
        btnThuePhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initViews() {
        rcPhongHienTai = findViewById(R.id.rcPhongHienTai);
        btnThuePhong = findViewById(R.id.btnThuePhong);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPhongsHienTai();
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
                Toast.makeText(SoDoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("TAG-ERR", t.getMessage());
            }
        });
    }

    private void setPhongHienTaisRecycler(){
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        rcPhongHienTai.setLayoutManager(layoutManager);
        PhongHienTaiAdapter adapter = new PhongHienTaiAdapter(this, phongHienTais);
        rcPhongHienTai.setAdapter(adapter);
        adapter.setOnItemClickListener(new PhongHienTaiAdapter.OnItemClickListener() {
            @Override
            public void onClick(String maPhong, Integer idChiTietPhieuThue, View view) {
                showPopupMenu(maPhong, idChiTietPhieuThue, view);
            }
        });

    }

    private void showPopupMenu(String maPhong, Integer idChiTietPhieuThue, View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_popup, popupMenu.getMenu());
        //Register for menu item click
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return onPopupMenuClick(maPhong, idChiTietPhieuThue, item);
            }
        });
        popupMenu.show();
    }

    private boolean onPopupMenuClick(String maPhong, Integer idChiTietPhieuThue, MenuItem item){
        if(item.getItemId() == R.id.menuChiTiet){
            if(idChiTietPhieuThue != null){
                Intent intent = new Intent(SoDoActivity.this, ChiTietPhieuThueActivity.class);
                intent.putExtra("idChiTietPhieuThue", idChiTietPhieuThue);
                startActivity(intent);
            }
        }
        if(item.getItemId() == R.id.menuTrangThai){
            openDialogChonTrangThai(maPhong);
        }

        return true;
    }

    private void openDialogChonTrangThai(String maPhong){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_chon_trang_thai);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windownAttributes);

        dialog.setCancelable(true);

        RecyclerView rcChonTrangThai = dialog.findViewById(R.id.rcChonTrangThai);
        getAllTrangThai(rcChonTrangThai, dialog, maPhong);

        dialog.show();
    }

    private void getAllTrangThai(RecyclerView rcChonTrangThai, Dialog dialog, String maPhong) {
        CallGetAllTrangThai.getAllTrangThai(new IGetAllTrangThai() {
            @Override
            public void onSuccess(List<TrangThai> responses) {
                trangThais = responses;
                setChonTrangThaiRecycler(rcChonTrangThai, dialog, maPhong);
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }


    private void setChonTrangThaiRecycler(RecyclerView recycler, Dialog dialog, String maPhong){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
        ChonTrangThaiAdapter adapter = new ChonTrangThaiAdapter(this, trangThais);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new ChonTrangThaiAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, int idTrangThai) {
                capNhatTrangThaiPhong(dialog, idTrangThai, maPhong);
            }
        });
    }

    private void capNhatTrangThaiPhong(Dialog dialog, int idTrangThai, String maPhong){
        CallCapNhatTrangThaiPhong.capNhatTrangThaiPhong(idTrangThai, maPhong, new ICapNhatTrangThaiPhong() {
            @Override
            public void onSuccess(ResultResponse resultResponse) {
                dialog.dismiss();
                Toast.makeText(SoDoActivity.this, "Cập nhât trạng thái phòng thành công!", Toast.LENGTH_SHORT).show();
                onResume();
            }

            @Override
            public void onError(Throwable t) {
                Log.d("AAA", t.getMessage());
            }
        });
    }
}