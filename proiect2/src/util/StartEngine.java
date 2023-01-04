package util;

import action.AfterChangePage;
import action.ChangePage;
import action.OnPage;
import com.fasterxml.jackson.databind.node.ArrayNode;
import data.ActionInput;
import data.Input;
import page.HomepageN;
import page.Page;

public class StartEngine {

    private Input input;
    private ArrayNode output;
    private ChangePage changePage;
    private OnPage onPage;
    private AfterChangePage afterChange;

    public StartEngine() {
    }

    public StartEngine(final Input input, final ArrayNode output) {
        this.input = input;
        this.output = output;
        this.changePage = new ChangePage(input, output);
        this.onPage = new OnPage(input, output);
        this.afterChange = new AfterChangePage(input, output);
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

    public final ChangePage getChangePage() {
        return changePage;
    }

    public final void setChangePage(final ChangePage changePage) {
        this.changePage = changePage;
    }

    public final OnPage getOnPage() {
        return onPage;
    }

    public final void setOnPage(final OnPage onPage) {
        this.onPage = onPage;
    }

    public final AfterChangePage getAfterChange() {
        return afterChange;
    }

    public final void setAfterChange(final AfterChangePage afterChange) {
        this.afterChange = afterChange;
    }

    /**
     *
     */
    public void start() {
        // the program starts from page "homepage neauthenticated"
        input.setCurrentPage(new HomepageN());
        Page oldPage;

        // iterates through each actions
        for (ActionInput act : input.getActions()) {

            if (act.getType().equals("change page")) {

                oldPage = input.getCurrentPage();
                changePage.setAction(act);
                afterChange.setAction(act);
                input.getCurrentPage().accept(changePage);

                // if the new page requires some action to be done when accessed
                if (oldPage != input.getCurrentPage()) {
                    input.getCurrentPage().accept(afterChange);
                }

            } else if (act.getType().equals("on page")) {
                onPage.setAction(act);
                input.getCurrentPage().accept(onPage);
            }
        }
    }
}
