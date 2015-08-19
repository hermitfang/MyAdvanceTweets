package com.codepath.apps.mysimpletweets.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.activities.ProfileActivity;
import com.codepath.apps.mysimpletweets.listeners.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class HomeTimelineFragment extends TweetsListFragment {
    private TwitterClient client;
    private long lastId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);
        if (lvTweets == null) {
            lvTweets = (ListView)v.findViewById(R.id.lvTweets);
        }

        // set scroll listener
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (lvTweets.getCount() > 5) {
                    populateTimeline(lastId);
                }
            }
        });

        // set item click listener
        lvTweets.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                        Tweet t = (Tweet) adapter.getItemAtPosition(pos);
                        String screenName = t.getUser().getScreenName();
                        long uid = t.getUser().getUid();

                        // Toast.makeText(getActivity(), "Get all from " + t.getUser().getScreenName(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getActivity(), ProfileActivity.class);
                        i.putExtra("screenName", screenName);
                        i.putExtra("uid", uid);
                        startActivity(i);
                    }
                }
        );

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get twtter rest client
        client = TwitterApplication.getRestClient(); //singleton...
        lastId = 0;
        populateTimeline(0);
    }

    // send api request to get timeline data
    // fill listview
    public void populateTimeline(long maxId) {
        final long mx = maxId;
        client.getHomeTimeline(maxId, new JsonHttpResponseHandler() {
            // success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("DEBUG Success JSON", json.toString());
                // swipeContainer.setRefreshing(false);
                addAll(Tweet.fromJSONArray(json));
                lastId = getUid();
            }


            // failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }

    /*
    public static HomeTimelineFragment newInstance() {
        HomeTimelineFragment homeTimelineFragment = new HomeTimelineFragment();
        // Bundle args = new Bundle();
        // args.putString("screenName", screenName);
        // userFragment.setArguments(args);
        return homeTimelineFragment;
    }
*/

}
