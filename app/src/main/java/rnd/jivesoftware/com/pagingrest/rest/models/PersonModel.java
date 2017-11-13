package rnd.jivesoftware.com.pagingrest.rest.models;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;

import java.util.Map;
import java.util.Objects;

public class PersonModel {
    public static DiffCallback<PersonModel> DIFF_CALLBACK = new DiffCallback<PersonModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull PersonModel oldItem, @NonNull PersonModel newItem) {
            return Objects.equals(oldItem.id, newItem.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull PersonModel oldItem, @NonNull PersonModel newItem) {
            ResourceModel oldAvatar = oldItem.resources.get("avatar");
            ResourceModel newAvatar = newItem.resources.get("avatar");
            return Objects.equals(oldItem.displayName, newItem.displayName) &&
                    Objects.equals(oldAvatar, newAvatar);
        }
    };

    public String id;
    public String displayName;
    public Map<String, ResourceModel> resources;
}
