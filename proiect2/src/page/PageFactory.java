package page;

public class PageFactory {
    public Page createPage(String pageType) {
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
