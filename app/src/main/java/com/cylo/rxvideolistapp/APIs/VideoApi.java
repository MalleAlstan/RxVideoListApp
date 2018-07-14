package com.cylo.rxvideolistapp.APIs;

import com.cylo.rxvideolistapp.Objects.VideoResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface VideoApi {

    // My API request for getting the video list from server, returning an Observable object.
    // The String added here will be combined with the "url" String defined in MainActivity
    // after building up API via "retrofit.create" and once method "getAllVideoBy" is called.
    @POST("Api/AppFiftyToneGraph/videoLink")
    Observable<VideoResponse> getAllVideoBy(@Body boolean once_no);
}
