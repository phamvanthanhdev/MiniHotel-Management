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
import com.minihotel.management.model.ChiTietPhieuDat;
import com.minihotel.management.utils.Common;

import java.util.List;

public class ChiTietPhieuDatAdapter extends RecyclerView.Adapter<ChiTietPhieuDatAdapter.RecentsViewHolder> {
    Context context;
    List<ChiTietPhieuDat> items;

    public ChiTietPhieuDatAdapter(Context context, List<ChiTietPhieuDat> items) {
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
        holder.txtSoLuongTrong.setText("Còn trống " + " phòng");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenHangPhong, txtDonGia, txtSoLuongDaDat, txtSoLuongTrong;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenHangPhong = itemView.findViewById(R.id.txtTenHangPhong);
            txtDonGia = itemView.findViewById(R.id.txtDonGia);
            txtSoLuongDaDat = itemView.findViewById(R.id.txtSoLuongDaDat);
            txtSoLuongTrong = itemView.findViewById(R.id.txtSoLuongTrong);
        }
    }
}
