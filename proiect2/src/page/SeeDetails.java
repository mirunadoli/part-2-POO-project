package page;

import action.ActionAbstract;
import data.Movie;

import java.util.ArrayList;

public class SeeDetails extends Page {

    private ArrayList<Movie> movie;

    public SeeDetails() {
        this.movie = new ArrayList<>();
    }

    public final ArrayList<Movie> getMovie() {
        return movie;
    }

    public final void setMovie(final ArrayList<Movie> movie) {
        this.movie = movie;
    }

    /**
     * method accept for the implementation of the visitor design pattern
     * @param action
     */
    public final void accept(final ActionAbstract action) {
        action.visit(this);
    }
}
