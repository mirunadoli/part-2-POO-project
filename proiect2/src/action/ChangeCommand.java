package action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import data.ActionInput;
import data.Input;
import page.Page;
import util.OutputMessage;

import java.util.ArrayList;

public class ChangeCommand extends CommandAbstract {
    private ActionInput action;
    private Input input;
    private ArrayNode output;
    private ArrayList<Page> previousPages = new ArrayList<>();
    private ChangePage changePage;
    private AfterChangePage afterChange;

    public ChangeCommand(final Input input, final ArrayNode output) {
        this.input = input;
        this.output = output;
        changePage = new ChangePage(input, output);
        afterChange = new AfterChangePage(input, output);
    }


    /**
     *
     */
    public void execute() {
        changePage.setAction(action);
        afterChange.setAction(action);
        previousPages.add(input.getCurrentPage());

        input.getCurrentPage().accept(changePage);

        // if the page was changed with succes
        if (previousPages.get(previousPages.size() - 1) !=  input.getCurrentPage()) {
            if (action.getPage().equals("see details")) {
                afterChange.setPreviousPage(previousPages.get(previousPages.size() - 1));
            }

            input.getCurrentPage().accept(afterChange);

            if (action.getPage().equals("see details")
                    && previousPages.get(previousPages.size() - 1) ==  input.getCurrentPage()) {
                previousPages.remove(input.getCurrentPage());
            }
            if (action.getPage().equals("logout")) {
                previousPages.clear();
            }

        } else {
            // remove page from prevPages
            previousPages.remove(input.getCurrentPage());
        }
    }

    /**
     *
     */
    public void undo() {
        OutputMessage message = new OutputMessage();
        if (input.getCurrentUser() == null) {
            message.addError(output);
            return;
        }

        if (previousPages.isEmpty()) {
            message.addError(output);
            return;
        }

        Page backPage = previousPages.get(previousPages.size() - 1);

        if (backPage.getPageType().equals("login") || backPage.getPageType().equals("register")) {
            message.addError(output);
            return;
        }

        input.setCurrentPage(backPage);
        previousPages.remove(input.getCurrentPage());

        if (backPage.getPageType().equals("movies")
                || backPage.getPageType().equals("see details")) {
            message.setCurrentMoviesList(backPage.getMoviesOnScreen());
            message.setCurrentUser(input.getCurrentUser());
            message.addToOutput(output);
        }

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

    public final ArrayList<Page> getPreviousPages() {
        return previousPages;
    }

    public final void setPreviousPages(final ArrayList<Page> previousPages) {
        this.previousPages = previousPages;
    }

    public final ChangePage getChangePage() {
        return changePage;
    }

    public final void setChangePage(final ChangePage changePage) {
        this.changePage = changePage;
    }

    public final AfterChangePage getAfterChange() {
        return afterChange;
    }

    public final void setAfterChange(final AfterChangePage afterChange) {
        this.afterChange = afterChange;
    }
}
