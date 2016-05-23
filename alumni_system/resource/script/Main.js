import Route from "./Route";
import LoginPage from "./page/LoginPage";
import ProfilePage from "./page/ProfilePage";
import AlumniViewPage from "./page/AlumniViewPage";

class Main {

    constructor() {
        let sitedataElem = $("sitedata");

        this.route(sitedataElem.attr("contextPath"));

        sitedataElem.remove();
    }

    route(contextURL) {
        var route = new Route(contextURL);

        route.doRoute("login/", function() {
            new LoginPage();
        });

        route.doRoute(["profile/*"], function() {
            new ProfilePage();
        });

        route.doRoute(["alumni/*"], function() {
            new AlumniViewPage();
        });

        route.doRoute("admin/", function() {
            var data = {
                labels: ["ปริญญาตรี", "ปริญญาโท", "ปริญญาเอก"],
                datasets: [
                    {
                        label: "จำนวนศิษย์เก่า",
                        backgroundColor: "rgba(16, 171, 234, 1)",
                        borderWidth: 0,
                        hoverBackgroundColor: "rgba(89, 211, 234, 1)",
                        data: [881, 0, 0],
                        yAxisID: "y-axis-0"
                    }
                ]
            };
            
            new Chart($("#alumniChart").get(0).getContext("2d"), {
                type: 'bar',
                data: data,
                options: {}
            });
        });

        route.doRoute("admin/alumni/*", function() {
            this.alumnitable = $("#alumni-table");

            if(this.alumnitable.length > 0) {
                this.alumnitable.DataTable();
            }
        });

        route.doRoute("admin/event/create", function() {
            $(".textarea").wysihtml5();
        });

        route.execute();
    }

}

new Main();