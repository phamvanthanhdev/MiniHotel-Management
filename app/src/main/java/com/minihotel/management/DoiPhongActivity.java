package com.minihotel.management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.minihotel.management.adapter.PhongHienTaiAdapter;
import com.minihotel.management.dto.ResultResponse;
import com.minihotel.management.managers.calls.CallDoiPhong;
import com.minihotel.management.managers.calls.CallPhongsHienTai;
import com.minihotel.management.managers.interfaces.IDoiPhong;
import com.minihotel.management.managers.interfaces.IPhongsHienTai;
import com.minihotel.management.model.PhongHienTai;
import com.minihotel.management.utils.Common;

import java.util.List;

public class DoiPhongActivity extends AppCompatActivity {
    private List<PhongHienTai> phongHienTais;
    private RecyclerView rcPhongHienTai;
    private int idChiTietPhieuThue;
    private String maPhong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_phong);

        idChiTietPhieuThue = getIntent().getIntExtra("idChiTietPhieuThue", 0);
        maPhong = getIntent().getStringExtra("maPhong");
        initViews();
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
        getPhongsHienTai();
    }
    private void initViews() {
        rcPhongHienTai = findViewById(R.id.rcPhongHienTai);
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
                Toast.makeText(DoiPhongActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
            public void onClick(String maPhong, int idHangPhong, Long giaPhong, Integer idChiTietPhieuThueSoDo, String trangThai, View view, LinearLayout layout) {
                if(!trangThai.equals("Sạch sẽ")){
                    Common.onCreateMessageDialog(DoiPhongActivity.this,
                            "Trạng thái phòng không sẵn sàng để thuê!").show();
                    return;
                }

                if(idChiTietPhieuThueSoDo != null){
                    Common.onCreateMessageDialog(DoiPhongActivity.this,
                            "Phòng này đang được cho thuê!").show();
                    return;
                }
                doiPhongKhachSan(idChiTietPhieuThue, maPhong);

            }
        });

    }

    private void doiPhongKhachSan(int idChiTietPhieuThue, String maPhong){
        CallDoiPhong.doiPhongKhachSan(idChiTietPhieuThue, maPhong, new IDoiPhong() {
            @Override
            public void onSuccess(ResultResponse response) {
                if(response.getCode() == 200){
                    Common.onCreateMessageDialog(DoiPhongActivity.this,
                            response.getMessage()).show();
                    finish();
                }else{
                    Common.onCreateMessageDialog(DoiPhongActivity.this,
                            response.getMessage()).show();
                }
            }

            @Override
            public void onError(Throwable t) {
                Log.d("TAG-ERR", t.getMessage());
                Common.onCreateMessageDialog(DoiPhongActivity.this,
                        "Lỗi đổi phòng!").show();
            }
        });
    }
}