package com.cylo.rxvideolistapp.Activities;

import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.VideoView;

import com.cylo.rxvideolistapp.Objects.RxBus;
import com.cylo.rxvideolistapp.Objects.Video;
import com.cylo.rxvideolistapp.R;

import io.reactivex.functions.Consumer;

public class VideoActivity extends AppCompatActivity {

    private VideoView mVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        mVideo = findViewById(R.id.videoView);

        showVideo();
    }

    private void showVideo(){

        RxBus.subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                getSupportActionBar().setTitle("Lesson " + ((Video)o).getId() + " - " + ((Video)o).getTitle());
                mVideo.setVideoURI(Uri.parse(((Video)o).getUrl()));
                mVideo.start();
            }
        });
    }
}
