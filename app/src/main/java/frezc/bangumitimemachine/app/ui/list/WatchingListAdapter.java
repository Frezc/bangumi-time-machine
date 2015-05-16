package frezc.bangumitimemachine.app.ui.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import frezc.bangumitimemachine.app.R;
import frezc.bangumitimemachine.app.entity.WatchingSubject;
import frezc.bangumitimemachine.app.ui.callback.OnItemClickListener;
import frezc.bangumitimemachine.app.ui.callback.OnItemLongClickListener;
import frezc.bangumitimemachine.app.ui.callback.OnSubjectUpdateListener;

import java.util.List;

/**
 * Created by freeze on 2015/5/10.
 */
public class WatchingListAdapter extends RecyclerView.Adapter<SSubjectViewHolder>
    implements OnSubjectUpdateListener {

    private List<WatchingSubject> data;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    @Override
    public SSubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ssubject,parent,false);
        SSubjectViewHolder vh = new SSubjectViewHolder(itemView,
                onItemClickListener, onItemLongClickListener, this);

        return vh;
    }


    @Override
    public void onBindViewHolder(SSubjectViewHolder holder, int position) {
        WatchingSubject subject = data.get(position);
        holder.subjectName.setText(subject.getSubject().getName_cn());
        holder.subjectProgressText.setText(
                getProgressText(subject.getEp_status(), subject.getSubject().getEps()));
        holder.subjectProgress.setMax(subject.getSubject().getEps());
        holder.subjectProgress.setProgress(subject.getEp_status());
        holder.subjectWatchNext.setText("EP."+(subject.getEp_status()+1));
    }

    public void clearData(){
        if(data != null) {
            int count = data.size();
            data.clear();
            notifyItemRangeRemoved(0, count);
        }
    }

    public void setData(List<WatchingSubject> data){
        clearData();
        this.data = data;
//        notifyDataSetChanged();
        notifyItemRangeInserted(0, data.size());
    }

    private String getProgressText(int ep_status, int eps) {
        return ""+ep_status+" / "+eps;
    }

    @Override
    public int getItemCount() {
        if(data == null){
            return 0;
        }else {
            return data.size();
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public void onSubjectUpdate(View v, int position) {

    }



}
