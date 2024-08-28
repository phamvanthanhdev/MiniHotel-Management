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
import com.minihotel.management.model.HangPhongDat;
import com.minihotel.management.model.ThongTinHangPhong;
import com.minihotel.management.utils.Common;

import java.util.List;

public class ThongTinHangPhongAdapter extends RecyclerView.Adapter<ThongTinHangPhongAdapter.RecentsViewHolder> {
    Context context;
    List<ThongTinHangPhong> items;
    OnButtonThemClickListener onButtonThemClickListener;

    public ThongTinHangPhongAdapter(Context context, List<ThongTinHangPhong> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.thong_tin_hang_phong_item, null);
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
        long donGia = 0;
        if(items.get(position).getGiaKhuyenMai() > 0)
            donGia = items.get(position).getGiaKhuyenMai();
        else 
            donGia = items.get(position).getGiaGoc();
        holder.txtDonGia.setText("Giá phòng: " + Common.convertCurrencyVietnamese(donGia));
        holder.txtSoLuongTrong.setText("Còn trống " + items.get(position).getSoLuongTrong() + " phòng");
        
        holder.btnThem.setOnClickListener(view ->
                onButtonThemClickListener.onClick(
                        position, items.get(position).getIdHangPhong()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenHangPhong, txtDonGia, txtSoLuongTrong;
        Button btnThem;
        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenHangPhong = itemView.findViewById(R.id.txtTenHangPhong);
            txtDonGia = itemView.findViewById(R.id.txtDonGia);
            txtSoLuongTrong = itemView.findViewById(R.id.txtSoLuongTrong);
            btnThem = itemView.findViewById(R.id.btnThem);
        }
    }

    public void setOnButtonThemClickListener(OnButtonThemClickListener onButtonThemClickListener) {
        this.onButtonThemClickListener = onButtonThemClickListener;
    }


    public interface OnButtonThemClickListener {
        void onClick(int position, int idHangPhong);
    }

}
