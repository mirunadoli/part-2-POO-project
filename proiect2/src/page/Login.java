package page;

import action.VisitorAbstract;

public class Login extends Page {

    public Login() {
        this.pageType = "login";
    }

    /**
     * method accept for the implementation of the visitor design pattern
     * @param action
     */
    public void accept(final VisitorAbstract action) {
        action.visit(this);
    }
}
