package page;

import action.VisitorAbstract;

public class Logout extends Page {

    public Logout() {
        this.pageType = "logout";
    }

    /**
     * method accept for the implementation of the visitor design pattern
     * @param action
     */
    public void accept(final VisitorAbstract action) {
        action.visit(this);
    }
}
