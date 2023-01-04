package data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class Movie {
    private String name;
    private int year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;

    private int numLikes;
    private int numRatings;
    private double rating;

    @JsonIgnore
    private double ratingSum;

    public Movie() {
    }

    /**
     *  used for creating the output
     *  makes a deep copy of an arraylist of movies
     * @param list
     * @return
     */
    ArrayList<String> deepCopyString(final ArrayList<String> list) {
        ArrayList<String> copy = new ArrayList<>();
        copy.addAll(list);
        return copy;
    }

    public Movie(final Movie movie) {
        this.name = movie.name;
        this.year = movie.year;
        this.duration = movie.duration;
        this.genres = movie.genres;
        this.actors = deepCopyString(movie.actors);
        this.countriesBanned = deepCopyString(movie.countriesBanned);
        this.numLikes = movie.numLikes;
        this.numRatings = movie.numRatings;
        this.rating = movie.rating;
        this.ratingSum = movie.ratingSum;
    }

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final int getYear() {
        return year;
    }

    public final void setYear(final int year) {
        this.year = year;
    }

    public final int getDuration() {
        return duration;
    }

    public final void setDuration(final int duration) {
        this.duration = duration;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }

    public final void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    public final ArrayList<String> getActors() {
        return actors;
    }

    public final void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    public final ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    public final void setCountriesBanned(final ArrayList<String> countriesBan) {
        this.countriesBanned = countriesBan;
    }

    public final int getNumLikes() {
        return numLikes;
    }

    public final void setNumLikes(final int numLikes) {
        this.numLikes = numLikes;
    }

    public final int getNumRatings() {
        return numRatings;
    }

    public final void setNumRatings(final int numRatings) {
        this.numRatings = numRatings;
    }

    public final double getRating() {
        return rating;
    }

    public final void setRating(final double rating) {
        this.rating = rating;
    }

    public final double getRatingSum() {
        return ratingSum;
    }

    public final void setRatingSum(final double ratingSum) {
        this.ratingSum = ratingSum;
    }
}
