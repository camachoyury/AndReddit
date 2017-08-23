package com.camachoyury.andreddit.interactor;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yury on 8/22/17.
 **/

public abstract class Interactor<T, Params> {

    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private CompositeDisposable disposables;


    public Interactor(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
        this.disposables = new CompositeDisposable();
    }

    /**
     * Builds an [Observable] which will be used when executing the current [UseCase].
     */

    abstract Observable<T> buildUseCaseObservable(Params params);

    /**
     * Executes the current use case.
     *
     * @param observer [DisposableObserver] which will be listening to the observable build
     *                 * by [.buildUseCaseObservable] ()} method.
     *                 *
     * @param params   Parameters (Optional) used to build/execute this use case.
     */

    public void execute(DisposableObserver<T> observer, Params params) {


        final Observable<T> observable = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
        addDisposable(observable.subscribeWith(observer));


    }

    /**
     * Dispose from current [CompositeDisposable].
     */
    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    /**
     * Dispose from current [CompositeDisposable].
     */
    private void addDisposable(Disposable disposable) {

        disposables.add(disposable);
    }
}
