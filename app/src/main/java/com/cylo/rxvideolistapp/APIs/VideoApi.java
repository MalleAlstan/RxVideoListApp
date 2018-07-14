package com.cylo.rxvideolistapp.APIs;

import com.cylo.rxvideolistapp.Responses.VideoResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface VideoApi {

    // API Service for getting the video list from server, returning an Observable.
    @POST("Api/AppFiftyToneGraph/videoLink")
    Observable<VideoResponse> getAllVideoBy(@Body boolean once_no);
}
