package frezc.bangumitimemachine.app.ui.drawer;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import frezc.bangumitimemachine.app.R;
import org.w3c.dom.Text;

/**
 * Created by freeze on 2015/4/28.
 */
public class Section implements View.OnClickListener, View.OnTouchListener{
    public static final int TYPE_SECTION = 0;
    public static final int TYPE_SECTION_ICON = 1;
    public static final int TYPE_DIVISOR = -1;
    public static final int TYPE_SUBHEADER = -2;

    private Context context;

    private int type=0;
    private View rootView=null;
    private ImageView iconView = null;
    private TextView textView = null;
    private TextView notificationView = null;

    private String text;
    private int notify = 0;

    //colors
    private int colorPressedBackground;
    private int colorNormalBackground;
    private int colorSelectedBackground;
    //section被选中时图标和文字变化的颜色,可以没有
    private int colorSelectedSection;
    private int textColor;
    private int iconColor;
    private int notificationColor;

    //hook
    private boolean hasColorSelectedSection;
    private boolean isSelected;

    //消息数量
    private int numberNotifications;

    private int tag;
    private OnClickListener onClickListener = null;

    public interface OnClickListener{
        void onClick(Section section);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public Section(Context context, int type, String text, int tag){
        init(context, type, text, 0, tag);
    }

    public Section(Context context, int type, String text, int iconId, int tag){
        init(context, type, text, iconId, tag);
    }


    private void init(Context context, int type, String text, int iconId, final int tag){
        this.context = context;
        this.type = type;
        switch (type){
            case TYPE_SECTION:
                rootView = LayoutInflater.from(context).inflate(R.layout.section, null);
                break;

            case TYPE_SECTION_ICON:
                rootView = LayoutInflater.from(context).inflate(R.layout.section_icon, null);
                iconView = (ImageView) rootView.findViewById(R.id.section_icon);
                iconView.setImageResource(iconId);
                break;
            //other section
        }

        textView = (TextView) rootView.findViewById(R.id.section_text);
        notificationView = (TextView) rootView.findViewById(R.id.section_notification);

        this.tag = tag;

        rootView.setOnClickListener(this);

        //从主题设置里初始化变量
        Resources.Theme theme = context.getTheme();
        TypedValue typedValue = new TypedValue();
        theme.resolveAttribute(R.attr.sectionStyle, typedValue,true);
        TypedArray values = theme.obtainStyledAttributes(typedValue.resourceId, R.styleable.Section);
        try {
            colorPressedBackground = values.getColor(R.styleable.Section_sectionBackgroundColorPressed,0x16000000);
            colorNormalBackground = values.getColor(R.styleable.Section_sectionBackgroundColor,0x00FFFFFF);
            colorSelectedBackground = values.getColor(R.styleable.Section_sectionBackgroundColorSelected,0x0A000000);

            iconColor = values.getColor(R.styleable.Section_sectionColorIcon,0x000);
            textColor = values.getColor(R.styleable.Section_sectionColorText,0x000);
            notificationColor = values.getColor(R.styleable.Section_sectionColorNotification,0x000);

            //设置文字和消息字体颜色
            if(textColor != 0x000) {
                textView.setTextColor(textColor);
            }
            if(notificationColor != 0x000) {
                notificationView.setTextColor(notificationColor);
            }
        }finally {
            values.recycle();
        }

        //其他属性初始化
        hasColorSelectedSection = false;
        numberNotifications = 0;
        isSelected = false;
    }

    /**
     * 设置点击后的icon和文字颜色(可以更改来适配主题颜色)
     * @param colorSelectedSection
     * @return
     */
    public Section setColorSelectedSection(int colorSelectedSection) {
        hasColorSelectedSection = true;
        this.colorSelectedSection = colorSelectedSection;
        return this;
    }

    /**
     * 设置消息数量
     * @param notifications
     * @return
     */
    public Section setNotification(int notifications){
        if(notifications < 1){
            notificationView.setText("");
        }else if(notifications > 99){
            notificationView.setText("99+");
        }else {
            notificationView.setText(String.valueOf(notifications));
        }

        numberNotifications = notifications;
        return this;
    }

    /**
     * 获得消息数量
     * @return
     */
    public int getNotifications(){
        return numberNotifications;
    }

    /**rootView的点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        //可以添加点击效果

        if(onClickListener != null){
            onClickListener.onClick(this);
        }
    }

    /**rootView的touch事件
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                rootView.setBackgroundColor(colorPressedBackground);
                return true;
            case MotionEvent.ACTION_CANCEL:
                if(isSelected){
                    rootView.setBackgroundColor(colorSelectedBackground);
                }else {
                    rootView.setBackgroundColor(colorNormalBackground);
                }
                return true;
            case MotionEvent.ACTION_UP:
                rootView.setBackgroundColor(colorSelectedBackground);

                return true;
        }
        return false;
    }

    public View getView() {
        return rootView;
    }

    public int getTag() {
        return tag;
    }
}
