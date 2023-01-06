package data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import util.Constants;

import java.util.ArrayList;
import java.util.Collections;

import static util.DeepCopy.*;

public class User {
    private Credentials credentials;
    private int tokensCount;
    private int numFreePremiumMovies;
    private ArrayList<Movie> purchasedMovies;
    private ArrayList<Movie> watchedMovies;
    private ArrayList<Movie> likedMovies;
    private ArrayList<Movie> ratedMovies;

    private ArrayList<Notification> notifications;

    // the movies that are not banned for the user
    @JsonIgnore
    private ArrayList<Movie> availableMovies;

    @JsonIgnore
    private ArrayList<String> subscribedGenres;


    public User() {
        this.tokensCount = 0;
        this.numFreePremiumMovies = Constants.FREE_MOVIES;
        this.purchasedMovies = new ArrayList<>();
        this.watchedMovies = new ArrayList<>();
        this.likedMovies = new ArrayList<>();
        this.ratedMovies = new ArrayList<>();
        this.availableMovies = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.subscribedGenres = new ArrayList<>();
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
        this.notifications = new ArrayList<>();
        this.subscribedGenres = new ArrayList<>();
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
        this.notifications = deepCopyNotif(user.notifications);
        this.subscribedGenres = deepCopyString(user.subscribedGenres);
    }

    /**
     * if a movie is deleted, give back the tokens
     * or a free movie to the user
     */
    void refundUser() {
        if (this.credentials.getAccountType().equals("premium")) {
            this.numFreePremiumMovies++;
        } else {
            this.tokensCount = this.tokensCount + 2;
        }
    }

    /**
     * deletes a movie from the user's lists
     * @param movie
     */
    void removeMovie(final Movie movie) {
        this.purchasedMovies.removeAll(Collections.singletonList(movie));
        this.watchedMovies.removeAll(Collections.singletonList(movie));
        this.ratedMovies.removeAll(Collections.singletonList(movie));
        this.likedMovies.removeAll(Collections.singletonList(movie));
    }

    /**
     * adds a movie to available movies list
     * @param movie
     */
    public void addMovie(final Movie movie) {
        int ok = 0;
        for (String country : movie.getCountriesBanned()) {
            if (country.equals(this.credentials.getCountry())) {
                ok = 1;
            }
        }
        if (ok == 0) {
            this.availableMovies.add(movie);
        }
    }

    /**
     * notifies the user when a movie is added
     * @param movieName
     */
    public void notifyUserAdd(final String movieName) {
        Notification notif = new Notification();
        notif.setMessage("ADD");
        notif.setMovieName(movieName);
        notifications.add(notif);
    }

    /**
     * notifies the user when a movie is deleted
     * @param movie
     */
    public void notifyUserDelete(final Movie movie) {
        Notification notif = new Notification();
        notif.setMessage("DELETE");
        notif.setMovieName(movie.getName());
        notifications.add(notif);

        refundUser();
        removeMovie(movie);
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

    public final ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public final void setNotifications(final ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public final ArrayList<String> getSubscribedGenres() {
        return subscribedGenres;
    }

    public final void setSubscribedGenres(final ArrayList<String> subscribedGenres) {
        this.subscribedGenres = subscribedGenres;
    }
}
