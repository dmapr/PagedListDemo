package rnd.jivesoftware.com.pagingrest;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListProvider;
import android.arch.paging.PagedList;

import rnd.jivesoftware.com.pagingrest.rest.PeopleDataSource;
import rnd.jivesoftware.com.pagingrest.rest.models.PersonModel;
import rnd.jivesoftware.com.pagingrest.rest.services.JiveService;

public class PeopleViewModel extends ViewModel {
    public static final int PAGE_SIZE = 15;
    public LiveData<PagedList<PersonModel>> peopleList;
    private PeopleDataSource peopleDataSource;

    public PeopleViewModel(final JiveService jiveService) {
        peopleList = new LivePagedListProvider<Integer, PersonModel>() {

            @Override
            protected DataSource<Integer, PersonModel> createDataSource() {
                peopleDataSource = new PeopleDataSource(jiveService);
                return peopleDataSource;
            }
        }.create(0, new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(PAGE_SIZE)
                .setPageSize(PAGE_SIZE)
                .build()
        );
    }

    public void reload() {
        peopleDataSource.invalidate();
    }
}
