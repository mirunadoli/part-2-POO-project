package action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import data.Movie;
import util.OutputMessage;
import data.User;
import page.*;

import java.util.ArrayList;

public abstract class ActionAbstract {

    /**
     * @param output
     * @param message
     */
    void addToOutput(final ArrayNode output, final OutputMessage message) {
        ArrayList<Movie> currentMovies = new ArrayList<>();
        for (Movie movie : message.getCurrentMoviesList()) {
            currentMovies.add(new Movie(movie));
        }
        User user = null;
        if (message.getCurrentUser() != null) {
            user = new User(message.getCurrentUser());
        }
        output.addObject().put("error", message.getError())
                .putPOJO("currentMoviesList", currentMovies)
                .putPOJO("currentUser", user);
    }


    /**
     * @param output
     */
    void addError(final ArrayNode output) {
        OutputMessage message = new OutputMessage();
        message.setError("Error");
        addToOutput(output, message);
    }

    /**
     * @param page
     */
    public abstract void visit(HomepageN page);

    /**
     * @param page
     */
    public abstract void visit(HomepageAuth page);

    /**
     * @param page
     */
    public abstract void visit(Login page);

    /**
     * @param page
     */
    public abstract void visit(Register page);

    /**
     * @param page
     */
    public abstract void visit(MoviesPage page);

    /**
     * @param page
     */
    public abstract void visit(SeeDetails page);

    /**
     * @param page
     */
    public abstract void visit(Upgrades page);

    /**
     * @param page
     */
    public abstract void visit(Logout page);

}
