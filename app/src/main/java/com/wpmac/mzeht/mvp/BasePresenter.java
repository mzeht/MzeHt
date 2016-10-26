package com.wpmac.mzeht.mvp;

/**
 * Created by wpmac on 16/9/10.
 */
public abstract class BasePresenter<T> {
    public T mView;
    public void attach(T view){
        this.mView = view;
    }
    public void detach(){
        mView = null;
    }
}
