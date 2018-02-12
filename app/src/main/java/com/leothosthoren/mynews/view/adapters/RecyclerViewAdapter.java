package com.leothosthoren.mynews.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leothosthoren.mynews.R;
import com.leothosthoren.mynews.model.ItemNews;

import java.util.ArrayList;

/**
 * Created by Sofiane M. alias Leothos Thoren on 01/02/2018
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> {

    private ArrayList<ItemNews> mItemNews;
    private OnItemClickListener mListener;

    public RecyclerViewAdapter(ArrayList<ItemNews> itemNews) {
        mItemNews = itemNews;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recyclerview, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view, mListener);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        final ItemNews currentItem = mItemNews.get(position);

        holder.mImageView.setImageResource(currentItem.getImage());
        holder.mTextView.setText(currentItem.getTitle());
        holder.mDateView.setText(currentItem.getDate());


    }

    @Override
    public int getItemCount() {
        return mItemNews.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    //This interface provide onItemClick method which allow to click on something in the activities class
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout mLinearLayout;
        public TextView mTextView;
        public ImageView mImageView;
        public TextView mDateView;

        public ItemViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mLinearLayout = itemView.findViewById(R.id.item_layout);
            mTextView = itemView.findViewById(R.id.item_title);
            mImageView = itemView.findViewById(R.id.item_image);
            mDateView = itemView.findViewById(R.id.item_date);

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

        @Override
        public void onClick(View v) {

        }
    }
}


