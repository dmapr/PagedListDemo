package rnd.jivesoftware.com.pagingrest.rest;

import android.arch.paging.KeyedDataSource;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Response;
import rnd.jivesoftware.com.pagingrest.rest.models.ActivitiesModel;
import rnd.jivesoftware.com.pagingrest.rest.models.ActivityModel;
import rnd.jivesoftware.com.pagingrest.rest.services.JiveService;
import rnd.jivesoftware.com.pagingrest.util.DateUtil;
import timber.log.Timber;

public class ActivitiesDataSource extends KeyedDataSource<Date, ActivityModel> {
    private final JiveService jiveService;

    public ActivitiesDataSource(JiveService jiveService) {
        this.jiveService = jiveService;
    }

    @NonNull
    @Override
    public Date getKey(@NonNull ActivityModel item) {
        return item.jive.collectionUpdated;
    }

    @Nullable
    @Override
    public List<ActivityModel> loadInitial(int pageSize) {
        List<ActivityModel> models = Collections.emptyList();
        try {
            Response<ActivitiesModel> response = jiveService.getActivities(pageSize).execute();
            ActivitiesModel body = response.body();
            if (body != null) {
                models = body.list;
            }
        } catch (IOException e) {
            Timber.e(e, "Failed with the initial load and page size %d", pageSize);
        }

        return models;
    }

    @Nullable
    @Override
    public List<ActivityModel> loadAfter(@NonNull Date currentEndKey, int pageSize) {
        List<ActivityModel> models = Collections.emptyList();
        try {
            Response<ActivitiesModel> response = jiveService.getOlderActivities(pageSize, DateUtil.toISO8601(currentEndKey)).execute();
            ActivitiesModel body = response.body();
            if (body != null) {
                models = body.list;
            }
        } catch (IOException e) {
            Timber.e(e, "Failed to load after %s and page size %d", currentEndKey, pageSize);
        }

        return models;
    }

    @Nullable
    @Override
    public List<ActivityModel> loadBefore(@NonNull Date currentBeginKey, int pageSize) {
        List<ActivityModel> models = Collections.emptyList();
        try {
            Response<ActivitiesModel> response = jiveService.getNewerActivities(pageSize, DateUtil.toISO8601(currentBeginKey)).execute();
            ActivitiesModel body = response.body();
            if (body != null) {
                models = body.list;
            }
        } catch (IOException e) {
            Timber.e(e, "Failed to load before %s and page size %d", currentBeginKey, pageSize);
        }

        return models;
    }

}
