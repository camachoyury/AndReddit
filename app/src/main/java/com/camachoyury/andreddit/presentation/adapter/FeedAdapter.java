package com.camachoyury.andreddit.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.camachoyury.andreddit.R;
import com.camachoyury.andreddit.model.Child;
import com.camachoyury.andreddit.model.Feed;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Hours;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yury on 8/23/17.
 */

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private boolean loading = true;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    public final static int SOURCE_IMAGE = 0;
    public final static int SOURCE_LINK = 1;
    private Context context;
    public List<Child> feedList = new ArrayList<Child>();
    private OnItemClickListener listener;

    public FeedAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false);
            return new FeedViewHolder(view);

        } else if (viewType == VIEW_TYPE_LOADING) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    public void setLoading(boolean loading){
        this.loading = loading;
    }


    @Override
    public int getItemViewType (int position) {

        if (feedList.size() < getMaxItemsOnList() ) {

            if (isPositionFooter(position)) {
                return VIEW_TYPE_LOADING;
            }
            return VIEW_TYPE_ITEM;
        }
        else return  VIEW_TYPE_ITEM ;
    }


    // validate if the position item is the last
    private boolean isPositionFooter (int position) {
        return position == feedList.size()-1;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof FeedViewHolder){

            bindFeedViewHolder(feedList.get(position),(FeedViewHolder) holder,listener);

        }else if(holder instanceof LoadingViewHolder){

            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;

            bindFooterViewHolder( loadingViewHolder);
        }
    }

    public  void addAll(List<Child> feeds){

        feedList.addAll(feeds);
        this.notifyDataSetChanged();
    }

    public void bindFeedViewHolder(final Child feed, FeedViewHolder holder, final OnItemClickListener listener) {

        String urlThumbnail = feed.getData().getThumbnail();
        if (urlThumbnail.contains(".jpg")) {
            Picasso.with(context)
                    .load(urlThumbnail).
                    placeholder(R.mipmap.ic_launcher).resize(300, 250).centerInside()
                    .error(R.mipmap.ic_launcher)
                    .into(holder.ivFeed);
        }else {
            holder.ivFeed.setVisibility(View.GONE);
        }

        holder.tvHoursAgo.setText(getDiffHours(feed.getData().createdUtc));
        holder.tvAuthor.setText(feed.getData().getAuthor());
        holder.tvNumComments.setText(feed.getData().getNumComments() + "");
        holder.tvTitle.setText(feed.getData().getTitle());
        holder.ivFeed.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(feed.getData() ,SOURCE_IMAGE);
            }
        });
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(feed.getData() ,SOURCE_LINK);
            }
        });
    }


    public void bindFooterViewHolder(LoadingViewHolder holder){

        LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
        loadingViewHolder.progressBar.setIndeterminate(true);
        loadingViewHolder.progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }


    /**
     * Feed viewholder
     */
    public static class FeedViewHolder extends RecyclerView.ViewHolder  {
        @BindView(R.id.ivFeed)
        ImageView ivFeed;
        @BindView(R.id.author)
        TextView tvAuthor;
        @BindView(R.id.ivFeedBottom)
        ImageView ivFeedBottom;
        @BindView(R.id.hours_ago)
        TextView tvHoursAgo;
        @BindView(R.id.tvNumComments)
        TextView tvNumComments;
        @BindView(R.id.tvTitle)
        TextView tvTitle;

        public FeedViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * Footer view holder used for show progressbar
     */
    public  static class LoadingViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.footer)
        ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Feed feed , int source);
    }

    public int getMaxItemsOnList(){
        return 50;
    }


    private String getDiffHours(long unixSeconds){

        Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
        DateTimeZone dtz = DateTimeZone.getDefault();
        DateTime dateTime = new DateTime(date,dtz);
        DateTime now = new DateTime();

        return Hours.hoursBetween(dateTime, now.toInstant()).getHours() + context.getString(R.string.hours);

    }
}
