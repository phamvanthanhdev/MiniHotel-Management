package com.minihotel.management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.minihotel.management.managers.calls.CallPhieuDatById;
import com.minihotel.management.managers.interfaces.IPhieuDatById;
import com.minihotel.management.model.PhieuDat;
import com.minihotel.management.utils.Common;
import com.minihotel.management.utils.Utils;

public class LuaChonThueActivity extends AppCompatActivity {
    private TextInputEditText edtMaDatPhong;
    private Button btnChuaDatPhong, btnXacNhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lua_chon_thue);

        setViews();
        setEvents();
    }

    private void setEvents() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtMaDatPhong.getText().toString().trim().equals("")){
                    Common.onCreateMessageDialog(LuaChonThueActivity.this,
                            "Vui lòng nhập đầy đủ thông tin").show();
                }else{
                    int idPhieuDat = Integer.parseInt(edtMaDatPhong.getText().toString());
                    getPhieuDatById(idPhieuDat);
                }
            }
        });
    }

    private void setViews() {
        edtMaDatPhong = findViewById(R.id.edtMaDatPhong);
        btnChuaDatPhong = findViewById(R.id.btnChuaDatPhong);
        btnXacNhan = findViewById(R.id.btnXacNhan);
    }

    private void getPhieuDatById(int idPhieuDat){
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
    }
}