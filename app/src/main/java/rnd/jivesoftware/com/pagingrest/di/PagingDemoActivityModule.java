package rnd.jivesoftware.com.pagingrest.di;

import android.arch.lifecycle.ViewModelProviders;

import dagger.Module;
import dagger.Provides;
import rnd.jivesoftware.com.pagingrest.ActivitiesViewModel;
import rnd.jivesoftware.com.pagingrest.ActivitiesViewModelFactory;
import rnd.jivesoftware.com.pagingrest.PagingDemoActivity;
import rnd.jivesoftware.com.pagingrest.PeopleViewModel;
import rnd.jivesoftware.com.pagingrest.PeopleViewModelFactory;

@Module
public class PagingDemoActivityModule {
    @Provides
    @PerActivity
    ActivitiesViewModel activitiesViewModel(PagingDemoActivity activity, ActivitiesViewModelFactory factory) {
        return ViewModelProviders.of(activity, factory).get(ActivitiesViewModel.class);
    }

    @Provides
    @PerActivity
    PeopleViewModel peopleViewModel(PagingDemoActivity activity, PeopleViewModelFactory factory) {
        return ViewModelProviders.of(activity, factory).get(PeopleViewModel.class);
    }
}
