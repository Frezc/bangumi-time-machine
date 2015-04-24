package frezc.bangumitimemachine.app.network.http;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

/**
 * Created by freeze on 2015/4/21.
 */
public class NetWorkTool {
    private static NetWorkTool mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;

    private NetWorkTool(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized NetWorkTool getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetWorkTool(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void loadImage(String url, ImageLoader.ImageListener listener){
        mImageLoader.get(url,listener);
    }

    public void cancelAll(Object tag){
        getRequestQueue().cancelAll(tag);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public static String GZipDecoderToString(byte[] data) {
        GZIPInputStream gzipInputStream = null;
        try {
            gzipInputStream = new GZIPInputStream(
                    new ByteArrayInputStream(data));
            InputStreamReader reader = new InputStreamReader(gzipInputStream);
            BufferedReader in = new BufferedReader(reader);
            StringBuilder sb = new StringBuilder();
            String read;
            while ((read = in.readLine()) != null){
                sb.append(read);
            }
            String json = sb.toString();
            reader.close();
            in.close();
            gzipInputStream.close();
            return json;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
