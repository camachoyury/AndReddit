package com.camachoyury.andreddit.presentation.presenter;


import com.camachoyury.andreddit.presentation.activity.MainActivity;
import com.camachoyury.andreddit.interactor.FeedInteractor;
import com.camachoyury.andreddit.model.Child;
import com.camachoyury.andreddit.network.FeedResponse;
import com.camachoyury.andreddit.interactor.JobExecutor;
import com.camachoyury.andreddit.presentation.UIThread;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by yury on 8/23/17.
 */

public class FeedPresenter {

    protected static final int MAX_ITEMS_PER_REQUEST = 10;
    private String after = "";

    private MainActivity mainActivity;
    FeedInteractor interactor;
    public FeedPresenter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        interactor = new FeedInteractor(new JobExecutor(),new UIThread());
    }

    public void loadNewItems(){
        interactor.execute(new ProceduresObserver(), new String[]{MAX_ITEMS_PER_REQUEST+"", ""});
    }


    public void loadMoreItems(){
       interactor.execute(new ProceduresObserver(), new String[]{MAX_ITEMS_PER_REQUEST+"", after});
    }

    public class ProceduresObserver extends DisposableObserver<FeedResponse> {

        @Override
        public void onNext(FeedResponse feedResponse) {

            after = feedResponse.data.after;
            List<Child> feeds =  feedResponse.data.getChildren();
            mainActivity.addData(feeds);
        }

        @Override
        public void onError(Throwable e) {
            mainActivity.showMessage("An Error Occurred");
        }

        @Override
        public void onComplete() {

        }
    }

    public  void onDestroy(){
        interactor.dispose();

    }




}
