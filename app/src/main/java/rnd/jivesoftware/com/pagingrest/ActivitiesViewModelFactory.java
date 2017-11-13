package rnd.jivesoftware.com.pagingrest;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import java.lang.reflect.InvocationTargetException;

import rnd.jivesoftware.com.pagingrest.rest.services.JiveService;
import timber.log.Timber;

public class ActivitiesViewModelFactory implements ViewModelProvider.Factory {
    private final JiveService jiveService;

    public ActivitiesViewModelFactory(JiveService jiveService) {
        this.jiveService = jiveService;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (ActivitiesViewModel.class.isAssignableFrom(modelClass)) {
            try {
                return modelClass.getConstructor(JiveService.class).newInstance(jiveService);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                Timber.e(e, "Failed to instantiate a view model");
            }
        }
        return null;
    }
}
