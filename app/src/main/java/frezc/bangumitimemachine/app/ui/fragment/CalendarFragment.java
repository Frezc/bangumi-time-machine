package frezc.bangumitimemachine.app.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import frezc.bangumitimemachine.app.R;
import frezc.bangumitimemachine.app.entity.DetailSubject;
import frezc.bangumitimemachine.app.entity.Subject;
import frezc.bangumitimemachine.app.entity.WeekSubjects;
import frezc.bangumitimemachine.app.network.http.GsonRequest;
import frezc.bangumitimemachine.app.network.http.NetParams;
import frezc.bangumitimemachine.app.network.http.NetWorkTool;
import frezc.bangumitimemachine.app.ui.UIParams;
import frezc.bangumitimemachine.app.ui.customview.SubjectsCardView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by freeze on 2015/5/2.
 */
public class CalendarFragment extends Fragment
        implements GsonRequest.OnListResponseListener<WeekSubjects>,Response.ErrorListener,
        SubjectsCardView.OnItemSelectListener{

    private List<WeekSubjects> weekSubjectsList;
    private LinearLayout calendarContainer;
    private ProgressBar progressBar;
    private TextView errorView;
    private View rootView;

    private NetWorkTool netWorkTool;
    //请求头
    private GsonRequest<WeekSubjects> request;

    public static CalendarFragment newInstance(Context context){
        CalendarFragment calendarFragment = new CalendarFragment();
        calendarFragment.setNetWorkTool(NetWorkTool.getInstance(context));
        return calendarFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Map<String,String> headers = new HashMap<String, String>();
        headers.put("Accept-Encoding","gzip");
        headers.put("User-Agent", "android-async-http/1.4.1 (http://loopj.com/android-async-http)");
        request = new GsonRequest<WeekSubjects>(activity,Request.Method.GET,
                NetParams.CALENDAR_URL, WeekSubjects.class,headers,this,this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
            calendarContainer = (LinearLayout) rootView.findViewById(R.id.calendar_subjects);
            progressBar = (ProgressBar) rootView.findViewById(R.id.progress);
            errorView = (TextView) rootView.findViewById(R.id.error);
        }
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(weekSubjectsList == null){
            refresh();
        }
    }

    public void setNetWorkTool(NetWorkTool netWorkTool) {
        this.netWorkTool = netWorkTool;
    }

    /**
     * 刷新fragment
     */
    public void refresh(){
        if(isAdded()) {
            netWorkTool.addToRequestQueue(request);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressBar.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelect(Subject subject) {
        Toast.makeText(getActivity(),"选中了"+subject.getName_cn(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(List<WeekSubjects> response) {
        weekSubjectsList = response;
        progressBar.setVisibility(View.GONE);
        calendarContainer.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins((int) (8 * UIParams.density), (int) (8 * UIParams.density),
                (int) (8 * UIParams.density), (int) (8 * UIParams.density));
        for(int i=0; i<response.size(); i++){
            SubjectsCardView subjectsCardView = new SubjectsCardView(getActivity());
            subjectsCardView.setWeekSubjects(response.get(i));
            subjectsCardView.setOnItemSelectListener(this);
            calendarContainer.addView(subjectsCardView,params);
        }
    }
}
