package rnd.jivesoftware.com.pagingrest;

import android.arch.lifecycle.Observer;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.Lazy;
import rnd.jivesoftware.com.pagingrest.rest.models.PersonModel;

public class PeopleViewFragment extends PagingDemoFragment {
    @Inject Lazy<PeopleViewModel> peopleViewModel;
    @Inject JivePeopleAdapter jivePeopleAdapter;

    @Override
    protected void reloadViewModel() {
        peopleViewModel.get().reload();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        peopleViewModel.get().peopleList.observe(this, new Observer<PagedList<PersonModel>>() {
            @Override
            public void onChanged(@Nullable PagedList<PersonModel> personModels) {
                jivePeopleAdapter.setList(personModels);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        recyclerView.setAdapter(jivePeopleAdapter);
        return view;
    }
}
