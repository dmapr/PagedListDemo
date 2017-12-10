package rnd.jivesoftware.com.pagingrest.rest;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rnd.jivesoftware.com.pagingrest.rest.models.ActivitiesModel;
import rnd.jivesoftware.com.pagingrest.rest.models.ActivityModel;
import rnd.jivesoftware.com.pagingrest.rest.services.JiveService;
import rnd.jivesoftware.com.pagingrest.util.DateUtil;
import timber.log.Timber;

public class ActivitiesDataSource extends PageKeyedDataSource<Date, ActivityModel> {
    private final JiveService jiveService;

    public ActivitiesDataSource(JiveService jiveService) {
        this.jiveService = jiveService;
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Date> params, @NonNull final LoadInitialCallback<Date, ActivityModel> callback) {
        jiveService.getActivities(params.requestedLoadSize).enqueue(new Callback<ActivitiesModel>() {
            @Override
            public void onResponse(@NonNull Call<ActivitiesModel> call, @NonNull Response<ActivitiesModel> response) {
                ActivitiesModel body = response.body();
                List<ActivityModel> models = Collections.emptyList();
                Date newerDate = null;
                Date olderDate = null;
                if (body != null) {
                    models = body.list;
                    if (!models.isEmpty()) {
                        olderDate = models.get(models.size() - 1).jive.collectionUpdated;
                        newerDate = models.get(0).jive.collectionUpdated;
                    }
                }
                callback.onResult(models, olderDate, newerDate);
            }

            @Override
            public void onFailure(@NonNull Call<ActivitiesModel> call, @NonNull Throwable t) {
                Timber.e(t,"Failed to load activities with %s", params);
                callback.onResult(Collections.<ActivityModel>emptyList(), null, null);
            }
        });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Date> params, @NonNull final LoadCallback<Date, ActivityModel> callback) {
        jiveService.getNewerActivities(params.requestedLoadSize, DateUtil.toISO8601(params.key)).enqueue(new Callback<ActivitiesModel>() {
            @Override
            public void onResponse(@NonNull Call<ActivitiesModel> call, @NonNull Response<ActivitiesModel> response) {
                ActivitiesModel body = response.body();
                List<ActivityModel> models = Collections.emptyList();
                Date newerDate = null;
                if (body != null) {
                    models = body.list;
                    if (!models.isEmpty()) {
                        newerDate = models.get(0).jive.collectionUpdated;
                    }
                }
                callback.onResult(models, newerDate);
            }

            @Override
            public void onFailure(@NonNull Call<ActivitiesModel> call, @NonNull Throwable t) {
                Timber.e(t,"Failed to load activities with %s", params);
                callback.onResult(Collections.<ActivityModel>emptyList(), null);
            }
        });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Date> params, @NonNull final LoadCallback<Date, ActivityModel> callback) {
        jiveService.getOlderActivities(params.requestedLoadSize, DateUtil.toISO8601(params.key)).enqueue(new Callback<ActivitiesModel>() {
            @Override
            public void onResponse(@NonNull Call<ActivitiesModel> call, @NonNull Response<ActivitiesModel> response) {
                ActivitiesModel body = response.body();
                List<ActivityModel> models = Collections.emptyList();
                Date olderDate = null;
                if (body != null) {
                    models = body.list;
                    if (!models.isEmpty()) {
                        olderDate = models.get(models.size() - 1).jive.collectionUpdated;
                    }
                }
                callback.onResult(models, olderDate);
            }

            @Override
            public void onFailure(@NonNull Call<ActivitiesModel> call, @NonNull Throwable t) {
                Timber.e(t,"Failed to load activities with %s", params);
                callback.onResult(Collections.<ActivityModel>emptyList(), null);
            }
        });
    }
}
