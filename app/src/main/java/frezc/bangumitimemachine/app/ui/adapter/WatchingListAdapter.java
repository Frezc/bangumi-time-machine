package frezc.bangumitimemachine.app.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import frezc.bangumitimemachine.app.R;
import frezc.bangumitimemachine.app.entity.WatchingSubject;
import frezc.bangumitimemachine.app.ui.callback.OnItemClickListener;
import frezc.bangumitimemachine.app.ui.callback.OnItemLongClickListener;

import java.util.List;

/**
 * Created by freeze on 2015/5/10.
 */
public class WatchingListAdapter extends RecyclerView.Adapter<WatchingListAdapter.SSubjectViewHolder> {

    private List<WatchingSubject> data;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public WatchingListAdapter(@NonNull List<WatchingSubject> data){
        this.data = data;

    }

    @Override
    public SSubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;
    }


    @Override
    public void onBindViewHolder(SSubjectViewHolder holder, int position) {
        WatchingSubject subject = data.get(position);
        holder.subjectName.setText(subject.getName());
        holder.subjectProgressText.setText(
                getProgressText(subject.getEp_status(), subject.getSubject().getEps()));
        holder.subjectProgress.setMax(subject.getSubject().getEps());
        holder.subjectProgress.setProgress(subject.getEp_status());
        holder.subjectWatchNext.setText("EP."+(subject.getEp_status()+1));
    }

    private String getProgressText(int ep_status, int eps) {
        return ""+ep_status+" / "+eps;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public static class SSubjectViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener,View.OnLongClickListener{
        public ImageView subjectImg;
        public TextView subjectName, subjectProgressText, subjectWatchNext;
        public ProgressBar subjectProgress;
        public LinearLayout subjectUpdate;
        public RelativeLayout rootView;

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
            rootView = (RelativeLayout) itemView.findViewById(R.id.subject_root);

            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);
            subjectUpdate.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.subject_root:

                    break;
                case R.id.subject_watch_update:

                    break;
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    public interface OnSubjectUpdateListener{
        void onSubjectUpdate(int position);
    }

}
