package ru.kuchanov.material.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.kuchanov.material.R;
import ru.kuchanov.material.RecyclerAdapter;

/**
 * Created by Юрий on 17.09.2015.
 */
public class FragmentTab1 extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_tab_1, container, false);

        RecyclerView recycler = (RecyclerView) v.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        String[] mDataSet = new String[100];
        for (int i = 0; i < 100; i++)
        {
            mDataSet[i] = "Tab1, item" + i;
        }

        recycler.setAdapter(new RecyclerAdapter(mDataSet));

        return v;
    }
}