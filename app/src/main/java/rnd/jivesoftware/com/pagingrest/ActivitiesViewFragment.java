package rnd.jivesoftware.com.pagingrest;

import android.arch.lifecycle.Observer;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.Lazy;
import rnd.jivesoftware.com.pagingrest.rest.models.ActivityModel;

public class ActivitiesViewFragment extends PagingDemoFragment {
    // Important: the injection happens before onCreate is processed
    // Therefore we need to inject a Lazy ViewModel, otherwise the LiveData framework
    // will not initialize properly in time
    @Inject Lazy<ActivitiesViewModel> activitiesViewModel;
    @Inject JiveActivitiesAdapter jiveActivitiesAdapter;


    @Override
    protected void reloadViewModel() {
        activitiesViewModel.get().reload();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        activitiesViewModel.get().activitiesList.observe(this, new Observer<PagedList<ActivityModel>>() {
            @Override
            public void onChanged(@Nullable PagedList<ActivityModel> activityModels) {
                jiveActivitiesAdapter.setList(activityModels);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        recyclerView.setAdapter(jiveActivitiesAdapter);
        return view;
    }
}
