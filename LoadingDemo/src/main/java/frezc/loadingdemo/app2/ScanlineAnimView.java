package frezc.loadingdemo.app2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by freeze on 2015/4/26.
 */
public class ScanlineAnimView extends SurfaceView
    implements SurfaceHolder.Callback{

    private SurfaceHolder holder;
    private GradientDrawable gradientDrawable =
            new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                    new int[]{0xffffff,0xffF8F8F8});
    private int position = 0;

    private DrawThread drawThread = new DrawThread();

    public ScanlineAnimView(Context context) {
        this(context, null);
    }

    public ScanlineAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        drawThread.running=true;
        drawThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        drawThread.running = false;
    }

    class DrawThread extends Thread{
        Canvas canvas;
        boolean running;
        Rect dirty = new Rect(0,0,0,0);
        int top=0,bottom=0;
        int speed = 6;

        @Override
        public void run() {
            while (running){
                if(position < (300 + speed)){
                    top = 0;
                }else {
                    top = position - 300 - speed;
                }
                if(position > getMeasuredHeight()){
                    bottom = getMeasuredHeight();
                }else {
                    bottom = position;
                }
                gradientDrawable.setBounds(0, position - 300, getMeasuredWidth(), position);
                dirty.set(0,top,getMeasuredWidth(),bottom);
                //清空
                canvas = holder.lockCanvas(dirty);
                gradientDrawable.draw(canvas);
                holder.unlockCanvasAndPost(canvas);
                position += speed;
                if(position > getMeasuredHeight() + 300){
                    position = 0;
                }
                try {
                    sleep(33);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

