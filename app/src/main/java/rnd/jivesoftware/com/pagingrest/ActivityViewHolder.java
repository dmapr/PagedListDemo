package rnd.jivesoftware.com.pagingrest;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;

import rnd.jivesoftware.com.pagingrest.rest.models.ActivityModel;

public class ActivityViewHolder extends RecyclerView.ViewHolder {
    private final TextView actorName;
    private final TextView activityDate;
    private final TextView activityText;
    private final ImageView actorAvatar;

    public ActivityViewHolder(View itemView) {
        super(itemView);

        actorName = itemView.findViewById(R.id.actor_name);
        activityDate = itemView.findViewById(R.id.activity_date);
        activityText = itemView.findViewById(R.id.activity_text);
        actorAvatar = itemView.findViewById(R.id.actor_avatar);
    }

    public void bind(ActivityModel activityModel) {
        activityDate.setText(DateFormat.getDateTimeInstance().format(activityModel.jive.collectionUpdated));
        actorName.setText(activityModel.actor.displayName);
        activityText.setText(TextUtils.isEmpty(activityModel.title) ? Html.fromHtml(activityModel.content) : activityModel.title);

        Glide.with(actorAvatar)
                .asBitmap()
                .load(activityModel.actor.image.url)
                .into(actorAvatar);
    }
}
