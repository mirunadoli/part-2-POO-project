package page;

import action.VisitorAbstract;

public class HomepageAuth extends Page {

    public HomepageAuth() {
        this.setPageType("homepage authenticated");
    }

    /**
     * method accept for the implementation of the visitor design pattern
     * @param action
     */
    public void accept(final VisitorAbstract action) {
        action.visit(this);
    }
}
