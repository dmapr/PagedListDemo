package rnd.jivesoftware.com.pagingrest;

import android.arch.paging.PagedListAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import rnd.jivesoftware.com.pagingrest.rest.models.ActivityModel;

public class JiveActivitiesAdapter extends PagedListAdapter<ActivityModel, ActivityViewHolder> {
    @Inject
    public JiveActivitiesAdapter() {
        super(ActivityModel.DIFF_CALLBACK);
    }

    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_item, parent, false);
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActivityViewHolder holder, int position) {
        ActivityModel activityModel = getItem(position);
        if (activityModel != null) {
            holder.bind(activityModel);
        }
    }
}
