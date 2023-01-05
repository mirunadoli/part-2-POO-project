package util;

import data.Movie;
import data.Notification;

import java.util.ArrayList;

public final class DeepCopy {
    /**
     *  used for creating the output
     *  makes a deep copy of an arraylist of movies
     * @param list
     * @return
     */
    public static ArrayList<String> deepCopyString(final ArrayList<String> list) {
        ArrayList<String> copy = new ArrayList<>();
        copy.addAll(list);
        return copy;
    }

    /**
     * used for creating the output
     * makes a deep copy of an arraylist of movies
     * @param list
     * @return
     */
    public static ArrayList<Movie> deepCopy(final ArrayList<Movie> list) {
        ArrayList<Movie> copy = new ArrayList<>();
        for (Movie movie : list) {
            copy.add(new Movie(movie));
        }
        return copy;
    }

    public static ArrayList<Notification> deepCopyNotif(final ArrayList<Notification> list) {
        ArrayList<Notification> copy = new ArrayList<>();
        for (Notification notif : list) {
            copy.add(new Notification(notif));
        }
        return copy;
    }
}
