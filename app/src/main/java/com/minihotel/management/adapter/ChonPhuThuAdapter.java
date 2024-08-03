package com.minihotel.management.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minihotel.management.R;
import com.minihotel.management.model.PhuThu;
import com.minihotel.management.utils.Common;

import java.util.List;

public class ChonPhuThuAdapter extends RecyclerView.Adapter<ChonPhuThuAdapter.RecentsViewHolder> {
    Context context;
    List<PhuThu> items;
    OnButtonThemClickListener onButtonThemClickListener;
    public ChonPhuThuAdapter(Context context, List<PhuThu> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chon_phu_thu_item, null);
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

        holder.btnThem.setOnClickListener(view ->
                onButtonThemClickListener.onClick(
                        position, items.get(position).getIdPhuThu(), items.get(position).getDonGia()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder {
        TextView txtNoiDung, txtDonGia;
        Button btnThem;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNoiDung = itemView.findViewById(R.id.txtNoiDung);
            txtDonGia = itemView.findViewById(R.id.txtDonGia);
            btnThem = itemView.findViewById(R.id.btnThem);
        }
    }

    public void setOnButtonThemClickListener(OnButtonThemClickListener onButtonThemClickListener) {
        this.onButtonThemClickListener = onButtonThemClickListener;
    }

    public interface OnButtonThemClickListener {
        void onClick(int position, int idPhuThu, long donGia);
    }
}
