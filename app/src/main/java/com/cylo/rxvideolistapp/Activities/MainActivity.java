package com.cylo.rxvideolistapp.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;

import com.cylo.rxvideolistapp.APIs.VideoApi;
import com.cylo.rxvideolistapp.Adapters.VideoAdapter;
import com.cylo.rxvideolistapp.Objects.Video;
import com.cylo.rxvideolistapp.R;
import com.cylo.rxvideolistapp.Responses.VideoResponse;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private String TAG = "john-rx";

    // url (scheme + host), must end with a "/" which only contains scheme + host
    private static String BASE_URL = "http://www.izaodao.com/";
    private RecyclerView mRecyclerView;
    private VideoAdapter mVideoAdapter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading... ");
        mProgressDialog.show();

        showVideoList();
    }

    private void showVideoList() {

        // build up retrofit (add Url, Gson Factory and RxJava Adapter)
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        VideoApi videoApi = retrofit.create(VideoApi.class);

        Observable<VideoResponse> observable = videoApi.getAllVideoBy(true);
        observable.subscribeOn(Schedulers.io())                      // Set threads for calling api and response via scheduler
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {// Subscribe the returning data from API (add to list)
                    if (bean.getRet() == 1) {
                        ArrayList<Video> videos = bean.getData();
                        setRecyclerView(videos);
                    }
                    mProgressDialog.dismiss();
                }, onError -> {
                    Log.i(TAG, "onError:" + onError.getMessage());
                    mProgressDialog.dismiss();
                });
    }

    private void setRecyclerView(ArrayList<Video> videos){
        // Handle the RecyclerView
        mVideoAdapter = new VideoAdapter(this, videos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager
                (this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration
                (this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mVideoAdapter);
    }

}
