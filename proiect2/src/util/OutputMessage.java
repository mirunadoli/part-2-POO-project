package util;

import data.Movie;
import data.User;

import java.util.ArrayList;

public class OutputMessage {
    private String error;
    private ArrayList<Movie> currentMoviesList;
    private User currentUser;


    public OutputMessage() {
        error = null;
        currentMoviesList = new ArrayList<>();
        currentUser = null;
    }

    public final String getError() {
        return error;
    }

    public final void setError(final String error) {
        this.error = error;
    }

    public final ArrayList<Movie> getCurrentMoviesList() {
        return currentMoviesList;
    }

    public final void setCurrentMoviesList(final ArrayList<Movie> currentMoviesList) {
        this.currentMoviesList = currentMoviesList;
    }

    public final User getCurrentUser() {
        return currentUser;
    }

    public final void setCurrentUser(final User currentUser) {
        this.currentUser = currentUser;
    }
}
