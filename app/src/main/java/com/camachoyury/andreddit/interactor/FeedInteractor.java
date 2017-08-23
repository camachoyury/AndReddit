package com.camachoyury.andreddit.interactor;

import com.camachoyury.andreddit.network.FeedResponse;
import com.camachoyury.andreddit.network.FeedsRepository;

import io.reactivex.Observable;

/**
 * Created by yury on 8/22/17.
 */

public class FeedInteractor extends Interactor<FeedResponse,String[]> {

    FeedsRepository repository;
    public FeedInteractor(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    Observable<FeedResponse> buildUseCaseObservable(String... s) {
        repository = new FeedsRepository();
        return repository.getFeeds(s[0],s[1]);
    }
}
