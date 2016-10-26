package com.wpmac.mzeht.mvp;

import com.wpmac.mzeht.pojo.PostQueryInfo;

/**
 * Created by wpmac on 16/9/10.
 */
public interface MainView{
        void updateListUI(PostQueryInfo postQueryInfo);
        void errorToast(String message);
        void showProgressDialog();
        void hideProgressDialog();

}
