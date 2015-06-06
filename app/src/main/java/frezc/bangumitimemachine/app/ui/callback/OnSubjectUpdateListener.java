package frezc.bangumitimemachine.app.ui.callback;

import android.view.View;

/**
 * Created by freeze on 2015/5/15.
 */
public interface OnSubjectUpdateListener{
    void onSubjectUpdate(View v,int position);
    void onSubjectDrop(View v,int position);
    void onSubjectCompelete(View v, int position);
}