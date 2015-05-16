package frezc.bangumitimemachine.app.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.android.volley.Response;
import frezc.bangumitimemachine.app.network.http.NetWorkTool;
import frezc.bangumitimemachine.app.ui.callback.OnRefreshListener;

import java.util.Map;

/**
 * Created by freeze on 2015/5/14.
 */
public abstract class NetFragment extends Fragment implements OnRefreshListener,
        Response.ErrorListener{

    protected NetWorkTool netWorkTool;
    protected Map<String,String> headers;

    /**
     * 重置fragment数据的flag
     */
    protected boolean isReset;

    public void setNetWorkTool(NetWorkTool netWorkTool) {
        this.netWorkTool = netWorkTool;
    }

    //刷新函数
    public abstract void refresh();

    public void setResetFlag(boolean flag){
        isReset = flag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //保持framgent
        setRetainInstance(true);
    }
}
