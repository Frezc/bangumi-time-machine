package frezc.bangumitimemachine.app.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import frezc.bangumitimemachine.app.R;
import frezc.bangumitimemachine.app.entity.Subject;
import frezc.bangumitimemachine.app.entity.WeekSubjects;
import frezc.bangumitimemachine.app.network.http.GsonRequest;
import frezc.bangumitimemachine.app.network.http.NetParams;
import frezc.bangumitimemachine.app.network.http.NetWorkTool;
import frezc.bangumitimemachine.app.ui.UIParams;
import frezc.bangumitimemachine.app.ui.customview.SubjectsCardView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by freeze on 2015/5/2.
 * 实现了请求回调接口, 项目选择接口, 刷新接口
 */
public class CalendarFragment extends NetFragment
        implements GsonRequest.OnListResponseListener<WeekSubjects>,
        SubjectsCardView.OnItemSelectListener {

    private List<WeekSubjects> weekSubjectsList;
    private LinearLayout calendarContainer;
    private ProgressBar progressBar;
    private TextView errorView;
    private View rootView;


    public static CalendarFragment newInstance(Context context){
        CalendarFragment calendarFragment = new CalendarFragment();
        calendarFragment.setNetWorkTool(NetWorkTool.getInstance(context));
        calendarFragment.setResetFlag(true);
        return calendarFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        headers = new HashMap<String, String>();
        headers.put("Accept-Encoding","gzip");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

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
        if(isReset){
            showLoading();
            refresh();
            isReset = false;
        }
    }

    /**
     * 刷新fragment
     */
    @Override
    public void refresh(){
        if(isAdded()) {
            netWorkTool.clearCache();
            GsonRequest<WeekSubjects> request = new GsonRequest<WeekSubjects>(getActivity(),Request.Method.GET,
                    NetParams.CALENDAR_URL, WeekSubjects.class,headers,this,this);
            request.setTag(this);
            netWorkTool.addToRequestQueue(request);
        }
    }

    private void showLoading(){
        progressBar.setVisibility(View.VISIBLE);
        calendarContainer.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    private void showCalendar(){
        progressBar.setVisibility(View.GONE);
        calendarContainer.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    private void showError(){
        progressBar.setVisibility(View.GONE);
        calendarContainer.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        showError();
        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelect(Subject subject) {
        Toast.makeText(getActivity(),"选中了"+subject.getName_cn(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(List<WeekSubjects> response) {
        if(weekSubjectsList != null){
            weekSubjectsList.clear();
        }
        weekSubjectsList = response;
        showCalendar();
        //清空container
        calendarContainer.removeAllViews();
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

    @Override
    public void onRefresh() {
        showLoading();
        refresh();
    }
}
