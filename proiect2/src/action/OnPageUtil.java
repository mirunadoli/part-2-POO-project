package action;

import data.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public final class OnPageUtil {

    private OnPageUtil() {

    }

    /**
     * creates a list with the available movies for the current user
     */
    public static void createAvailableMovies(final Input input) {

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
     * searches for a movie that starts with the string given in action
     * @return
     */
    public static ArrayList<Movie> search(final Input input, final ActionInput action) {
        ArrayList<Movie> searched = new ArrayList<>();
        for (Movie movie : input.getCurrentPage().getMoviesOnScreen()) {
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
    public static int compareDuration(final Movie o1, final Movie o2, final String sort) {
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
    public static int compareRating(final Movie o1, final Movie o2, final String sort) {
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
    public static ArrayList<Movie> sort(final Input input, final ActionInput action) {
        // creates the list
        ArrayList<Movie> sorted = new ArrayList<>();
        sorted.addAll(input.getCurrentPage().getMoviesOnScreen());
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
    public static ArrayList<Movie> contains(final Input input, final ActionInput action) {

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

}
