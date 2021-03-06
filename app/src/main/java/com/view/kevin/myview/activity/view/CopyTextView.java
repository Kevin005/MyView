package com.xsfuture.xsfuture2.view;

/**
 * Created by liuwei on 2017/1/4.
 */

import android.content.Context;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.EditText;


public class CopyTextView extends EditText {
    public CopyTextView(Context context) {
        super(context);
    }

    public CopyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CopyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 长按弹出文本选择框menu的关键方法：可以选择复制、剪切等等功能，视该textview的具体实现而定
// 如果希望不弹出这个menu界面，只要把这个方法返回空就ok
    @Override
    protected MovementMethod getDefaultMovementMethod() {
        return super.getDefaultMovementMethod();
    }


    // 点击menu中的选定item的具体处理方法，捕捉点击文本复制、剪切等按钮的动作
// 如果要在点击复制按钮之后取消该textview的cursor可见性的具体监听写在这里
    @Override
    public boolean onTextContextMenuItem(int id) {
        setCursorVisible(true);
        boolean flag;
        if (id != android.R.id.switchInputMethod) {
            flag = super.onTextContextMenuItem(id);
        } else {
            setCursorVisible(false);
            return false;
        }
        if (id == android.R.id.copy) {
            setCursorVisible(false);
        }
        return flag;
    }


    @Override
    protected void onCreateContextMenu(ContextMenu menu) {
        super.onCreateContextMenu(menu);
        if (isInputMethodTarget()) {
            menu.removeItem(android.R.id.switchInputMethod);
        }
    }


    // textview的点击捕捉
// 如果双击textview选中了具体文字，则使cursor可见
    int cursorStart = -1;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean flag = super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN && hasSelection()) {
            if (cursorStart == -1) {// 由于点击选中文字后，再点击其他位置，第一次点击时显示的hasSelection依然为true，这样一来cursor会依然还在，为了避免这种情况，我这里多对selectionStart进行了一次验证
                setCursorVisible(true);
                cursorStart = getSelectionStart();
            } else {
                setCursorVisible(false);
                cursorStart = -1;
            }
        }
        return flag;
    }

    // 当按返回键取消文字复制时，使cursor再次不可见
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean flag = super.onKeyDown(keyCode, event);
        if (!hasSelection()) {
            setCursorVisible(false);
            cursorStart = -1;
        }
        return flag;
    }
}
