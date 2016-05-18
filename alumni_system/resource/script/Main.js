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

                        // The properties below allow an array to be specified to change the value of the item at the given index
                        // String or array - the bar color
                        backgroundColor: "rgba(16, 171, 234, 1)",

                        // String or array - bar stroke color
                        //borderColor: "rgba(220,220,220,1)",

                        // Number or array - bar border width
                        borderWidth: 0,

                        // String or array - fill color when hovered
                        hoverBackgroundColor: "rgba(89, 211, 234, 1)",

                        // String or array - border color when hovered
                        //hoverBorderColor: "rgba(220,220,220,1)",

                        // The actual data
                        data: [881, 0, 0],

                        // String - If specified, binds the dataset to a certain y-axis. If not specified, the first y-axis is used.
                        yAxisID: "y-axis-0",
                    }
                    //,
                    //{
                    //    label: "My Second dataset",
                    //    backgroundColor: "rgba(220,220,220,0.2)",
                    //    borderColor: "rgba(220,220,220,1)",
                    //    borderWidth: 1,
                    //    hoverBackgroundColor: "rgba(220,220,220,0.2)",
                    //    hoverBorderColor: "rgba(220,220,220,1)",
                    //    data: [28, 48, 40, 19, 86, 27, 90]
                    //}
                ]
            };

            var ctx = $("#alumniChart").get(0).getContext("2d");
            var alumniNewChart = new Chart(ctx, {
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