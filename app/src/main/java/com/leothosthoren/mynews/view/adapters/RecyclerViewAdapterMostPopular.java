package com.leothosthoren.mynews.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.leothosthoren.mynews.R;
import com.leothosthoren.mynews.model.ModelTools;
import com.leothosthoren.mynews.model.apis.articles.MostPopular;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sofiane M. alias Leothos Thoren on 01/02/2018
 */
public class RecyclerViewAdapterMostPopular extends RecyclerView.Adapter<RecyclerViewAdapterMostPopular.ItemViewHolder> {

    private List<MostPopular.Result> mMostPopularResult = new ArrayList<>();
    private OnItemClickListener mListener;
    private RequestManager mGlide;


    public RecyclerViewAdapterMostPopular(List<MostPopular.Result> mostPopularResult, RequestManager glide) {
        mMostPopularResult = mostPopularResult;
        mGlide = glide;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item_, parent, false);
        return new ItemViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        holder.updateWithMostPopular(this.mMostPopularResult.get(position), mGlide);

    }

    @Override
    public int getItemCount() {
        return mMostPopularResult.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    //This interface provide onItemClick method which allow to click on something in the activities class
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.item_layout)
        RelativeLayout mLayout;
        @BindView(R.id.item_title)
        TextView mTextView;
        @BindView(R.id.item_image)
        ImageView mImageView;
        @BindView(R.id.item_date)
        TextView mDateView;
        @BindView(R.id.item_summary)
        TextView mSummaryView;
        ModelTools mFormater = new ModelTools();


        public ItemViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            //Here we give the possibility to click on item (itemView)
            //We can choose to click on a specific element like an image view instead
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

        public void updateWithMostPopular(MostPopular.Result result, RequestManager glide) {

            this.mTextView.setText(result.getSection());
            this.mDateView.setText(mFormater.getItemArticleFormatedDate((result.getPublishedDate())));
            this.mSummaryView.setText(result.getTitle());
            if((result.getMedia() != null) && (!result.getMedia().isEmpty()) && (!result.getMedia().get(0).getMediaMetadata().isEmpty()))
                glide.load(result.getMedia().get(0).getMediaMetadata().get(0).getUrl()).into(this.mImageView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}


