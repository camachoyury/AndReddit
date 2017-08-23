package com.camachoyury.andreddit.presentation;


import com.camachoyury.andreddit.interactor.PostExecutionThread;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by yury on 8/22/17.
 */


public class UIThread implements PostExecutionThread {


    public UIThread() {
    }

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}