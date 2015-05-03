package frezc.bangumitimemachine.app.network.http;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import com.android.volley.*;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by freeze on 2015/4/23.
 */
public class GsonRequest<T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private Map<String,String> headers;
    private final Response.Listener<T> listener;
    private final OnListResponseListener<T> listListener;
    private final boolean isArray;
    private final Context context;

    public interface OnListResponseListener<T>{
        void onResponse(List<T> response);
    }

    public GsonRequest(int method, String url, Class<T> clazz, Map<String,String> headers,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        isArray = false;
        this.context = null;
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
        this.listListener = null;
    }

    /**
     * @param context for MainLooper
     * @param method
     * @param url
     * @param clazz
     * @param headers
     * @param listener
     * @param errorListener
     */
    public GsonRequest(Context context, int method, String url, Class<T> clazz, Map<String,String> headers,
                       OnListResponseListener<T> listener, Response.ErrorListener errorListener){
        super(method,url,errorListener);
        isArray = true;
        this.context = context;
        this.clazz = clazz;
        this.headers = headers;
        this.listListener = listener;
        this.listener = null;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String json;
            if("gzip".equals(networkResponse.headers.get("Content-Encoding"))) {
                json = NetWorkTool.GZipDecoderToString(networkResponse.data);
            }else {
                json = new String(networkResponse.data,
                        HttpHeaderParser.parseCharset(networkResponse.headers));
            }
            Log.i("GsonRequest",json);
            if(isArray){
                final List<T> list = new ArrayList<T>();
                JsonParser parser = new JsonParser();
                JsonArray jsonArray = parser.parse(json).getAsJsonArray();
                for(JsonElement obj : jsonArray){
                    T t = gson.fromJson(obj, clazz);
                    list.add(t);
                }
                new Handler(context.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (listListener != null) {
                            listListener.onResponse(list);
                        }
                    }
                });
                return Response.success(list.get(0), HttpHeaderParser.parseCacheHeaders(networkResponse));
            }else {
                return Response.success(gson.fromJson(json, clazz),
                        HttpHeaderParser.parseCacheHeaders(networkResponse));
            }
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T t) {
        if(listener != null) {
            listener.onResponse(t);
        }
    }
}
