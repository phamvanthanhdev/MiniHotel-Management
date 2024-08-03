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
import com.minihotel.management.model.ChiTietPhuThu;
import com.minihotel.management.utils.Common;

import java.util.List;

public class ChiTietPhuThuAdapter extends RecyclerView.Adapter<ChiTietPhuThuAdapter.RecentsViewHolder> {
    Context context;
    List<ChiTietPhuThu> items;
    OnButtonTangClickListener onButtonTangClickListener;
    OnButtonGiamClickListener onButtonGiamClickListener;
    public ChiTietPhuThuAdapter(Context context, List<ChiTietPhuThu> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.phu_thu_item, null);
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
        holder.txtNoiDung.setText(items.get(position).getNoiDung());
        holder.txtDonGia.setText("Đơn giá: " + Common.convertCurrencyVietnamese(items.get(position).getDonGia()) + " VNĐ");
        long tongTien = items.get(position).getDonGia() * items.get(position).getSoLuong();
        holder.txtTongTien.setText("Tổng tiền: " + Common.convertCurrencyVietnamese(tongTien) + " VNĐ");
        holder.txtSoLuong.setText(items.get(position).getSoLuong()+"");

        holder.btnTang.setOnClickListener(view ->
                onButtonTangClickListener.onClick(
                        position, items.get(position).getIdPhuThu(), items.get(position).getDonGia()));

        holder.btnGiam.setOnClickListener(view ->
                onButtonGiamClickListener.onClick(
                        position, items.get(position).getIdPhuThu(), items.get(position).getDonGia()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder {
        TextView txtNoiDung, txtDonGia, txtTongTien, txtSoLuong;
        ImageButton btnTang, btnGiam;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNoiDung = itemView.findViewById(R.id.txtNoiDung);
            txtDonGia = itemView.findViewById(R.id.txtDonGia);
            txtTongTien = itemView.findViewById(R.id.txtTongTien);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
            btnTang = itemView.findViewById(R.id.btnTang);
            btnGiam = itemView.findViewById(R.id.btnGiam);
        }
    }

    public void setOnButtonTangClickListener(OnButtonTangClickListener onButtonTangClickListener) {
        this.onButtonTangClickListener = onButtonTangClickListener;
    }

    public interface OnButtonTangClickListener {
        void onClick(int position, int idDichVu, long donGia);
    }

    public void setOnButtonGiamClickListener(OnButtonGiamClickListener onButtonGiamClickListener) {
        this.onButtonGiamClickListener = onButtonGiamClickListener;
    }

    public interface OnButtonGiamClickListener {
        void onClick(int position, int idDichVu, long donGia);
    }
}
