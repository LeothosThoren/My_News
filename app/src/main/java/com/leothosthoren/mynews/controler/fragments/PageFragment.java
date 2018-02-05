package com.leothosthoren.mynews.controler.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leothosthoren.mynews.R;
import com.leothosthoren.mynews.model.ItemNews;
import com.leothosthoren.mynews.view.adapters.RecyclerViewAdapter;

import java.util.ArrayList;


public class PageFragment extends Fragment {

    private static final String KEY_POSITION = "position";
    private static final String KEY_COLOR = "color";

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ItemNews> mItemNews = new ArrayList<>();

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
//        buildRecyclerView();
//        mItemNews.add(new ItemNews("titre", "02/02/2017", R.id.item_image, "Long résumé"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_layout, container, false);
        RecyclerView rootView = (RecyclerView) result.findViewById(R.id.frag_recycler_view);

        // 5 - Get data from Bundle (created in method newInstance)
        int position = getArguments().getInt(KEY_POSITION, -1);
        int color = getArguments().getInt(KEY_COLOR, -1);

        // 6 - Update widgets with it
        rootView.setBackgroundColor(color);
        return result;
    }

    private void buildRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RecyclerViewAdapter(mItemNews);
        //Call new classes
        mLayoutManager = new LinearLayoutManager(getActivity());

        //Set them with natives methods
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }


}
