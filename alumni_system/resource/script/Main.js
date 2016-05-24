import Route from "./Route";
import LoginPage from "./page/LoginPage";
import ProfilePage from "./page/ProfilePage";

class Main {

    constructor() {
        let sitedataElem = $("sitedata");

        this.route(sitedataElem.attr("contextPath"));

        sitedataElem.remove();
    }

    route(contextURL) {
        var route = new Route(contextURL);

        route.doRoute("login/", () => {
            new LoginPage();
        });

        route.doRoute(["profile/*"], () => {
            new ProfilePage();
        });

        route.doRoute(["alumni/*"], () => {
            this.alumnitable = $("#alumni-table");

            if(this.alumnitable.length > 0) {
                this.alumnitable.DataTable();
            }
        });

        route.doRoute("admin/", () => {
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

        route.doRoute("admin/alumni/*", () => {
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

$(document).ready(() => {
    new Main();
});
