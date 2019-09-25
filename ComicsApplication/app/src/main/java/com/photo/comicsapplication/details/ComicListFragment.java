package com.photo.comicsapplication.details;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.FirebaseAppLifecycleListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.photo.comicsapplication.R;
import com.photo.comicsapplication.home.HomeActivity;
import com.photo.comicsapplication.home.HomeCategoriesAdapter;
import com.photo.comicsapplication.model.CategoriesModel;
import com.photo.comicsapplication.model.ComicModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.photo.comicsapplication.Constants.CATEGORIES;
import static com.photo.comicsapplication.Constants.COMIC;
import static com.photo.comicsapplication.Constants.DATABASE_NAME;


public class ComicListFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference myRef;

    RecyclerView rcvComic;
    List<ComicModel> comicList;
    HomeComicAdapter comicAdapter;

    String categoriesComic;

    ImageButton buttonBack;

    public ComicListFragment() {
        // Required empty public constructor
    }

    public void setCategoriesComic(String categoriesComic) {
        this.categoriesComic = categoriesComic;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comic_list, container, false);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(DATABASE_NAME);

        comicList = new ArrayList<>();

        TextView tvTitle = view.findViewById(R.id.tvTitle);
        tvTitle.setText(categoriesComic);
        buttonBack = view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("comicListFragment");
                if (fragment instanceof ComicListFragment) {
                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }
        });
        rcvComic = view.findViewById(R.id.rcvComic);

//        rcvComic.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rcvComic.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        comicAdapter = new HomeComicAdapter(comicList, getActivity(), new HomeComicAdapter.IComicItemClicked() {
            @Override
            public void onComicItemClicked(ComicModel model) {
                if (getActivity() instanceof HomeActivity) {

                    ComicDetailsFragment comicDetailsFragment = new ComicDetailsFragment();
                    comicDetailsFragment.setNameComicSelected(model.getComicName());
                    comicDetailsFragment.setIdComicSelected(model.getComicId());
                    comicDetailsFragment.setObjectComicSelected(model);
                    ((HomeActivity) getActivity()).openFragment(comicDetailsFragment, "comicDetailsFragment");
                }
            }
        });

        rcvComic.setAdapter(comicAdapter);

        loadComicFromFirebase();

        return view;
    }



    private void loadComicFromFirebase() {
        myRef.child(COMIC).orderByChild(CATEGORIES).equalTo(categoriesComic).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    //nó trả về 1 DataSnapShot, mà giá trị đơn nên gọi getValue trả về 1 HashMap
                    HashMap<String, Object> hashMap = (HashMap<String, Object>) dataSnapshot.getValue();

                    comicList.clear();

                    if (hashMap != null) {

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            ComicModel comicModel = postSnapshot.getValue(ComicModel.class);
                            comicList.add(comicModel);
                        }

                        comicAdapter.setData(comicList);

                        Log.e("SIZE", (String.valueOf(comicList.size())));

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

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
