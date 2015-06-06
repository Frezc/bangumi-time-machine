package frezc.bangumitimemachine.app.network.http;

import android.util.Base64;
import com.android.volley.Request;
import com.android.volley.Response;
import frezc.bangumitimemachine.app.entity.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by freeze on 2015/4/23.
 * for Bangumi Auth
 * 未加密
 */
public class BasicAuth {
    private GsonRequest<User> gsonRequest;
    private Response.Listener<User> successListener;
    private Response.ErrorListener errorListener;

    public final Map<String, String> authHeaders = new HashMap<String, String>();

    public BasicAuth(String username, String password, Response.Listener<User> successListener,
              Response.ErrorListener errorListener){
        authHeaders.put("Accept-Encoding","gzip");
        authHeaders.put("User-Agent", "android-async-http/1.4.1 (http://loopj.com/android-async-http)");
        authHeaders.put("Authorization", "Basic "+getBASE64(username+":"+password));
        gsonRequest = new GsonRequest<User>(Request.Method.POST, NetParams.AUTH_URL,
                User.class, authHeaders, successListener, errorListener);
    }

    public void setUsernameAndPassword(String username, String password){
        if(username != null && password != null) {
            authHeaders.remove("Authorization");
            authHeaders.put("Authorization", "Basic "+getBASE64(username+":"+password));
        }
    }

    public void sendRequest(NetWorkTool netWorkTool){
        netWorkTool.addToRequestQueue(gsonRequest);
    }

    public void cancel(){
        gsonRequest.cancel();
    }

    public String getBASE64(String s){
        if(s == null) return null;
        return new String(Base64.encode(s.getBytes(),Base64.DEFAULT));
    }
}
