package rnd.jivesoftware.com.pagingrest;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListProvider;
import android.arch.paging.PagedList;

import java.util.Date;

import rnd.jivesoftware.com.pagingrest.rest.ActivitiesDataSource;
import rnd.jivesoftware.com.pagingrest.rest.models.ActivityModel;
import rnd.jivesoftware.com.pagingrest.rest.services.JiveService;

public class ActivitiesViewModel extends ViewModel {
    public LiveData<PagedList<ActivityModel>> activitiesList;
    private ActivitiesDataSource activitiesDataSource;

    public ActivitiesViewModel(final JiveService jiveService) {
        activitiesList = new LivePagedListProvider<Date, ActivityModel>() {

            @Override
            protected DataSource<Date, ActivityModel> createDataSource() {
                activitiesDataSource = new ActivitiesDataSource(jiveService);
                return activitiesDataSource;
            }
        }.create(new Date(), new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(5)
                .setPageSize(5)
                .build()
        );
    }

    public void reload() {
        activitiesDataSource.invalidate();
    }
}
