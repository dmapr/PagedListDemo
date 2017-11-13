package rnd.jivesoftware.com.pagingrest.rest.models;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;

import java.util.Objects;

public class ActivityModel {
    public static DiffCallback<ActivityModel> DIFF_CALLBACK = new DiffCallback<ActivityModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull ActivityModel oldItem, @NonNull ActivityModel newItem) {
            return Objects.equals(oldItem.jive.collection, newItem.jive.collection);
        }

        @Override
        public boolean areContentsTheSame(@NonNull ActivityModel oldItem, @NonNull ActivityModel newItem) {
            return Objects.equals(oldItem.actor, newItem.actor) &&
                    Objects.equals(oldItem.content, newItem.content) &&
                    Objects.equals(oldItem.title, newItem.title);
        }
    };
    public String content = "";
    public String title = "";

    public ActorModel actor;
    public JiveModel jive;
}
