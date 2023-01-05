package action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import data.ActionInput;
import data.Input;
import data.Movie;
import data.User;
import util.OutputMessage;

import java.util.ArrayList;

public class DatabaseAction {
    Input input;
    ArrayNode output;

    public DatabaseAction(Input input, ArrayNode output) {
        this.input = input;
        this.output = output;
    }


    public void addMovie(Movie movie) {
        for (Movie mov : input.getMovies()) {
            if (mov.getName().equals(movie.getName())) {
                OutputMessage message = new OutputMessage();
                message.addError(output);
                return;
            }
        }

        input.getMovies().add(movie);
        for (User user : input.getUsers()) {
            for (String genre : movie.getGenres()) {
                if (user.getSubscribedGenres().contains(genre)) {
                    user.notifyUserAdd(movie.getName());
                }
            }
        }
    }

    public void deleteMovie(String movieName) {
        int ok = 0;
        Movie movieToDelete = null;
        for (Movie movie : input.getMovies()) {
            if (movie.getName().equals(movieName)) {
                movieToDelete = movie;
                ok = 1;
            }
        }

        // if the movie didn't exist in the database
        if (ok == 0) {
            OutputMessage message = new OutputMessage();
            message.addError(output);
            return;
        }

        input.getMovies().remove(movieToDelete);

        for (User user : input.getUsers()) {
            if (user.getPurchasedMovies().contains(movieToDelete)) {
                user.notifyUserDelete(movieToDelete);
            }

            // if the users have the movie in available
            if (user.getAvailableMovies().contains(movieToDelete)) {
                user.getAvailableMovies().remove(movieToDelete);
            }
        }

        // if the movie is seen on screen
        if (input.getCurrentPage().getMoviesOnScreen().contains(movieToDelete)) {
            input.getCurrentPage().getMoviesOnScreen().remove(movieToDelete);
        }
    }

    public void execute(ActionInput action) {
        if (action.getFeature().equals("add")) {
            addMovie(action.getAddedMovie());
        } else {
            deleteMovie(action.getDeletedMovie());
        }
    }
}
