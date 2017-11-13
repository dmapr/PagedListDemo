package rnd.jivesoftware.com.pagingrest.rest.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rnd.jivesoftware.com.pagingrest.rest.models.ActivitiesModel;

public interface JiveService {
    @GET("api/core/v3/activities?collapse=true&filter=type(document,discussion,post)&filter=verb(created,modified)")
    Call<ActivitiesModel> getActivities(@Query("count") int count);

    @GET("api/core/v3/activities?collapse=true&filter=type(document,discussion,post)&filter=verb(created,modified)")
    Call<ActivitiesModel> getNewerActivities(@Query("count") int count, @Query("after") String after);

    @GET("api/core/v3/activities?collapse=true&filter=type(document,discussion,post)&filter=verb(created,modified)")
    Call<ActivitiesModel> getOlderActivities(@Query("count") int count, @Query("before") String before);
}
