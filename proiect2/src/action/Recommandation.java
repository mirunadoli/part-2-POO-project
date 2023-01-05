package action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import data.Input;
import data.Movie;
import data.Notification;
import data.User;
import util.OutputMessage;

import java.util.*;

public class Recommandation {

    private Input input;
    private ArrayNode output;

    public Recommandation(final Input input, final ArrayNode output) {
        this.input = input;
        this.output = output;
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
     *
     */
    public void recommend() {

        // if no recommandation is needed
        if (input.getCurrentUser() == null
                || input.getCurrentUser().getCredentials().getAccountType().equals("standard")) {
            return;
        }

        OutputMessage message = new OutputMessage();
        HashMap<String, Integer> likedGen = new HashMap<>();
        User user = input.getCurrentUser();
        Notification notif = new Notification();
        int like = 1;

        if (user.getLikedMovies().isEmpty()) {
            notif.setMovieName("No recommendation");
            notif.setMessage("Recommendation");
            user.getNotifications().add(notif);
            message.setCurrentUser(user);
            message.setCurrentMoviesList(null);
            message.addToOutput(output);
        }

        for (Movie movie : user.getLikedMovies()) {
            for (String genre : movie.getGenres()) {
                if (!likedGen.containsKey(genre)) {
                    likedGen.put(genre, like);
                } else {
                    likedGen.replace(genre, likedGen.get(genre) + 1);
                }
            }
        }

        // sort genres by number of likes
        LinkedList<Map.Entry<String, Integer>> genresToSort = new LinkedList<>(likedGen.entrySet());
        Collections.sort(genresToSort, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(final Map.Entry<String, Integer> o1, final Map.Entry<String, Integer> o2) {
                if (o1.getValue().equals(o2.getValue())) {
                    return o1.getKey().compareTo(o2.getKey());
                }
                return o2.getValue() - o1.getValue();
            }
        });

        // sort movies by number of likes
        ArrayList<Movie> database = input.getCurrentUser().getAvailableMovies();
        Collections.sort(database, (o1, o2) -> o2.getNumLikes() - o1.getNumLikes());

        for (Map.Entry<String, Integer> genre : genresToSort) {
            for (Movie movie : database) {
                if (movie.getGenres().contains(genre.getKey())
                        && !(user.getWatchedMovies().contains(movie))) {
                    notif.setMovieName(movie.getName());
                    notif.setMessage("Recommendation");
                    user.getNotifications().add(notif);
                    message.setCurrentUser(user);
                    message.setCurrentMoviesList(null);
                    message.addToOutput(output);
                    return;
                }
            }
        }
    }
}
