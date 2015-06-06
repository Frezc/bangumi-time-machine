package frezc.bangumitimemachine.app.ui.customview;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.Toast;
import frezc.bangumitimemachine.app.ui.UIParams;
import frezc.bangumitimemachine.app.ui.UIUtil;

/**
 * Created by freeze on 2015/5/31.
 */
public class SwipeLayout extends FrameLayout{


    private ViewDragHelper dragHelper;

    private Status status;
    private int dragLimit = 0;

    public enum Status{
        Open,
        Close
    }

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        status = Status.Close;
    }

    private void init() {
        dragHelper = ViewDragHelper.create(this, 0.8f, new ViewDragCallback());
    }

    public View getSurfaceView(){
        if(getChildCount() == 0)
            return null;
        else
            return getChildAt(getChildCount() - 1);
    }

    public View getBottomView() {
        if(getChildCount() < 2)
            return null;
        else
            return getChildAt(getChildCount() - 2);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        /*switch (ev.getAction()){
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                dragHelper.cancel();
                Toast.makeText(getContext(),"cancel",Toast.LENGTH_SHORT).show();
                return false;
        }*/

        return dragHelper.shouldInterceptTouchEvent(ev) || super.onInterceptTouchEvent(ev);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if(dragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }



    public int getDragLimit() {
        return dragLimit;
    }

    public void setDragLimit(int dragLimit) {
        this.dragLimit = UIUtil.dp2px(dragLimit);
    }

    public void setDragLimit(float dragLimit){
        this.dragLimit = (int) dragLimit;
    }

    //打开侧滑菜单
    public void open(){
        if(dragHelper.settleCapturedViewAt(getPaddingLeft()+dragLimit,getPaddingTop())){
            ViewCompat.postInvalidateOnAnimation(this);
        }
        status = Status.Open;
    }

    //关闭
    public void close(){
        if(dragHelper.settleCapturedViewAt(getPaddingLeft(),getPaddingTop())){
            ViewCompat.postInvalidateOnAnimation(this);
        }
        status = Status.Close;
    }

    private class ViewDragCallback extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            Log.i("drag",""+(child==getSurfaceView()));
            return child == getSurfaceView() || child == getBottomView();
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            Log.i("drag","clampViewPositionHorizontal "+left+","+dx);
            if(child == getSurfaceView()){
                if(left < getPaddingLeft()) return getPaddingLeft();
                if(left > getPaddingLeft() + dragLimit) return getPaddingLeft() + dragLimit;
            }else if(child == getBottomView()){
                if(left > getPaddingLeft()) return getPaddingLeft();
            }
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return getPaddingTop();
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return dragLimit;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            Log.i("drag","onViewPositionChanged "+left+","+top+","+dx+","+dy);
            View sufaceView = getSurfaceView();
            if(sufaceView == null) return;
            //may be null
            View bottomView = getBottomView();

            if(changedView == sufaceView){
                if(bottomView != null){
                    bottomView.offsetLeftAndRight(dx);
//                    bottomView.setTranslationX(left - dragLimit);
                }
            }else if(bottomView == changedView){
                sufaceView.offsetLeftAndRight(dx);
//                sufaceView.setTranslationX(left + dragLimit);
            }
            invalidate();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            Log.i("drag","onViewReleased "+xvel+","+yvel);
//            dragHelper.smoothSlideViewTo(releasedChild,0,0);
            float minVelocity = dragHelper.getMinVelocity();
            View sufaceView = getSurfaceView();
            if(sufaceView == null){
                return;
            }

            float willOpenPercent = (status == Status.Close) ? .25f : .75f;
            if(xvel > minVelocity){
                open();
            }else if(xvel < -minVelocity){
                close();
            }else {
                float openPercent = 1f * getSurfaceView().getLeft() / dragLimit;
                if(openPercent > willOpenPercent) open();
                else close();
            }
        }
/*
        @Override
        public void onViewDragStateChanged(int state) {
            if(state == dragState)
                return;

            switch (state){
                case ViewDragHelper.STATE_DRAGGING:
                    Toast.makeText(getContext(),"Dragging",Toast.LENGTH_SHORT).show();
                    break;
                case ViewDragHelper.STATE_IDLE:
                    Toast.makeText(getContext(),"Idle",Toast.LENGTH_SHORT).show();
                    break;
                case ViewDragHelper.STATE_SETTLING:
                    Toast.makeText(getContext(),"Setting",Toast.LENGTH_SHORT).show();
                    break;
            }
            dragState = state;
            super.onViewDragStateChanged(state);
        }
*/
    }
}
