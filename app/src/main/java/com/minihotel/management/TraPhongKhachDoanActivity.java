package com.minihotel.management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.minihotel.management.adapter.ChiTietPhieuThueAdapter;
import com.minihotel.management.adapter.ChonDichVuAdapter;
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
    private Button btnTimKiem, btnXacNhan;
    private RecyclerView rcChiTietPhieuThue;
    private PhieuThue phieuThue;
    private List<ChiTietPhieuThue> chiTietPhieuThues;
    private ArrayList<Integer> idChiTietPhieuThuesDaChon = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tra_phong_khach_doan);

        initViews();
        setEvents();
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
            public void onSuccess(PhieuThue response) {
                phieuThue = response;
                Log.d("AAA", response.toString());
                getChiTietPhieuThueByPhieuThue(phieuThue.getIdPhieuThue());
            }

            @Override
            public void onError(Throwable t) {
                Log.d("TAG-ERR", t.getMessage());
                Common.onCreateMessageDialog(TraPhongKhachDoanActivity.this,
                        "Không tìm thấy phiếu thuê!").show();
            }
        });
    }

    public void getChiTietPhieuThueByPhieuThue(int id){
        CallChiTietPhieuThueByPhieuThue.getChiTietPhieuThueByPhieuThue(id, new IChiTietPhieuThueByPhieuThue() {
            @Override
            public void onSuccess(List<ChiTietPhieuThue> responses) {
                chiTietPhieuThues = responses;
                setChiTietPhieuThueRecycler();
            }

            @Override
            public void onError(Throwable t) {
                Common.onCreateMessageDialog(TraPhongKhachDoanActivity.this,
                        "Không tìm thấy chi tiết phiếu thuê nào!").show();
            }
        });
    }

    private void setChiTietPhieuThueRecycler(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        rcChiTietPhieuThue.setLayoutManager(layoutManager);
        ChiTietPhieuThueAdapter adapter = new ChiTietPhieuThueAdapter(this, chiTietPhieuThues);
        rcChiTietPhieuThue.setAdapter(adapter);
        adapter.setOnCheckBoxClickListener(new ChiTietPhieuThueAdapter.OnCheckBoxClickListener() {
            @Override
            public void onChecked(int position, boolean isChecked, int idChiTietPhieuThue) {
                if(isChecked == true)
                    idChiTietPhieuThuesDaChon.add(idChiTietPhieuThue);
                if(isChecked == false)
                    idChiTietPhieuThuesDaChon.remove(idChiTietPhieuThue);

                Toast.makeText(TraPhongKhachDoanActivity.this, "size ptdc " + idChiTietPhieuThuesDaChon.size(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void setEvents() {
        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int idPhieuThue = Integer.parseInt(edtMaPhieuThue.getText().toString());
//                getPhieuThueById(idPhieuThue);
                String cmnd = edtCmnd.getText().toString();
                getPhieuThueByCmnd(cmnd);
            }
        });

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idChiTietPhieuThuesDaChon.size() <= 0){
                    Common.onCreateMessageDialog(TraPhongKhachDoanActivity.this,
                            "Chưa chọn chi tiết phiếu thuê nào!").show();
                }else{
                    Intent intent = new Intent(TraPhongKhachDoanActivity.this, TraPhongKhachDoanThanhToanActivity.class);
                    intent.putIntegerArrayListExtra("idChiTietPhieuThuesDaChon", idChiTietPhieuThuesDaChon);
                    intent.putExtra("idphieuThue", phieuThue.getIdPhieuThue());
                    startActivity(intent);
                }
            }
        });
    }

    private void initViews() {
        edtCmnd = findViewById(R.id.edtCmnd);
        rcChiTietPhieuThue = findViewById(R.id.rcChiTietPhieuThue);
        btnTimKiem = findViewById(R.id.btnTimKiem);
        btnXacNhan = findViewById(R.id.btnXacNhan);
    }
}