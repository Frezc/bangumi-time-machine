package frezc.bangumitimemachine.app.network.http;

import android.util.Log;
import com.android.volley.*;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by freeze on 2015/4/23.
 */
public class GsonRequest<T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private Map<String,String> headers;
    private final Response.Listener<T> listener;
    private boolean isArray = false;

    public GsonRequest(int method, String url, Class<T> clazz, Map<String,String> headers,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
    }

    public void setArray(boolean flag){
        isArray = flag;
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
            if(networkResponse.headers.get("Content-Encoding").equals("gzip")) {
                json = NetWorkTool.GZipDecoderToString(networkResponse.data);
            }else {
                json = new String(networkResponse.data,
                        HttpHeaderParser.parseCharset(networkResponse.headers));
            }
            Log.i("GsonRequest",json);
            return Response.success(gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T t) {
        listener.onResponse(t);
    }
}
