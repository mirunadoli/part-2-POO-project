package action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import data.*;
import page.*;
import util.OutputMessage;

public class AfterChangePage extends ActionAbstract {


    private ActionInput action;
    private Input input;
    private ArrayNode output;

    public AfterChangePage() {
    }

    public AfterChangePage(final Input input, final ArrayNode output) {
        this.input = input;
        this.output = output;
    }

    public final ActionInput getAction() {
        return action;
    }

    public final void setAction(final ActionInput action) {
        this.action = action;
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


    /**
     * @param page
     */
    public void visit(final HomepageN page) {

    }

    /**
     * @param page
     */
    public void visit(final HomepageAuth page) {

    }

    /**
     * @param page
     */
    public void visit(final Login page) {

    }

    /**
     * @param page
     */
    public void visit(final Register page) {

    }

    /**
     * @param page
     */
    public void visit(final MoviesPage page) {
        OutputMessage message = new OutputMessage();
        User user = input.getCurrentUser();

        // resets movies seen on screen
        user.getMoviesOnScreen().removeAll(user.getMoviesOnScreen());
        user.getMoviesOnScreen().addAll(user.getAvailableMovies());

        // shows available movies on screen
        message.setCurrentUser(user);
        message.setCurrentMoviesList(user.getMoviesOnScreen());
        addToOutput(output, message);
    }

    /**
     * @param page
     */
    public void visit(final SeeDetails page) {
        User user = input.getCurrentUser();
        OutputMessage message = new OutputMessage();

        // verify if the movie exists and can be seen by the user
        for (Movie movie : input.getCurrentUser().getMoviesOnScreen()) {
            if (movie.getName().equals(action.getMovie())) {
                page.getMovie().add(movie);
            }
        }

        // if it doesn't exist, show error and return to movies page
        if (page.getMovie().isEmpty()) {
            addError(output);
            input.setCurrentPage(new MoviesPage());
            return;
        }

        // sets movies on screen
        user.getMoviesOnScreen().removeAll(user.getMoviesOnScreen());
        user.getMoviesOnScreen().addAll(page.getMovie());

        // adds to output
        message.setCurrentUser(input.getCurrentUser());
        message.setCurrentMoviesList(page.getMovie());
        addToOutput(output, message);
    }

    /**
     * @param page
     */
    public void visit(final Upgrades page) {

    }

    /**
     * @param page
     */
    public void visit(final Logout page) {
        // resets the current page and user
        input.setCurrentPage(new HomepageN());
        input.setCurrentUser(null);
    }
}
