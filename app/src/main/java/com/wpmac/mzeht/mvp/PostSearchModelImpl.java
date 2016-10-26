package com.wpmac.mzeht.mvp;

import com.wpmac.mzeht.PostServiceBiz;
import com.wpmac.mzeht.retrofit.RetrofitUtils;
import com.wpmac.mzeht.pojo.PostQueryInfo;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wpmac on 16/9/10.
 */
public class PostSearchModelImpl implements PostSearchModel {
    @Override
    public void requestPostSearch(String type, String postid, final PostSearchCallback callback) {
        RetrofitUtils.getInstance()
                .getRetrofit()
                .create(PostServiceBiz.class)
                .searchRx(type, postid)
                //访问网络切换异步线程
                .subscribeOn(Schedulers.io())
                //响应结果处理切换成主线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PostQueryInfo>() {
                    @Override
                    public void onCompleted() {
                        //请求结束回调
                    }

                    @Override
                    public void onError(Throwable e) {
                        //错误回调
                        callback.requestPostSearchFail(e.getMessage());
                    }

                    @Override
                    public void onNext(PostQueryInfo postQueryInfo) {
                        //成功结果返回
                        callback.requestPostSearchSuccess(postQueryInfo);
                    }
                });
    }

}
