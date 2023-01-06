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
    private Recommandation recommandation;

    public StartEngine() {
    }

    public StartEngine(final Input input, final ArrayNode output) {
        this.input = input;
        this.output = output;
        this.changeCommand = new ChangeCommand(input, output);
        this.onPage = new OnPage(input, output);
        this.databaseAction = new DatabaseAction(input, output);
        this.recommandation = new Recommandation(input, output);
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

    public final ChangeCommand getChangeCommand() {
        return changeCommand;
    }

    public final void setChangeCommand(final ChangeCommand changeCommand) {
        this.changeCommand = changeCommand;
    }

    public final DatabaseAction getDatabaseAction() {
        return databaseAction;
    }

    public final void setDatabaseAction(final DatabaseAction databaseAction) {
        this.databaseAction = databaseAction;
    }

    public final Recommandation getRecommandation() {
        return recommandation;
    }

    public final void setRecommandation(final Recommandation recommandation) {
        this.recommandation = recommandation;
    }

    /**
     * the start of the program
     */
    public void start() {
        // the program starts from page "homepage neauthenticated"
        input.setCurrentPage(new HomepageN());

        // iterates through each action
        for (ActionInput act : input.getActions()) {
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
                databaseAction.setChangeCommand(changeCommand);
                databaseAction.execute(act);
            }
        }

        recommandation.recommend();

    }




}
