package action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import data.ActionInput;
import data.Input;
import data.User;
import page.Page;
import page.SeeDetails;
import util.OutputMessage;

import java.util.ArrayList;

public class ChangeCommand extends CommandAbstract{
    private ActionInput action;
    private Input input;
    private ArrayNode output;
    private ArrayList<Page> previousPages = new ArrayList<>();
    private ChangePage changePage;
    private AfterChangePage afterChange;

    public ChangeCommand(Input input, ArrayNode output) {
        this.input = input;
        this.output = output;
        changePage = new ChangePage(input, output);
        afterChange = new AfterChangePage(input, output);
    }


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
            if (action.getPage().equals("logout")) {
                previousPages.clear();
            }
        } else {
            // remove page from prevPages
            previousPages.remove(input.getCurrentPage());
        }
    }

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

        if (backPage.getPageType().equals("movies") ||
                backPage.getPageType().equals("see details")) {
            message.setCurrentMoviesList(backPage.getMoviesOnScreen());
            message.setCurrentUser(input.getCurrentUser());
            message.addToOutput(output);
        }

    }


    public ActionInput getAction() {
        return action;
    }

    public void setAction(ActionInput action) {
        this.action = action;
    }

    public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public ArrayNode getOutput() {
        return output;
    }

    public void setOutput(ArrayNode output) {
        this.output = output;
    }

    public ArrayList<Page> getPreviousPages() {
        return previousPages;
    }

    public void setPreviousPages(ArrayList<Page> previousPages) {
        this.previousPages = previousPages;
    }

    public ChangePage getChangePage() {
        return changePage;
    }

    public void setChangePage(ChangePage changePage) {
        this.changePage = changePage;
    }

    public AfterChangePage getAfterChange() {
        return afterChange;
    }

    public void setAfterChange(AfterChangePage afterChange) {
        this.afterChange = afterChange;
    }
}
