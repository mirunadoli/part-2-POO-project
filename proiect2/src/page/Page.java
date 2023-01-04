package page;

import action.ActionAbstract;

public abstract class Page {

    /**
     * method accept for the implementation of the visitor design pattern
     * @param action
     */
    public abstract void accept(ActionAbstract action);
}
