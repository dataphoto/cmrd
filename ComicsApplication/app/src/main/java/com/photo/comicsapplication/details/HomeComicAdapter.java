package com.photo.comicsapplication.details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.photo.comicsapplication.R;
import com.photo.comicsapplication.model.ComicModel;

import java.util.List;

public class HomeComicAdapter extends RecyclerView.Adapter<HomeComicAdapter.ViewHolder>{

    private List<ComicModel> list;
    private Context mContext;
    private HomeComicAdapter.IComicItemClicked listener;

    public HomeComicAdapter(List<ComicModel> list, Context mContext, HomeComicAdapter.IComicItemClicked listener) {
        this.list = list;
        this.mContext = mContext;
        this.listener = listener;
    }

    public void setData(List<ComicModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeComicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_comic, parent, false);
        return new HomeComicAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeComicAdapter.ViewHolder holder, int position) {
        final ComicModel model = list.get(position);
        if (model.getLinkImage() != null) {
            Glide.with(mContext).load(model.getLinkImage()).error(R.drawable.ic_launcher_background).into(holder.imageComic);
        }

        if (model.getComicName() != null) {
            holder.tvComic.setText(model.getComicName());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onComicItemClicked(model);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvComic;
        RoundedImageView imageComic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageComic = itemView.findViewById(R.id.imageComic);
            tvComic = itemView.findViewById(R.id.textComic);

        }
    }

    public interface IComicItemClicked {
        void onComicItemClicked(ComicModel model);
    }
}