package com.dev.dongworry.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by dengyaoning on 17/3/11.
 */

public class CButton extends Button {
    public CButton(Context context) {
        super(context);
    }

    public CButton(Context context, AttributeSet attrs) {
        super(context, attrs,android.R.attr.borderlessButtonStyle);
    }
}
