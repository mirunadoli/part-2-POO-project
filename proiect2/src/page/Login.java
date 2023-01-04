package page;

import action.ActionAbstract;

public class Login extends Page {

    /**
     * method accept for the implementation of the visitor design pattern
     * @param action
     */
    public void accept(final ActionAbstract action) {
        action.visit(this);
    }
}
