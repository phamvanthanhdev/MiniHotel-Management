package com.minihotel.management.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minihotel.management.R;
import com.minihotel.management.model.DoanhThuTheoNgay;
import com.minihotel.management.model.HoaDonNgay;
import com.minihotel.management.utils.Common;

import java.time.LocalDate;
import java.util.List;

public class DoanhThuNgayAdapter extends RecyclerView.Adapter<DoanhThuNgayAdapter.RecentsViewHolder> {
    Context context;
    List<DoanhThuTheoNgay> items;
    OnItemClickListener onItemClickListener;

    public DoanhThuNgayAdapter(Context context, List<DoanhThuTheoNgay> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.doanh_thu_ngay_item, null);
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
        DoanhThuTheoNgay item = items.get(position);
        holder.txtTongTien.setText(Common.convertCurrencyVietnamese(item.getTongTien()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.txtNgayTao.setText(Common.fommatDateShow(LocalDate.parse(item.getNgayTao())));
        }

        holder.itemView.setOnClickListener(view ->
                onItemClickListener.onClick(position, item.getNgayTao()));

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder {

        TextView txtTongTien, txtNgayTao;
        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTongTien = itemView.findViewById(R.id.txtTongTien);
            txtNgayTao = itemView.findViewById(R.id.txtNgayTao);
        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int position, String ngayTao);
    }
}
