package page;

import action.VisitorAbstract;

public class Register extends Page {

    public Register() {
        this.pageType = "register";
    }

    /**
     * method accept for the implementation of the visitor design pattern
     * @param action
     */
    public void accept(final VisitorAbstract action) {
        action.visit(this);
    }
}
