package com.leothosthoren.mynews.controler.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.leothosthoren.mynews.R;
import com.leothosthoren.mynews.model.ItemNews;
import com.leothosthoren.mynews.model.NewYorkTimeTopStories;
import com.leothosthoren.mynews.model.Utils.NetworkAsyncTask;
import com.leothosthoren.mynews.model.Utils.NewYorkTimesCalls;
import com.leothosthoren.mynews.view.adapters.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class PageFragment extends Fragment implements NetworkAsyncTask.Listeners, NewYorkTimesCalls.Callbacks {

    private static final String KEY_POSITION = "position";
    private static final String KEY_COLOR = "color";
    private ProgressBar progressBar;

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ItemNews> mItemNews = new ArrayList<>();

    private TextView mTextView;

    public PageFragment() {
        // Required empty public constructor
    }

    public static PageFragment newInstance(int position, int color) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        args.putInt(KEY_COLOR, color);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_layout, container, false);
        mRecyclerView = (RecyclerView) result.findViewById(R.id.frag_recycler_view);
        mTextView = (TextView) result.findViewById(R.id.frag_text);
        progressBar = (ProgressBar) result.findViewById(R.id.activity_main_progress_bar);
        int position = getArguments().getInt(KEY_POSITION, -1);
        int color = getArguments().getInt(KEY_COLOR, -1);


        //Call the recyclerView builder method
        this.buildRecyclerView();
//        executeHttpRequest(0);
        executeHttpRequestWithRetrofit();


        // 5 - Get data from Bundle (created in method newInstance)

        mItemNews.add(new ItemNews("mon titre", "02/02/2017", R.mipmap.ic_launcher, "Long résumé"));
        mItemNews.add(new ItemNews("mon titre", "02/12/2017", R.mipmap.ic_launcher_round, "Très Long résumé"));
        mItemNews.add(new ItemNews("mon titre", "18/10/2017", R.mipmap.ic_launcher, "Très très Long résumé"));

        // 6 - Update widgets with it

        mRecyclerView.setBackgroundColor(color);

        return result;
    }

    private void buildRecyclerView() {

        mAdapter = new RecyclerViewAdapter(mItemNews);
        //Set them with natives methods
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Here we allow the toast text to appear only if the comment is not empty at his own position
                Toast.makeText(getContext(), "Click on item number " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }


    //---------------
    // HTTP REQUEST
    //---------------

    private void executeHttpRequest() {
        new NetworkAsyncTask(this).execute
                ("http://api.nytimes.com/svc/topstories/v2/home.json?api-key=7aa6518af840427eb78832360465fa9e");
    }

    @Override
    public void onPreExecute() {
        this.updateUIWhenStartingHTTPRequest();
    }

    @Override
    public void doInBackground() {
    }

    @Override
    public void onPostExecute(String json) {
        this.updateUIWhenStopingHTTPRequest(json);
    }

    // ------------------------------
    //  HTTP REQUEST (Retrofit Way)
    // ------------------------------

    // 4 - Execute HTTP request and update UI
    private void executeHttpRequestWithRetrofit() {
        this.updateUIWhenStartingHTTPRequest();
        NewYorkTimesCalls.fetchFollowing(this, "home");
    }

    // 2 - Override callback methods

    @Override
    public void onResponse(@Nullable List<NewYorkTimeTopStories> section) {
        // 2.1 - When getting response, we update UI
        if (section != null) this.updateUIWithListOfSection(section);
    }

    @Override
    public void onFailure() {
        // 2.2 - When getting error, we update UI
        this.updateUIWhenStopingHTTPRequest("An error happened !");
    }


    // ------------------
    //  UPDATE UI
    // ------------------

    private void updateUIWhenStartingHTTPRequest() {
        progressBar.setVisibility(View.VISIBLE);
//        this.mTextView.setText("Downloading...");
    }

    private void updateUIWhenStopingHTTPRequest(String response) {
        progressBar.setVisibility(View.GONE);
        this.mTextView.setText(response);
    }

    // 3 - Update UI showing only name of users
    private void updateUIWithListOfSection(List<NewYorkTimeTopStories> section) {
        StringBuilder stringBuilder = new StringBuilder();
        for (NewYorkTimeTopStories sect : section) {
            stringBuilder.append("-" + sect.getSection() + "\n");
        }
        updateUIWhenStopingHTTPRequest(stringBuilder.toString());
    }
}




