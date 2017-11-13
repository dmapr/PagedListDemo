package rnd.jivesoftware.com.pagingrest;

import android.arch.paging.PagedListAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rnd.jivesoftware.com.pagingrest.rest.models.PersonModel;

public class JivePeopleAdapter extends PagedListAdapter<PersonModel, PersonViewHolder> {
    public JivePeopleAdapter() {
        super(PersonModel.DIFF_CALLBACK);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_list_item, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        PersonModel personModel = getItem(position);
        if (personModel != null) {
            holder.bind(personModel);
        }
    }
}
