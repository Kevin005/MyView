package com.view.kevin.myview.activity.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import com.view.kevin.myview.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_123 = (Button) findViewById(R.id.btn_123);
        btn_123.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popDialog();
            }
        });
    }

    private void popDialog() {
        View view_dialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.view_calendar, null);
        CalendarView cal_view = (CalendarView) view_dialog.findViewById(R.id.cal_view);
        final Dialog build2 = new Dialog(MainActivity.this, R.style.dialog);
        build2.setContentView(view_dialog);
        build2.setCanceledOnTouchOutside(true);
        build2.show();
        build2.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //TODO post
            }
        });
        cal_view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Log.e("abc", year + "====" + month + "====" + dayOfMonth);
            }
        });
    }
}
