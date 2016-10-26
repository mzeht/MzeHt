package com.wpmac.mzeht.mvp;

import com.wpmac.mzeht.pojo.PostQueryInfo;

/**
 * Created by wpmac on 16/9/10.
 */
public interface PostSearchModel {

    /**
     * 请求快递信息
     * @param type 快递类型
     * @param postid 快递单号
     * @param callback 结果回调
     */
    void requestPostSearch(String type,String postid,PostSearchCallback callback);
    interface PostSearchCallback{
        void requestPostSearchSuccess(PostQueryInfo postQueryInfo);
        void requestPostSearchFail(String failStr);
    }
}
