package rnd.jivesoftware.com.pagingrest.rest;

import android.arch.paging.PositionalDataSource;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rnd.jivesoftware.com.pagingrest.rest.models.PeopleModel;
import rnd.jivesoftware.com.pagingrest.rest.models.PersonModel;
import rnd.jivesoftware.com.pagingrest.rest.services.JiveService;
import timber.log.Timber;

public class PeopleDataSource extends PositionalDataSource<PersonModel> {
    private final JiveService jiveService;

    public PeopleDataSource(JiveService jiveService) {
        this.jiveService = jiveService;
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams params, @NonNull final LoadInitialCallback<PersonModel> callback) {
        jiveService.getPeople(params.requestedLoadSize, params.requestedStartPosition).enqueue(new Callback<PeopleModel>() {
            @Override
            public void onResponse(@NonNull Call<PeopleModel> call, @NonNull Response<PeopleModel> response) {
                PeopleModel body = response.body();
                List<PersonModel> models = Collections.emptyList();
                if (body != null) {
                    models = body.list;
                }
                callback.onResult(models, params.requestedStartPosition);
            }

            @Override
            public void onFailure(@NonNull Call<PeopleModel> call, @NonNull Throwable t) {
                Timber.e(t,"Failed to load activities with %s", params);
                callback.onResult(Collections.<PersonModel>emptyList(), 0);
            }
        });
    }

    @Override
    public void loadRange(@NonNull final LoadRangeParams params, @NonNull final LoadRangeCallback<PersonModel> callback) {
        jiveService.getPeople(params.loadSize, params.startPosition).enqueue(new Callback<PeopleModel>() {
            @Override
            public void onResponse(@NonNull Call<PeopleModel> call, @NonNull Response<PeopleModel> response) {
                PeopleModel body = response.body();
                List<PersonModel> models = Collections.emptyList();
                if (body != null) {
                    models = body.list;
                }

                callback.onResult(models);
            }

            @Override
            public void onFailure(@NonNull Call<PeopleModel> call, @NonNull Throwable t) {
                Timber.e(t,"Failed to load activities with %s", params);
                callback.onResult(Collections.<PersonModel>emptyList());
            }
        });
    }
}
