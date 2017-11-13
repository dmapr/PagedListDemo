package rnd.jivesoftware.com.pagingrest;

import android.app.Application;

import timber.log.Timber;

public class JiveApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
    }
}
