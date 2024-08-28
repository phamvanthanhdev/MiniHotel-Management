package com.minihotel.management.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.minihotel.management.R;
import com.minihotel.management.model.HoaDonNgay;
import com.minihotel.management.model.PhongHienTai;
import com.minihotel.management.utils.Common;

import java.time.LocalDate;
import java.util.List;

public class HoaDonNgayAdapter extends RecyclerView.Adapter<HoaDonNgayAdapter.RecentsViewHolder> {
    Context context;
    List<HoaDonNgay> items;
    OnItemClickListener onItemClickListener;

    public HoaDonNgayAdapter(Context context, List<HoaDonNgay> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hoa_don_ngay_item, null);
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
        HoaDonNgay item = items.get(position);
        holder.txtSoHoaDon.setText("Số hóa đơn: " + item.getSoHoaDon());
        holder.txtTongDichVu.setText("Dịch vụ: " + Common.convertCurrencyVietnamese(item.getTongDichVu()));
        holder.txtTongPhuThu.setText("Phụ thu: " + Common.convertCurrencyVietnamese(item.getTongPhuThu()));
        holder.txtTongTienPhong.setText(Common.convertCurrencyVietnamese(item.getTongGiaPhong()));
        long tongThu = item.getTongGiaPhong() + item.getTongDichVu() + item.getTongPhuThu();
        holder.txtTongThu.setText(Common.convertCurrencyVietnamese(tongThu));
        holder.txtThucThu.setText(Common.convertCurrencyVietnamese(item.getTongTien()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.txtNgayTao.setText(Common.fommatDateShow(LocalDate.parse(item.getNgayTao())));
        }

        holder.itemView.setOnClickListener(view ->
                onItemClickListener.onClick(position, item.getSoHoaDon()));

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder {

        TextView txtSoHoaDon, txtTongDichVu, txtTongPhuThu, txtTongTienPhong;
        TextView txtTongThu, txtThucThu, txtNgayTao;
        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSoHoaDon = itemView.findViewById(R.id.txtSoHoaDon);
            txtTongDichVu = itemView.findViewById(R.id.txtTongDichVu);
            txtTongPhuThu = itemView.findViewById(R.id.txtTongPhuThu);
            txtTongTienPhong = itemView.findViewById(R.id.txtTongTienPhong);
            txtTongThu = itemView.findViewById(R.id.txtTongThu);
            txtThucThu = itemView.findViewById(R.id.txtThucThu);
            txtNgayTao = itemView.findViewById(R.id.txtNgayTao);
        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int position, String soHoaDon);
    }
}
