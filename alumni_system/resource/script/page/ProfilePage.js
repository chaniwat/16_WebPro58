import FormUtils from "./../ultility/FormUtils";
import DateSelectionBuilder from "./../ultility/DateSelectionBuilder";

export default class ProfilePage {

    constructor(contextURL) {
        this.contextURL = contextURL;
        this.usertype = $("#profilepage-usertype").val();
        
        if(this.usertype == "ALUMNI") {
            this.profileform = $("#alumni-form");
            this.profileformbtn = this.profileform.find("button#alumni-form-btn");

            new DateSelectionBuilder(this.profileform.find("#alumni-form-birthdate-year"), this.profileform.find("#alumni-form-birthdate-month"), this.profileform.find("#alumni-form-birthdate-day"));

            this.profileform.find("#alumni-form-jobtype").change(this, this.jobTypeChange);
            this.profileform.find("#alumni-form-jobname").change(this, this.jobChange);

            FormUtils.setValToDefault(this.profileform.find("#alumni-form-province"));
            FormUtils.setValToDefault(this.profileform.find("#alumni-form-jobtype"));
            this.profileform.find("#alumni-form-jobtype").trigger("change", () => {
                FormUtils.setValToDefault(this.profileform.find("#alumni-form-jobname"));
            });
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
    
    jobTypeChange(e, callback) {
        let o = e.data;
        let jobtype_id = o.profileform.find("#alumni-form-jobtype").val();
        if(jobtype_id > 0) {
            o.profileform.find("#alumni-form-jobtypeother").attr("readonly", true);
            o.profileform.find("#alumni-form-jobtypeother").val("");
            o.profileform.find("#alumni-form-jobname").removeAttr("readonly");
            o.profileform.find("#alumni-form-jobname").val("null");
            o.profileform.find("#alumni-form-jobnameother").val("");
            o.profileform.find("#alumni-form-jobnameother").attr("readonly", true);
            
            $.get(o.contextURL + "/ajax/job?jobtype_id=" + jobtype_id, (data) => {
                let insideElem = "<option value=\"null\">โปรดเลือก</option>\n";
                $.each(data, (i, data) => { insideElem += "<option value=\"" + data.id + "\">" + data.name_th + "</option>"; });
                insideElem += "<option value=\"0\">อื่นๆ</option>";
                o.profileform.find("#alumni-form-jobname").html(insideElem);
                
                if(callback) callback();
            }, "json");
        } else if(jobtype_id == 0) {
            o.profileform.find("#alumni-form-jobtypeother").removeAttr("readonly");
            o.profileform.find("#alumni-form-jobname").attr("readonly", true);
            o.profileform.find("#alumni-form-jobname").val("0");
            o.profileform.find("#alumni-form-jobnameother").removeAttr("readonly");
        } else {
            o.profileform.find("#alumni-form-jobtypeother").attr("readonly", true);
            o.profileform.find("#alumni-form-jobtypeother").val("");
            o.profileform.find("#alumni-form-jobname").attr("readonly", true);
            o.profileform.find("#alumni-form-jobname").val("null");
            o.profileform.find("#alumni-form-jobnameother").val("");
            o.profileform.find("#alumni-form-jobnameother").attr("readonly", true);
        }
    }

    jobChange(e) {
        let o = e.data;
        let job_id = o.profileform.find("#alumni-form-jobname").val();
        if(job_id == "null" || job_id > 0) {
            o.profileform.find("#alumni-form-jobnameother").val("");
            o.profileform.find("#alumni-form-jobnameother").attr("readonly", true);
        } else if(job_id == 0) {
            o.profileform.find("#alumni-form-jobnameother").removeAttr("readonly");
        }
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
