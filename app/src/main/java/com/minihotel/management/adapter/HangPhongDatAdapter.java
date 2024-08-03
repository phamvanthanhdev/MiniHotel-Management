package com.minihotel.management.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minihotel.management.R;
import com.minihotel.management.model.HangPhongDat;
import com.minihotel.management.utils.Common;

import java.util.List;

public class HangPhongDatAdapter extends RecyclerView.Adapter<HangPhongDatAdapter.RecentsViewHolder> {
    Context context;
    List<HangPhongDat> items;
    OnButtonTangClickListener onButtonTangClickListener;
    OnButtonGiamClickListener onButtonGiamClickListener;

    public HangPhongDatAdapter(Context context, List<HangPhongDat> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hang_phong_thue_item, null);
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
        holder.txtSoLuongDaDat.setText("Đã đặt " + items.get(position).getSoLuong() + " phòng");
        holder.txtDonGia.setText(Common.convertCurrencyVietnamese(items.get(position).getDonGia()) + " VNĐ");
        holder.txtSoLuongTrong.setText("Còn trống " + items.get(position).getSoLuongTrong() + " phòng");
        holder.txtSoLuong.setText(items.get(position).getSoLuong() + "");

        holder.btnTang.setOnClickListener(view ->
                onButtonTangClickListener.onClick(
                        position, items.get(position).getIdHangPhong(),
                        items.get(position).getSoLuong(), items.get(position).getSoLuongTrong()));
        holder.btnGiam.setOnClickListener(view ->
                onButtonGiamClickListener.onClick(
                        position, items.get(position).getIdHangPhong(), items.get(position).getSoLuong()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenHangPhong, txtDonGia, txtSoLuongDaDat, txtSoLuongTrong, txtSoLuong;
        TextView btnTang, btnGiam;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenHangPhong = itemView.findViewById(R.id.txtTenHangPhong);
            txtDonGia = itemView.findViewById(R.id.txtDonGia);
            txtSoLuongDaDat = itemView.findViewById(R.id.txtSoLuongDaDat);
            txtSoLuongTrong = itemView.findViewById(R.id.txtSoLuongTrong);
            btnTang = itemView.findViewById(R.id.btnTang);
            btnGiam = itemView.findViewById(R.id.btnGiam);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
        }
    }

    public void setOnButtonTangClickListener(OnButtonTangClickListener onButtonTangClickListener) {
        this.onButtonTangClickListener = onButtonTangClickListener;
    }

    public void setOnButtonGiamClickListener(OnButtonGiamClickListener onButtonGiamClickListener) {
        this.onButtonGiamClickListener = onButtonGiamClickListener;
    }

    public interface OnButtonTangClickListener {
        void onClick(int position, int id, int soLuong, int soLuongTrong);
    }

    public interface OnButtonGiamClickListener {
        void onClick(int position, int id, int soLuong);
    }
}
