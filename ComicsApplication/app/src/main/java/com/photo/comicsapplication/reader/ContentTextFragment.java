package com.photo.comicsapplication.reader;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bluejamesbond.text.DocumentView;
import com.photo.comicsapplication.R;
import com.photo.comicsapplication.utils.SharedPrefUtils;

public class ContentTextFragment extends Fragment {

    DocumentView documentView;
    RelativeLayout rootContent;

    public ContentTextFragment() {
        // Required empty public constructor
    }

//    public ContentTextFragment newInstance(ChuongSach chuongSach){
//        ContentTextFragment contentFragment = new ContentTextFragment();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("chapter_page",chuongSach);
//
//        contentFragment.setArguments(bundle);
//        return contentFragment;
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_content_text, container, false);

        return view;
    }

}
