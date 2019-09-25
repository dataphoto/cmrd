package com.photo.comicsapplication.details;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.photo.comicsapplication.R;
import com.photo.comicsapplication.home.HomeActivity;
import com.photo.comicsapplication.model.ChapterModel;
import com.photo.comicsapplication.model.ComicModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static com.photo.comicsapplication.Constants.CATEGORIES;
import static com.photo.comicsapplication.Constants.CHAPTER;
import static com.photo.comicsapplication.Constants.COMIC;
import static com.photo.comicsapplication.Constants.DATABASE_NAME;

public class ComicDetailsFragment extends Fragment {

    private String comicId;
    private String comicName;
    private ComicModel comicModel;
    FirebaseDatabase database;
    DatabaseReference myRef;

    RecyclerView rcvChapter;
    List<ChapterModel> chapterList;
    HomeChapterAdapter chapterAdapter;

    public ComicDetailsFragment() {
        // Required empty public constructor
    }

    public void setIdComicSelected(String idComic) {
        this.comicId = idComic;
    }

    public void setNameComicSelected(String nameComic) {
        this.comicName = nameComic;
    }

    public void setObjectComicSelected(ComicModel comicModel) {
        this.comicModel = comicModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comic_details, container, false);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(DATABASE_NAME);
        chapterList = new ArrayList<>();

        initButtonBack(view);

        rcvChapter = view.findViewById(R.id.rcvChapter);

        rcvChapter.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
//        rcvChapter.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        chapterAdapter = new HomeChapterAdapter(chapterList, getActivity(), new HomeChapterAdapter.IChapterItemClicked() {
            @Override
            public void onChapterItemClicked() {

            }
        });

        rcvChapter.setAdapter(chapterAdapter);

        loadChapterFromFirebase();
        loadDetailsComic(view);
        return view;
    }

    private void initButtonBack(View view) {
        ImageButton buttonBack = view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFragmentCurrent();
            }
        });

        TextView tvComic = view.findViewById(R.id.tvComic);
        tvComic.setText(comicName);
    }
    
    private void removeFragmentCurrent() {
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("comicDetailsFragment");
        if (fragment instanceof ComicDetailsFragment) {
            getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }


    private void loadChapterFromFirebase() {
        if (TextUtils.isEmpty(comicId)) {
            removeFragmentCurrent();
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            return;
        }
        myRef.child(CHAPTER).child(comicId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    HashMap<String, Object> hashMap = (HashMap<String, Object>) dataSnapshot.getValue();

                    chapterList.clear();

                    if (hashMap != null) {

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            ChapterModel ChapterModel = postSnapshot.getValue(ChapterModel.class);
                            chapterList.add(ChapterModel);
                        }

                        Collections.sort(chapterList, new Comparator<ChapterModel>(){
                            public int compare(ChapterModel s1, ChapterModel s2) {

                                int model1 = Integer.valueOf(s1.getIndexChapter());
                                int model2 = Integer.valueOf(s2.getIndexChapter());

                                if (model1 < model2) {
                                    return -1;
                                } else if (model1 == model2) {
                                    return 0;
                                } else {
                                    return 1;
                                }
                            }
                        });


                        chapterAdapter.setData(chapterList);

                        Log.e("SIZE", (String.valueOf(chapterList.size())));

                    }
                } catch (Exception ex) {
                    Log.e("ERROR_JSON", ex.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("LOI_CHITIET", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    private int getIntChapter(String value) {
        String[] parts = value.split("-");
        String chapter = parts[1];
        return Integer.valueOf(chapter);
    }

    private String convertStringChapterToCompare(String value) {
        String[] parts = value.split("-");
        String chapter = parts[1];
        return chapter;
    }

    private void loadDetailsComic(View view) {
        if (comicModel == null) {
            return;
        }

        RoundedImageView imageComic = view.findViewById(R.id.imageDetailsComic);
        TextView tvCategories = view.findViewById(R.id.tvCategories);
        TextView tvAuthor = view.findViewById(R.id.tvAuthor);
        TextView tvStatus = view.findViewById(R.id.tvStatus);
        ReadMoreTextView tvDescription = view.findViewById(R.id.tvDescription);

        Glide.with(view.getContext()).load(comicModel.getLinkImage()).error(R.drawable.ic_launcher_background).into(imageComic);
        tvCategories.setText("Categories: " + comicModel.getCategories());
        tvAuthor.setText("Author: " + comicModel.getAuthor());
        tvStatus.setText("Status: " + comicModel.getStatus());
        tvDescription.setText(comicModel.getDescription());
//        tvCategories.setText("Categories: " + comicModel.getCategories());

        Button buttonRead = view.findViewById(R.id.buttonRead);
        buttonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }

}
