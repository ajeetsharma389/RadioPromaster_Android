package com.example.swiftradiopromaster_android;

public class getJsonData {

    private String trackName;
    private String Genre;
    private String streamURL;
    public  getJsonData(String trackName, String Genre, String streamURL){
        this.trackName = trackName;
        this.Genre = Genre;
        this.streamURL = streamURL;

    }

    public String getGenre(){
        return Genre;
    }
    public void setGenre(String genre){
        this.Genre = genre ;

    }

    public String getTrackName(){
        return trackName;
    }
    public void setTrackName(String trackName){
        this.trackName = trackName ;

    }

    public String getStreamURL(){
        return streamURL;
    }
    public void setStreamURL(String streamURL){
        this.streamURL = streamURL ;

    }
}
