package action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import data.*;
import page.*;

import java.util.ArrayList;

import util.Constants;
import util.OutputMessage;

public class OnPage extends VisitorAbstract {

    private ActionInput action;
    private Input input;
    private ArrayNode output;

    public OnPage() {
    }

    public OnPage(final Input input, final ArrayNode output) {
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
     * method visit for the implementation of the visitor design pattern
     * @param page
     */
    public void visit(final HomepageN page) {
        OutputMessage message = new OutputMessage();
        message.addError(output);
    }

    /**
     * @param page
     */
    public void visit(final HomepageAuth page) {
        OutputMessage message = new OutputMessage();
        message.addError(output);
    }


    /**
     * ok - verify if the login action is successful
     * loginUser - the user that is logging in
     * @param page
     */
    public void visit(final Login page) {
        int ok = 0;
        User loginUser = null;
        Credentials creds = action.getCredentials();
        OutputMessage message = new OutputMessage();

        // if action is not of type login, error
        if (!(action.getFeature().equals("login"))) {
            message.addError(output);
            return;
        }

        // compare the credentials given to those of the existing users
        for (User user : input.getUsers()) {
            if (creds.getName().equals(user.getCredentials().getName())) {
                if (creds.getPassword().equals(user.getCredentials().getPassword())) {
                    ok = 1;
                    loginUser = user;
                }
            }
        }

        // if the user is not found
        if (ok != 1) {
            input.setCurrentPage(new HomepageN());
            message.addError(output);
            return;
        }

        // prepares the info for the next page
        input.setCurrentUser(loginUser);
        input.setCurrentPage(new HomepageAuth());
        message.setCurrentUser(loginUser);
        message.addToOutput(output);


        // selects the movies that the user can see
        if (loginUser.getAvailableMovies().isEmpty()) {
            OnPageUtil.createAvailableMovies(input);
        }

    }

    /**
     * @param page
     */
    public void visit(final Register page) {

        OutputMessage message = new OutputMessage();
        // if action is not of type register => error
        if (!(action.getFeature().equals("register"))) {
            input.setCurrentPage(new HomepageN());
            message.addError(output);
            return;
        }

        // if there is a user registered with the same name => error
        for (User user : input.getUsers()) {
            if (action.getCredentials().getName().equals(user.getCredentials().getName())) {
                input.setCurrentPage(new HomepageN());
                message.addError(output);
                return;
            }
        }

        // add the user to the database
        User newUser = new User(action.getCredentials());
        input.getUsers().add(newUser);

        // prepares the info for the next page
        input.setCurrentUser(newUser);
        input.setCurrentPage(new HomepageAuth());
        message.setCurrentUser(newUser);
        message.addToOutput(output);

        // selects the movies that the user can see
        if (newUser.getAvailableMovies().isEmpty()) {
            OnPageUtil.createAvailableMovies(input);
        }

    }


    /**
     * @param page
     */
    public void visit(final MoviesPage page) {
        OutputMessage message = new OutputMessage();

        if (action.getFeature().equals("search")) {
            ArrayList<Movie> searched = OnPageUtil.search(input, action);

            message.setCurrentMoviesList(searched);
            message.setCurrentUser(input.getCurrentUser());
            message.addToOutput(output);

        } else if (action.getFeature().equals("filter")) {
            Filter filter = action.getFilters();
            User user = input.getCurrentUser();

            // if the movies should be sorted
            if (filter.getSort() != null) {
                ArrayList<Movie> sorted = OnPageUtil.sort(input, action);

                input.getCurrentPage().getMoviesOnScreen().clear();
                input.getCurrentPage().getMoviesOnScreen().addAll(sorted);

                message.setCurrentMoviesList(sorted);
                message.setCurrentUser(user);
                message.addToOutput(output);

            // if the user is searching for specific actors/genres
            } else if (filter.getContains() != null) {
                ArrayList<Movie> contain = OnPageUtil.contains(input, action);

                input.getCurrentPage().getMoviesOnScreen().clear();
                input.getCurrentPage().getMoviesOnScreen().addAll(contain);

                message.setCurrentMoviesList(contain);
                message.setCurrentUser(user);
                message.addToOutput(output);
            }

        } else {
            message.addError(output);
        }

    }

    /**
     * @param page
     */
    public void visit(final SeeDetails page) {
        User user = input.getCurrentUser();
        Movie movie = page.getMoviesOnScreen().get(0);
        OutputMessage message = new OutputMessage();
        message.setCurrentUser(user);
        message.setCurrentMoviesList(page.getMoviesOnScreen());

        if (action.getFeature().equals("purchase")) {
            // verify if the movie is already purchased
            if (user.getPurchasedMovies().contains(movie)) {
                message.addError(output);
                return;
            }

            // verify if account is premium
            if (user.getCredentials().getAccountType().equals("premium")) {

                // if yes, verify if there are any free movies left
                if (user.getNumFreePremiumMovies() > 0) {
                    // if yes, decrease the count of free movies and add the movie to purchased
                    user.setNumFreePremiumMovies(user.getNumFreePremiumMovies() - 1);
                    user.getPurchasedMovies().add(movie);
                    message.addToOutput(output);
                    return;
                }
            }

            int tokens = user.getTokensCount();

            // verify if there are enough tokens
            if (tokens < Constants.MOVIE_PRICE) {
                message.addError(output);
                return;
            }
            // decrease the tokens and add the movie to purchased
            tokens = tokens - Constants.MOVIE_PRICE;
            user.setTokensCount(tokens);
            user.getPurchasedMovies().add(movie);
            message.addToOutput(output);
            return;


        } else if (action.getFeature().equals("watch")) {
            // verify if the movie is purchased
            if (!(user.getPurchasedMovies().contains(movie))) {
                message.addError(output);
                return;
            }

            // add the movie to watched list
            if (!user.getWatchedMovies().contains(movie)) {
                user.getWatchedMovies().add(movie);
            }
            message.addToOutput(output);
            return;

        }

        if (action.getFeature().equals("like")) {
            // verify if the movie is watched
            if (!(user.getWatchedMovies().contains(movie))) {
                message.addError(output);
                return;
            }

            // verify if the user gave a like already
            if (user.getLikedMovies().contains(movie)) {
                return;
            }

            // add the movie to liked movies and increase the number of likes for the movie
            user.getLikedMovies().add(movie);
            movie.setNumLikes(movie.getNumLikes() + 1);
            message.addToOutput(output);
            return;

        }

        if (action.getFeature().equals("rate")) {
            // verify if the movie is watched
            if (!(user.getWatchedMovies().contains(movie))) {
                message.addError(output);
                return;
            }

            // verify if the rating is a valid number
            if (action.getRate() < 0 || action.getRate() > Constants.MAX_RATE) {
                message.addError(output);
                return;
            }

            double ratingSum = 0;
            // if it's first time rating
            if (!user.getRatedMovies().contains(movie)) {

                user.getRatedMovies().add(movie);
                movie.setNumRatings(movie.getNumRatings() + 1);
                movie.getRatingMap().put(user.getCredentials().getName(), action.getRate());
            } else {
                movie.getRatingMap().replace(user.getCredentials().getName(), action.getRate());
            }

            // calculate new rating
            for (String key : movie.getRatingMap().keySet()) {
                ratingSum = ratingSum + movie.getRatingMap().get(key);
            }
            movie.setRating(ratingSum / movie.getNumRatings());
            message.addToOutput(output);

            return;

        }

        if (action.getFeature().equals("subscribe")) {
            // verify if genre is between movie genres
            if (!movie.getGenres().contains(action.getSubscribedGenre())) {
                message.addError(output);
                return;
            }
            // verify if the user hasn't subscribed already to the genre
            if (user.getSubscribedGenres().contains(action.getSubscribedGenre())) {
                message.addError(output);
                return;
            }

            // add it to subscribed genres
            user.getSubscribedGenres().add(action.getSubscribedGenre());
            return;
        }

        message.addError(output);
    }

    /**
     * @param page
     */
    public void visit(final Upgrades page) {

        OutputMessage message = new OutputMessage();

        if (action.getFeature().equals("buy tokens")) {
            int balance = Integer.parseInt(input.getCurrentUser().getCredentials().getBalance());
            int tokens = input.getCurrentUser().getTokensCount();

            // verify if there is enough balance
            if (balance < action.getCount()) {
                message.addError(output);
                return;
            }
            // -count from balance, +count to tokens
            balance = balance - action.getCount();
            tokens = tokens + action.getCount();

            input.getCurrentUser().getCredentials().setBalance(Integer.toString(balance));
            input.getCurrentUser().setTokensCount(tokens);

        } else if (action.getFeature().equals("buy premium account")) {
            int tokens = input.getCurrentUser().getTokensCount();

            // verify if the user has enough tokens
            if (tokens < Constants.PREMIUM_PRICE) {
                message.addError(output);
                return;
            }

            // sets the account to premium and decreases the amount of tokens
            tokens = tokens - Constants.PREMIUM_PRICE;
            input.getCurrentUser().setTokensCount(tokens);
            input.getCurrentUser().getCredentials().setAccountType("premium");

        } else {
            message.addError(output);
        }
    }

    /**
     * @param page
     */
    public void visit(final Logout page) {
        OutputMessage message = new OutputMessage();
        message.addError(output);
    }
}
