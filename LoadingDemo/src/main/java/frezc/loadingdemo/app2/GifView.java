package frezc.loadingdemo.app2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by freeze on 2015/4/26.
 */
public class GifView extends SurfaceView
    implements SurfaceHolder.Callback{

    private SurfaceHolder holder;

    private DrawThread drawThread = new DrawThread();

    private Movie movie;
    private int resId;
    private long movieStartTime = -1;

    private float scale;
    private int measuredMovieWidth;
    private int measuredMovieHeight;
    private float left,top;

    public GifView(Context context) {
        this(context, null);
    }

    public GifView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.GifView);
        resId = ta.getResourceId(R.styleable.GifView_src, R.drawable.dance);
        ta.recycle();

        holder = getHolder();
        holder.addCallback(this);

        movie = Movie.decodeStream(getResources().openRawResource(resId));
        drawThread.setDaemon(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread.running=true;
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        drawThread.running = false;
        movieStartTime = -1;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(movie != null){
            int movieWidth = movie.width();
            int movieHeight = movie.height();
            int maximumWidth = MeasureSpec.getSize(widthMeasureSpec);
            float scaleW = (float) movieWidth / (float) maximumWidth;
            scale = 1f / scaleW;
            measuredMovieWidth = maximumWidth;
            measuredMovieHeight = (int) (movieHeight * scale);
            setMeasuredDimension(measuredMovieWidth, measuredMovieHeight);
        }else {
            setMeasuredDimension(getSuggestedMinimumWidth(),getSuggestedMinimumHeight());
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.left = (getMeasuredWidth() - measuredMovieWidth) / 2f;
        this.top = (getMeasuredHeight() - measuredMovieHeight) / 2f;
    }

    private void drawMovieFrame(Canvas canvas) {
        if(canvas == null) return;
        long now = SystemClock.uptimeMillis();
        if(movieStartTime == -1){
            movieStartTime = now;
        }
        int duration = movie.duration();
        if(duration == 0){
            duration = 1000;
        }
        int currentAnimTime = (int) ((now - movieStartTime) % duration);
        movie.setTime(currentAnimTime);
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.scale(scale, scale);
        movie.draw(canvas, left / scale, top / scale);
        canvas.restore();
    }

    class DrawThread extends Thread{
        Canvas canvas;
        boolean running;
        @Override
        public void run() {
            while (running && holder != null){
                canvas = holder.lockCanvas();
                drawMovieFrame(canvas);
                holder.unlockCanvasAndPost(canvas);
                try {
                    sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

