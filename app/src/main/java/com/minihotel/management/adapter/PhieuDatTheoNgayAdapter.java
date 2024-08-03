package com.minihotel.management.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minihotel.management.R;
import com.minihotel.management.model.ChiTietSuDungDichVu;
import com.minihotel.management.model.PhieuDatTheoNgay;
import com.minihotel.management.utils.Common;

import java.time.LocalDate;
import java.util.List;

public class PhieuDatTheoNgayAdapter extends RecyclerView.Adapter<PhieuDatTheoNgayAdapter.RecentsViewHolder> {
    Context context;
    List<PhieuDatTheoNgay> items;
    OnItemClickListener onItemClickListener;
    public PhieuDatTheoNgayAdapter(Context context, List<PhieuDatTheoNgay> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.phieu_dat_theo_ngay_item, null);
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
        holder.txtTenKhachHang.setText(items.get(position).getTenKhachHang());
        holder.txtCmnd.setText("Cmnd: " + items.get(position).getCmnd());
        holder.txtIdPhieuDat.setText(items.get(position).getIdPhieuDat()+"");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.txtNgayDen.setText(Common.fommatDateShow(LocalDate.parse(items.get(position).getNgayBatDau())));
            holder.txtNgayDi.setText(Common.fommatDateShow(LocalDate.parse(items.get(position).getNgayTraPhong())));
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
        TextView txtTenKhachHang, txtCmnd, txtNgayDen, txtNgayDi, txtIdPhieuDat;
        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIdPhieuDat = itemView.findViewById(R.id.txtIdPhieuDat);
            txtTenKhachHang = itemView.findViewById(R.id.txtTenKhachHang);
            txtCmnd = itemView.findViewById(R.id.txtCmnd);
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
