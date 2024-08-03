package com.minihotel.management.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minihotel.management.R;
import com.minihotel.management.dto.ResultResponse;
import com.minihotel.management.managers.calls.CallKiemTraPhongThue;
import com.minihotel.management.managers.calls.CallPhongTrongByIdHangPhong;
import com.minihotel.management.managers.interfaces.IKiemTraPhongThue;
import com.minihotel.management.managers.interfaces.IPhongTrongByIdHangPhong;
import com.minihotel.management.model.HangPhongDat;
import com.minihotel.management.model.PhongChon;
import com.minihotel.management.model.PhongTrong;
import com.minihotel.management.utils.Common;
import com.minihotel.management.utils.Utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ChonPhongThueAdapter extends RecyclerView.Adapter<ChonPhongThueAdapter.RecentsViewHolder> {
    Context context;
    List<HangPhongDat> items;
    String ngayDenDat, ngayDiDat;
//    OnButtonClickListener onButtonClickListener;

    public ChonPhongThueAdapter(Context context, List<HangPhongDat> items, String ngayDenDat, String ngayDiDat) {
        this.context = context;
        this.items = items;
        this.ngayDenDat = ngayDenDat;
        this.ngayDiDat = ngayDiDat;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chon_phong_thue_item, null);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.width = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        layoutParams.setMargins(10, 0, 10, 20);
        view.setLayoutParams(layoutParams);
        return new RecentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentsViewHolder holder, int position) {
        holder.txtTenHangPhong.setText(items.get(position).getTenHangPhong());
        holder.txtSoLuong.setText("Đã đặt " + items.get(position).getSoLuong() + " phòng");
        getPhongTrongByIdHangPhong(items.get(position),
                                    ngayDenDat, ngayDiDat, holder.rcChonPhongThue, holder.itemView);

    }

    private void getPhongTrongByIdHangPhong(HangPhongDat hangPhongChon, String ngayDenThue, String ngayDiThue, RecyclerView rcPhongThue, View view){
        CallPhongTrongByIdHangPhong.getPhongTrongByIdHangPhong(hangPhongChon.getIdHangPhong(), ngayDenThue, ngayDiThue, new IPhongTrongByIdHangPhong() {
            @Override
            public void onSuccess(List<PhongTrong> phongTrongs) {
                List<PhongChon> phongChons = new ArrayList<>();
                for (PhongTrong phongTrong:phongTrongs) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        phongChons.add(Utils.convertPhongTrongToPhongChon(phongTrong, LocalDate.parse(ngayDenThue), LocalDate.parse(ngayDiThue)));
                    }
                }
                setPhongThueRecycler(rcPhongThue, view, phongChons, hangPhongChon);
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }

    private void setPhongThueRecycler(RecyclerView rcPhongThue, View view, List<PhongChon> phongChons, HangPhongDat hangPhongChon){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL, false);
        rcPhongThue.setLayoutManager(layoutManager);
        PhongThueAdapter adapter = new PhongThueAdapter(view.getContext(), phongChons);
        rcPhongThue.setAdapter(adapter);
        adapter.setOnButtonChonClickListener(new PhongThueAdapter.OnButtonChonClickListener() {
            @Override
            public void onClick(int position, PhongChon phongChon) {
                //Kiểm tra số phòng đã chọn có vượt quá số phòng thuộc hạng phòng đã chọn trước đó
                if(!phongChon.isDaChon() && Utils.soLuongPhongThuocHangPhong(phongChon.getIdHangPhong()) >= hangPhongChon.getSoLuong()){
                    Common.onCreateMessageDialog((Activity) view.getContext(),
                            "Số lượng phòng không thể vượt quá số lượng đã chọn!").show();
                    return;
                }
                //Kiểm tra thời gian muốn thuê có còn trống 1 phòng thuộc hạng phòng chứa phòng muốn chọn không
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    //Nếu phòng chưa được chọn
                    if(!phongChon.isDaChon()) {
                        //Nếu thời gian thuê lớn hơn thời gian đặt trước đó thì phải kiểm tra
                        if(phongChon.getNgayDenThue().isAfter(LocalDate.parse(ngayDiDat))) {
                            LocalDate ngayBatDauThueThem = Common.getPlusDay(LocalDate.parse(ngayDiDat));
                            LocalDate ngayKetThucThueThem = phongChon.getNgayDenThue();
                            int idHangPhong = phongChon.getIdHangPhong();
                            CallKiemTraPhongThue.kiemTraPhongThue(idHangPhong,
                                    Common.fommatDateRequest(ngayBatDauThueThem),
                                    Common.fommatDateRequest(ngayKetThucThueThem),
                                    new IKiemTraPhongThue() {
                                        @Override
                                        public void onSuccess(ResultResponse resultResponse) {
                                            if (resultResponse.getCode() == 200) {
                                                Utils.addPhongChon(phongChon);
                                                phongChon.setDaChon(true);
                                                adapter.notifyItemChanged(position);
                                            } else {
                                                Common.onCreateMessageDialog((Activity) view.getContext(),
                                                        "Số lượng phòng không đủ để thuê thêm!").show();
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable t) {
                                            Log.d("TAG-ERR", t.getMessage());
                                        }
                                    });
                        }else{
                            Utils.addPhongChon(phongChon);
                            phongChon.setDaChon(true);
                            adapter.notifyItemChanged(position);
                        }
                    }else{
                        Utils.removePhongChon(phongChon);
                        phongChon.setDaChon(false);
                        adapter.notifyItemChanged(position);
                    }
                }*/
                if(!phongChon.isDaChon()) {
                    Utils.addPhongChon(phongChon);
                    phongChon.setDaChon(true);
                    adapter.notifyItemChanged(position);
                }else{
                    Utils.removePhongChon(phongChon);
                    phongChon.setDaChon(false);
                    adapter.notifyItemChanged(position);
                }
                Log.d("AAA", "size " + Utils.phongChons.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenHangPhong, txtSoLuong;
        RecyclerView rcChonPhongThue;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenHangPhong = itemView.findViewById(R.id.txtTenHangPhong);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
            rcChonPhongThue = itemView.findViewById(R.id.rcChonPhongThue);
        }
    }

//    public void setOnButtonTangClickListener(OnButtonTangClickListener onButtonTangClickListener) {
//        this.onButtonTangClickListener = onButtonTangClickListener;
//    }
//
//    public void setOnButtonGiamClickListener(OnButtonGiamClickListener onButtonGiamClickListener) {
//        this.onButtonGiamClickListener = onButtonGiamClickListener;
//    }
//
//    public interface OnButtonTangClickListener {
//        void onClick(int position, int id, int soLuong, int soLuongTrong);
//    }
//
//    public interface OnButtonGiamClickListener {
//        void onClick(int position, int id, int soLuong);
//    }
}
