package in.silive.androidslidingup.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.silive.androidslidingup.Adapter.NewsFeedsAdapter;
import in.silive.androidslidingup.R;
import in.silive.androidslidingup.models.Feeds;

/**
 * Created by kone on 13/9/15.
 */
public class NewsFeedsFragment extends Fragment {
    private View rootView;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.news_feed, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
      //  NewsFeedsAdapter newsFeedsAdapter = new NewsFeedsAdapter(Feeds.createFeedList(20));
       // recyclerView.setAdapter(newsFeedsAdapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }
}
