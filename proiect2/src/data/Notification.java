package data;

public class Notification {
    private String movieName;
    private String message;

    public Notification() {
    }

    public Notification(final Notification notif) {
        this.message = notif.message;
        this.movieName = notif.movieName;
    }

    public final String getMovieName() {
        return movieName;
    }

    public final void setMovieName(final String movieName) {
        this.movieName = movieName;
    }

    public final String getMessage() {
        return message;
    }

    public final void setMessage(final String message) {
        this.message = message;
    }
}
