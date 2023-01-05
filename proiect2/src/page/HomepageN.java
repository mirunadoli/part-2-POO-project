package page;

import action.VisitorAbstract;

public class HomepageN extends Page {

    public HomepageN() {
        this.pageType = "homepage neauthenticated";
    }

    /**
     * method accept for the implementation of the visitor design pattern
     * @param action
     */
    public void accept(final VisitorAbstract action) {
        action.visit(this);
    }
}
