package com.photo.comicsapplication.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.photo.comicsapplication.R;
import com.photo.comicsapplication.details.ComicListFragment;
import com.photo.comicsapplication.model.CategoriesModel;
import com.photo.comicsapplication.response.CategoriesResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.photo.comicsapplication.Constants.CATEGORIES;
import static com.photo.comicsapplication.Constants.DATABASE_NAME;

public class HomeActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;

    RecyclerView rcvCategories;
    List<CategoriesModel> categoriesList;
    HomeCategoriesAdapter categoriesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(DATABASE_NAME);

        rcvCategories = findViewById(R.id.rcvCategories);
        rcvCategories.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        categoriesList = new ArrayList<>();
        categoriesAdapter = new HomeCategoriesAdapter(categoriesList, this, new HomeCategoriesAdapter.ICategoriesItemClicked() {
            @Override
            public void onCategoriesItemClicked(String name) {
                ComicListFragment comicListFragment = new ComicListFragment();
                comicListFragment.setCategoriesComic(name);
                openFragment(comicListFragment, "comicListFragment");
            }
        });

        rcvCategories.setAdapter(categoriesAdapter);

        loadCategoriesFromFirebase();
    }

    private void loadCategoriesFromFirebase() {
        myRef.child(CATEGORIES).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    HashMap<String, Object> hashMap = (HashMap<String, Object>) dataSnapshot.getValue();

                    categoriesList.clear();
                    if (hashMap != null) {

                        for (String categories: hashMap.keySet()) {
                            categoriesList.add(new CategoriesModel(categories, "", ""));
                        }

                        categoriesAdapter.setData(categoriesList);
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

    public void openFragment(Fragment fragment, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.rootView, fragment, tag);
        transaction.commit();
    }


    @Override
    public void onBackPressed() {

        Fragment fragment = getSupportFragmentManager().findFragmentByTag("comicListFragment");
        if (fragment instanceof ComicListFragment) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }
}
