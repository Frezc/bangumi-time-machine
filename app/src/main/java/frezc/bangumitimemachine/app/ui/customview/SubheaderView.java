package frezc.bangumitimemachine.app.ui.customview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import frezc.bangumitimemachine.app.R;
import frezc.bangumitimemachine.app.ui.UIParams;

/**
 * Created by freeze on 2015/4/29.
 */
public class SubheaderView extends LinearLayout {
    private int titleColor;
    private TextView textView;

    public SubheaderView(Context context, String text) {
        super(context);
        init(context,text);
    }

    private void init(Context context, String text) {
        setOrientation(VERTICAL);

        // add divisor
        DivisorView divisorView = new DivisorView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,1);
        params.setMargins(0, (int) (8 * UIParams.density), 0, (int) (8 * UIParams.density));
        addView(divisorView, params);

        // add text
        textView = new TextView(context);
        textView.setText(text);
        textView.setAlpha(0.54f);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setGravity(Gravity.START);
        LinearLayout.LayoutParams paramsText = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsText.setMargins((int) (16 * UIParams.density), 0,
                (int) (16 * UIParams.density), (int) (4 * UIParams.density));
        addView(textView, paramsText);

        // get subheader text color from theme
        Resources.Theme theme = context.getTheme();
        TypedValue typedValue = new TypedValue();
        theme.resolveAttribute(R.attr.subheaderTitleColor, typedValue, true);
        TypedArray values = theme.obtainStyledAttributes(typedValue.resourceId, R.styleable.SubheaderView);
        titleColor = values.getColor(R.styleable.SubheaderView_subheaderTitleColor, 0xff000000);
        values.recycle();
        textView.setTextColor(titleColor);
    }

    public void setTitleFont(Typeface font){
        textView.setTypeface(font);
    }

    public void setTitle(CharSequence title){
        textView.setText(title);
    }

    public void setTitleColor(int color){
        textView.setTextColor(color);
    }

    public int getTitleColor() {
        return titleColor;
    }

    public CharSequence getTitle(){
        return textView.getText();
    }
}
