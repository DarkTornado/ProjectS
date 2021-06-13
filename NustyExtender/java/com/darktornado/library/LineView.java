package com.darktornado.library;

import android.content.Context;
import android.widget.TextView;

public class LineView extends TextView {
    public LineView(Context ctx) {
        super(ctx);
        setWidth(-1);
        setHeight(dip2px(1));
    }

    public void setColor(int color) {
        setBackgroundColor(color);
    }

    public int dip2px(int dips) {
        return (int) Math.ceil(dips * this.getResources().getDisplayMetrics().density);
    }
}
