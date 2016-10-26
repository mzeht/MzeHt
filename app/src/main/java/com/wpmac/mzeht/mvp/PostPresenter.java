package com.wpmac.mzeht.mvp;

import com.wpmac.mzeht.pojo.PostQueryInfo;

/**
 * Created by wpmac on 16/9/10.
 */
public class PostPresenter extends BasePresenter<MainView>{
    private PostSearchModel postSearchModel;
    public PostPresenter(MainView mainView){
        attach(mainView);
        postSearchModel = new PostSearchModelImpl();
    }
    public void requestHomeData(String type,String postid){
        if(postSearchModel == null||mView == null)
            return;
        mView.showProgressDialog();
        postSearchModel.requestPostSearch(type, postid, new PostSearchModel.PostSearchCallback() {
            @Override
            public void requestPostSearchSuccess(PostQueryInfo postQueryInfo) {
                mView.hideProgressDialog();
                if(postQueryInfo!=null&&"ok".equals(postQueryInfo.getMessage())) {
                    mView.updateListUI(postQueryInfo);
                }
            }
            @Override
            public void requestPostSearchFail(String failStr) {
                mView.showProgressDialog();
                mView.errorToast(failStr);
            }
        });
    }
}
