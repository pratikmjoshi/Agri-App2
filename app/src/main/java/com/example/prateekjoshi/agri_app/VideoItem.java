package com.example.prateekjoshi.agri_app;

/**
 * Created by Prateek Joshi on 1/16/2017.
 */

public class VideoItem {
    private String videoName;
    private int videoPicture;

    public VideoItem(String videoName,int videoPicture) {
        this.videoName = videoName;
        this.videoPicture = videoPicture;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public int getVideoPicture(){
        return videoPicture;
    }

    public void setVideoPicture(int videoPicture) {
        this.videoPicture = videoPicture;
    }

}
