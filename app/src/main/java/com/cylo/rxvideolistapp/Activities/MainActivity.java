package com.cylo.rxvideolistapp.Activities;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cylo.rxvideolistapp.APIs.VideoApi;
import com.cylo.rxvideolistapp.Adapters.VideoAdapter;
import com.cylo.rxvideolistapp.Objects.Video;
import com.cylo.rxvideolistapp.R;
import com.cylo.rxvideolistapp.Responses.VideoResponse;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // url (scheme + host), must end with a "/" which can only be scheme + host
    // you may put other params with annotations in the API Interface to easily create different api calls
    private static String BASE_URL = "http://www.izaodao.com/";
    private String TAG = "john-rx";
    private ArrayList<Video> mVideosList;
    private RecyclerView mRecyclerView;
    private VideoAdapter mVideoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        mVideosList = new ArrayList<Video>();

        showVideoList();
    }

    private void showVideoList(){

        // build up retrofit (add Url, Gson Factory and RxJava Adapter)
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        VideoApi videoApi = retrofit.create(VideoApi.class);

        // Observable getting from api
        Observable<VideoResponse> observable = videoApi.getAllVideoBy(true);

        // giving the working thread for api calling via scheduler
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                // subscription on the returning data of api (printing result in the log)
                .subscribe(bean -> {

                    // print the response results and video detail in log
                    if(bean.getRet() == 1){
                        List<Video> videos = bean.getData();
                        Log.d("tag", "onResponse result " + bean.getMsg());
                        for(Video video : videos){
                            mVideosList.add(video);
                            Log.d(TAG, "id: " + video.getId() +
                                    "  name: " + video.getName() +
                                    "  title: " + video.getTitle());
                            Log.d(TAG, "url: " + video.getUrl());
                            Log.d(TAG, "------------------------------------------------------------");
                        }
                    }

                    mVideoAdapter = new VideoAdapter(mVideosList);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager
                            (this, LinearLayoutManager.VERTICAL, false));
                    mRecyclerView.addItemDecoration(new DividerItemDecoration
                            (this, DividerItemDecoration.VERTICAL));
                    mRecyclerView.setItemAnimator( new DefaultItemAnimator());
                    mRecyclerView.setAdapter(mVideoAdapter);

                },onError->{
                    Log.i(TAG, "onError:" + onError.getMessage());
                });
    }
}
