package com.wpmac.mzeht;

import com.wpmac.mzeht.pojo.PostQueryInfo;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wpmac on 16/9/10.
 */
public interface PostServiceBiz {

    @POST("query")
    Observable<PostQueryInfo> searchRx(@Query("type") String type, @Query("postid") String postid);
}
