package action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import data.ActionInput;
import data.Input;
import data.Movie;
import data.User;
import page.Page;
import util.OutputMessage;

public class DatabaseAction {
    private Input input;
    private ArrayNode output;
    private ChangeCommand changeCommand;

    public DatabaseAction(final Input input, final ArrayNode output) {
        this.input = input;
        this.output = output;
    }

    public final Input getInput() {
        return input;
    }

    public final void setInput(final Input input) {
        this.input = input;
    }

    public final ArrayNode getOutput() {
        return output;
    }

    public final void setOutput(final ArrayNode output) {
        this.output = output;
    }

    public final ChangeCommand getChangeCommand() {
        return changeCommand;
    }

    public final void setChangeCommand(final ChangeCommand changeCommand) {
        this.changeCommand = changeCommand;
    }

    /**
     *
     * @param movie
     */
    public void addMovie(final Movie movie) {
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
                    break;
                }
            }
        }

        for (User user : input.getUsers()) {
            user.addMovie(movie);
        }

        if (input.getCurrentPage().getPageType().equals("movies")) {
            input.getCurrentPage().getMoviesOnScreen().add(movie);
        }

        if (input.getCurrentUser().getAvailableMovies().contains(movie)) {
            for (Page page : changeCommand.getPreviousPages()) {
                if (page.getPageType().equals("movies")) {
                    page.getMoviesOnScreen().add(movie);
                }
            }
        }
    }

    /**
     *
     * @param movieName
     */
    public void deleteMovie(final String movieName) {
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
        Page current = input.getCurrentPage();
        if (current.getMoviesOnScreen() != null
                && current.getMoviesOnScreen().contains(movieToDelete)) {
            input.getCurrentPage().getMoviesOnScreen().remove(movieToDelete);
        }
    }

    /**
     *
     * @param action
     */
    public void execute(final ActionInput action) {
        if (action.getFeature().equals("add")) {
            addMovie(action.getAddedMovie());
        } else {
            deleteMovie(action.getDeletedMovie());
        }
    }
}
