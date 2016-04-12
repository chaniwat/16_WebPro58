import FormUtils from "./../ultility/FormUtils";
import DateSelectionBuilder from "./../ultility/DateSelectionBuilder";

export default class {

    constructor() {
        this.usertype = $("#profilepage-usertype").val();

        if(this.usertype == "ALUMNI") {
            this.profileform = $("#alumni-form");
            this.profileformbtn = this.profileform.find("button#alumni-form-btn");
            this.profileformbtn.click(this, this.changeAlumniFormState);
            this.profileformstate = "VIEW";

            var dateSelection = new DateSelectionBuilder(
                this.profileform.find("#alumni-form-birthdate-year"),
                this.profileform.find("#alumni-form-birthdate-month"),
                this.profileform.find("#alumni-form-birthdate-day")
            );

            FormUtils.setValToDefault(this.profileform.find("#alumni-form-province"));

            FormUtils.disableAll(this.profileform);
        } else if(this.usertype == "TEACHER") {
            this.profileform = $("#teacher-form");
            this.profileformbtn = this.profileform.find("button#teacher-form-btn");
            this.profileformbtn.click(this, this.changeAlumniFormState);
            this.profileformstate = "VIEW";

            FormUtils.setValToDefault(this.profileform.find("#teacher-form-workstatus"));

            FormUtils.disableAll(this.profileform);
        }
    }

    changeAlumniFormState(e) {
        var o = e.data;

        if(o.profileformstate == "VIEW") {
            FormUtils.enableAllWithException(o.profileform);

            o.profileformstate = "EDIT";
            o.profileformbtn.attr("type", "submit");
            o.profileformbtn.removeClass("btn-primary").addClass("btn-success");
            o.profileformbtn.html("บันทึก");

            e.preventDefault();
        }
    }

}
