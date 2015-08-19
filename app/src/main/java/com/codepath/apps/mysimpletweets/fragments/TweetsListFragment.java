package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.adapters.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TweetsListFragment extends Fragment {
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    protected ListView lvTweets;

    private SwipeRefreshLayout swipeContainer;

        // inflaction logic
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
        // get ListView object
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        // set adapter
        lvTweets.setAdapter(aTweets);
        return v;
    }

    // creation lifecycle

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create arraylist as data source
        tweets = new ArrayList<>();
        // construct adapter from data source
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);

        /*
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                populateTimeline(lastId);
            }
        });
        */

    }

    public void addAll(List<Tweet> tweets) {
        aTweets.addAll(tweets);
    }

    public long getUid() {
        return aTweets.getItem(aTweets.getCount() - 1).getUid();
    }

    public void clearTweets () {
        aTweets.clear();
    }
}
