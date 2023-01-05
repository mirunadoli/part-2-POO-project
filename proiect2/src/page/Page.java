package page;

import action.VisitorAbstract;
import com.fasterxml.jackson.annotation.JsonIgnore;
import data.Movie;

import java.util.ArrayList;

public abstract class Page {

    String pageType;

    ArrayList<Movie> moviesOnScreen;




    /**
     * method accept for the implementation of the visitor design pattern
     * @param action
     */
    public abstract void accept(VisitorAbstract action);

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public ArrayList<Movie> getMoviesOnScreen() {
        return moviesOnScreen;
    }

    public void setMoviesOnScreen(ArrayList<Movie> moviesOnScreen) {
        this.moviesOnScreen = moviesOnScreen;
    }
}
