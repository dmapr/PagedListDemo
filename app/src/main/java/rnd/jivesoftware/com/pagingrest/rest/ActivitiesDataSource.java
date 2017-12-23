package rnd.jivesoftware.com.pagingrest.rest;

import android.arch.paging.PageKeyedDataSource;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rnd.jivesoftware.com.pagingrest.rest.models.ActivitiesModel;
import rnd.jivesoftware.com.pagingrest.rest.models.ActivityModel;
import rnd.jivesoftware.com.pagingrest.rest.services.JiveService;
import timber.log.Timber;

public class ActivitiesDataSource extends PageKeyedDataSource<String, ActivityModel> {
    private final JiveService jiveService;

    public ActivitiesDataSource(JiveService jiveService) {
        this.jiveService = jiveService;
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, ActivityModel> callback) {
        jiveService.getActivities(params.requestedLoadSize).enqueue(new Callback<ActivitiesModel>() {
            @Override
            public void onResponse(@NonNull Call<ActivitiesModel> call, @NonNull Response<ActivitiesModel> response) {
                ActivitiesModel body = response.body();
                List<ActivityModel> models = Collections.emptyList();
                String before = null;
                String after = null;
                if (body != null) {
                    models = body.list;
                    if (body.links != null) {
                        if (body.links.next != null) {
                            Uri next = Uri.parse(body.links.next);
                            before = next.getQueryParameter("before");
                        }
                        if (body.links.previous != null) {
                            Uri next = Uri.parse(body.links.previous);
                            after = next.getQueryParameter("after");
                        }
                    }
                 }
                callback.onResult(models, before, after);
            }

            @Override
            public void onFailure(@NonNull Call<ActivitiesModel> call, @NonNull Throwable t) {
                Timber.e(t,"Failed to load activities with %s", params);
                callback.onResult(Collections.<ActivityModel>emptyList(), null, null);
            }
        });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<String> params, @NonNull final LoadCallback<String, ActivityModel> callback) {
        jiveService.getNewerActivities(params.requestedLoadSize, params.key).enqueue(new Callback<ActivitiesModel>() {
            @Override
            public void onResponse(@NonNull Call<ActivitiesModel> call, @NonNull Response<ActivitiesModel> response) {
                ActivitiesModel body = response.body();
                List<ActivityModel> models = Collections.emptyList();
                String after = null;
                if (body != null) {
                    models = body.list;
                    if (body.links != null && body.links.previous != null) {
                        Uri next = Uri.parse(body.links.previous);
                        after = next.getQueryParameter("after");
                    }
                }
                callback.onResult(models, after);
            }

            @Override
            public void onFailure(@NonNull Call<ActivitiesModel> call, @NonNull Throwable t) {
                Timber.e(t,"Failed to load activities with %s", params);
                callback.onResult(Collections.<ActivityModel>emptyList(), null);
            }
        });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<String> params, @NonNull final LoadCallback<String, ActivityModel> callback) {
        jiveService.getOlderActivities(params.requestedLoadSize, params.key).enqueue(new Callback<ActivitiesModel>() {
            @Override
            public void onResponse(@NonNull Call<ActivitiesModel> call, @NonNull Response<ActivitiesModel> response) {
                ActivitiesModel body = response.body();
                List<ActivityModel> models = Collections.emptyList();
                String before = null;
                if (body != null) {
                    models = body.list;
                    if (body.links != null && body.links.next != null) {
                        Uri next = Uri.parse(body.links.next);
                        before = next.getQueryParameter("before");
                    }
                }
                callback.onResult(models, before);
            }

            @Override
            public void onFailure(@NonNull Call<ActivitiesModel> call, @NonNull Throwable t) {
                Timber.e(t,"Failed to load activities with %s", params);
                callback.onResult(Collections.<ActivityModel>emptyList(), null);
            }
        });
    }
}
