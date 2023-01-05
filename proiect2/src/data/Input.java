package data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import page.Page;

import java.util.ArrayList;

public class Input {

    @JsonIgnore
    private Page currentPage;

    @JsonIgnore
    private User currentUser;

    private ArrayList<User> users;
    private ArrayList<Movie> movies;
    private ArrayList<ActionInput> actions;

    public final ArrayList<User> getUsers() {
        return users;
    }

    public final void setUsers(final ArrayList<User> users) {
        this.users = users;
    }

    public final ArrayList<Movie> getMovies() {
        return movies;
    }

    public final void setMovies(final ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public final ArrayList<ActionInput> getActions() {
        return actions;
    }

    public final void setActions(final ArrayList<ActionInput> actions) {
        this.actions = actions;
    }

    public final Page getCurrentPage() {
        return currentPage;
    }

    public final void setCurrentPage(final Page currentPage) {
        this.currentPage = currentPage;
    }

    public final User getCurrentUser() {
        return currentUser;
    }

    public final void setCurrentUser(final User currentUser) {
        this.currentUser = currentUser;
    }

}
