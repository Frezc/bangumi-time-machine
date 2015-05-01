package frezc.bangumitimemachine.app.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import frezc.bangumitimemachine.app.entity.WeekSubjects;

import java.util.List;

/**
 * Created by freeze on 2015/5/2.
 */
public class CalendarFragment extends Fragment {

    private List<WeekSubjects> weekSubjectsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new ProgressBar(getActivity());
    }


}
