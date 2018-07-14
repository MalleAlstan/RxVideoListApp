package com.cylo.rxvideolistapp;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static String BASE_URL = "http://www.izaodao.com/Api/";
    private String TAG = "john-rx";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getVideoList();
    }

    public void getVideoList(){

        // build up retrofit (add Url, Gson Converter and RxJava Adapter)
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

                    // if response OK, print the response results and video detail in log
                    if(bean.getRet() == 1){
                        Log.d("tag", "onResponse result " + bean.getMsg());
                        List<Video> data = bean.getData();
                        for(Video video : data){
                            Log.d(TAG, "id: " + video.getId() +
                                    "  name: " + video.getName() +
                                    "  title: " + video.getTitle());
                            Log.d(TAG, "url: " + video.getUrl());
                            Log.d(TAG, "------------------------------------------------------------");
                        }
                    }

                },onError->{
                    Log.i(TAG, "onError:" + onError.getMessage());
                });
    }

}