package data;

public class Filter {
    private Sort sort;
    private Contains contains;


    public final Sort getSort() {
        return sort;
    }

    public final void setSort(final Sort sort) {
        this.sort = sort;
    }

    public final Contains getContains() {
        return contains;
    }

    public final void setContains(final Contains contains) {
        this.contains = contains;
    }
}
