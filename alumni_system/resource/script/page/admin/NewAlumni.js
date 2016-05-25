import FormUtils from "../../ultility/FormUtils";
import DateSelectionBuilder from "../../ultility/DateSelectionBuilder";

export default class NewAlumni {
    
    constructor(contextURL) {
        this.contextURL = contextURL;

        this.alumnifindform = $("#alumni-find-form");
        this.alumninewform = $("#alumni-form");
        this.alumnitrackform = $("#alumni-track-form");

        if(this.alumnifindform.length > 0) {
            FormUtils.bindNotEmptyForm(this.alumnifindform);
            
            this.alumnifindform.submit(this, (e) => {
                let o = e.data;
                if(!FormUtils.isFormComplete(o.alumnifindform)) {
                    e.preventDefault();
                    if($("span.submit-alert").length <= 0) {
                        $("<span class=\"submit-alert text-danger\">โปรดกรอกข้อมูลที่ต้องการให้ครบ</span>").insertAfter(o.alumnifindform.find("#alumni-find-form-btn"));
                    }
                }
            });
        } else if(this.alumninewform.length > 0) {
            this.doAlumniNewForm();

        } else if(this.alumnitrackform.length > 0) {
            this.doAlumniTrackForm();
        }
    }

    doAlumniNewForm() {
        new DateSelectionBuilder(this.alumninewform.find("#alumni-form-birthdate-year"), this.alumninewform.find("#alumni-form-birthdate-month"), this.alumninewform.find("#alumni-form-birthdate-day"));
        
        this.alumninewform.find("#alumni-form-jobtype").change(this, this.jobTypeChange);
        this.alumninewform.find("#alumni-form-jobname").change(this, this.jobChange);
        
        FormUtils.setValToDefault(this.alumninewform.find("#alumni-form-jobtype"));
        this.alumninewform.find("#alumni-form-jobtype").trigger("change", () => {
            FormUtils.setValToDefault(this.alumninewform.find("#alumni-form-jobname"));
        });
    }

    jobTypeChange(e, callback) {
        let o = e.data;
        let jobtype_id = o.alumninewform.find("#alumni-form-jobtype").val();
        if(jobtype_id > 0) {
            o.alumninewform.find("#alumni-form-jobtypeother").attr("readonly", true);
            o.alumninewform.find("#alumni-form-jobtypeother").val("");
            o.alumninewform.find("#alumni-form-jobname").removeAttr("readonly");
            o.alumninewform.find("#alumni-form-jobname").val("null");
            o.alumninewform.find("#alumni-form-jobnameother").val("");
            o.alumninewform.find("#alumni-form-jobnameother").attr("readonly", true);

            $.get(o.contextURL + "/ajax/job?jobtype_id=" + jobtype_id, (data) => {
                let insideElem = "<option value=\"null\">โปรดเลือก</option>\n";
                $.each(data, (i, data) => { insideElem += "<option value=\"" + data.id + "\">" + data.name_th + "</option>"; });
                insideElem += "<option value=\"0\">อื่นๆ</option>";
                o.alumninewform.find("#alumni-form-jobname").html(insideElem);

                if(callback) callback();
            }, "json");
        } else if(jobtype_id == 0) {
            o.alumninewform.find("#alumni-form-jobtypeother").removeAttr("readonly");
            o.alumninewform.find("#alumni-form-jobname").attr("readonly", true);
            o.alumninewform.find("#alumni-form-jobname").val("0");
            o.alumninewform.find("#alumni-form-jobnameother").removeAttr("readonly");
        } else {
            o.alumninewform.find("#alumni-form-jobtypeother").attr("readonly", true);
            o.alumninewform.find("#alumni-form-jobtypeother").val("");
            o.alumninewform.find("#alumni-form-jobname").attr("readonly", true);
            o.alumninewform.find("#alumni-form-jobname").val("null");
            o.alumninewform.find("#alumni-form-jobnameother").val("");
            o.alumninewform.find("#alumni-form-jobnameother").attr("readonly", true);
        }
    }

    jobChange(e) {
        let o = e.data;
        let job_id = o.alumninewform.find("#alumni-form-jobname").val();
        if(job_id == "null" || job_id > 0) {
            o.alumninewform.find("#alumni-form-jobnameother").val("");
            o.alumninewform.find("#alumni-form-jobnameother").attr("readonly", true);
        } else if(job_id == 0) {
            o.alumninewform.find("#alumni-form-jobnameother").removeAttr("readonly");
        }
    }

    doAlumniTrackForm() {
        this.select = {
            degreeElem: $("#alumni-track-degree"),
            curriculumElem: $("#alumni-track-curriculumid"),
            trackElem: $("#alumni-track-trackid")
        };

        this.select.curriculumElem.attr("disabled", true);
        this.select.trackElem.attr("disabled", true);

        this.select.degreeElem.change(this, (e) => {
            let o = e.data;
            if(o.select.degreeElem.val() != "") {
                $.get(o.contextURL + "/ajax/curriculum?degree=" + o.select.degreeElem.val(), (data) => {
                    let insideElem = "<option value=\"\">โปรดเลือก</option>\n";
                    $.each(data, (i, data) => { insideElem += "<option value=\"" + data.id + "\">" + data.cname_th + " " + data.sname_th + "(ปี " + data.cyear + ")</option>"; });
                    o.select.curriculumElem.html(insideElem);
                    o.select.curriculumElem.removeAttr("disabled");
                    o.select.curriculumElem.val("");
                    o.select.trackElem.attr("disabled", true);
                    o.select.trackElem.val("");
                }, "json");
            } else {
                let insideElem = "<option value=\"\">โปรดเลือก</option>\n";
                o.select.curriculumElem.html(insideElem);
                o.select.trackElem.html(insideElem);

                o.select.curriculumElem.attr("disabled", true);
                o.select.curriculumElem.val("");
                o.select.trackElem.attr("disabled", true);
                o.select.trackElem.val("");
            }
        });

        this.select.curriculumElem.change(this, (e) => {
            let o = e.data;
            if(o.select.curriculumElem.val() != "") {
                $.get(o.contextURL + "/ajax/track?curriculum_id=" + o.select.curriculumElem.val(), (data) => {
                    let insideElem = "<option value=\"\">โปรดเลือก</option>\n";
                    $.each(data, (i, data) => { insideElem += "<option value=\"" + data.id + "\">" + data.name_th + "</option>"; });
                    o.select.trackElem.html(insideElem);
                    o.select.trackElem.removeAttr("disabled");
                    o.select.trackElem.val("");
                }, "json");
            } else {
                let insideElem = "<option value=\"\">โปรดเลือก</option>\n";
                o.select.trackElem.html(insideElem);

                o.select.trackElem.attr("disabled", true);
                o.select.trackElem.val("");
            }
        });

        FormUtils.bindNotEmptyForm(this.alumnitrackform);

        this.alumnitrackform.submit(this, (e) => {
            let o = e.data;
            if(!FormUtils.isFormComplete(o.alumnitrackform)) {
                e.preventDefault();
                if($("span.submit-alert").length <= 0) {
                    $("<span class=\"submit-alert text-danger\">โปรดกรอกข้อมูลที่ต้องการให้ครบ</span>").insertAfter(o.alumnitrackform.find("#alumni-track-form-btn"));
                }
            }
        });
    }
}
