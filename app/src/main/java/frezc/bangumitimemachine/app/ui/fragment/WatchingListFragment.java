package frezc.bangumitimemachine.app.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import frezc.bangumitimemachine.app.MyApplication;
import frezc.bangumitimemachine.app.R;
import frezc.bangumitimemachine.app.entity.WatchingSubject;
import frezc.bangumitimemachine.app.network.http.GsonRequest;
import frezc.bangumitimemachine.app.network.http.NetParams;
import frezc.bangumitimemachine.app.network.http.NetWorkTool;
import frezc.bangumitimemachine.app.ui.list.ListDecoration;
import frezc.bangumitimemachine.app.ui.list.WatchingListAdapter;
import frezc.bangumitimemachine.app.ui.callback.OnItemClickListener;
import frezc.bangumitimemachine.app.ui.callback.OnItemLongClickListener;

import java.util.HashMap;
import java.util.List;

/**
 * Created by freeze on 2015/5/6.
 */
public class WatchingListFragment extends NetFragment implements
        OnItemClickListener, OnItemLongClickListener,
        GsonRequest.OnListResponseListener<WatchingSubject>{

    private RecyclerView watchingList;
    private ProgressBar progressBar;
    private LinearLayout errorView;

    private WatchingListAdapter watchingListAdapter;

    public static WatchingListFragment newInstance(Context context){
        WatchingListFragment watchingListFragment = new WatchingListFragment();
        watchingListFragment.setNetWorkTool(NetWorkTool.getInstance(context));
        watchingListFragment.setResetFlag(true);
        return watchingListFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        headers = new HashMap<String, String>();
        headers.put("Accept-Encoding", "gzip");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_watching, container, false);
        watchingList = (RecyclerView) rootView.findViewById(R.id.watching_list);
        progressBar = (ProgressBar) rootView.findViewById(R.id.watching_progress);
        errorView = (LinearLayout) rootView.findViewById(R.id.watching_error);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        watchingList.setLayoutManager(linearLayoutManager);
        watchingList.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.ItemDecoration decoration = new ListDecoration(getActivity());
        watchingList.addItemDecoration(decoration);

        watchingListAdapter = new WatchingListAdapter();
        watchingListAdapter.setOnItemClickListener(this);
        watchingListAdapter.setOnItemLongClickListener(this);

        watchingList.setAdapter(watchingListAdapter);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(isReset){
            showLoading();
            refresh();
            isReset = false;
        }
    }

    private void showList(){
        watchingList.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    private void showLoading(){
        watchingList.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    private void showError(){
        watchingList.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void refresh(){
        if(MyApplication.isUserLogin() && isAdded()) {
            GsonRequest<WatchingSubject> request = new GsonRequest<WatchingSubject>(getActivity(),
                    Request.Method.GET, NetParams.getWatchingUrl(MyApplication.getLoginUser().getId()),
                    WatchingSubject.class, headers, this,this);
            netWorkTool.addToRequestQueue(request);
        }else {
            showError();
        }

    }

    @Override
    public void onRefresh() {
        showLoading();
        refresh();
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        showError();
        Toast.makeText(getActivity(), ""+volleyError, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(List<WatchingSubject> response) {
        showList();
        watchingListAdapter.setData(response);
    }
}
