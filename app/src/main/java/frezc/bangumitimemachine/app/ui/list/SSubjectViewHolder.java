package frezc.bangumitimemachine.app.ui.list;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.*;
import frezc.bangumitimemachine.app.R;
import frezc.bangumitimemachine.app.ui.callback.OnItemClickListener;
import frezc.bangumitimemachine.app.ui.callback.OnItemLongClickListener;
import frezc.bangumitimemachine.app.ui.callback.OnSubjectUpdateListener;
import frezc.bangumitimemachine.app.ui.customview.SwipeLayout;

/**
 * Created by freeze on 2015/5/15.
 */
public class SSubjectViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener,View.OnLongClickListener{
    public ImageView subjectImg;
    public TextView subjectName, subjectProgressText, subjectWatchNext;
    public ProgressBar subjectProgress;
    public LinearLayout subjectUpdate;
    public SwipeLayout rootView;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnSubjectUpdateListener onSubjectUpdateListener;



    public SSubjectViewHolder(View itemView, OnItemClickListener onItemClickListener,
                              OnItemLongClickListener onItemLongClickListener,
                              OnSubjectUpdateListener onSubjectUpdateListener) {
        super(itemView);
        subjectImg = (ImageView) itemView.findViewById(R.id.subject_img);
        subjectName = (TextView) itemView.findViewById(R.id.subject_name);
        subjectProgressText = (TextView) itemView.findViewById(R.id.subject_progress_text);
        subjectWatchNext = (TextView) itemView.findViewById(R.id.subject_watch_update_next);
        subjectProgress = (ProgressBar) itemView.findViewById(R.id.subject_progress);
        subjectUpdate = (LinearLayout) itemView.findViewById(R.id.subject_watch_update);
        rootView = (SwipeLayout) itemView.findViewById(R.id.subject_root);

        rootView.setOnClickListener(this);
        rootView.setOnLongClickListener(this);
        subjectUpdate.setOnClickListener(this);

        //两个侧滑菜单
        rootView.findViewById(R.id.intent_drop).setOnClickListener(this);
        rootView.findViewById(R.id.intent_finish).setOnClickListener(this);

        rootView.findViewById(R.id.intent_drop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("drag","you click the drop button");
            }
        });

        this.onItemClickListener = onItemClickListener;
        this.onItemLongClickListener = onItemLongClickListener;
        this.onSubjectUpdateListener = onSubjectUpdateListener;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.subject_root:
                if(onItemClickListener != null){
                    onItemClickListener.onItemClick(v, getLayoutPosition());
                }
                break;
            case R.id.subject_watch_update:
                if(onSubjectUpdateListener != null){
                    onSubjectUpdateListener.onSubjectUpdate(v, getLayoutPosition());
                    v.setClickable(false);
                }
                break;
            case R.id.intent_drop:
                if(onSubjectUpdateListener != null){
                    onSubjectUpdateListener.onSubjectDrop(v, getLayoutPosition());
                }
                break;
            case R.id.intent_finish:
                if(onSubjectUpdateListener != null){
                    onSubjectUpdateListener.onSubjectCompelete(v, getLayoutPosition());
                }
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(onItemLongClickListener != null){
            onItemLongClickListener.onItemLongClick(v,getLayoutPosition());
            return true;
        }
        return false;
    }
}
