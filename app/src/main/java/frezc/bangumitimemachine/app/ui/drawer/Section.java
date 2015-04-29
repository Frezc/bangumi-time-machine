package frezc.bangumitimemachine.app.ui.drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import frezc.bangumitimemachine.app.R;

/**
 * Created by freeze on 2015/4/28.
 */
public class Section {
    public static final int TYPE_SECTION = 0;
    public static final int TYPE_SECTION_ICON = 1;
    public static final int TYPE_DIVISOR = -1;
    public static final int TYPE_SUBHEADER = -2;

    private Context context;

    private int type=0;
    private View rootView=null;

    private String text;
    private int notify = 0;

    private int tag;
    private OnClickListener onClickListener = null;

    public interface OnClickListener{
        void onClick(View v, int tag);
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
                ImageView imageView = (ImageView) rootView.findViewById(R.id.section_icon);
                imageView.setImageResource(iconId);
                break;
            //other section
        }

        this.tag = tag;

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //可以添加点击效果

                if(Section.this.onClickListener != null){
                    Section.this.onClickListener.onClick(rootView, tag);
                }
            }
        });
    }

    public View getView() {
        return rootView;
    }
}
