package com.example.siva.attendanceassist;

/**
 * Created by Siva on 09-Jan-16.
 */
public class DataProvider {

    private int poster;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }

    public DataProvider(int poster,String title)
    {
        this.setPoster(poster);
        this.setTitle(title);
    }
}
