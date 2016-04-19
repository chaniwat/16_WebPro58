import Route from "./Route";
import LoginPage from "./page/LoginPage";
import ProfilePage from "./page/ProfilePage";
import AlumniViewPage from "./page/AlumniViewPage";

class Main {

    constructor() {
        var siteDataContextElem = $("sitedata");
        var contextPath = siteDataContextElem.attr("contextPath");

        this.route(contextPath);

        siteDataContextElem.remove();
    }

    route(contextPath) {
        var route = new Route(contextPath);

        route.doRoute("login/", function() {
            new LoginPage();
        });

        route.doRoute(["profile/*"], function() {
            new ProfilePage();
        });

        route.doRoute(["alumni/*"], function() {
            new AlumniViewPage();
        });

        route.execute();
    }

}

new Main();