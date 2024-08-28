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
import com.minihotel.management.model.PhieuDat;
import com.minihotel.management.model.PhieuThue;
import com.minihotel.management.utils.Common;

import java.time.LocalDate;
import java.util.List;

public class PhieuDatKhachHangAdapter extends RecyclerView.Adapter<PhieuDatKhachHangAdapter.RecentsViewHolder> {
    Context context;
    List<PhieuDat> items;
    OnItemClickListener onItemClickListener;
    public PhieuDatKhachHangAdapter(Context context, List<PhieuDat> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.phieu_dat_item, null);
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
        holder.txtIdPhieuDat.setText("Mã: "+ items.get(position).getIdPhieuDat());
        holder.txtTamUng.setText("Tiền tạm ứng: " + Common.convertCurrencyVietnamese((long) items.get(position).getTienTamUng()));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate ngayDen = LocalDate.parse(items.get(position).getNgayBatDau());
            LocalDate ngayDi = LocalDate.parse(items.get(position).getNgayTraPhong());
            holder.txtNgayDen.setText(Common.fommatDateShow(ngayDen));
            holder.txtNgayDi.setText(Common.fommatDateShow(ngayDi));
        }


        holder.itemView.setOnClickListener(view ->
                onItemClickListener.onClick(
                        position, items.get(position).getIdPhieuDat()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder {
        TextView txtIdPhieuDat, txtTamUng, txtNgayDen, txtNgayDi;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIdPhieuDat = itemView.findViewById(R.id.txtIdPhieuDat);
            txtTamUng = itemView.findViewById(R.id.txtTamUng);
            txtNgayDen = itemView.findViewById(R.id.txtNgayDen);
            txtNgayDi = itemView.findViewById(R.id.txtNgayDi);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int position, int idPhieuDat);
    }
}
