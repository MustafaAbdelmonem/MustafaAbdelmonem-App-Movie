package com.example.enmustafa.mustafaabdelmonemmovie;

import io.realm.RealmObject;


public class MovieDB extends RealmObject {

    private long id;
    private String overview;
    private String poster_path;
    private String release_date;
    private String title;
    private String backdrop_path;
    private double vote_average;
    private String author;
    private String content;

    private String trailername;
    private String trailerkey;

    public void setId(long id) {
        this.id = id;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MovieDB()

    {

    }

    public MovieDB(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public MovieDB(long id, String poster_path, String title) {
        this.poster_path = poster_path;
        this.title = title;
        this.id = id;
    }

    public MovieDB(String poster_path, String overview, String release_date, String title, String backdrop_path, double vote_average) {
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.vote_average = vote_average;
    }


    public long getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getTrailername() {
        return trailername;
    }

    public String getTrailerkey() {
        return trailerkey;
    }

    public void setTrailername(String trailername) {
        this.trailername = trailername;
    }

    public void setTrailerkey(String trailerkey) {
        this.trailerkey = trailerkey;
    }
}
