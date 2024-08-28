package com.minihotel.management.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minihotel.management.R;
import com.minihotel.management.model.KhachThue;
import com.minihotel.management.model.PhieuDat;
import com.minihotel.management.utils.Common;

import java.time.LocalDate;
import java.util.List;

public class KhachThueAdapter extends RecyclerView.Adapter<KhachThueAdapter.RecentsViewHolder> {
    Context context;
    List<KhachThue> items;
    OnItemClickListener onItemClickListener;
    OnButtonXoaClickListener onButtonXoaClickListener;
    public KhachThueAdapter(Context context, List<KhachThue> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.khach_thue_item, null);
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
        holder.txtMaPhong.setText("Mã phòng: "+ items.get(position).getMaPhong());
        holder.txtHoTen.setText(items.get(position).getHoTen());
        holder.txtSdt.setText(items.get(position).getSdt());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate ngayDen = LocalDate.parse(items.get(position).getNgayDen());
            LocalDate ngayDi = LocalDate.parse(items.get(position).getNgayDi());
            holder.txtNgayDen.setText(Common.fommatDateShow(ngayDen));
            holder.txtNgayDi.setText(Common.fommatDateShow(ngayDi));
        }

        holder.btnXoaKhachThue.setOnClickListener(view ->
                onButtonXoaClickListener.onClick(
                        position, items.get(position).getMaPhong(), items.get(position).getSdt()));

        holder.itemView.setOnClickListener(view ->
                onItemClickListener.onClick(
                        position, items.get(position).getMaPhong()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaPhong, txtHoTen, txtSdt, txtNgayDen, txtNgayDi;
        ImageButton btnXoaKhachThue;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaPhong = itemView.findViewById(R.id.txtMaPhong);
            txtHoTen = itemView.findViewById(R.id.txtHoTen);
            txtSdt = itemView.findViewById(R.id.txtSdt);
            txtNgayDen = itemView.findViewById(R.id.txtNgayDen);
            txtNgayDi = itemView.findViewById(R.id.txtNgayDi);
            btnXoaKhachThue = itemView.findViewById(R.id.btnXoaKhachThue);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int position, String maPhong);
    }

    public void setOnButtonXoaClickListener(OnButtonXoaClickListener onButtonXoaClickListener) {
        this.onButtonXoaClickListener = onButtonXoaClickListener;
    }

    public interface OnButtonXoaClickListener {
        void onClick(int position, String maPhong, String sdt);
    }
}
