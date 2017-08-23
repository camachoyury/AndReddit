package com.camachoyury.andreddit.interactor;

import io.reactivex.Scheduler;

/**
 * Created by yury on 8/22/17.
 */

public interface PostExecutionThread {

    Scheduler getScheduler();
}
