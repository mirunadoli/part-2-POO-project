package page;

import action.VisitorAbstract;
import data.Movie;

import java.util.ArrayList;

public abstract class Page {

    private String pageType;

    private ArrayList<Movie> moviesOnScreen;

    /**
     *
     * @param movie
     */
    public void notifyPage(final Movie movie) {

    }

    /**
     * method accept for the implementation of the visitor design pattern
     * @param action
     */
    public abstract void accept(VisitorAbstract action);

    public final String getPageType() {
        return pageType;
    }

    public final void setPageType(final String pageType) {
        this.pageType = pageType;
    }

    public final ArrayList<Movie> getMoviesOnScreen() {
        return moviesOnScreen;
    }

    public final void setMoviesOnScreen(final ArrayList<Movie> moviesOnScreen) {
        this.moviesOnScreen = moviesOnScreen;
    }
}
