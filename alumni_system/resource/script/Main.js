import Route from "./Route";
import LoginPage from "./page/LoginPage";
import ProfilePage from "./page/ProfilePage";
import AlumniTrackPage from "./page/AlumniTrackPage";
import ViewDataPage from "./page/ViewDataPage";
import NewAlumni from "./page/admin/NewAlumni";
import NewUser from "./page/admin/NewUser";
import SurveyFormCreate from "./page/admin/SurveyFormCreate";

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

        route.doRoute(["profile/*", "admin/profile/*"], () => {
            new ProfilePage(contextURL);
        });

        route.doRoute(["alumni/*", "admin/alumni/*", "admin/user/*", "admin/survey/*"], () => {
            new ViewDataPage();
        });

        route.doRoute(["admin/event/all"], () => {
            let alumnitable = $("#alumni-table");
            let searchform = $("#table-searchform");

            if(alumnitable.length > 0) {
                let datatable = alumnitable.DataTable({
                    "order": [[ 0, "desc" ]]
                });

                let searchtype = searchform.find("#table-searchtype");
                let searchinput = searchform.find("#table-searchinput");

                searchinput.keyup(() => {
                    datatable.columns(searchtype.val()).search(searchinput.val()).draw();
                });

                searchtype.change(() => {
                    searchinput.trigger('keyup');
                });

                $("#alumni-table_filter").html(searchform);
            }
        });

        route.doRoute(["admin/user/add"], () => {
            new NewUser();
        });
        
        route.doRoute(["track/edit/*", "admin/track/edit/*"], () => {
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

        route.doRoute("admin/alumni/add", () => {
            new NewAlumni(contextURL);
        });

        route.doRoute("admin/event/create", function() {
            $(".textarea").wysihtml5();
        });

        route.doRoute("admin/survey/create", function() {
            new SurveyFormCreate();
        });

        route.execute();
    }

}

$(document).ready(() => {
    new Main();
});
