package com.minihotel.management.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minihotel.management.R;
import com.minihotel.management.model.PhongChon;
import com.minihotel.management.utils.Common;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class PhongThueAdapter extends RecyclerView.Adapter<PhongThueAdapter.RecentsViewHolder> {
    Context context;
    List<PhongChon> items;
    OnButtonChonClickListener onButtonChonClickListener;
    public PhongThueAdapter(Context context, List<PhongChon> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.phong_thue_item, null);
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
        holder.txtMaPhong.setText(items.get(position).getMaPhong());
        holder.txtNgayDiThue.setText(Common.fommatDateShow(items.get(position).getNgayDiThue()));
        holder.txtNgayDenThue.setText(Common.fommatDateShow(items.get(position).getNgayDenThue()));
        if(items.get(position).isDaChon()){
            holder.btnChon.setText("Bỏ chọn");
            holder.btnChon.setBackgroundResource(R.drawable.btn_red);
        }else{
            holder.btnChon.setText("Chọn");
            holder.btnChon.setBackgroundResource(R.drawable.btn_blue);
        }

        holder.txtNgayDiThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(items.get(position).isDaChon()){
                    Common.onCreateMessageDialog((Activity) context,
                            "Vui lòng hủy chọn phòng trước khi chọn thời gian thuê").show();
                    return;
                }
                pickDate(holder.txtNgayDiThue, (Activity) context, 1, items.get(position));*/
                Common.onCreateMessageDialog((Activity) context,
                        "Chưa áp dụng chọn phòng cho ngày bắt đầu thuê").show();
            }
        });

        holder.txtNgayDenThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(items.get(position).isDaChon()){
                    Common.onCreateMessageDialog((Activity) context,
                            "Vui lòng hủy chọn phòng trước khi chọn thời gian thuê").show();
                    return;
                }
                pickDate(holder.txtNgayDenThue, (Activity) context, 2, items.get(position));
            }
        });

        holder.btnChon.setOnClickListener(view ->
                onButtonChonClickListener.onClick(
                        position, items.get(position)));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaPhong, txtNgayDiThue, txtNgayDenThue;
        Button btnChon;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaPhong = itemView.findViewById(R.id.txtMaPhong);
            btnChon = itemView.findViewById(R.id.btnChon);
            txtNgayDiThue = itemView.findViewById(R.id.txtNgayDiThue);
            txtNgayDenThue = itemView.findViewById(R.id.txtNgayDenThue);
        }
    }

    public static void pickDate(TextView txt, Activity activity, int type, PhongChon phongChon){
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DAY_OF_MONTH);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month, dayOfMonth);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalDate chooseDate = LocalDate.of(year, month + 1, dayOfMonth);
                    txt.setText(Common.fommatDateShow(chooseDate));
                    if(type == 1) // Ngay di thue
                        phongChon.setNgayDiThue(chooseDate);
                    else if(type == 2)
                        phongChon.setNgayDenThue(chooseDate);
                }

            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    public void setOnButtonChonClickListener(OnButtonChonClickListener onButtonChonClickListener) {
        this.onButtonChonClickListener = onButtonChonClickListener;
    }

    public interface OnButtonChonClickListener {
        void onClick(int position, PhongChon phongChon);
    }
}
