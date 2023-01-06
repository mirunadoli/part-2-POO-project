package action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import data.ActionInput;
import data.Input;
import page.*;
import util.OutputMessage;

public class ChangePage extends VisitorAbstract {

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
        OutputMessage message = new OutputMessage();
        PageFactory pageFactory = new PageFactory();

        // selects the next page
        switch (action.getPage()) {
            case "login":
            case "register":
                nextPage = pageFactory.createPage(action.getPage());
                break;
            default: break;
        }

        // if the page can't be accessed from the current page
        if (nextPage == null) {
            message.addError(output);
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
        OutputMessage message = new OutputMessage();
        PageFactory pageFactory = new PageFactory();

        // selects the next page
        switch (action.getPage()) {
            case "movies":
            case "upgrades":
            case "logout":
                nextPage = pageFactory.createPage(action.getPage());
                break;
            default: break;
        }

        // if the page can't be accessed from the current page
        if (nextPage == null) {
            message.addError(output);
            return;
        }

        // else, set the current page
        input.setCurrentPage(nextPage);

    }

    /**
     * @param page
     */
    public void visit(final Login page) {
        OutputMessage message = new OutputMessage();
        message.addError(output);
    }

    /**
     * @param page
     */
    public void visit(final Register page) {
        OutputMessage message = new OutputMessage();
        message.addError(output);
    }

    /**
     * @param page
     */
    public void visit(final MoviesPage page) {
        Page nextPage = null;
        OutputMessage message = new OutputMessage();
        PageFactory pageFactory = new PageFactory();

        // selects the next page
        switch (action.getPage()) {
            case "homepage authenticated":
            case "see details":
            case "logout":
            case "movies":
                nextPage = pageFactory.createPage(action.getPage());
                break;
            default: break;
        }

        // if the page can't be accessed from the current page
        if (nextPage == null) {
            message.addError(output);
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
        OutputMessage message = new OutputMessage();
        PageFactory pageFactory = new PageFactory();

        // selects the next page
        switch (action.getPage()) {
            case "homepage authenticated":
            case "upgrades":
            case "logout":
            case "movies":
                nextPage = pageFactory.createPage(action.getPage());
                break;
            default: break;
        }

        // if the page can't be accessed from the current page
        if (nextPage == null) {
            message.addError(output);
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
        OutputMessage message = new OutputMessage();
        PageFactory pageFactory = new PageFactory();

        // selects the next page
        switch (action.getPage()) {
            case "homepage authenticated":
            case "logout":
            case "movies":
                nextPage = pageFactory.createPage(action.getPage());
                break;
            default: break;
        }

        // if the page can't be accessed from the current page
        if (nextPage == null) {
            message.addError(output);
            return;
        }

        // else, set the current page
        input.setCurrentPage(nextPage);
    }

    /**
     * @param page
     */
    public void visit(final Logout page) {
        OutputMessage message = new OutputMessage();
        message.addError(output);
    }

}
