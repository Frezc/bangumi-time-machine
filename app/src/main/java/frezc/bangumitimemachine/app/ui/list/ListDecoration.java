package frezc.bangumitimemachine.app.ui.list;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import frezc.bangumitimemachine.app.ui.UIParams;

/**
 * Created by freeze on 2015/5/16.
 * RecyclerView的分隔线
 */
public class ListDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    private Drawable divider;

    public ListDecoration(Context context){
        final TypedArray ta = context.obtainStyledAttributes(ATTRS);
        divider = ta.getDrawable(0);
        ta.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = (int) (parent.getPaddingLeft()+8* UIParams.density);
        int right = (int) (parent.getWidth() - parent.getPaddingRight() - 8*UIParams.density);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + divider.getIntrinsicHeight();
            divider.setBounds(left,top,right,bottom);
            divider.draw(c);
        }
    }
}
