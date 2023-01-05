package data;

public class ActionInput {
    private String type;
    private String page;
    private String movie;
    private String feature;
    private Credentials credentials;
    private String startsWith;
    private Filter filters;
    private int count;
    private double rate;

    private Movie addedMovie;
    private String deletedMovie;

    private String subscribedGenre;

    public final String getType() {
        return type;
    }

    public final void setType(final String type) {
        this.type = type;
    }

    public final String getPage() {
        return page;
    }

    public final void setPage(final String page) {
        this.page = page;
    }

    public final String getMovie() {
        return movie;
    }

    public final void setMovie(final String movie) {
        this.movie = movie;
    }

    public final String getFeature() {
        return feature;
    }

    public final void setFeature(final String feature) {
        this.feature = feature;
    }

    public final Credentials getCredentials() {
        return credentials;
    }

    public final void setCredentials(final Credentials creds) {
        this.credentials = creds;
    }

    public final String getStartsWith() {
        return startsWith;
    }

    public final void setStartsWith(final String startsWith) {
        this.startsWith = startsWith;
    }

    public final Filter getFilters() {
        return filters;
    }

    public final void setFilters(final Filter filters) {
        this.filters = filters;
    }

    public final int getCount() {
        return count;
    }

    public final void setCount(final int count) {
        this.count = count;
    }

    public final double getRate() {
        return rate;
    }

    public final void setRate(final double rate) {
        this.rate = rate;
    }

    public final Movie getAddedMovie() {
        return addedMovie;
    }

    public final void setAddedMovie(final Movie addedMovie) {
        this.addedMovie = addedMovie;
    }

    public final String getDeletedMovie() {
        return deletedMovie;
    }

    public final void setDeletedMovie(final String deletedMovie) {
        this.deletedMovie = deletedMovie;
    }

    public final String getSubscribedGenre() {
        return subscribedGenre;
    }

    public final void setSubscribedGenre(final String subscribedGenre) {
        this.subscribedGenre = subscribedGenre;
    }
}
