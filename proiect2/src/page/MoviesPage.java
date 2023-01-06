package page;

import action.VisitorAbstract;
import data.Movie;

import java.util.ArrayList;

public class MoviesPage extends Page {

    public MoviesPage() {
        this.setPageType("movies");
        this.setMoviesOnScreen(new ArrayList<>());
    }

    /**
     * method accept for the implementation of the visitor design pattern
     * @param action
     */
    public final void accept(final VisitorAbstract action) {
        action.visit(this);
    }

    /**
     * notifies a movies page if a new movie is added to the database
     * @param movie
     */
    public final void notifyPage(final Movie movie) {
        this.getMoviesOnScreen().add(movie);
    }
}
