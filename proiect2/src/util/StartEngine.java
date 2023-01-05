package util;

import action.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import data.ActionInput;
import data.Input;
import page.HomepageN;
import page.Page;

public class StartEngine {

    private Input input;
    private ArrayNode output;
    private ChangeCommand changeCommand;
    private OnPage onPage;
    private DatabaseAction databaseAction;


    public StartEngine() {
    }

    public StartEngine(final Input input, final ArrayNode output) {
        this.input = input;
        this.output = output;
        this.changeCommand = new ChangeCommand(input, output);
        this.onPage = new OnPage(input, output);
        this.databaseAction = new DatabaseAction(input, output);
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

    public final OnPage getOnPage() {
        return onPage;
    }

    public final void setOnPage(final OnPage onPage) {
        this.onPage = onPage;
    }

    public ChangeCommand getChangeCommand() {
        return changeCommand;
    }

    public void setChangeCommand(ChangeCommand changeCommand) {
        this.changeCommand = changeCommand;
    }

    /**
     *
     */
    public void start() {
        // the program starts from page "homepage neauthenticated"
        input.setCurrentPage(new HomepageN());
        int num = 0;
        // iterates through each actions
        for (ActionInput act : input.getActions()) {
            //output.addObject().put("action type", act.getType()).put("num", num)
             //       .put("feature", act.getFeature());
            num++;
            if (act.getType().equals("change page")) {

                changeCommand.setAction(act);
                changeCommand.execute();

            } else if (act.getType().equals("back")) {

                changeCommand.setAction(act);
                changeCommand.undo();

            } else if (act.getType().equals("on page")) {
                Page prevPage = input.getCurrentPage();
                onPage.setAction(act);
                input.getCurrentPage().accept(onPage);
                if (prevPage != input.getCurrentPage()) {
                    changeCommand.getPreviousPages().add(prevPage);
                }
            } else if (act.getType().equals("database")) {
                databaseAction.execute(act);
            }
        }

        
    }
}
