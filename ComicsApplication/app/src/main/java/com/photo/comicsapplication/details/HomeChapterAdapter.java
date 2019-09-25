package com.photo.comicsapplication.details;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.photo.comicsapplication.R;
import com.photo.comicsapplication.model.ChapterModel;


import java.util.List;

public class HomeChapterAdapter extends RecyclerView.Adapter<HomeChapterAdapter.ViewHolder>{

    private List<ChapterModel> list;
    private Context mContext;
    private HomeChapterAdapter.IChapterItemClicked listener;

    public HomeChapterAdapter(List<ChapterModel> list, Context mContext, HomeChapterAdapter.IChapterItemClicked listener) {
        this.list = list;
        this.mContext = mContext;
        this.listener = listener;
    }

    public void setData(List<ChapterModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeChapterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_chapter, parent, false);
        return new HomeChapterAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeChapterAdapter.ViewHolder holder, int position) {
        ChapterModel model = list.get(position);

        if (!TextUtils.isEmpty(model.getChapterName())) {
            holder.tvChapter.setText(model.getChapterName());
            holder.tvIndex.setText(String.valueOf(position));
        }

    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvChapter,tvIndex;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIndex = itemView.findViewById(R.id.tvIndex);
            tvChapter = itemView.findViewById(R.id.tvChapter);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onChapterItemClicked();
                    }
                }
            });
        }
    }

    public interface IChapterItemClicked {
        void onChapterItemClicked();
    }
}