import Route from "./Route";
import LoginPage from "./page/LoginPage";
import ProfilePage from "./page/ProfilePage";
import AlumniTrackPage from "./page/AlumniTrackPage";
import ViewAlumniPage from "./page/ViewAlumniPage";

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
            new ProfilePage(contextURL);
        });

        route.doRoute(["alumni/*", "admin/alumni/*"], () => {
            new ViewAlumniPage();
        });
        
        route.doRoute("track/edit/*", () => {
            new AlumniTrackPage(contextURL);
        });

        route.doRoute("admin/", () => {
            let flag = true;

            try {
                if(window.data == null) {
                    flag = false;
                }
            } catch (ReferenceError) {
                flag = false;
            }

            if(!flag) {
                window.data = {
                    labels: ["ปริญญาตรี", "ปริญญาโท", "ปริญญาเอก"],
                    datasets: [
                        {
                            label: "จำนวนศิษย์เก่า",
                            backgroundColor: "rgba(16, 171, 234, 1)",
                            borderWidth: 0,
                            hoverBackgroundColor: "rgba(89, 211, 234, 1)",
                            data: [0, 0, 0],
                            yAxisID: "y-axis-0"
                        }
                    ]
                };
            }
            
            new Chart($("#alumniChart").get(0).getContext("2d"), {
                type: 'bar',
                data: window.data,
                options: {}
            });
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
