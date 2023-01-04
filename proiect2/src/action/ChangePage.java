package action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import data.ActionInput;
import data.Input;
import page.*;

public class ChangePage extends ActionAbstract {

    private ActionInput action;
    private Input input;
    private ArrayNode output;

    public ChangePage() {

    }

    public ChangePage(final Input input, final ArrayNode output) {
        this.input = input;
        this.output = output;
    }

    public final ActionInput getAction() {
        return action;
    }

    public final void setAction(final ActionInput action) {
        this.action = action;
    }

    public final Input getInput() {
        return input;
    }

    public final void setInput(final Input input) {
        this.input = input;
    }

    public final ArrayNode getOutput() {
        return output;
    }

    public final void setOutput(final ArrayNode output) {
        this.output = output;
    }


    /**
     * @param page
     */
    public void visit(final HomepageN page) {
        Page nextPage = null;

        // selects the next page
        if (action.getPage().equals("login")) {
            nextPage = new Login();
        } else if (action.getPage().equals("register")) {
            nextPage = new Register();
        }

        // if the page can't be accessed from the current page
        if (nextPage == null) {
            addError(output);
            return;
        }

        // else, set the current page
        input.setCurrentPage(nextPage);

    }

    /**
     * @param page
     */
    public void visit(final HomepageAuth page) {
        Page nextPage = null;

        // selects the next page
        if (action.getPage().equals("movies")) {
            nextPage = new MoviesPage();
        } else if (action.getPage().equals("upgrades")) {
            nextPage = new Upgrades();
        } else if (action.getPage().equals("logout")) {
            nextPage = new Logout();
        }

        // if the page can't be accessed from the current page
        if (nextPage == null) {
            addError(output);
            return;
        }

        // else, set the current page
        input.setCurrentPage(nextPage);

    }

    /**
     * @param page
     */
    public void visit(final Login page) {
        // there isn't any page that can be accesed from the login page
        addError(output);
    }

    /**
     * @param page
     */
    public void visit(final Register page) {
        // there isn't any page that can be accesed from the register page
        addError(output);
    }

    /**
     * @param page
     */
    public void visit(final MoviesPage page) {
        Page nextPage = null;

        // selects the next page
        if (action.getPage().equals("homepage authenticated")) {
            nextPage = new HomepageAuth();
        } else if (action.getPage().equals("see details")) {
            nextPage = new SeeDetails();
        } else if (action.getPage().equals("logout")) {
            nextPage = new Logout();
        } else if (action.getPage().equals("movies")) {
            nextPage = new MoviesPage();
        }

        // if the page can't be accessed from the current page
        if (nextPage == null) {
            addError(output);
            return;
        }

        // else, set the current page
        input.setCurrentPage(nextPage);
    }

    /**
     * @param page
     */
    public void visit(final SeeDetails page) {
        Page nextPage = null;

        // selects the next page
        if (action.getPage().equals("movies")) {
            nextPage = new MoviesPage();
        } else if (action.getPage().equals("homepage authenticated")) {
            nextPage = new HomepageAuth();
        } else if (action.getPage().equals("upgrades")) {
            nextPage = new Upgrades();
        } else if (action.getPage().equals("logout")) {
            nextPage = new Logout();
        }

        // if the page can't be accessed from the current page
        if (nextPage == null) {
            addError(output);
            return;
        }

        // else, set the current page
        input.setCurrentPage(nextPage);
    }

    /**
     * @param page
     */
    public void visit(final Upgrades page) {
        Page nextPage = null;

        // selects the next page
        if (action.getPage().equals("movies")) {
            nextPage = new MoviesPage();
        } else if (action.getPage().equals("homepage authenticated")) {
            nextPage = new HomepageAuth();
        } else if (action.getPage().equals("logout")) {
            nextPage = new Logout();
        }

        // if the page can't be accessed from the current page
        if (nextPage == null) {
            addError(output);
            return;
        }

        // else, set the current page
        input.setCurrentPage(nextPage);
    }

    /**
     * @param page
     */
    public void visit(final Logout page) {
        // there isn't any page that can be accesed from the login page
        addError(output);
    }

}
