package page;

public class PageFactory {
    /**
     * creates a new page based on the type given
     * @param pageType
     * @return
     */
    public Page createPage(final String pageType) {
        switch (pageType) {
            case "login": return new Login();
            case "register": return new Register();
            case "logout": return new Logout();
            case "see details": return new SeeDetails();
            case "upgrades": return new Upgrades();
            case "movies": return new MoviesPage();
            case "homepage authenticated": return new HomepageAuth();
            case "homepage neauthenticated": return new HomepageN();
            default: return null;
        }
    }
}
