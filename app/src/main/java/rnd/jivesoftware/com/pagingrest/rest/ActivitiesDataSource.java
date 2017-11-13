package rnd.jivesoftware.com.pagingrest.rest;

import android.arch.paging.KeyedDataSource;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Response;
import rnd.jivesoftware.com.pagingrest.rest.models.ActivitiesModel;
import rnd.jivesoftware.com.pagingrest.rest.models.ActivityModel;
import rnd.jivesoftware.com.pagingrest.rest.services.JiveService;
import timber.log.Timber;

public class ActivitiesDataSource extends KeyedDataSource<Date, ActivityModel> {
    private final JiveService jiveService;
    private final SimpleDateFormat dateFormat;

    public ActivitiesDataSource(JiveService jiveService) {
        this.jiveService = jiveService;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH);
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
            Timber.e(e, "Failed with the initial load");
        }

        return models;
    }

    @Nullable
    @Override
    public List<ActivityModel> loadAfter(@NonNull Date currentEndKey, int pageSize) {
        List<ActivityModel> models = Collections.emptyList();
        try {
            Response<ActivitiesModel> response = jiveService.getOlderActivities(pageSize, toIso8601(currentEndKey)).execute();
            ActivitiesModel body = response.body();
            if (body != null) {
                models = body.list;
            }
        } catch (IOException e) {
            Timber.e(e, "Failed with the initial load");
        }

        return models;
    }

    @Nullable
    @Override
    public List<ActivityModel> loadBefore(@NonNull Date currentBeginKey, int pageSize) {
        List<ActivityModel> models = Collections.emptyList();
        try {
            Response<ActivitiesModel> response = jiveService.getNewerActivities(pageSize, toIso8601(currentBeginKey)).execute();
            ActivitiesModel body = response.body();
            if (body != null) {
                models = body.list;
            }
        } catch (IOException e) {
            Timber.e(e, "Failed with the initial load");
        }

        return models;
    }

    private String toIso8601(Date date) {
        return dateFormat.format(date);
    }
}
