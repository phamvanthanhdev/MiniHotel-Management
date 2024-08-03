package com.minihotel.management.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.minihotel.management.model.PhongChon;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Locale;

public class Common {
    public static final String convertCurrencyVietnamese(Long currency){
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(currency);
    }
    public static final Bitmap decodeBase64ToBitmap(String base64Str) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.decode(base64Str, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
    public static Long calculateBetweenDate(LocalDate date1, LocalDate date2){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            return ChronoUnit.DAYS.between(date1, date2);
        }
        return null;
    }

    public static LocalDate getCurrentDate(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalDate.now();
        }
        return null;
    }

    public static Dialog onCreateMessageDialog(Activity activity, String message) {
        // Use the Builder class for convenient dialog construction.
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
//                    .setPositiveButton(R.string.start, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            // START THE GAME!
//                        }
//                    })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancels the dialog.
                        dialog.dismiss();
                    }
                });
        // Create the AlertDialog object and return it.
        return builder.create();
    }

    public static LocalDate getPlusDayCurrentDate(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalDate.now().plusDays(1);
        }
        return null;
    }

    public static LocalDate getPlusDay(LocalDate localDate){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return localDate.plusDays(1);
        }
        return null;
    }
    // Format Date để hiển thị lên màn hình
    public static String fommatDateShow(LocalDate currentDate){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //EEEE, dd 'thg' MM yyyy
            return currentDate.format(DateTimeFormatter.ofPattern("dd 'thg' MM, yyyy", new Locale("vi")));
        }
        return null;
    }
    //Format Date để gửi request lên server
    public static String fommatDateRequest(LocalDate currentDate){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            return currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return null;
    }


}
