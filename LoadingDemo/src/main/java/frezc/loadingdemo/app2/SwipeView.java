package frezc.loadingdemo.app2;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * Created by freeze on 2015/5/31.
 */
public class SwipeView extends FrameLayout{
    private final double AUTO_OPEN_SPEED_LIMIT = 800.0;
    private ViewDragHelper dragHelper;
    private View surfaceView;
    private View bottomView;
    private int dragState = 0;
    private int dragDistance = (int) (50*getResources().getDisplayMetrics().density+0.5f);

    public SwipeView(Context context) {
        this(context, null);
    }

    public SwipeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        dragHelper = ViewDragHelper.create(this, 1f, new ViewDragCallback());
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
        switch (ev.getAction()){
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                dragHelper.cancel();
                Toast.makeText(getContext(),"cancel",Toast.LENGTH_SHORT).show();
                return false;
        }

        return dragHelper.shouldInterceptTouchEvent(ev);
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

    private class ViewDragCallback extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            Log.i("drag",""+(child==getSurfaceView()));
            return child == getSurfaceView();
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if(left > dragDistance)
                return dragDistance;
            else if(left < -dragDistance)
                return -dragDistance;
            else
                return left;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return dragDistance;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            Log.i("drag","onViewReleased "+xvel+","+yvel);
//            dragHelper.smoothSlideViewTo(releasedChild,0,0);
            if(dragHelper.settleCapturedViewAt(0, 0)){
                ViewCompat.postInvalidateOnAnimation(SwipeView.this);
            }
        }

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
    }
}
