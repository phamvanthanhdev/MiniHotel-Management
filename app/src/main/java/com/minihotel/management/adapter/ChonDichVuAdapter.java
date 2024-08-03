package com.minihotel.management.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minihotel.management.R;
import com.minihotel.management.model.DichVu;
import com.minihotel.management.model.PhongChon;
import com.minihotel.management.utils.Common;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class ChonDichVuAdapter extends RecyclerView.Adapter<ChonDichVuAdapter.RecentsViewHolder> {
    Context context;
    List<DichVu> items;
    OnButtonThemClickListener onButtonThemClickListener;
    public ChonDichVuAdapter(Context context, List<DichVu> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chon_dich_vu_item, null);
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

        holder.btnThem.setOnClickListener(view ->
                onButtonThemClickListener.onClick(
                        position, items.get(position).getIdDichVu(), items.get(position).getDonGia()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenDichVu, txtDonGia;
        Button btnThem;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenDichVu = itemView.findViewById(R.id.txtTenDichVu);
            txtDonGia = itemView.findViewById(R.id.txtDonGia);
            btnThem = itemView.findViewById(R.id.btnThem);
        }
    }

    public void setOnButtonThemClickListener(OnButtonThemClickListener onButtonThemClickListener) {
        this.onButtonThemClickListener = onButtonThemClickListener;
    }

    public interface OnButtonThemClickListener {
        void onClick(int position, int idDichVu, long donGia);
    }
}
