package rnd.jivesoftware.com.pagingrest;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import rnd.jivesoftware.com.pagingrest.rest.models.PersonModel;

public class PersonViewHolder extends RecyclerView.ViewHolder {
    private final TextView personName;
    private final ImageView personAvatar;

    public PersonViewHolder(View itemView) {
        super(itemView);

        personName = itemView.findViewById(R.id.person_name);
        personAvatar = itemView.findViewById(R.id.person_avatar);
    }

    public void bind(PersonModel personModel) {
        personName.setText(personModel.displayName);

        Glide.with(personAvatar)
                .asBitmap()
                .load(personModel.resources.get("avatar").ref)
                .apply(RequestOptions.circleCropTransform())
                .into(personAvatar);
    }
}
