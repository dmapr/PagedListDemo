package rnd.jivesoftware.com.pagingrest.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import rnd.jivesoftware.com.pagingrest.PagingDemoActivity;

@Module
public abstract class ActivityInjectorsModule {
    @PerActivity
    @ContributesAndroidInjector(modules = {PagingDemoActivityModule.class, FragmentInjectorsModule.class})
    public abstract PagingDemoActivity mainActivity();
}
