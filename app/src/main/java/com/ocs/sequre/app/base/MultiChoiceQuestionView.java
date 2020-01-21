package com.ocs.sequre.app.base;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.ocs.sequre.R;

public class MultiChoiceQuestionView extends LinearLayoutCompat {
    public MultiChoiceQuestionView(Context context) {
        super(context);
        init(context, null);
    }

    public MultiChoiceQuestionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MultiChoiceQuestionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);
        MaterialCheckBox checkBox = new MaterialCheckBox(context);
        checkBox.setText("Answer 1");
        checkBox.setChecked(true);
        addView(checkBox);

        MaterialCheckBox checkBox2 = new MaterialCheckBox(context);
        checkBox2.setText("Answer 2");
        checkBox2.setChecked(false);
        addView(checkBox2);

        LinearLayoutCompat.LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, R.style.App_Widget_Button);
        MaterialButton button = new MaterialButton(contextThemeWrapper, null, R.style.AppTheme);
        button.setText(R.string.submit);
//        button.setTextColor(Color.GREEN);
        addView(button, layoutParams);
    }
}
