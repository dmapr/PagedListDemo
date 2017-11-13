package rnd.jivesoftware.com.pagingrest.rest;

import android.arch.paging.DataSource;
import android.arch.paging.TiledDataSource;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import retrofit2.Response;
import rnd.jivesoftware.com.pagingrest.rest.models.PeopleModel;
import rnd.jivesoftware.com.pagingrest.rest.models.PersonModel;
import rnd.jivesoftware.com.pagingrest.rest.services.JiveService;
import timber.log.Timber;

public class PeopleDataSource extends TiledDataSource<PersonModel> {
    private final JiveService jiveService;

    public PeopleDataSource(JiveService jiveService) {
        this.jiveService = jiveService;
    }

    @Override
    public int countItems() {
        return DataSource.COUNT_UNDEFINED;
    }

    @Override
    public List<PersonModel> loadRange(int startPosition, int count) {
        List<PersonModel> models = Collections.emptyList();
        try {
            Response<PeopleModel> response = jiveService.getPeople(count, startPosition).execute();
            PeopleModel body = response.body();
            if (body != null) {
                models = body.list;
            }
        } catch (IOException e) {
            Timber.e(e, "Failed from position %d and count %d", startPosition, count);
        }
        return models;
    }
}
