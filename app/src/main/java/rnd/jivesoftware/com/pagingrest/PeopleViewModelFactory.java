package rnd.jivesoftware.com.pagingrest;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import rnd.jivesoftware.com.pagingrest.rest.services.JiveService;
import timber.log.Timber;

public class PeopleViewModelFactory implements ViewModelProvider.Factory {
    private final JiveService jiveService;

    @Inject
    public PeopleViewModelFactory(JiveService jiveService) {
        this.jiveService = jiveService;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (PeopleViewModel.class.isAssignableFrom(modelClass)) {
            try {
                return modelClass.getConstructor(JiveService.class).newInstance(jiveService);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                Timber.e(e, "Failed to instantiate a view model");
            }
        }
        return null;
    }
}
