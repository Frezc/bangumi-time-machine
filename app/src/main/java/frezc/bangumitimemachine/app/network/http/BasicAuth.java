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
    private GsonRequest<User> userInfoRequest,userNetabaRequest;
    private final Response.Listener<User> successListener;
    private final Response.ErrorListener errorListener;

    private NetWorkTool netWorkTool;

    private User user;

    public final Map<String, String> authHeaders = new HashMap<String, String>();

    public BasicAuth(String username, String password, Response.Listener<User> successListener,
              Response.ErrorListener errorListener){
        authHeaders.put("Accept-Encoding","gzip");
        authHeaders.put("User-Agent", "android-async-http/1.4.1 (http://loopj.com/android-async-http)");
        authHeaders.put("Authorization", "Basic "+getBASE64(username+":"+password));

        this.successListener = successListener;
        this.errorListener = errorListener;

        userInfoRequest = new GsonRequest<User>(Request.Method.POST, NetParams.AUTH_URL,
                User.class, authHeaders, new Response.Listener<User>() {
            @Override
            public void onResponse(User user) {
                if(user == null || user.getAuth() == null){
                    BasicAuth.this.successListener.onResponse(user);
                }else {
                    BasicAuth.this.user = user;
                    requestNetabareAuth();
                }
            }
        }, errorListener);
    }

    private void requestNetabareAuth(){
        userNetabaRequest = new GsonRequest<User>(Request.Method.POST, NetParams.NETABARE_AUTH,
                User.class, authHeaders, new Response.Listener<User>() {
            @Override
            public void onResponse(User user) {
                BasicAuth.this.user.setNetabaAuth(user.getUsername()+":"+user.getAuth());
                successListener.onResponse(BasicAuth.this.user);
            }
        }, errorListener);

        netWorkTool.addToRequestQueue(userNetabaRequest);
    }

    public void setUsernameAndPassword(String username, String password){
        if(username != null && password != null) {
            authHeaders.remove("Authorization");
            authHeaders.put("Authorization", "Basic "+getBASE64(username+":"+password));
        }
    }

    public void sendRequest(NetWorkTool netWorkTool){
        this.netWorkTool = netWorkTool;
        netWorkTool.addToRequestQueue(userInfoRequest);
    }

    public void cancel(){
        userInfoRequest.cancel();

        if(userNetabaRequest != null){
            userNetabaRequest.cancel();
        }
    }

    public String getBASE64(String s){
        if(s == null) return null;
        return new String(Base64.encode(s.getBytes(),Base64.DEFAULT));
    }
}
