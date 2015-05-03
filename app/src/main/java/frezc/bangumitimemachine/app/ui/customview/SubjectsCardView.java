package frezc.bangumitimemachine.app.ui.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;
import frezc.bangumitimemachine.app.R;
import frezc.bangumitimemachine.app.entity.DetailSubject;
import frezc.bangumitimemachine.app.entity.Subject;
import frezc.bangumitimemachine.app.entity.WeekSubjects;
import frezc.bangumitimemachine.app.ui.UIParams;


import java.util.List;

/**
 * Created by freeze on 2015/5/2.
 */
public class SubjectsCardView extends CardView implements View.OnTouchListener{

    private TextView titleView;
    private LinearLayout subjectContainer;
    private WeekSubjects weekSubjects;

    private int dayId = 0;

    private int colorPressedBackground;

    private OnItemSelectListener onItemSelectListener = null;

    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener) {
        this.onItemSelectListener = onItemSelectListener;
    }

    public interface OnItemSelectListener{
        void onItemSelect(Subject subject);
    }

    public SubjectsCardView(Context context) {

        this(context, null);
    }

    public SubjectsCardView(Context context, AttributeSet attrs) {
        super(context, attrs);


        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_daysubjects, null);
        this.addView(rootView, params);

        titleView = (TextView) rootView.findViewById(R.id.subjects_title);
        subjectContainer = (LinearLayout) rootView.findViewById(R.id.subjects_container);

        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.SubjectsCardView);

        String title;
        float titleSize;
        int titleColor;
        int titleBackground;
        int containerBackground;
        try {
            title = ta.getString(R.styleable.SubjectsCardView_title_text);
            titleSize = ta.getDimension(R.styleable.SubjectsCardView_title_textSize, 0f);
            titleColor = ta.getColor(R.styleable.SubjectsCardView_title_textColor, 0);
            titleBackground = ta.getColor(R.styleable.SubjectsCardView_title_background, 0);
            containerBackground = ta.getColor(R.styleable.SubjectsCardView_container_background, 0);
            colorPressedBackground = ta.getColor(R.styleable.SubjectsCardView_subject_pressedColor, 0x1a000000);
        }finally {
            ta.recycle();
        }

        title = title == null ? "" : title;
        titleView.setText(title);
        if(titleSize != 0f){
            titleView.setTextSize(titleSize);
        }
        if(titleColor != 0){
            titleView.setTextColor(titleColor);
        }
        if(titleBackground != 0){
            titleView.setBackgroundColor(titleBackground);
        }
        if(containerBackground != 0){
            subjectContainer.setBackgroundColor(containerBackground);
        }
    }

    public void setWeekSubjects(WeekSubjects weekSubjects) {
        this.weekSubjects = weekSubjects;
        subjectContainer.removeAllViews();
        List<Subject> subjectList = weekSubjects.getItems();
        if(weekSubjects == null){
            titleView.setText("未知的一天");
        }else {
            //设置日期
            dayId = weekSubjects.getWeekday().id;
            titleView.setText(weekSubjects.getWeekday().cn);
            //设置内容
            if (subjectList.size() == 0) {
                TextView textView = new TextView(getContext());
                textView.setText("现在并没有什么东西哦(030)");
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                subjectContainer.addView(textView, new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            } else {
                for (int i = 0; i < subjectList.size(); i++) {
                    final TextView textView = new TextView(getContext());
                    String name = subjectList.get(i).getName_cn();
                    if(name == null || name.isEmpty()){
                        textView.setText(subjectList.get(i).getName());
                    }else {
                        textView.setText(name);
                    }
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    textView.setAlpha(0.87f);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, (int) (36 * UIParams.density));
                    params.setMargins(0, (int) (1 * UIParams.density), 0, (int) (1 * UIParams.density));
                    textView.setOnTouchListener(this);
                    textView.setId(i);
                    textView.setGravity(Gravity.CENTER_VERTICAL);
                    subjectContainer.addView(textView, params);
                }
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                v.setBackgroundColor(colorPressedBackground);
                return true;

            case MotionEvent.ACTION_CANCEL:
                v.setBackgroundColor(0);
                return true;

            case MotionEvent.ACTION_UP:
                v.setBackgroundColor(0);
                if(onItemSelectListener != null){
                    int i = v.getId();
                    if(i < 0 || i > weekSubjects.getItems().size()){
                        throw new IndexOutOfBoundsException("View id is out of Subjects");
                    }
                    onItemSelectListener.onItemSelect(weekSubjects.getItems().get(i));
                }
                return true;
        }
        return false;
    }

    public int getDayId() {
        return dayId;
    }
}
