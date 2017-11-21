package rnd.jivesoftware.com.pagingrest.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import rnd.jivesoftware.com.pagingrest.ActivitiesViewFragment;
import rnd.jivesoftware.com.pagingrest.PeopleViewFragment;

@Module
public abstract class FragmentInjectorsModule {
    @PerFragment
    @ContributesAndroidInjector
    public abstract PeopleViewFragment peopleViewFragment();

    @PerFragment
    @ContributesAndroidInjector
    public abstract ActivitiesViewFragment activitiesViewFragment();
}
