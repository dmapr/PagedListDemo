package rnd.jivesoftware.com.pagingrest;

import javax.inject.Singleton;

import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;
import rnd.jivesoftware.com.pagingrest.di.ActivityInjectorsModule;
import rnd.jivesoftware.com.pagingrest.di.NetworkModule;
import timber.log.Timber;

public class PagingDemoApplication extends DaggerApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerPagingDemoApplication_Component
                .builder()
                .create(this);
    }

    @Singleton
    @dagger.Component(modules = {
            AndroidSupportInjectionModule.class,
            NetworkModule.class,
            ActivityInjectorsModule.class
    })
    public interface Component extends AndroidInjector<PagingDemoApplication> {
        @dagger.Component.Builder
        abstract class Builder extends AndroidInjector.Builder<PagingDemoApplication> {
        }
    }
}
