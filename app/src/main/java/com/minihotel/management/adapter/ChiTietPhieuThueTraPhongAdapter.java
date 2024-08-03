package com.minihotel.management.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minihotel.management.R;
import com.minihotel.management.model.ChiTietPhieuThue;
import com.minihotel.management.utils.Common;

import java.time.LocalDate;
import java.util.List;

public class ChiTietPhieuThueTraPhongAdapter extends RecyclerView.Adapter<ChiTietPhieuThueTraPhongAdapter.RecentsViewHolder> {
    Context context;
    List<ChiTietPhieuThue> items;
    public ChiTietPhieuThueTraPhongAdapter(Context context, List<ChiTietPhieuThue> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chitiet_phieuthue_traphong_item, null);
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
        holder.txtTenPhong.setText(items.get(position).getMaPhong());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate ngayDen = LocalDate.parse(items.get(position).getNgayDen());
            LocalDate ngayDi = LocalDate.parse(items.get(position).getNgayDi());
            long soNgayThue = Common.calculateBetweenDate(ngayDen, ngayDi);
            holder.txtSoNgayThue.setText("Số ngày thuê: " + soNgayThue + " ngày");
            long donGia = items.get(position).getDonGia();
            holder.txtDonGia.setText("Đơn giá: " + Common.convertCurrencyVietnamese(donGia));
            long tongTien = donGia * soNgayThue;
            holder.txtTongTien.setText("Tổng tiền: " + Common.convertCurrencyVietnamese(tongTien));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenPhong, txtSoNgayThue, txtDonGia, txtTongTien;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenPhong = itemView.findViewById(R.id.txtTenPhong);
            txtSoNgayThue = itemView.findViewById(R.id.txtSoNgayThue);
            txtDonGia = itemView.findViewById(R.id.txtDonGia);
            txtTongTien = itemView.findViewById(R.id.txtTongTien);
        }
    }

}
