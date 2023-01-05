package util;

import com.fasterxml.jackson.databind.node.ArrayNode;
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


    /**
     * @param output
     */
    public void addToOutput(final ArrayNode output) {
        ArrayList<Movie> currentMovies = new ArrayList<>();
        for (Movie movie : this.currentMoviesList) {
            currentMovies.add(new Movie(movie));
        }
        User user = null;
        if (this.currentUser != null) {
            user = new User(this.currentUser);
        }
        output.addObject().put("error", this.error)
                .putPOJO("currentMoviesList", currentMovies)
                .putPOJO("currentUser", user);
    }


    /**
     * @param output
     */
    public void addError(final ArrayNode output) {
        this.setError("Error");
        this.setCurrentUser(null);
        this.setCurrentMoviesList(new ArrayList<>());
        addToOutput(output);
    }
}
