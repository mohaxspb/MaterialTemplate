package ru.kuchanov.material;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Юрий on 17.09.2015.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
{

    private String[] mDataset;

    // Конструктор
    public RecyclerAdapter(String[] dataset)
    {
        mDataset = dataset;
    }

//    @Override
//    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
//    {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
//        ViewHolder vh = new ViewHolder(v);
//        return vh;
//    }
@Override
public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
{
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_card_image_right, parent, false);
    ViewHolder vh = new ViewHolder(v);
    return vh;
}

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.mTextView.setText(mDataset[position]);
    }

    @Override
    public int getItemCount()
    {
        return mDataset.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView;

        public ViewHolder(View v)
        {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.art_card_title_tv);
        }
    }
}
