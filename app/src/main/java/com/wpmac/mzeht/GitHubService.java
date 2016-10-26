package com.wpmac.mzeht;

import com.wpmac.mzeht.pojo.PostQueryInfo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by wpmac on 16/9/9.
 */
public interface GitHubService {

    @GET("users/{user}/repos")
    Call<ResponseBody> listRepos(@Path("user") String user);

    @POST("query")
    Call<PostQueryInfo> search(@Query("type") String type, @Query("postid") String postid);
}
