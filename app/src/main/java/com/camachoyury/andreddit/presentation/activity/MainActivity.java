package com.camachoyury.andreddit.presentation.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.camachoyury.andreddit.R;
import com.camachoyury.andreddit.model.Child;
import com.camachoyury.andreddit.model.Feed;
import com.camachoyury.andreddit.presentation.adapter.FeedAdapter;
import com.camachoyury.andreddit.presentation.presenter.FeedPresenter;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yury on 8/22/17.
 */

public class MainActivity extends AppCompatActivity {

    FeedPresenter presenter;
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_main_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    LinearLayoutManager mLinearLayoutManager;
    protected static final int MAX_ITEMS_PER_REQUEST = 10;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private FeedAdapter adapter;

    public final static String LIST_STATE_KEY = "list_state";
    private Parcelable mListState;


    // Item listener
    private FeedAdapter.OnItemClickListener listener = new FeedAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(Feed feed, int source) {

            String url="";
            if (source == FeedAdapter.SOURCE_IMAGE){
                url = feed.getUrl();

            }if (source == FeedAdapter.SOURCE_LINK){
                url = "https://www.reddit.com" + feed.getPermalink();
            }
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
    };

    // swipoe leintener for update the last feeds
    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            adapter.feedList.clear();
            presenter.loadNewItems();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        adapter = new FeedAdapter(this, listener);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnScrollListener(recyclerViewOnScrollListener);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
        presenter = new FeedPresenter(this);
        JodaTimeAndroid.init(this);
        mSwipeRefreshLayout.setOnRefreshListener(refreshListener);
        presenter.loadMoreItems();

    }

    // scroll listener used for make the pagination
    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);


            int visibleItemCount = mLinearLayoutManager.getChildCount();
            int totalItemCount = mLinearLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLinearLayoutManager.findFirstVisibleItemPosition();

            if (totalItemCount < 50){
                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= MAX_ITEMS_PER_REQUEST) {
                        isLoading = true;
                        presenter.loadMoreItems();
                    }
                }
            }
        }
    };

    // method for load the new feeds on the list
    public void addData(List<Child> feeds){
        isLoading = false;
        adapter.addAll(feeds);
        adapter.notifyDataSetChanged();
        if (mSwipeRefreshLayout.isRefreshing()){
        mSwipeRefreshLayout.setRefreshing(false);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        // Save list state
        mListState = mLinearLayoutManager.onSaveInstanceState();
        state.putParcelable(LIST_STATE_KEY, mListState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);

        if(state != null) {
            mListState = state.getParcelable(LIST_STATE_KEY);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mListState != null) {
            mLinearLayoutManager.onRestoreInstanceState(mListState);
        }
    }

    // method for show messages emitted from the presenter
    public void showMessage(String message){

        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

}
