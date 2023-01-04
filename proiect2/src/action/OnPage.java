package action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import data.*;
import page.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import util.Constants;
import util.OutputMessage;

public class OnPage extends ActionAbstract {

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
        // there are no on page actions for this page
        addError(output);
    }

    /**
     * @param page
     */
    public void visit(final HomepageAuth page) {
        // there are no on page actions for this page
        addError(output);
    }

    /**
     * creates a list with the available movies for the current user
     */
    public void createAvailableMovies() {

        User user = input.getCurrentUser();
        int ok;

        for (Movie movie : input.getMovies()) {
            ok = 1;
            // verifies if the movie is banned in the user's country
            for (String country : movie.getCountriesBanned()) {
                if (user.getCredentials().getCountry().equals(country)) {
                    ok = 0;
                }
            }

            // if it is not banned, add it to the list
            if (ok == 1) {
                user.getAvailableMovies().add(movie);
            }
        }
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

        // if action is not of type login, error
        if (!(action.getFeature().equals("login"))) {
            addError(output);
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
            addError(output);
            return;
        }

        // prepares the info for the next page
        input.setCurrentUser(loginUser);
        input.setCurrentPage(new HomepageAuth());
        OutputMessage message = new OutputMessage();
        message.setCurrentUser(loginUser);
        addToOutput(output, message);


        // selects the movies that the user can see
        if (loginUser.getAvailableMovies().isEmpty()) {
            createAvailableMovies();
        }

    }

    /**
     * @param page
     */
    public void visit(final Register page) {

        // if action is not of type register => error
        if (!(action.getFeature().equals("register"))) {
            input.setCurrentPage(new HomepageN());
            addError(output);
            return;
        }

        // if there is a user registered with the same name => error
        for (User user : input.getUsers()) {
            if (action.getCredentials().getName().equals(user.getCredentials().getName())) {
                input.setCurrentPage(new HomepageN());
                addError(output);
                return;
            }
        }

        // add the user to the database
        User newUser = new User(action.getCredentials());
        input.getUsers().add(newUser);

        // prepares the info for the next page
        input.setCurrentUser(newUser);
        input.setCurrentPage(new HomepageAuth());
        OutputMessage message = new OutputMessage();
        message.setCurrentUser(newUser);
        addToOutput(output, message);

        // selects the movies that the user can see
        if (newUser.getAvailableMovies().isEmpty()) {
            createAvailableMovies();
        }

    }

    /**
     * searches for a movie that starts with the string given in action
     * @return
     */
    ArrayList<Movie> search() {
        ArrayList<Movie> searched = new ArrayList<>();
        for (Movie movie : input.getCurrentUser().getMoviesOnScreen()) {
            if (movie.getName().startsWith(action.getStartsWith())) {
                searched.add(movie);
            }
        }
        return searched;
    }


    /**
     * compares the duration of two movies based on the given filter
     * @param o1
     * @param o2
     * @param sort
     * @return
     */
    public int compareDuration(final Movie o1, final Movie o2, final String sort) {
        if (sort.equals("decreasing")) {
            return o2.getDuration() - o1.getDuration();
        } else {
            return o1.getDuration() - o2.getDuration();
        }
    }

    /**
     * compares the ratings of two movies based on the given filter
     * @param o1
     * @param o2
     * @param sort
     * @return
     */
    public int compareRating(final Movie o1, final Movie o2, final String sort) {
        if (sort.equals("decreasing")) {
            return Double.compare(o2.getRating(), o1.getRating());
        } else {
            return Double.compare(o1.getRating(), o2.getRating());
        }
    }

    /**
     * creates a sorted list of the movies shown on screen
     * @return
     */
    ArrayList<Movie> sort() {
        // creates the list
        ArrayList<Movie> sorted = new ArrayList<>();
        sorted.addAll(input.getCurrentUser().getMoviesOnScreen());
        Filter filter = action.getFilters();
        boolean sortDuration = true;
        boolean sortRating = true;

        // if it shouldn't be sorted, return
        if (filter.getSort() == null) {
            return sorted;
        }

        if (filter.getSort().getDuration() == null) {
            sortDuration = false;
        }
        if (filter.getSort().getRating() == null) {
            sortRating = false;
        }

        // if it has to be sorted by duration and rating
        if (sortDuration && sortRating) {
            // it is sorted first by duration
            if (filter.getSort().getDuration().equals("decreasing")) {
                Collections.sort(sorted, new Comparator<Movie>() {
                    @Override
                    public int compare(final Movie o1, final Movie o2) {
                        // if the durations are equal, sort by rating
                        if (o1.getDuration() == o2.getDuration()) {
                            String sort = filter.getSort().getRating();
                            return compareRating(o1, o2, sort);
                        }
                        return o2.getDuration() - o1.getDuration();
                    }
                });
            } else if (filter.getSort().getDuration().equals("increasing")) {
                Collections.sort(sorted, new Comparator<Movie>() {
                    @Override
                    public int compare(final Movie o1, final Movie o2) {
                        if (o1.getDuration() == o2.getDuration()) {
                            String sort = filter.getSort().getRating();
                            return compareRating(o1, o2, sort);
                        }
                        return o1.getDuration() - o2.getDuration();
                    }
                });
            }
            return sorted;
        }

        // if it has to be sorted only by duration
        if (sortDuration) {
            String sort = filter.getSort().getDuration();
            Collections.sort(sorted, (o1, o2) -> compareDuration(o1, o2, sort));
            return sorted;
        }


        // if it has to be sorted only by duration
        if (sortRating) {
            String sort = filter.getSort().getRating();
            Collections.sort(sorted, (o1, o2) -> compareRating(o1, o2, sort));
        }
        return sorted;
    }


    /**
     * creates a list with the movies that contain the actors or genres
     * given in the command
     * @return
     */
    public ArrayList<Movie> contains() {

        ArrayList<Movie> movies = input.getCurrentUser().getAvailableMovies();
        ArrayList<Movie> contains = new ArrayList<>();
        ArrayList<String> actors = new ArrayList<>();
        ArrayList<String> genres = new ArrayList<>();

        // copies the lists of actors and genres
        if (action.getFilters().getContains().getActors() != null) {
            actors.addAll(action.getFilters().getContains().getActors());
        }
        if (action.getFilters().getContains().getGenre() != null) {
            genres.addAll(action.getFilters().getContains().getGenre());
        }
        int ok;

        // for every movie, verify if it contains the actors and the genres given
        for (Movie mov : movies) {
            ok = 1;

            for (String act : actors) {
                if (!(mov.getActors().contains(act))) {
                    ok = 0;
                }
            }

            for (String gen : genres) {
                if (!(mov.getGenres().contains(gen))) {
                    ok = 0;
                }
            }

            // if yes, add it to the list
            if (ok == 1) {
                contains.add(mov);
            }
        }

        return contains;
    }

    /**
     * @param page
     */
    public void visit(final MoviesPage page) {

        // if action is of type search
        if (action.getFeature().equals("search")) {
            ArrayList<Movie> searched = search();
            OutputMessage message = new OutputMessage();

            message.setCurrentMoviesList(searched);
            message.setCurrentUser(input.getCurrentUser());
            addToOutput(output, message);

        // if action is of type filter
        } else if (action.getFeature().equals("filter")) {
            Filter filter = action.getFilters();
            User user = input.getCurrentUser();

            // if the movies should be sorted
            if (filter.getSort() != null) {
                ArrayList<Movie> sorted = sort();
                OutputMessage message = new OutputMessage();

                user.getMoviesOnScreen().removeAll(input.getCurrentUser().getMoviesOnScreen());
                user.getMoviesOnScreen().addAll(sorted);

                message.setCurrentMoviesList(sorted);
                message.setCurrentUser(user);
                addToOutput(output, message);

            // if the user is searching for specific actors/genres
            } else if (filter.getContains() != null) {
                ArrayList<Movie> contain = contains();
                OutputMessage message = new OutputMessage();

                user.getMoviesOnScreen().removeAll(input.getCurrentUser().getMoviesOnScreen());
                user.getMoviesOnScreen().addAll(contain);

                message.setCurrentMoviesList(contain);
                message.setCurrentUser(user);
                addToOutput(output, message);
            }

        // if the user tries any other action => error
        } else {
            addError(output);
        }

    }

    /**
     * @param page
     */
    public void visit(final SeeDetails page) {
        User user = input.getCurrentUser();
        Movie movie = page.getMovie().get(0);
        OutputMessage message = new OutputMessage();
        message.setCurrentUser(user);
        message.setCurrentMoviesList(page.getMovie());

        if (action.getFeature().equals("purchase")) {
            // verify if the movie is already purchased
            if (user.getPurchasedMovies().contains(movie)) {
                addError(output);
                return;
            }

            // verify if account is premium
            if (user.getCredentials().getAccountType().equals("premium")) {

                // if yes, verify if there are any free movies left
                if (user.getNumFreePremiumMovies() > 0) {
                    // if yes, decrease the count of free movies and add the movie to purchased
                    user.setNumFreePremiumMovies(user.getNumFreePremiumMovies() - 1);
                    user.getPurchasedMovies().add(movie);
                    addToOutput(output, message);
                    return;
                }
            }

            int tokens = user.getTokensCount();

            // verify if there are enough tokens
            if (tokens < Constants.MOVIE_PRICE) {
                addError(output);
                return;
            }
            // decrease the tokens and add the movie to purchased
            tokens = tokens - Constants.MOVIE_PRICE;
            user.setTokensCount(tokens);
            user.getPurchasedMovies().add(movie);
            addToOutput(output, message);
            return;


        } else if (action.getFeature().equals("watch")) {
            // verify if the movie is purchased
            if (!(user.getPurchasedMovies().contains(movie))) {
                addError(output);
                return;
            }

            // add the movie to watched list
            user.getWatchedMovies().add(movie);
            addToOutput(output, message);
            return;

        }

        if (action.getFeature().equals("like")) {
            // verify if the movie is watched
            if (!(user.getWatchedMovies().contains(movie))) {
                addError(output);
                return;
            }

            // verify if the user gave a like already
            if (user.getLikedMovies().contains(movie)) {
                return;
            }

            // add the movie to liked movies and increase the number of likes for the movie
            user.getLikedMovies().add(movie);
            movie.setNumLikes(movie.getNumLikes() + 1);
            addToOutput(output, message);
            return;


        }

        if (action.getFeature().equals("rate")) {
            // verify if the movie is watched
            if (!(user.getWatchedMovies().contains(movie))) {
                addError(output);
                return;
            }

            // verify if the rating is a valid number
            if (action.getRate() < 0 || action.getRate() > Constants.MAX_RATE) {
                addError(output);
                return;
            }

            // verify if the user has already rated the movie
            if (user.getRatedMovies().contains(movie)) {
                return;
            }

            // add it to rated movies list
            user.getRatedMovies().add(movie);

            // increase the num ratings for the movie and recalculate the rating
            movie.setNumRatings(movie.getNumRatings() + 1);
            movie.setRatingSum(movie.getRatingSum() + action.getRate());
            movie.setRating(movie.getRatingSum() / movie.getNumRatings());
            addToOutput(output, message);
            return;


        }
        addError(output);
    }

    /**
     * @param page
     */
    public void visit(final Upgrades page) {

        if (action.getFeature().equals("buy tokens")) {
            int balance = Integer.parseInt(input.getCurrentUser().getCredentials().getBalance());
            int tokens = input.getCurrentUser().getTokensCount();

            // verify if there is enough balance
            if (balance < action.getCount()) {
                addError(output);
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
                addError(output);
                return;
            }

            // sets the account to premium and decreases the amount of tokens
            tokens = tokens - Constants.PREMIUM_PRICE;
            input.getCurrentUser().setTokensCount(tokens);
            input.getCurrentUser().getCredentials().setAccountType("premium");

        } else {
            addError(output);
        }
    }

    /**
     * @param page
     */
    public void visit(final Logout page) {
        // there are no on page actions for this page
        addError(output);
    }
}
