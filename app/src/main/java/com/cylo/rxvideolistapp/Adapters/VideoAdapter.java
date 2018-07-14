package com.cylo.rxvideolistapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cylo.rxvideolistapp.Activities.MainActivity;
import com.cylo.rxvideolistapp.Objects.Video;
import com.cylo.rxvideolistapp.R;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter{

    private ArrayList<Video> mVideos;
    private Context mContext;

    public VideoAdapter(Context context, ArrayList<Video> videos){
        mContext = context;
        mVideos = videos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_list_video, parent, false);

        return new VideoHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((VideoHolder) holder).mId.setText(String.valueOf("Lesson " + mVideos.get(position).getId()));
        ((VideoHolder) holder).mTitle.setText(mVideos.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mVideos.size() != 0 ? mVideos.size() : 0;
    }

    public class VideoHolder extends RecyclerView.ViewHolder {

        private TextView mId;
        private TextView mTitle;
        private ImageView mPlayVideo;

        public VideoHolder(View itemView) {
            super(itemView);

            mId = itemView.findViewById(R.id.textView_id);
            mTitle = itemView.findViewById(R.id.textView_title);
            mPlayVideo = itemView.findViewById(R.id.imageView);

            RxView.clicks(mPlayVideo)
                    .subscribe(aVoid -> {
                        Toast.makeText(mContext,
                                mVideos.get(getAdapterPosition()).getTitle() + " is chosen.",
                                Toast.LENGTH_SHORT).show();
                    });
        }
    }
}
