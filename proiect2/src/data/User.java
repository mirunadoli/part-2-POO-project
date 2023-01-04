package data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import util.Constants;

import java.util.ArrayList;

public class User {
    private Credentials credentials;
    private int tokensCount;
    private int numFreePremiumMovies;
    private ArrayList<Movie> purchasedMovies;
    private ArrayList<Movie> watchedMovies;
    private ArrayList<Movie> likedMovies;
    private ArrayList<Movie> ratedMovies;

    // the movies that are not banned for the user
    @JsonIgnore
    private ArrayList<Movie> availableMovies;

    // the movies shown on screen at any moment
    @JsonIgnore
    private ArrayList<Movie> moviesOnScreen;

    /**
     * used for creating the output
     * makes a deep copy of an arraylist of movies
     * @param list
     * @return
     */
    ArrayList<Movie> deepCopy(final ArrayList<Movie> list) {
        ArrayList<Movie> copy = new ArrayList<>();
        for (Movie movie : list) {
            copy.add(new Movie(movie));
        }
        return copy;
    }

    public User() {
        this.tokensCount = 0;
        this.numFreePremiumMovies = Constants.FREE_MOVIES;
        this.purchasedMovies = new ArrayList<>();
        this.watchedMovies = new ArrayList<>();
        this.likedMovies = new ArrayList<>();
        this.ratedMovies = new ArrayList<>();
        this.availableMovies = new ArrayList<>();
        this.moviesOnScreen = new ArrayList<>();
    }

    public User(final Credentials credentials) {
        this.credentials = credentials;
        this.tokensCount = 0;
        this.numFreePremiumMovies = Constants.FREE_MOVIES;
        this.purchasedMovies = new ArrayList<>();
        this.watchedMovies = new ArrayList<>();
        this.likedMovies = new ArrayList<>();
        this.ratedMovies = new ArrayList<>();
        this.availableMovies = new ArrayList<>();
        this.moviesOnScreen = new ArrayList<>();
    }

    public User(final User user) {
        this.credentials = new Credentials(user.credentials);
        this.tokensCount = user.tokensCount;
        this.numFreePremiumMovies = user.numFreePremiumMovies;
        this.purchasedMovies = deepCopy(user.purchasedMovies);
        this.watchedMovies = deepCopy(user.watchedMovies);
        this.likedMovies = deepCopy(user.likedMovies);
        this.ratedMovies = deepCopy(user.ratedMovies);
        this.availableMovies = deepCopy(user.availableMovies);
        this.moviesOnScreen = deepCopy(user.moviesOnScreen);
    }


    public final Credentials getCredentials() {
        return credentials;
    }

    public final void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    public final int getTokensCount() {
        return tokensCount;
    }

    public final void setTokensCount(final int tokensCount) {
        this.tokensCount = tokensCount;
    }

    public final int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    public final void setNumFreePremiumMovies(final int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    public final ArrayList<Movie> getPurchasedMovies() {
        return purchasedMovies;
    }

    public final void setPurchasedMovies(final ArrayList<Movie> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public final ArrayList<Movie> getWatchedMovies() {
        return watchedMovies;
    }

    public final void setWatchedMovies(final ArrayList<Movie> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public final ArrayList<Movie> getLikedMovies() {
        return likedMovies;
    }

    public final void setLikedMovies(final ArrayList<Movie> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public final ArrayList<Movie> getRatedMovies() {
        return ratedMovies;
    }

    public final void setRatedMovies(final ArrayList<Movie> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    public final ArrayList<Movie> getAvailableMovies() {
        return availableMovies;
    }

    public final void setAvailableMovies(final ArrayList<Movie> availableMovies) {
        this.availableMovies = availableMovies;
    }

    public final ArrayList<Movie> getMoviesOnScreen() {
        return moviesOnScreen;
    }

    public final void setMoviesOnScreen(final ArrayList<Movie> moviesOnScreen) {
        this.moviesOnScreen = moviesOnScreen;
    }
}
