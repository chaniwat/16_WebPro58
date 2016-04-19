import Route from "./Route";
import LoginPage from "./page/LoginPage";
import ProfilePage from "./page/ProfilePage";
import AlumniViewPage from "./page/AlumniViewPage";
import FormBuilderPage from "./page/admin/survey/FormBuilderPage";

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

        route.doRoute("admin/survey/create", function() {
            new FormBuilderPage(contextPath);
        });

        route.execute();
    }

}

new Main();