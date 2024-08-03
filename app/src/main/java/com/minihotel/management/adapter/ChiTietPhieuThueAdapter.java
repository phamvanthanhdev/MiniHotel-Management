package com.minihotel.management.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minihotel.management.R;
import com.minihotel.management.model.ChiTietPhieuThue;
import com.minihotel.management.model.DichVu;
import com.minihotel.management.utils.Common;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class ChiTietPhieuThueAdapter extends RecyclerView.Adapter<ChiTietPhieuThueAdapter.RecentsViewHolder> {
    Context context;
    List<ChiTietPhieuThue> items;
    OnCheckBoxClickListener onCheckBoxClickListener;
    public ChiTietPhieuThueAdapter(Context context, List<ChiTietPhieuThue> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chi_tiet_phieu_thue_item, null);
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
        if(!items.get(position).getDaThanhToan()) {
            holder.txtTenPhong.setText(items.get(position).getMaPhong());
        }else {
            holder.txtTenPhong.setText(items.get(position).getMaPhong() + " - " + "Đã thanh toán");
            holder.cbChon.setVisibility(View.INVISIBLE);
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate ngayDen = LocalDate.parse(items.get(position).getNgayDen());
            LocalDate ngayDi = LocalDate.parse(items.get(position).getNgayDi());
            holder.txtNgayDen.setText(Common.fommatDateShow(ngayDen));
            holder.txtNgayDi.setText(Common.fommatDateShow(ngayDi));
        }
        holder.cbChon.setOnCheckedChangeListener((view, isChecked) ->
                onCheckBoxClickListener.onChecked(position,isChecked, items.get(position).getIdChiTietPhieuThue()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenPhong, txtNgayDen, txtNgayDi;
        CheckBox cbChon;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenPhong = itemView.findViewById(R.id.txtTenPhong);
            txtNgayDen = itemView.findViewById(R.id.txtNgayDen);
            txtNgayDi = itemView.findViewById(R.id.txtNgayDi);
            cbChon = itemView.findViewById(R.id.cbChon);
        }
    }

    public void setOnCheckBoxClickListener(OnCheckBoxClickListener onCheckBoxClickListener) {
        this.onCheckBoxClickListener = onCheckBoxClickListener;
    }

    public interface OnCheckBoxClickListener {
        void onChecked(int position, boolean isChecked, int idChiTietPhieuThue);
    }
}
