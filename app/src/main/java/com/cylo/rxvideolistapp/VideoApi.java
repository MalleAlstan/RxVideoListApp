package com.cylo.rxvideolistapp;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface VideoApi {

    @POST("AppFiftyToneGraph/videoLink")
    Observable<VideoResponse> getAllVideoBy(@Body boolean once_no);
}
