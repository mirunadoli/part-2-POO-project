package action;

import page.*;

public abstract class VisitorAbstract {


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
