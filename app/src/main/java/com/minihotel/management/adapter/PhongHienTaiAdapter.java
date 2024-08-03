package com.minihotel.management.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.minihotel.management.R;
import com.minihotel.management.model.PhongHienTai;
import com.minihotel.management.utils.Common;

import java.time.LocalDate;
import java.util.List;

public class PhongHienTaiAdapter extends RecyclerView.Adapter<PhongHienTaiAdapter.RecentsViewHolder> {
    Context context;
    List<PhongHienTai> items;
    OnItemClickListener onItemClickListener;

    public PhongHienTaiAdapter(Context context, List<PhongHienTai> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.phong_hien_tai_item, null);
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
        PhongHienTai item = items.get(position);
        holder.txtTenPhong.setText(item.getMaPhong());
        holder.txtTenHangPhong.setText(item.getTenHangPhong());
        if(item.getGiaKhuyenMai() == 0){
            holder.txtGiaPhong.setText(Common.convertCurrencyVietnamese(item.getGiaGoc()) + " VNĐ");
        }else{
            holder.txtGiaPhong.setText(Common.convertCurrencyVietnamese(item.getGiaKhuyenMai()) + " VNĐ");
        }
        if(!item.getTenTrangThai().trim().equals("Sạch sẽ")){
            holder.txtTrangThai.setTextColor(Color.RED);
        }
        holder.txtTrangThai.setText(item.getTenTrangThai());

        if(item.getDaThue()){
            long betweenDate = -1;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                betweenDate = Common.calculateBetweenDate(Common.getCurrentDate(), LocalDate.parse(item.getNgayDi()));
            }
            if(betweenDate == 0){
                holder.txtThoiGian.setText("Trả phòng vào hôm nay");
            }else {
                holder.txtThoiGian.setText(betweenDate + " ngày nữa trả phòng");
            }
            holder.layout.setBackgroundResource(R.color.bg_warning);
        }else{
            if(item.getTenTrangThai().trim().equals("Sạch sẽ")) {
                holder.txtThoiGian.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.green));
                holder.txtThoiGian.setText("Có thể cho thuê");
            }else{
                holder.txtThoiGian.setText("");
            }
        }

        holder.itemView.setOnClickListener(view ->
                onItemClickListener.onClick(item.getMaPhong(), item.getIdChiTietPhieuThue(), holder.itemView));

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenPhong, txtTenHangPhong, txtGiaPhong, txtTrangThai, txtThoiGian;
        LinearLayout layout;
        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenPhong = itemView.findViewById(R.id.txtTenPhong);
            txtTenHangPhong = itemView.findViewById(R.id.txtTenHangPhong);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
            txtGiaPhong = itemView.findViewById(R.id.txtGiaPhong);
            txtThoiGian = itemView.findViewById(R.id.txtThoiGian);
            layout = itemView.findViewById(R.id.layout);
        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(String maPhong, Integer idChiTietPhieuThue, View view);
    }
}
