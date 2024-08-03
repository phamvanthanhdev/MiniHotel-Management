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
import com.minihotel.management.model.TrangThai;
import com.minihotel.management.utils.Common;

import java.util.List;

public class ChonTrangThaiAdapter extends RecyclerView.Adapter<ChonTrangThaiAdapter.RecentsViewHolder> {
    Context context;
    List<TrangThai> items;
    OnItemClickListener onItemClickListener;
    public ChonTrangThaiAdapter(Context context, List<TrangThai> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chon_trang_thai_item, null);
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
        holder.txtTenTrangThai.setText(items.get(position).getTenTrangThai());

        holder.itemView.setOnClickListener(view ->
                onItemClickListener.onClick(
                        position, items.get(position).getIdTrangThai()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenTrangThai;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenTrangThai = itemView.findViewById(R.id.txtTenTrangThai);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int position, int idTrangThai);
    }
}
