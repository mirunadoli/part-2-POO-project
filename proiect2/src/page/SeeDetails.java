package page;

import action.VisitorAbstract;
import data.Movie;

import java.util.ArrayList;

public class SeeDetails extends Page {


    public SeeDetails() {
        this.pageType = "see details";
        this.moviesOnScreen = new ArrayList<>();
    }

    /**
     * method accept for the implementation of the visitor design pattern
     * @param action
     */
    public final void accept(final VisitorAbstract action) {
        action.visit(this);
    }
}
