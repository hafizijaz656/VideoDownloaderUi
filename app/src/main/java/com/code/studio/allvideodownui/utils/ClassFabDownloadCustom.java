package com.code.studio.allvideodownui.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/* renamed from: hd.video.downloader.allvideodownloader.app.tool.ClassFabDownloadCustom */
public class ClassFabDownloadCustom extends FloatingActionButton implements View.OnTouchListener {
    private static final float D_TOLERANCE = 10.0f;

    /* renamed from: dX */
    private float f380dX;

    /* renamed from: dY */
    private float f381dY;
    private float downRawX;
    private float downRawY;

    public ClassFabDownloadCustom(Context context) {
        super(context);
        init();
    }

    public ClassFabDownloadCustom(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public ClassFabDownloadCustom(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        setOnTouchListener(this);
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int action = motionEvent.getAction();
        if (action == 0) {
            this.downRawX = motionEvent.getRawX();
            this.downRawY = motionEvent.getRawY();
            this.f380dX = view.getX() - this.downRawX;
            this.f381dY = view.getY() - this.downRawY;
            return true;
        } else if (action == 2) {
            int width = view.getWidth();
            int height = view.getHeight();
            View view2 = (View) view.getParent();
            int width2 = view2.getWidth();
            int height2 = view2.getHeight();
            float min = Math.min((float) ((width2 - width) - marginLayoutParams.rightMargin), Math.max((float) marginLayoutParams.leftMargin, motionEvent.getRawX() + this.f380dX));
            view.animate().x(min).y(Math.min((float) ((height2 - height) - marginLayoutParams.bottomMargin), Math.max((float) marginLayoutParams.topMargin, motionEvent.getRawY() + this.f381dY))).setDuration(0).start();
            return true;
        } else if (action != 1) {
            return super.onTouchEvent(motionEvent);
        } else {
            float rawX = motionEvent.getRawX();
            float rawY = motionEvent.getRawY();
            float f = rawX - this.downRawX;
            float f2 = rawY - this.downRawY;
            if (Math.abs(f) >= D_TOLERANCE || Math.abs(f2) >= D_TOLERANCE) {
                return true;
            }
            return performClick();
        }
    }
}
