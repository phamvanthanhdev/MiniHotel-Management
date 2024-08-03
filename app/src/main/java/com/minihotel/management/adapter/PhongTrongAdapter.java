package com.minihotel.management.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minihotel.management.R;
import com.minihotel.management.model.PhongTrong;

import java.util.List;

public class PhongTrongAdapter extends RecyclerView.Adapter<PhongTrongAdapter.RecentsViewHolder> {
    Context context;
    List<PhongTrong> items;
    OnItemClickListener onItemClickListener;

    public PhongTrongAdapter(Context context, List<PhongTrong> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.phong_trong_item, null);
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
        PhongTrong item = items.get(position);
        holder.txtTenPhong.setText(item.getMaPhong());
        holder.txtTang.setText("Tầng " + item.getTang());
        holder.txtTrangThai.setText(item.getTrangThai());
        holder.itemView.setOnClickListener(view ->
                onItemClickListener.onClick(items.get(position).getMaPhong()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenPhong, txtTang, txtTrangThai;
        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenPhong = itemView.findViewById(R.id.txtTenPhong);
            txtTang = itemView.findViewById(R.id.txtTang);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(String maPhong);
    }
}
