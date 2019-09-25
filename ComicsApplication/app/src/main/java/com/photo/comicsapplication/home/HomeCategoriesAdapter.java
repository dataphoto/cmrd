package com.photo.comicsapplication.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.photo.comicsapplication.R;
import com.photo.comicsapplication.model.CategoriesModel;

import org.w3c.dom.Text;

import java.util.List;

public class HomeCategoriesAdapter extends RecyclerView.Adapter<HomeCategoriesAdapter.ViewHolder>{

    private List<CategoriesModel> list;
    private Context mContext;
    private ICategoriesItemClicked listener;

    public HomeCategoriesAdapter(List<CategoriesModel> list, Context mContext,ICategoriesItemClicked listener) {
        this.list = list;
        this.mContext = mContext;
        this.listener = listener;
    }

    public void setData(List<CategoriesModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeCategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_categories, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCategoriesAdapter.ViewHolder holder, final int position) {
        final CategoriesModel model = list.get(position);

        holder.tvIndex.setText(String.valueOf(position));
        holder.tvCategories.setText(model.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onCategoriesItemClicked(model.getName());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIndex, tvCategories;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIndex = itemView.findViewById(R.id.tvIndex);
            tvCategories = itemView.findViewById(R.id.tvCategories);


        }
    }

    public interface ICategoriesItemClicked {
        void onCategoriesItemClicked(String name);
    }
}
