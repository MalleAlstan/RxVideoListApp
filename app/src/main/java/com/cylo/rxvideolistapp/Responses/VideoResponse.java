package com.cylo.rxvideolistapp.Responses;

import com.cylo.rxvideolistapp.Objects.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoResponse {

    // Add needed variables from Json data
    private int ret = -1;
    private String msg = "";
    private ArrayList<Video> data;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<Video> getData() {
        return data;
    }

    public void setData(ArrayList<Video> data) {
        this.data = data;
    }

}
