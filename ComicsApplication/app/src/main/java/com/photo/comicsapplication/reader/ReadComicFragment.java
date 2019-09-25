package com.photo.comicsapplication.reader;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.photo.comicsapplication.R;

public class ReadComicFragment extends Fragment {

    ViewPager vpComic;


    public ReadComicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read_comic, container, false);


        vpComic = view.findViewById(R.id.vpComic);

//        pagerAdapter = new MyPagerAdapter(this);
//        vpComic.setAdapter(pagerAdapter);

// Assign the page transformer to the ViewPager.



        return view;
    }

}
