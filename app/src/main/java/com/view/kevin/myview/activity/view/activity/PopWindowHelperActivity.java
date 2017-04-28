package com.view.kevin.myview.activity.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.view.kevin.myview.R;
import com.view.kevin.myview.activity.view.PopupWindowHelper;

public class PopWindowHelperActivity extends Activity implements View.OnClickListener {
    PopupWindowHelper popupWindowHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popwindow_helper);
        View popView = LayoutInflater.from(this).inflate(R.layout.view_popwindow_layout, null);
        popupWindowHelper = new PopupWindowHelper(popView);
        ImageButton imageButton = (ImageButton) findViewById(R.id.imgBtn_a);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowHelper.showAsPopUp(v);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                popupWindowHelper.showAsPopUp(v);

                break;
            case R.id.btn_2:
                popupWindowHelper.showAsPopUp(v);

                break;
            case R.id.btn_3:
                popupWindowHelper.dismiss();

                break;
            case R.id.btn_4:
                popupWindowHelper.showFromTop(v);

                break;
            case R.id.btn_5:
                popupWindowHelper.showFromBottom(v);

                break;
        }
    }
}

