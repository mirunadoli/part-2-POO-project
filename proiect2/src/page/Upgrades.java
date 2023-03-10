package page;

import action.VisitorAbstract;

public class Upgrades extends Page {

    public Upgrades() {
        this.setPageType("upgrades");
    }

    /**
     * method accept for the implementation of the visitor design pattern
     * @param action
     */
    public void accept(final VisitorAbstract action) {
        action.visit(this);
    }
}
