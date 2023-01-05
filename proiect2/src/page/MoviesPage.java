package page;

import action.VisitorAbstract;

import java.util.ArrayList;

public class MoviesPage extends Page {

    public MoviesPage() {
        this.pageType = "movies";
        this.moviesOnScreen = new ArrayList<>();
    }

    /**
     * method accept for the implementation of the visitor design pattern
     * @param action
     */
    public void accept(final VisitorAbstract action) {
        action.visit(this);
    }
}
