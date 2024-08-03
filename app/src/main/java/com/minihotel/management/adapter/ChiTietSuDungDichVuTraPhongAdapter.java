package com.minihotel.management.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minihotel.management.R;
import com.minihotel.management.model.ChiTietSuDungDichVu;
import com.minihotel.management.utils.Common;

import java.util.List;

public class ChiTietSuDungDichVuTraPhongAdapter extends RecyclerView.Adapter<ChiTietSuDungDichVuTraPhongAdapter.RecentsViewHolder> {
    Context context;
    List<ChiTietSuDungDichVu> items;
    public ChiTietSuDungDichVuTraPhongAdapter(Context context, List<ChiTietSuDungDichVu> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dich_vu_tra_phong_item, null);
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
        holder.txtTenDichVu.setText(items.get(position).getTenDichVu());
        holder.txtDonGia.setText("Đơn giá: " + Common.convertCurrencyVietnamese(items.get(position).getDonGia()) + " VNĐ");
        long tongTien = items.get(position).getDonGia() * items.get(position).getSoLuong();
        holder.txtTongTien.setText("Tổng tiền: " + Common.convertCurrencyVietnamese(tongTien) + " VNĐ");
        holder.txtSoLuong.setText(items.get(position).getSoLuong()+"");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenDichVu, txtDonGia, txtTongTien, txtSoLuong;
        ImageButton btnTang, btnGiam;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenDichVu = itemView.findViewById(R.id.txtTenDichVu);
            txtDonGia = itemView.findViewById(R.id.txtDonGia);
            txtTongTien = itemView.findViewById(R.id.txtTongTien);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
            btnTang = itemView.findViewById(R.id.btnTang);
            btnGiam = itemView.findViewById(R.id.btnGiam);
        }
    }

}
