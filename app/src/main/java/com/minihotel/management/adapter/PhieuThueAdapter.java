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
import com.minihotel.management.model.PhieuThue;
import com.minihotel.management.utils.Common;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class PhieuThueAdapter extends RecyclerView.Adapter<PhieuThueAdapter.RecentsViewHolder> {
    Context context;
    List<PhieuThue> items;
    OnItemClickListener onItemClickListener;
    public PhieuThueAdapter(Context context, List<PhieuThue> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.phieu_thue_item, null);
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
        holder.txtIdPhieuThue.setText("Mã: "+ items.get(position).getIdPhieuThue());
        holder.txtDatTruoc.setText(items.get(position).getIdPhieuDat() == null ? "Không đặt trước" : "Có đặt trước");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate ngayDen = LocalDate.parse(items.get(position).getNgayDen());
            LocalDate ngayDi = LocalDate.parse(items.get(position).getNgayDi());
            holder.txtNgayDen.setText(Common.fommatDateShow(ngayDen));
            holder.txtNgayDi.setText(Common.fommatDateShow(ngayDi));
        }


        holder.itemView.setOnClickListener(view ->
                onItemClickListener.onClick(
                        position, items.get(position).getIdPhieuThue()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder {
        TextView txtIdPhieuThue, txtDatTruoc, txtNgayDen, txtNgayDi;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIdPhieuThue = itemView.findViewById(R.id.txtIdPhieuThue);
            txtDatTruoc = itemView.findViewById(R.id.txtDatTruoc);
            txtNgayDen = itemView.findViewById(R.id.txtNgayDen);
            txtNgayDi = itemView.findViewById(R.id.txtNgayDi);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int position, int idPhieuThue);
    }
}
