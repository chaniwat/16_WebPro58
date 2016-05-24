import FormUtils from "./../ultility/FormUtils";
import DateSelectionBuilder from "./../ultility/DateSelectionBuilder";

export default class ProfilePage {

    constructor() {
        this.usertype = $("#profilepage-usertype").val();
        //
        if(this.usertype == "ALUMNI") {
            this.profileform = $("#alumni-form");
            this.profileformbtn = this.profileform.find("button#alumni-form-btn");

            new DateSelectionBuilder(this.profileform.find("#alumni-form-birthdate-year"), this.profileform.find("#alumni-form-birthdate-month"), this.profileform.find("#alumni-form-birthdate-day"));
            FormUtils.setValToDefault(this.profileform.find("#alumni-form-province"));
        } else if(this.usertype == "TEACHER") {
            this.profileform = $("#teacher-form");
            this.profileformbtn = this.profileform.find("button#teacher-form-btn");
        
            FormUtils.setValToDefault(this.profileform.find("#teacher-form-workstatus"));
        } else if(this.usertype == "STAFF") {
            this.profileform = $("#staff-form");
            this.profileformbtn = this.profileform.find("button#staff-form-btn");
        
            FormUtils.setValToDefault(this.profileform.find("#staff-form-worksection"));
        }
        
        this.profileformstate = "VIEW";
        this.profileformbtn.click(this, this.submitForm);
        FormUtils.bindNotEmptyForm(this.profileform);
        FormUtils.disableAll(this.profileform);
    }

    submitForm(e) {
        var o = e.data;

        if(o.profileformstate == "VIEW") {
            FormUtils.enableAllWithException(o.profileform);

            o.profileformstate = "EDIT";
            o.profileformbtn.attr("type", "submit");
            o.profileformbtn.removeClass("btn-primary").addClass("btn-success");
            o.profileformbtn.html("บันทึก");

            e.preventDefault();
        } else if(o.profileformstate == "EDIT") {
            if(!FormUtils.isFormComplete(o.profileform)) {
                e.preventDefault();
                if($("span.submit-alert").length <= 0) {
                    $("<span class=\"submit-alert text-danger\">โปรดกรอกข้อมูลที่ต้องการให้ครบ</span>").insertAfter(o.profileformbtn);
                }
            }
        }
    }

}
